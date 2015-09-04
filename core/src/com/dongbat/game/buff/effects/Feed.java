/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.buff.effects;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.utils.Array;
import com.dongbat.game.buff.BuffEffect;
import com.dongbat.game.component.CollisionComponent;
import com.dongbat.game.component.Stats;
import com.dongbat.game.util.ECSUtil;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.UnitUtil;
import com.dongbat.game.util.UuidUtil;
import com.dongbat.game.util.WorldQueryUtil;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class Feed implements BuffEffect {

  private float feedPerSecond = 0.5f;
  private float totalFeedVolume;

  @Override
  public void durationStart(World world, Entity source, Entity target) {

    totalFeedVolume = PhysicsUtil.getSquare(world, target);
  }

  @Override
  public void update(World world, Entity source, Entity target) {
    CollisionComponent collision = EntityUtil.getComponent(world, target, CollisionComponent.class);
    Array<UUID> collidedList = collision.getCollidedList();
    Array<Entity> collidedEntityList = new Array<Entity>();
    for (UUID uuid : collidedList) {
      Entity entity = UuidUtil.getEntityByUuid(world, uuid);
      if (entity == null) {
        continue;
      }
      Stats stats = EntityUtil.getComponent(world, entity, Stats.class);
      if (stats != null && stats.isAllowComsumming()) {
        if (WorldQueryUtil.isQueen(world, entity.getId())) {
          continue;
        }
        collidedEntityList.add(entity);
      }
    }
    if (collidedEntityList.size == 0) {
      totalFeedVolume = PhysicsUtil.getSquare(world, target);
    } else {
      int size = collidedEntityList.size;
      float feedAmount = totalFeedVolume * feedPerSecond * ECSUtil.getStep(world);
      if (feedAmount >= PhysicsUtil.getSquare(world, target)) {
        feedAmount = PhysicsUtil.getSquare(world, target);
        UnitUtil.destroy(target);
      } else {
        PhysicsUtil.increaseSquare(world, target, -feedAmount);
      }
      float dividend = feedAmount / size;

      for (Entity e : collidedEntityList) {
        PhysicsUtil.increaseSquare(world, e, dividend);
      }
    }
  }

  @Override
  public void durationEnd(World world, Entity source, Entity target) {

  }

}
