/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.strategy;

import com.artemis.BaseSystem;
import com.artemis.utils.Bag;
import com.dongbat.game.util.object.CustomWorld;

/**
 *
 * @author tao
 */
public class InvocationStrategy extends com.artemis.InvocationStrategy {

  private CustomWorld world;

  public InvocationStrategy(CustomWorld world) {
    this.world = world;
  }

  @Override
  protected void process(Bag<BaseSystem> systems) {
    if (world.isIgnoringSystem()) {
      updateEntityStates();
      return;
    }
    Object[] systemsData = systems.getData();
    for (int i = 0, s = systems.size(); s > i; i++) {
      updateEntityStates();

      BaseSystem system = (BaseSystem) systemsData[i];
      if (!system.isPassive()) {
        system.process();
      }
    }
  }
}
