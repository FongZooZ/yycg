/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.buff.effects;

import com.artemis.Entity;
import com.artemis.World;
import com.dongbat.game.buff.BuffEffect;
import com.dongbat.game.component.Food;
import com.dongbat.game.util.EntityUtil;

/**
 *
 * @author Admin
 */
public class ToxicFood implements BuffEffect {

  float toxicScale;

  @Override
  public void durationStart(World world, Entity source, Entity target) {
    Food foodComponent = EntityUtil.getComponent(world, target, Food.class);
    if (foodComponent == null) {
      return;
    }
    foodComponent.setToxic(true);
  }

  @Override
  public void update(World world, Entity source, Entity target) {
  }

  @Override
  public void durationEnd(World world, Entity source, Entity target) {
    Food foodComponent = EntityUtil.getComponent(world, target, Food.class);
    if (foodComponent == null) {
      return;
    }
    foodComponent.setToxic(false);
  }

}
