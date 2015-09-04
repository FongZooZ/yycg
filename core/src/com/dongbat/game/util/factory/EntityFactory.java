/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util.factory;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.game.component.AbilityComponent;
import com.dongbat.game.component.BuffComponent;
import com.dongbat.game.component.CollisionComponent;
import com.dongbat.game.component.Detection;
import com.dongbat.game.component.Display;
import com.dongbat.game.component.Food;
import com.dongbat.game.component.Physics;
import com.dongbat.game.component.Player;
import com.dongbat.game.component.Stats;
import com.dongbat.game.component.UnitMovement;
import com.dongbat.game.component.UnitType;
import com.dongbat.game.registry.UnitRegistry;
import com.dongbat.game.unit.UnitInfo;
import com.dongbat.game.util.AbilityUtil;
import com.dongbat.game.util.AnimationUtil;
import com.dongbat.game.util.BuffUtil;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.UuidUtil;
import static com.dongbat.game.util.localUtil.Constants.PHYSICS.FOOD_RADIUS;
import java.util.UUID;

/**
 * @author Admin
 */
public class EntityFactory {

  /**
   * Create player unit and add to world
   *
   * @param world artemis world
   * @param type
   * @param position spawn position
   * @return Player entity that was just created
   */
  public static Entity createPlayer(World world, Vector2 position, String type) {
    Entity e = world.createEntity(UUID.randomUUID());
    CollisionComponent collision = new CollisionComponent();

    UnitType unitType = new UnitType(type);
    UnitInfo info = UnitRegistry.get(type);

    String abilities = info.getAbilities();

    AbilityComponent abilityComponent = new AbilityComponent();
    AbilityUtil.abilityComponentFilled(abilities, abilityComponent);

    Stats stats = new Stats();
    stats.setBaseRateSpeed(info.getBaseSpeedRate());

    Physics physics = new Physics();
    physics.setBody(PhysicsUtil.createBody(PhysicsUtil.getPhysicsWorld(world), position, info.getRadius(), e));
    physics.getBody().setUserData(UuidUtil.getUuid(e));

    UnitMovement movement = new UnitMovement();
    movement.setDirectionVelocity(new Vector2());
    movement.setDisabled(false);

    Player player = new Player();

    Display display = new Display();

    e.edit()
      .add(abilityComponent)
      .add(unitType)
      .add(new BuffComponent())
      .add(physics)
      .add(stats)
      .add(player)
      .add(movement)
      .add(new Detection())
      .add(collision)
      .add(display);

    display.setPosition(PhysicsUtil.getPosition(world, e));
    display.setRadius(PhysicsUtil.getRadius(world, e));
    display.setRotation(EntityUtil.getComponent(world, e, UnitMovement.class).getDirectionVelocity().angle());
//        TextureAtlas move = AssetUtil.getUnitAtlas().get("move");
//        Animation animation = new Animation(0.1f, move.getRegions());
//    animation.setPlayMode(Animation.PlayMode.LOOP);
//    display.setDefaultAnimation(new AnimatedSprite(animation));
    display.setDefaultAnimation(AnimationUtil.getUnitAnimation());
    return e;
  }

  /**
   * Create Food with box2d Steering behavior
   *
   * @param world artemis world
   * @param position spawn position
   * @return Food entity that was just created
   */
  public static Entity createFood(World world, Vector2 position, UUID parent) {
    Entity e = world.createEntity(UUID.randomUUID());
    Physics physics = new Physics();
    physics.setBody(PhysicsUtil.createBody(PhysicsUtil.getPhysicsWorld(world), position, FOOD_RADIUS, e));
    physics.getBody().setUserData(UuidUtil.getUuid(e));

    Stats s = new Stats();
    s.setConsumable(true);
    s.setAllowComsumming(false);
    s.setParent(parent);

    UnitMovement movement = new UnitMovement();
    movement.setDirectionVelocity(new Vector2());

    Display display = new Display();

    e.edit().add(new CollisionComponent())
      .add(physics)
      .add(s)
      .add(new Food())
      .add(movement)
      .add(new Detection())
      .add(new BuffComponent())
      .add(display);

    display.setPosition(PhysicsUtil.getPosition(world, e));
    display.setRadius(PhysicsUtil.getRadius(world, e));
    display.setRotation(EntityUtil.getComponent(world, e, UnitMovement.class).getDirectionVelocity().angle());
    display.setDefaultAnimation(AnimationUtil.getHotFood());
    return e;
  }

  public static Entity createAbsorbableFood(World world, Vector2 position, float radius) {
    Entity e = world.createEntity(UUID.randomUUID());
    Physics physics = new Physics();
    physics.setBody(PhysicsUtil.createBody(PhysicsUtil.getPhysicsWorld(world), position, radius, e));
    physics.getBody().setUserData(UuidUtil.getUuid(e));
//    Food food = new Food();
    Stats stats = new Stats();
    stats.setAllowComsumming(false);
    stats.setConsumable(false);

    e.edit().add(new CollisionComponent())
      .add(physics)
      .add(new BuffComponent())
      .add(stats)
      .add(new UnitMovement())
      .add(new Detection())
      .add(new BuffComponent());

    BuffUtil.addBuff(world, e, e, "Feed", -1, 1, "feedPerSecond", 0.5f);

    return e;
  }

}
