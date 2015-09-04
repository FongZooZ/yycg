package com.dongbat.game.util;

import com.artemis.World;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Created by FongZooZ on 9/2/2015. Contain world and its status. True = game
 * ended, otherwise false
 */
public class GameUtil {

  private static ObjectMap<World, Boolean> gameStatus;

  public static void init() {
    if (gameStatus == null) {
      gameStatus = new ObjectMap<World, Boolean>();
    }
  }

  public static void addGame(World world) {
    gameStatus.put(world, false);
  }

  public static void endGame(World world) {
    gameStatus.put(world, true);
  }

  public static boolean isOver(World world) {
    return gameStatus.get(world);
  }
}
