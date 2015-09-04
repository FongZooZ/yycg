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
import com.dongbat.game.component.Detection;
import com.dongbat.game.util.BuffUtil;
import com.dongbat.game.util.ECSUtil;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.UuidUtil;
import com.dongbat.game.util.factory.EntityFactory;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class SelfDefense implements BuffEffect {

  private int framePerFood;
  private float foodRadius;
  private long lastFrameCast;

  @Override
  public void durationStart(World world, Entity source, Entity target) {
    framePerFood = framePerFood == 0 ? 60 : framePerFood;
    foodRadius = foodRadius == 0 ? 2 : foodRadius;
    lastFrameCast = ECSUtil.getFrame(world);
  }

  @Override
  public void update(World world, Entity source, Entity target) {
    if (ECSUtil.getFrame(world) - lastFrameCast > framePerFood) {
      lastFrameCast = ECSUtil.getFrame(world);
      Detection detection = EntityUtil.getComponent(world, target, Detection.class);
      UUID id = detection.getNearestPlayer();
      if (id != null) {
        Entity e = UuidUtil.getEntityByUuid(world, id);
        if (e == null || !e.isActive() || PhysicsUtil.getBody(world, e) == null) {
          return;
        }
        Vector2 entityPos = PhysicsUtil.getPosition(world, e);
        Vector2 queenPos = PhysicsUtil.getPosition(world, source);
        Vector2 destination = entityPos.cpy().sub(queenPos.cpy());
        for (int i = 0; i < 1; i++) {
          float d = ECSUtil.getRandom(world).getFloat(-30, 30);

          Vector2 direction = destination.cpy().scl(1).nor().rotate(d);
          Entity food = EntityFactory.createFood(world, queenPos, UuidUtil.getUuid(source));
          PhysicsUtil.setRadius(world, food, foodRadius);

          BuffUtil.addBuff(world, source, food, "ToBeRemoved", 1000, 1);
          BuffUtil.addBuff(world, source, food, "ToxicFood", 1000, 1);

          BuffUtil.addBuff(world, source, food, "Forced", (int) (400 * ECSUtil.getRandom(world).getFloat(0.5f, 1.25f)), 1, "forceStrength", 5f, "direction", direction
          );
        }
      }
    }

  }

  @Override
  public void durationEnd(World world, Entity source, Entity target) {
  }

}
