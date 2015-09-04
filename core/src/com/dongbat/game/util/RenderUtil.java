package com.dongbat.game.util;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by FongZooZ on 8/29/2015.
 */
public class RenderUtil {

  private static SpriteBatch batch;

  public static SpriteBatch getBatch() {
    if (batch == null) {
      batch = new SpriteBatch();
    }
    return batch;
  }
}
