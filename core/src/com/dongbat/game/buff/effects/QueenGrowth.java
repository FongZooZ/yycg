/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.buff.effects;

import com.artemis.Entity;
import com.artemis.World;
import com.dongbat.game.buff.BuffEffect;
import com.dongbat.game.component.Physics;
import com.dongbat.game.util.ECSUtil;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.PhysicsUtil;

/**
 *
 * @author Admin
 */
public class QueenGrowth implements BuffEffect {

  private float growFrame;
  private float growPercent;
  private float cap = 20;

  @Override
  public void durationStart(World world, Entity source, Entity target) {
  }

  @Override
  public void update(World world, Entity source, Entity target) {
    if (ECSUtil.getFrame(world) % growFrame == 0) {
      Physics physicsComponent = EntityUtil.getComponent(world, target, Physics.class);
      float oldRadius = PhysicsUtil.getRadius(world, target);
      if (oldRadius >= cap) {
        return;
      }
      physicsComponent.getBody().getFixtureList().get(0).getShape().setRadius(oldRadius * (1 + growPercent / 100));

    }
  }

  @Override
  public void durationEnd(World world, Entity source, Entity target) {
  }

}
