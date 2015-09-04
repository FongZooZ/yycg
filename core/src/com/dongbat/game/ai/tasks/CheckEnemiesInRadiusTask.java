/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.ai.tasks;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.dongbat.game.util.PhysicsUtil;

/**
 *
 * @author Admin
 */
public class CheckEnemiesInRadiusTask extends Task<Entity> {

  public int size;
  public float radius;

  @Override
  public void run() {
    Entity e = getObject();
    if (radius == 0 || size == 0) {
      fail();
      return;
    }
    World world = e.getWorld();
    Vector2 position = PhysicsUtil.getPosition(world, e);
    Array<Entity> entities = null;
    if (entities.size >= size) {
      success();
    } else {
      fail();
    }
  }

  @Override
  protected Task<Entity> copyTo(Task<Entity> task) {
    CheckEnemiesInRadiusTask check = (CheckEnemiesInRadiusTask) task;
    check.size = size;
    check.radius = radius;

    return task;
  }

}
