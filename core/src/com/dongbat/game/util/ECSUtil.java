/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util;

import com.artemis.BaseSystem;
import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.managers.UuidEntityManager;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.dongbat.game.strategy.InvocationStrategy;
import com.dongbat.game.system.AiControlledSystem;
import com.dongbat.game.system.AnimationRenderSystem;
import com.dongbat.game.system.BorderlandSystem;
import com.dongbat.game.system.Box2dSystem;
import com.dongbat.game.system.BuffSystem;
import com.dongbat.game.system.CheckWinningConditionSystem;
import com.dongbat.game.system.ConsumingSystem;
import com.dongbat.game.system.DetectionCleanupSystem;
import com.dongbat.game.system.DetectionSystem;
import com.dongbat.game.system.DisplayUpdateSystem;
import com.dongbat.game.system.FoodAnimationSystem;
import com.dongbat.game.system.GameStageSystem;
import com.dongbat.game.system.InputProcessorSystem;
import com.dongbat.game.system.MovementSystem;
import com.dongbat.game.system.localSystem.BorderRenderSystem;
import com.dongbat.game.system.localSystem.CameraUpdateSystem;
import com.dongbat.game.system.localSystem.GridRendererSystem;
import com.dongbat.game.system.localSystem.LocalInputSystem;
import com.dongbat.game.system.localSystem.ParallaxBackgroundSystem;
import com.dongbat.game.util.msg.WorldState;
import com.dongbat.game.util.networkUtil.WorldStateUtil;
import com.dongbat.game.util.object.CustomWorld;
import com.dongbat.game.util.object.PredictableRandom;
import com.dongbat.game.util.object.WorldProgress;

/**
 * @author Admin
 */
public class ECSUtil {

  public static final ObjectMap<World, WorldProgress> worldProgressMap = new ObjectMap<World, WorldProgress>();
  public static final ObjectMap<World, PredictableRandom> worldRandomMap = new ObjectMap<World, PredictableRandom>();

  public static World createWorld() {
    return createWorld(false);
  }

  public static World createWorld(boolean server) {
    WorldConfiguration config = initWorldConfig();

    CustomWorld world = new CustomWorld(config);
    world.setInvocationStrategy(new InvocationStrategy(world));
    return world;
  }

  private static WorldConfiguration initWorldConfig() {
    WorldConfiguration config = new WorldConfiguration();

//    setSystem(config, new CollisionCleanupSystem(), false);
    setSystem(config, new Box2dSystem(1), false);
    setSystem(config, new AiControlledSystem(15), false);
    setSystem(config, new BuffSystem(), false); // gay lag, mat 300 entities
//    setSystem(config, new CollisionSystem(1), false); // 1200 collided trong list
    setSystem(config, new DetectionCleanupSystem(20), false);
    setSystem(config, new DetectionSystem(20), false);
    setSystem(config, new ConsumingSystem(), false);
    setSystem(config, new InputProcessorSystem(), false);
    setSystem(config, new BorderlandSystem(), false);
    setSystem(config, new MovementSystem(), false); // gay lag, mat 400

    // for rendering
    setSystem(config, new DisplayUpdateSystem(), true);
    setSystem(config, new CameraUpdateSystem(), true);
//    setSystem(config, new HUDRenderSystem(), true); // gay lag

    setSystem(config, new LocalInputSystem(), true); // gay lag, mat 200
    setSystem(config, new GridRendererSystem(), true); // gay lag, mat hon 300

    setSystem(config, new ParallaxBackgroundSystem(), true);
    setSystem(config, new BorderRenderSystem(), true);
    setSystem(config, new FoodAnimationSystem(), true);
    setSystem(config, new AnimationRenderSystem(), true);
//    setSystem(config, new Box2dDebugRendererSystem(), true);

    setSystem(config, new GameStageSystem(), true);
    setSystem(config, new CheckWinningConditionSystem(), false);

    config.setManager(new UuidEntityManager());
    return config;
  }

  /**
   * Set systems to the artemis world then initialize
   *
   * @param world
   */
  public static void init(World world) {
    WorldProgress worldProgress = new WorldProgress(0.015f);
    worldProgressMap.put(world, worldProgress);
    worldRandomMap.put(world, new PredictableRandom());
  }

  public static void updateEntityStates(CustomWorld world) {
    world.setIgnoringSystem(true);
    world.process();
    world.setIgnoringSystem(false);
  }

  private static Array<WorldState> histories = new Array<WorldState>();

  /**
   * Process a game loop
   *
   * @param world artemis world
   * @param delta delta time
   */
  public static void process(World world, float delta) {
    WorldProgress worldProgress = getWorldProgress(world);
    if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
      worldProgress.setRewinding(true);
    }
    if (worldProgress.isRewinding()) {
      worldProgress.setRewinding(false);
      if (histories.size > 0) {
        WorldState state = histories.pop();
        WorldStateUtil.load(world, state);
      }
    } else {
      while (histories.size > 1000) {
        histories.removeIndex(0);
      }
      worldProgressMap.get(world).stepWorld(world, delta);
      if (getFrame(world) == 0) {
        updateEntityStates((CustomWorld) world);
      }
      if(getFrame(world) % 3 == 0) {
        WorldState save = WorldStateUtil.save(world);
        histories.add(save);  
      }
    }
    processPassive(world, delta);

  }

  public static void normalProcess(World world, float delta) {
    world.setDelta(delta);
    world.process();
    getWorldProgress(world).advanced();
  }

  /**
   * Process passive system
   *
   * @param world artemis world
   * @param delta delta time
   */
  public static void processPassive(World world, float delta) {
    world.setDelta(delta);
    ImmutableBag<BaseSystem> systems = world.getSystems();
    for (BaseSystem system : systems) {
      if (system.isPassive()) {
        system.process();
      }
    }
  }

  /**
   * Set a system to artemis world
   *
   * @param config config of artemis world
   * @param system system you want to add to world
   * @param isPassive is passive system or not
   */
  private static void setSystem(WorldConfiguration config, BaseSystem system, boolean isPassive) {
    config.setSystem(system, isPassive);
  }

  public static PredictableRandom getRandom(World world) {
    return worldRandomMap.get(world);
  }

  public static WorldProgress getWorldProgress(World world) {
    return worldProgressMap.get(world);
  }

  public static long getFrame(World world) {
    return worldProgressMap.get(world).getFrame();
  }

  public static float getStep(World world) {
    return worldProgressMap.get(world).getStep();
  }

  public static void setFrame(World world, long frame) {
    worldProgressMap.get(world).setFrame(frame);
  }

  public static Array<Component> getAllComponents(World world, Entity e) {
    Array<Component> all = new Array<Component>();
    Bag<Component> components = world.getComponentManager().getComponentsFor(e, new Bag<Component>());
    for (Component component : components) {
      all.add(component);
    }
    return all;
  }

}
