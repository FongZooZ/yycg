/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.ai.tasks;

import com.artemis.Entity;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.dongbat.game.util.MovementUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.WorldQueryUtil;

/**
 *
 * @author Admin
 */
public class AvoidToBeEaten extends LeafTask<Entity> {

  @Override
  public void run() {

    Entity e = getObject();
    float radius = PhysicsUtil.getRadius(e.getWorld(), e);
    Vector2 currentPos = PhysicsUtil.getPosition(e.getWorld(), e);
    Vector2 directionToPlayer = null;
    float distance2 = 999999999;
    Array<Entity> anyInRadius = WorldQueryUtil.findAnyInRadius(e.getWorld(), PhysicsUtil.getPosition(e.getWorld(), e), 10);
    for (Entity entity : anyInRadius) {
      if (radius < PhysicsUtil.getRadius(entity.getWorld(), entity)) {
        Vector2 nearPlayerPos = PhysicsUtil.getPosition(e.getWorld(), entity);
        float newDistance2 = nearPlayerPos.cpy().sub(currentPos.cpy()).len2();
        if (newDistance2 < distance2) {
          directionToPlayer = nearPlayerPos.cpy().sub(currentPos);
          distance2 = newDistance2;
        }
      }
    }
    if (directionToPlayer != null && distance2 > 0) {
      MovementUtil.addMoveInput(e.getWorld(), e, directionToPlayer.cpy().scl(-1));
    }
    success();
  }

  @Override
  protected Task<Entity> copyTo(Task<Entity> task) {
    return task;
  }
}
