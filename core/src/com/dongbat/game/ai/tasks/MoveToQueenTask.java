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
import static com.dongbat.game.util.WorldQueryUtil.findNearestEntityInList;

/**
 *
 * @author Admin
 */
public class MoveToQueenTask extends LeafTask<Entity> {

  @Override
  public void run() {
    Entity e = getObject();
    Array<Entity> foodInRadius = WorldQueryUtil.findQueenInRadius(e.getWorld(), PhysicsUtil.getPosition(e.getWorld(), e), 350);
    Entity nearestQueen = findNearestEntityInList(e.getWorld(), PhysicsUtil.getPosition(e.getWorld(), e), foodInRadius);
    Vector2 currentPos = PhysicsUtil.getPosition(e.getWorld(), e);
    if (nearestQueen == null) {
      return;
    }
    Vector2 foodPosition = PhysicsUtil.getPosition(e.getWorld(), nearestQueen);
    Vector2 destinationToFood = foodPosition.cpy().sub(currentPos);
    MovementUtil.addMoveInput(e.getWorld(), e, destinationToFood);
    success();
  }

  @Override
  protected Task<Entity> copyTo(Task<Entity> task) {
    return task;
  }

}
