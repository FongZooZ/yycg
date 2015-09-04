/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.ai.tasks;

import com.artemis.Entity;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.dongbat.game.util.MovementUtil;

/**
 *
 * @author Admin
 */
public class MoveToTargetTask extends LeafTask<Entity> {

  @Override
  public void run() {
    Entity object = getObject();
    if (MovementUtil.hasArrived(object)) {
      success();
    } else {
      running();
    }
  }

  @Override
  protected Task<Entity> copyTo(Task<Entity> task) {
    return task;
  }

}
