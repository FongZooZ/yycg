/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.buff.effects;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.dongbat.game.buff.BuffEffect;
import com.dongbat.game.util.BuffUtil;

/**
 *
 * @author Admin
 */
public class DelayBuff implements BuffEffect {

  private String buffName;
  private int duration;
  private int level = 1;
  private ObjectMap<String, Object> args;

  @Override
  public void durationStart(World world, Entity source, Entity target) {
  }

  @Override
  public void update(World world, Entity source, Entity target) {
  }

  @Override
  public void durationEnd(World world, Entity source, Entity target) {
    Array<Object> toPass = new Array<Object>();
    for (ObjectMap.Entry<String, Object> arg : args) {
      toPass.add(arg.key);
      toPass.add(arg.value);
    }
    BuffUtil.addBuff(world, source, target, buffName, duration, 1, toPass.toArray());

  }

}
