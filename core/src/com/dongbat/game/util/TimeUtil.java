/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util;

import com.artemis.World;

/**
 * @author Admin
 */
public class TimeUtil {

  public static long getCurrentFrame(World world) {
    return ECSUtil.getWorldProgress(world).getFrame();
  }

  public static long convertFrameToMillis(World world, long frame) {
    float step = ECSUtil.getWorldProgress(world).getStep();
    return (long) (step * frame * 1000);
  }

  public static long getCurrentFrameToMillis(World world) {
    float step = ECSUtil.getWorldProgress(world).getStep();
    long frame = ECSUtil.getWorldProgress(world).getFrame();
    return (long) (step * frame * 1000);
  }

  public static long convertMillisToFrame(World world, long milis) {
    float step = ECSUtil.getWorldProgress(world).getStep();
    return (long) (milis / step / 1000);
  }

  public static long getFrameSince(World world, long oldFrame) {
    long currentFrame = getCurrentFrame(world);
    return currentFrame - oldFrame;
  }

  public static long getFrameSinceToMillis(World world, long oldFrame) {
    long frameSince = getFrameSince(world, oldFrame);
    return convertFrameToMillis(world, frameSince);
  }

}
