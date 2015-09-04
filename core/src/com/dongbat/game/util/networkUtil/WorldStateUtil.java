/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util.networkUtil;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.EntityEdit;
import com.artemis.World;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.dongbat.game.component.Physics;
import com.dongbat.game.component.Player;
import com.dongbat.game.dataobject.CustomInput;
import com.dongbat.game.system.Box2dSystem;
import com.dongbat.game.util.ECSUtil;
import static com.dongbat.game.util.ECSUtil.getAllComponents;
import static com.dongbat.game.util.ECSUtil.getFrame;
import static com.dongbat.game.util.ECSUtil.getRandom;
import static com.dongbat.game.util.ECSUtil.getWorldProgress;
import static com.dongbat.game.util.ECSUtil.normalProcess;
import com.dongbat.game.util.EntityUtil;
import static com.dongbat.game.util.EntityUtil.getAllEntities;
import com.dongbat.game.util.PhysicsUtil;
import static com.dongbat.game.util.UuidUtil.getEntityByUuid;
import static com.dongbat.game.util.UuidUtil.getUuid;
import static com.dongbat.game.util.UuidUtil.setUuid;
import com.dongbat.game.util.WorldQueryUtil;
import com.dongbat.game.util.box2d.serializer.Box2dState;
import com.dongbat.game.util.msg.NetworkInput;
import com.dongbat.game.util.msg.WorldState;
import static com.dongbat.game.util.networkUtil.KryoUtil.getKryo;
import com.dongbat.game.util.object.CustomWorld;
import com.dongbat.game.util.object.EntityState;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import java.io.ByteArrayInputStream;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class WorldStateUtil {

  public static WorldState copy(WorldState state) {
    return getKryo().copy(state);
  }

  public static byte[] serialize(WorldState state) {
    Output output = new Output(2048, -1);
    getKryo().writeObjectOrNull(output, state, WorldState.class);
    byte[] buffer = output.getBuffer();
    output.close();
    return buffer;
  }

  public static WorldState deserialize(byte[] data) {
    Input input = new Input(new ByteArrayInputStream(data));
    WorldState worldState = getKryo().readObjectOrNull(input, WorldState.class);
    input.close();
    return worldState;
  }

  public static WorldState save(World world) {
    ObjectMap<String, Object> data = new ObjectMap<String, Object>();
    com.badlogic.gdx.physics.box2d.World physicsWorld = PhysicsUtil.getPhysicsWorld(world);
//    data.put("physicsWorld", Box2dJsonUtil.toString(physicsWorld));
    data.put("physicsWorld", Box2dStateUtil.fromWorld(physicsWorld));
    data.put("seed", getRandom(world).getSeed());

    Array<EntityState> entityStates = new Array<EntityState>();
    IntBag entities = getAllEntities(world);
    for (int i = 0; i < entities.size(); i++) {
      int id = entities.get(i);
      Entity entity = world.getEntity(id);
      Array<Component> allComponents = getAllComponents(world, entity);
      EntityState entityState = new EntityState(getUuid(entity), allComponents, new ObjectMap<String, Object>());
      entityStates.add(entityState);
    }

    WorldState worldState = new WorldState(getFrame(world), data, entityStates);
    return WorldStateUtil.copy(worldState);
  }

  public static void load(World w, WorldState state) {
    CustomWorld world = (CustomWorld) w;
    WorldState s = WorldStateUtil.copy(state);
    getWorldProgress(world).setFrame(s.getCurrentFrame());
    long seed = (Long) s.getWorldData().get("seed");
    getRandom(world).setSeed(seed);

    world.setIgnoringSystem(true);

    IntBag allEntities = getAllEntities(world);
    for (int i = 0; i < allEntities.size(); i++) {
      int id = allEntities.get(i);
      world.deleteEntity(id);
    }

    world.process();

    for (EntityState entityState : s.getEntityStates()) {
      Entity e = EntityUtil.createEntity(world);
      setUuid(e, entityState.getUuid());
      EntityEdit edit = e.edit();
      for (Component component : entityState.getComponents()) {
        if (component instanceof Physics) {
          edit.add(new Physics());
        } else {
          edit.add(component);
        }
      }
    }

//    String physicsWorldData = (String) s.getData().get("physicsWorld");
//    com.badlogic.gdx.physics.box2d.World physicsWorld = Box2dJsonUtil.toWorld(physicsWorldData);
    Box2dState box2dState = (Box2dState) s.getWorldData().get("physicsWorld");
    com.badlogic.gdx.physics.box2d.World physicsWorld = Box2dStateUtil.toWorld(box2dState);
    com.badlogic.gdx.physics.box2d.World oldWorld = PhysicsUtil.getPhysicsWorld(world);
    PhysicsUtil.setPhysicsWorld(world, physicsWorld);
    oldWorld.dispose();

    world.process();

    Array<Body> bodies = new Array<Body>();
    physicsWorld.getBodies(bodies);
    for (Body body : bodies) {
      if (body.getUserData() == null) {
        continue;
      }
      UUID id = (UUID) body.getUserData();
      Entity e = getEntityByUuid(world, id);

      EntityUtil.getComponent(world, e, Physics.class).setBody(body);

    }
    world.getSystem(Box2dSystem.class).setUninitialized(true);
    world.process();
    world.setIgnoringSystem(false);

  }

  public static void rewindAndReplay(World world, WorldState baseState, Array<NetworkInput> unprocessedInputs, long targetFrame, float step) {
    load(world, baseState);

    for (NetworkInput networkInput : unprocessedInputs) {
      UUID id = networkInput.getId();
      CustomInput customInput = networkInput.getInput();
      Entity e = getEntityByUuid(world, id);
      EntityUtil.getComponent(world, e, Player.class).getInputs().put(networkInput.getFrameIndex(), customInput);
    }
    while (ECSUtil.getFrame(world) <= targetFrame) {
      normalProcess(world, step);
    }
  }

  public static void load(World world, WorldState baseState, boolean keepOriginal) {
    WorldState state = baseState;
    if (keepOriginal) {
      state = WorldStateUtil.copy(baseState);
    }
    load(world, state);
  }

}
