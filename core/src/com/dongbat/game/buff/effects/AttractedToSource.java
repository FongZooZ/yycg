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
 *
 * @author BINHDUONG
 */
public class AttractedToSource implements BuffEffect {

  private float forceStrength;

  private boolean ignoreMass = true;

  @Override
  public void durationStart(World world, Entity source, Entity t) {
    MovementUtil.disableMovement(t);
    PhysicsUtil.setVelocity(world, t, new Vector2());
  }

  @Override
  public void update(World world, Entity source, Entity target) {
    if (null == source || !source.isActive() || PhysicsUtil.getBody(world, source) == null) {
      return;
    }
    Vector2 impulse = PhysicsUtil.getPosition(world, source).cpy().sub(PhysicsUtil.getPosition(world, target)).nor().scl(forceStrength);
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
