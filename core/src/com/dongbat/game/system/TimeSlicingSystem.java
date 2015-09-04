/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.system;

import com.artemis.BaseSystem;
import com.dongbat.game.util.ECSUtil;

/**
 *
 * @author Admin
 */
public abstract class TimeSlicingSystem extends BaseSystem {

  private final int frameSlicing;

  public TimeSlicingSystem(int frameSlicing) {
    super();
    this.frameSlicing = frameSlicing;
  }

  protected boolean scheduleProcess() {
    return ECSUtil.getFrame(world) % frameSlicing == 0;
  }

  protected abstract void processTimeSlice();

  @Override
  protected void processSystem() {
    float delta = world.getDelta();
    world.setDelta(frameSlicing * ECSUtil.getStep(world));
    if (scheduleProcess()) {
      processTimeSlice();
    }
    world.setDelta(delta);
  }

}
