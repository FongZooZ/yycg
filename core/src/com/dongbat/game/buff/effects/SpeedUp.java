/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.buff.effects;

import com.artemis.Entity;
import com.artemis.World;
import com.dongbat.game.buff.BuffEffect;
import com.dongbat.game.component.Stats;
import com.dongbat.game.util.EntityUtil;

/**
 *
 * @author MINHMINH
 */
public class SpeedUp implements BuffEffect {

  private float speedUpAmount;

  @Override
  public void durationStart(World world, Entity source, Entity target) {

    Stats targetStats = EntityUtil.getComponent(world, target, Stats.class);
    targetStats.setModifierSpeed(speedUpAmount);
  }

  @Override
  public void update(World world, Entity source, Entity target) {
  }

  @Override
  public void durationEnd(World world, Entity source, Entity target) {
    Stats sourceStats = EntityUtil.getComponent(world, target, Stats.class);
    sourceStats.setModifierSpeed(sourceStats.getModifierSpeed() - speedUpAmount);
  }

}
