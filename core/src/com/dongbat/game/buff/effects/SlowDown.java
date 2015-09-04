/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.buff.effects;

import com.artemis.Entity;
import com.artemis.World;
import com.dongbat.game.buff.BuffEffect;

/**
 *
 * @author Admin
 */
public class SlowDown implements BuffEffect {

  private float slowDownAmount;

  @Override
  public void durationStart(World world, Entity source, Entity target) {

  }

  @Override
  public void update(World world, Entity source, Entity target) {
  }

  @Override
  public void durationEnd(World world, Entity source, Entity target) {

  }
}
