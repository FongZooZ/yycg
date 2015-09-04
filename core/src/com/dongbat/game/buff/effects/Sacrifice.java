/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.buff.effects;

import com.artemis.Entity;
import com.artemis.World;
import com.dongbat.game.buff.BuffEffect;
import com.dongbat.game.util.PhysicsUtil;

/**
 *
 * @author Admin
 */
public class Sacrifice implements BuffEffect {

  private float percent;

  @Override
  public void durationStart(World world, Entity source, Entity target) {
    float radius = PhysicsUtil.getRadius(world, source);
    PhysicsUtil.setRadius(world, source, radius * (1 - percent));
  }

  @Override
  public void update(World world, Entity source, Entity target) {
  }

  @Override
  public void durationEnd(World world, Entity source, Entity target) {
  }

}
