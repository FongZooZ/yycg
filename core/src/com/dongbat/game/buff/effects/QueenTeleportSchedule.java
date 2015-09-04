/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.buff.effects;

import com.artemis.Entity;
import com.artemis.World;
import com.dongbat.game.buff.BuffEffect;
import com.dongbat.game.util.BuffUtil;
import com.dongbat.game.util.ECSUtil;

/**
 *
 * @author Admin
 */
public class QueenTeleportSchedule implements BuffEffect {

  private float scheduleFrame;

  @Override
  public void durationStart(World world, Entity source, Entity target) {
  }

  @Override
  public void update(World world, Entity source, Entity target) {
    if (ECSUtil.getFrame(world) % scheduleFrame == 0) {

//      float posX = (float) (ECSUtil.getRandom(world).getFloat(-1, 1) * scaleX);
//      float posY = (float) (ECSUtil.getRandom(world).getFloat(-1, 1) * scaleY);
//      PhysicsUtil.setPosition(world, target, new Vector2(posX, posY));
      BuffUtil.addBuff(world, source, target, "RandomDestinationSchedule", 1000, 1);
    }
  }

  @Override
  public void durationEnd(World world, Entity source, Entity target) {
  }

}
