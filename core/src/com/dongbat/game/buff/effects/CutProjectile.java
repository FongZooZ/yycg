/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.buff.effects;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.dongbat.game.buff.BuffEffect;
import com.dongbat.game.component.CollisionComponent;
import com.dongbat.game.util.BuffUtil;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.PlaneMathCalculator;
import com.dongbat.game.util.UnitUtil;
import com.dongbat.game.util.UuidUtil;
import com.dongbat.game.util.WorldQueryUtil;
import com.dongbat.game.util.factory.EntityFactory;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class CutProjectile implements BuffEffect {

  private float split = 0.5f;
  private Entity cuttingTarget;

  @Override
  public void durationStart(World world, Entity source, Entity target) {
  }

  @Override
  public void update(World world, Entity source, Entity target) {
    CollisionComponent collisionComponent = EntityUtil.getComponent(world, target, CollisionComponent.class);
    Array<UUID> uuidList = collisionComponent.getCollidedList();
    for (UUID uuid : uuidList) {
      Entity e = UuidUtil.getEntityByUuid(world, uuid);
      if (e == null) {
        return;
      }
      if (WorldQueryUtil.isPlayer(world, e.getId())) {
        if (e.equals(source)) {
          continue;
        }
        cuttingTarget = e;
        break;
      }
    }

    if (cuttingTarget != null) {
      Vector2 targetPosition = PhysicsUtil.getPosition(world, cuttingTarget);
      Vector2 sourcePosition = PhysicsUtil.getPosition(world, source);
      Vector2 vel = PhysicsUtil.getVelocity(world, target);
      float targetRadius = PhysicsUtil.getRadius(world, cuttingTarget);
      float percentTaken = PlaneMathCalculator.getPercentTaken(targetPosition, sourcePosition, vel, targetRadius);
      if (percentTaken > 0.5) {
        percentTaken = 1 - percentTaken;
      } else if (percentTaken > 0.49 && percentTaken < 0.51) {
        //  n
        UnitUtil.destroy(cuttingTarget);
        return;
      }
      float squareTargetRadius = (float) (targetRadius * targetRadius * Math.PI);
      float newRadius = (float) Math.sqrt(squareTargetRadius * percentTaken / Math.PI);
      float oldRadius = PhysicsUtil.getRadius(world, cuttingTarget);
      float newFoodSquare = (float) (oldRadius * oldRadius * Math.PI - newRadius * newRadius * Math.PI);
      float newFoodRadius = (float) Math.sqrt(newFoodSquare / Math.PI);

      PhysicsUtil.setRadius(world, cuttingTarget, newRadius);
      Entity a = EntityFactory.createAbsorbableFood(world, targetPosition, newFoodRadius);
      BuffUtil.addBuff(world, source, a, "Forced", 500, 1);
      BuffUtil.addBuff(world, source, cuttingTarget, "Forced", 500, 1, "forceStrength", newFoodRadius);
      UnitUtil.destroy(target);
    }
  }

  @Override
  public void durationEnd(World world, Entity source, Entity target) {
  }

}
