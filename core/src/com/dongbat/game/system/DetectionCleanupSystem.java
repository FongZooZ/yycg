/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.system;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.Entity;
import com.artemis.utils.IntBag;
import com.dongbat.game.component.Detection;
import com.dongbat.game.util.EntityUtil;

/**
 *
 * @author Admin
 */
public class DetectionCleanupSystem extends TimeSlicingSystem {

  public DetectionCleanupSystem(int frameSlicing) {
    super(frameSlicing);
  }

  @Override
  protected void processTimeSlice() {
    IntBag entities = world.getManager(AspectSubscriptionManager.class).get(Aspect.all(Detection.class)).getEntities();
    for (int i = 0; i < entities.size(); i++) {
      Entity e = world.getEntity(i);
      if (e == null) {
        continue;
      }
      Detection detection = EntityUtil.getComponent(world, e, Detection.class);
      detection.setNearestFood(null);
      detection.setNearestPlayer(null);
      detection.setNearestQueen(null);
    }
  }

}
