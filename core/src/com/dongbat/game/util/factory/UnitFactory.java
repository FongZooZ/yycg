/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util.factory;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.UuidEntityManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.game.component.AiControl;
import com.dongbat.game.component.BuffComponent;
import com.dongbat.game.component.CollisionComponent;
import com.dongbat.game.component.Detection;
import com.dongbat.game.component.Display;
import com.dongbat.game.component.Physics;
import com.dongbat.game.component.Player;
import com.dongbat.game.component.Queen;
import com.dongbat.game.component.Stats;
import com.dongbat.game.component.SubUnit;
import com.dongbat.game.component.UnitMovement;
import static com.dongbat.game.registry.UnitRegistry.get;
import static com.dongbat.game.registry.UnitRegistry.setUnitData;
import com.dongbat.game.unit.UnitInfo;
import com.dongbat.game.util.AnimationUtil;
import com.dongbat.game.util.AssetUtil;
import com.dongbat.game.util.BuffUtil;
import com.dongbat.game.util.ECSUtil;
import com.dongbat.game.util.EntityUtil;
import static com.dongbat.game.util.FoodSpawningUtil.scaleX;
import static com.dongbat.game.util.FoodSpawningUtil.scaleY;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.UuidUtil;
import java.util.UUID;
import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;

/**
 * @author Admin
 */
public class UnitFactory {

  /**
   * Create projectile unit
   *
   * @param world artemis world
   * @param parent entity that execute that projectile unit
   * @param position spawn position
   * @param radius unit radius
   * @return projectile unit that was just created
   */
  public static Entity createProjectileUnit(World world, Entity parent, Vector2 position, float radius, boolean allowConsumming, boolean consumable, boolean feedParent) {
    Entity e = world.createEntity(UUID.randomUUID());

    Stats stats = new Stats();
    stats.setAllowComsumming(allowConsumming);
    stats.setConsumable(consumable);
    stats.setParent(world.getManager(UuidEntityManager.class).getUuid(parent));
    stats.setFeedParent(feedParent);

    Physics physics = new Physics();
    physics.setBody(PhysicsUtil.createBody(PhysicsUtil.getPhysicsWorld(world), position, radius, e));
    physics.getBody().setUserData(UuidUtil.getUuid(e));
    physics.getBody().setBullet(true);
    e.edit().add(physics);

    UnitMovement movement = new UnitMovement();
    movement.setDirectionVelocity(new Vector2());

    Display display = new Display();
    e.edit()
      .add(stats)
      .add(new Player(false))
      .add(new BuffComponent())
      .add(new SubUnit())
      .add(new CollisionComponent())
      .add(new Detection())
      .add(movement)
      .add(display);

    display.setPosition(PhysicsUtil.getPosition(world, e));
    display.setRadius(PhysicsUtil.getRadius(world, e));
    display.setRotation(EntityUtil.getComponent(world, e, UnitMovement.class).getDirectionVelocity().angle());
//        TextureAtlas move = AssetUtil.getUnitAtlas().get("hot_food");
    Animation animation = new Animation(0.1f, new TextureRegion(AssetUtil.cold));
    animation.setPlayMode(Animation.PlayMode.LOOP);
    display.setDefaultAnimation(new AnimatedSprite(animation));

    return e;
  }

  /**
   * Create unit and add to artemis world
   *
   * @param world artemis world
   * @param unitType type of unit in String
   * @param position spawn position
   * @param args optional arguments
   * @return Unit that was just created
   */
  public static Entity createUnit(World world, String unitType, Vector2 position, Object... args) {
    Entity e = world.createEntity(UUID.randomUUID());

    UnitInfo unitInfo = get(unitType);
    setUnitData(world, e, args);

    CollisionComponent collision = new CollisionComponent();

    Stats stats = new Stats();
    stats.setBaseRateSpeed(unitInfo.getBaseSpeedRate());

    Physics physics = new Physics();
    e.edit().add(physics);
    physics.setBody(PhysicsUtil.createBody(PhysicsUtil.getPhysicsWorld(world), position, unitInfo.getRadius(), e));
    physics.getBody().setUserData(UuidUtil.getUuid(e));
    UnitMovement movement = new UnitMovement();
    movement.setDirectionVelocity(new Vector2());

    AiControl aiControl = new AiControl(unitInfo.getDefinitionPath());
    Display display = new Display();
    e.edit()
      .add(new BuffComponent())
      .add(aiControl)
      .add(new Player())
      .add(new Detection())
      .add(stats)
      .add(movement)
      .add(display)
      .add(collision);

    display.setPosition(PhysicsUtil.getPosition(world, e));
    display.setRadius(PhysicsUtil.getRadius(world, e));
    display.setRotation(EntityUtil.getComponent(world, e, UnitMovement.class).getDirectionVelocity().angle());
    display.setDefaultAnimation(AnimationUtil.getUnitAnimation());
    BuffUtil.addBuff(world, e, e, "FeedSmaller", -1, 1);

    return e;
  }

  public static Entity createQueen(World world, Vector2 position, float radius) {
    Entity e = world.createEntity(UUID.randomUUID());

    Stats stats = new Stats();
    stats.setAllowComsumming(false);
    stats.setConsumable(true);
    stats.setBaseRateSpeed(2000);

    BuffComponent buff = new BuffComponent();

    Physics physics = new Physics();
    physics.setBody(PhysicsUtil.createBody(PhysicsUtil.getPhysicsWorld(world), position, radius, e));
    physics.getBody().setUserData(UuidUtil.getUuid(e));

    UnitMovement movement = new UnitMovement();

    float posX = (float) (ECSUtil.getRandom(world).getFloat(-1, 1) * scaleX);
    float posY = (float) (ECSUtil.getRandom(world).getFloat(-1, 1) * scaleY);
    movement.setDirectionVelocity(new Vector2(posX, posY));

    Display display = new Display();

    e.edit().add(physics)
      .add(stats)
      .add(new CollisionComponent())
      .add(buff)
      .add(new Queen())
      .add(new Detection())
      .add(movement)
      .add(display);

    BuffUtil.addBuff(world, e, e, "QueenTeleportSchedule", -1, 1);
    BuffUtil.addBuff(world, e, e, "ProduceFoodSchedule", -1, 1);
    BuffUtil.addBuff(world, e, e, "FeedSmaller", -1, 1, "feedPerSecond", 0.2f);
    BuffUtil.addBuff(world, e, e, "SelfDefense", -1, 1, "framePerFood", 10);
    BuffUtil.addBuff(world, e, e, "QueenGrowth", -1, 1, "cap", 20);
    display.setPosition(PhysicsUtil.getPosition(world, e));
    display.setRadius(PhysicsUtil.getRadius(world, e));
    display.setRotation(EntityUtil.getComponent(world, e, UnitMovement.class).getDirectionVelocity().angle());
    TextureAtlas queen = AssetUtil.getUnitAtlas().get("queen");
    Animation animation = new Animation(0.1f, queen.getRegions());
    animation.setPlayMode(Animation.PlayMode.LOOP);
    display.setDefaultAnimation(new AnimatedSprite(animation));
    return e;
  }
}
