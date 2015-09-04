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
import com.dongbat.game.util.MovementUtil;
import com.dongbat.game.util.PhysicsUtil;

/**
 * @author Admin
 */
public class Forced implements BuffEffect {

  private float forceStrength;
  private Vector2 direction;
  private Vector2 target;

  private boolean ignoreMass = false;

  @Override
  public void durationStart(World world, Entity source, Entity t) {
    if (direction == null) {
      if (target != null) {
        direction = target.cpy().sub(PhysicsUtil.getPosition(world, t));
      } else {
        direction = new Vector2(0, 0);
      }
    }
    MovementUtil.disableMovement(t);
    PhysicsUtil.setVelocity(world, t, new Vector2());
  }

  @Override
  public void update(World world, Entity source, Entity target) {
    Vector2 impulse = direction.cpy().nor().scl(forceStrength);
    if (ignoreMass) {
      impulse.scl(PhysicsUtil.getBody(world, target).getMass());
    }
    PhysicsUtil.applyImpulse(world, target, impulse);
  }

  @Override
  public void durationEnd(World world, Entity source, Entity target) {
    MovementUtil.enableMovement(target);
    PhysicsUtil.setVelocity(world, target, new Vector2());
  }
}
