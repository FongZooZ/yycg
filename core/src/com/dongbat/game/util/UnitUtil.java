/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util;

import com.artemis.Entity;
import com.artemis.World;
import com.dongbat.game.component.Food;
import com.dongbat.game.component.Physics;
import com.dongbat.game.component.Player;
import com.dongbat.game.component.Stats;
import com.dongbat.game.component.SubUnit;
import static com.dongbat.game.util.WorldQueryUtil.isPlayer;
import com.dongbat.game.util.localUtil.Constants;
import java.util.UUID;

/**
 * @author Admin
 */
public class UnitUtil {

  /**
   * Make entity A eat entity B
   *
   * @param world artemis world
   * @param a entity A
   * @param b entity B
   */
  public static void eat(World world, Entity a, Entity b) {
    if (a == null || b == null) {
      return;
    }

    boolean consumable = UnitUtil.canEat(world, a, b);
    boolean toxic = isToxic(world, b);

    if (consumable && !toxic) {
      float multiplier = 5;
      if (WorldQueryUtil.isFood(world, b.getId())) {
        multiplier = 5;
      }
      if (EntityUtil.getComponent(world, b, SubUnit.class) != null) {
        multiplier = 1;
      }
      float increaseRadius = PhysicsUtil.increaseRadius(PhysicsUtil.getRadius(world, a), PhysicsUtil.getRadius(world, b), multiplier);
      PhysicsUtil.setRadius(world, a, increaseRadius);
      destroy(b);
      return;
    }
    if (consumable && toxic) {
      if (PhysicsUtil.getRadius(world, a) < PhysicsUtil.getRadius(world, b)) {
        destroy(b);
        destroy(a);
        return;
      }
      float multiplier = -1;
      if (WorldQueryUtil.isFood(world, b.getId())) {
        multiplier = -3;
      }
      float decreasedRadius = PhysicsUtil.increaseRadius(PhysicsUtil.getRadius(world, a), PhysicsUtil.getRadius(world, b), multiplier);
      if (decreasedRadius > 0) {
        PhysicsUtil.setRadius(world, a, decreasedRadius);
      } else {
        destroy(a);
      }
      destroy(b);
    }
  }

  /**
   * Check an entity can be eaten or not
   *
   * @param world artemis world
   * @param source
   * @param target
   * @return true if that entity can be eaten
   */
  public static boolean canEat(World world, Entity source, Entity target) {
    if (!canEat(world, source)) {
      return false;
    }
    Stats sourceStats = EntityUtil.getComponent(world, source, Stats.class);
    Stats targetStats = EntityUtil.getComponent(world, target, Stats.class);
    if (targetStats == null || sourceStats == null) {
      return true;
    }
    UUID sourceId = UuidUtil.getUuid(source);
    UUID targetId = UuidUtil.getUuid(target);
    if (targetStats.getParent() != null && targetStats.getParent().equals(sourceId) && !targetStats.isFeedParent()) {
      return false;
    }
    if (sourceStats.getParent() != null && sourceStats.getParent().equals(targetId)) {
      return false;
    }
    return targetStats.isConsumable();
  }

  /**
   * Check an entity can eat other unit or not
   *
   * @param world artemis world
   * @param e entity that you want to check
   * @return true if that entity can eat other unit
   */
  public static boolean canEat(World world, Entity e) {
    Stats stats = EntityUtil.getComponent(world, e, Stats.class);
    if (stats == null) {
      return false;
    }

    return stats.isAllowComsumming();
  }

  /**
   * Destroy an entity
   *
   * @param e entity that you want to destroy
   */
  public static void destroy(Entity e) {
    if (e == null) {
      return;
    }
    com.badlogic.gdx.physics.box2d.World physicsWorld = PhysicsUtil.getPhysicsWorld(e.getWorld());
    Physics physics = EntityUtil.getComponent(e.getWorld(), e, Physics.class);
    if (physics == null) {
      return;
    }
    if (physics.getBody() == null) {
      return;
    }
    if (!physics.getBody().isActive()) {
      return;
    }

    if (isPlayer(e.getWorld(), e.getId()) && !BuffUtil.hasBuff(e.getWorld(), e, "Respawn")) {
      Player p = EntityUtil.getComponent(e.getWorld(), e, Player.class);
      if (p.isGoingToRespawn()) {
        BuffUtil.addBuff(e.getWorld(), e, e, "Respawn", 2000, 1);
        return;
      }
    }
    physicsWorld.destroyBody(physics.getBody());
    e.deleteFromWorld();
  }

  /**
   * Calculate speed of an entity
   *
   * @param world artemis world
   * @param a entity that you wan to check
   */
  public static void speedCalculation(World world, Entity a) {
    if (a == null) {
      return;
    }
    Stats stat = EntityUtil.getComponent(world, a, Stats.class);
    float speed = 15000 / PhysicsUtil.getRadius(world, a) + stat.getBaseRateSpeed() * 15;
    if (speed >= Constants.PHYSICS.MAX_VELOCITY) {
      speed = Constants.PHYSICS.MAX_VELOCITY;
    }
    stat.setBaseMovementSpeed(speed);
  }

  private static boolean isToxic(World world, Entity b) {
    Food foodComponent = EntityUtil.getComponent(world, b, Food.class);
    if (foodComponent != null) {
      return foodComponent.isToxic();
    }
    return false;
  }
}
