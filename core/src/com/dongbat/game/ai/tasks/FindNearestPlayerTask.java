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
import com.dongbat.game.util.BuffUtil;
import com.dongbat.game.util.MovementUtil;
import com.dongbat.game.util.PhysicsUtil;
import static com.dongbat.game.util.WorldQueryUtil.findNearestEntityInList;
import static com.dongbat.game.util.WorldQueryUtil.findPlayerWithAiInRadius;

/**
 *
 * @author Admin
 */
public class FindNearestPlayerTask extends LeafTask<Entity> {

  @Override
  public void run() {
    Entity e = getObject();
    Array<Entity> playerInRadius = findPlayerWithAiInRadius(e.getWorld(), e, PhysicsUtil.getPosition(e.getWorld(), e), 100);
    Entity nearestPlayer = findNearestEntityInList(e.getWorld(), PhysicsUtil.getPosition(e.getWorld(), e), playerInRadius);
    Vector2 currentPos = PhysicsUtil.getPosition(e.getWorld(), e);
    if (nearestPlayer == null) {
      fail();
    } else {
      if (BuffUtil.hasBuff(nearestPlayer.getWorld(), nearestPlayer, "Respawn")) {
        fail();
      }
      Vector2 nearPlayerPos = PhysicsUtil.getPosition(e.getWorld(), nearestPlayer);
      Vector2 directionToPlayer = nearPlayerPos.cpy().sub(currentPos);
      MovementUtil.addMoveInput(e.getWorld(), e, directionToPlayer);
    }
    success();
  }

  @Override
  protected Task<Entity> copyTo(Task<Entity> task) {
    return task;
  }

}
