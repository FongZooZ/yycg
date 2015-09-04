/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.buff.effects;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.game.buff.BuffEffect;
import com.dongbat.game.component.UnitMovement;
import com.dongbat.game.util.BuffUtil;
import com.dongbat.game.util.ECSUtil;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.TimeUtil;
import com.dongbat.game.util.UuidUtil;
import com.dongbat.game.util.factory.EntityFactory;

/**
 *
 * @author Adminz
 */
public class ProduceFoodSchedule implements BuffEffect {

  private float scheduleFrame;
  private float overheatFrame;
  private float cooldownFrame;

  private float lastRest = 0;
  private boolean resting = false;

  @Override
  public void durationStart(World world, Entity source, Entity target) {
  }

  @Override
  public void update(World world, Entity source, Entity target) {
    long currentFrame = TimeUtil.getCurrentFrame(world);
    float elapsed = currentFrame - lastRest;

    if (!resting && elapsed > overheatFrame) {
      lastRest = currentFrame;
      resting = true;
    }

    if (resting && elapsed > overheatFrame + cooldownFrame) {
      resting = false;
    }

    if (resting) {
      return;
    }

    if (ECSUtil.getFrame(world) % scheduleFrame == 0) {
      Vector2 position = PhysicsUtil.getPosition(world, target);

      UnitMovement unitMovement = EntityUtil.getComponent(world, source, UnitMovement.class);
      Vector2 destination = unitMovement.getDirectionVelocity();
      float degree = 60;

      for (int i = 0; i < 1; i++) {
        float d = ECSUtil.getRandom(world).getFloat(-degree / 2, degree / 2);
        Vector2 direction = destination.cpy().scl(-1).nor().rotate(d);
        if (direction.cpy().nor().len2() == 0) {
          //TODO: fixxxxxxxxxxxxx this
          direction = new Vector2(ECSUtil.getRandom(world).getFloat(-1, 1), ECSUtil.getRandom(world).getFloat(-1, 1));
        }
        Entity food = EntityFactory.createFood(world, position, UuidUtil.getUuid(source));
        // TODO: food expiring system
        BuffUtil.addBuff(world, source, food, "ToBeRemoved", 3000, 1);
        BuffUtil.addBuff(world, source, food, "ToxicFood", 400, 1);
        BuffUtil.addBuff(world, source, food, "Forced", (int) (600 * ECSUtil.getRandom(world).getFloat(0.5f, 1.25f)), 1, "forceStrength", 0.75f, "direction", direction);
      }
    }
  }

  @Override
  public void durationEnd(World world, Entity source, Entity target) {
  }

}
