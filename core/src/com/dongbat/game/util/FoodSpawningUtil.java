/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.World;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.game.component.Food;
import com.dongbat.game.util.factory.EntityFactory;
import com.dongbat.game.util.localUtil.Constants;

/**
 * @author Admin
 */
public class FoodSpawningUtil {

  public static final float scaleX = Constants.GAME.FRAME_WIDTH;
//  public static final float scaleX = 100;
  public static final float scaleY = Constants.GAME.FRAME_HEIGHT;
//  public static final float scaleY = 100;

  /**
   * Spawn food to the map of the world
   *
   * @param world artemis world
   */
  public static void spawnFood(World world) {
    if (reachMaxFood(world) || ECSUtil.getFrame(world) % 1 != 0) {
      return;
    }

    float width = (float) (ECSUtil.getRandom(world).getFloat(0, 1) * scaleX * 2 - scaleX);
    float height = (float) (ECSUtil.getRandom(world).getFloat(0, 1) * scaleY * 2 - scaleY);
    for (int i = 0; i < 1; i++) {
      EntityFactory.createFood(world, new Vector2(width, height), null);
    }

  }

  /**
   * Check food in the map is maximum or not
   *
   * @param world artemis world
   * @return true if reach maximum food
   */
  private static boolean reachMaxFood(World world) {
    int size = world.getManager(AspectSubscriptionManager.class).get(Aspect.all(Food.class)).getEntities().size();
    return size > 10;
  }
}
