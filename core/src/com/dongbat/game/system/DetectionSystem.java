/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.system;

import com.artemis.Entity;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.WorldQueryUtil;
import static com.dongbat.game.util.WorldQueryUtil.addNearestFood;
import static com.dongbat.game.util.WorldQueryUtil.addNearestPlayer;
import static com.dongbat.game.util.WorldQueryUtil.addNearestQueen;
import static com.dongbat.game.util.WorldQueryUtil.getAllPlayerAndAi;
import static com.dongbat.game.util.WorldQueryUtil.getAllQueen;
import static com.dongbat.game.util.localUtil.Constants.PHYSICS.MIN_DETECTION_RADIUS;

/**
 *
 * @author Admin
 */
public class DetectionSystem extends TimeSlicingSystem {

  public DetectionSystem(int frameSlicing) {
    super(frameSlicing);
  }

  @Override
  protected void processTimeSlice() {
    IntBag allPlayer = getAllPlayerAndAi(world);
    for (int i = 0; i < allPlayer.size(); i++) {
      Entity e = world.getEntity(allPlayer.get(i));

      Vector2 position = PhysicsUtil.getPosition(world, e);
      float radius = PhysicsUtil.getRadius(world, e);

      Array<Entity> foodList = WorldQueryUtil.findFoodInRadius(world, position, radius + MIN_DETECTION_RADIUS * modifier);
      if (foodList.size > 0) {
        //Add nearest player to Food
        for (Entity food : foodList) {
          addNearestPlayer(world, food, e);
        }
        //Add nearest food to player
        Entity nearestFood = WorldQueryUtil.findNearestEntityInList(world, position, foodList);
        addNearestFood(world, e, nearestFood);
      }

      Array<Entity> queenList = WorldQueryUtil.findQueenInRadius(world, position, radius + MIN_DETECTION_RADIUS * modifier);
      if (queenList.size > 0) {
        for (Entity queen : queenList) {
          addNearestPlayer(world, queen, e);
        }
        Entity nearestQueen = WorldQueryUtil.findNearestEntityInList(world, position, foodList);
        addNearestQueen(world, e, nearestQueen);
      }

      Array<Entity> playerAndAiList = WorldQueryUtil.findPlayerWithAiInRadius(world, e, position, radius + MIN_DETECTION_RADIUS * modifier);
      if (playerAndAiList.size > 0) {
        for (Entity playerOrAi : playerAndAiList) {
          addNearestPlayer(world, playerOrAi, e);
        }
        Entity nearestPlayer = WorldQueryUtil.findNearestEntityInList(world, position, foodList);
        addNearestPlayer(world, e, nearestPlayer);
      }
    }

    IntBag allQueen = getAllQueen(world);
    for (int i = 0; i < allQueen.size(); i++) {
      Entity e = world.getEntity(allQueen.get(i));
      Vector2 position = PhysicsUtil.getPosition(world, e);
      float radius = PhysicsUtil.getRadius(world, e);
      Array<Entity> playerAndAiList = WorldQueryUtil.findPlayerWithAiInRadius(world, e, position, radius * 1.5f + MIN_DETECTION_RADIUS * modifier);
      if (playerAndAiList.size > 0) {
        Entity nearestPlayer = WorldQueryUtil.findNearestEntityInList(world, position, playerAndAiList);
        addNearestPlayer(world, e, nearestPlayer);
      }
    }
  }

  private float modifier = 1;

  public float getModifier() {
    return modifier;
  }

  public void setModifier(float modifier) {
    this.modifier = modifier;
  }

}
