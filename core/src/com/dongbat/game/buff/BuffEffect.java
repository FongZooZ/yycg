/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.buff;

import com.artemis.Entity;
import com.artemis.World;

/**
 * @author Admin
 */
public interface BuffEffect {

  /**
   * Start buff effect
   *
   * @param world artemis world
   * @param source entity that affect buff effect to entity target
   * @param target entity that is affected from buff effect
   */
  public void durationStart(World world, Entity source, Entity target);

  /**
   * Update buff effect when game loop process
   *
   * @param world artemis world
   * @param source entity that affect buff effect to entity target
   * @param target entity that is affected from buff effect
   */
  public void update(World world, Entity source, Entity target);

  /**
   * End buff when duration is reach maximum value
   *
   * @param world artemis world
   * @param source entity that affect buff effect to entity target
   * @param target entity that is affected from buff effect
   */
  public void durationEnd(World world, Entity source, Entity target);
}
