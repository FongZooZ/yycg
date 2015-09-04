/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/**
 *
 * @author tao
 */
public class CameraUtil {

  private static OrthographicCamera camera;
  private static ExtendViewport viewport;
  public static final int MIN_WIDTH = 80;
  public static final int MIN_HEIGHT = 48;

  public static void init() {
    camera = new OrthographicCamera();
    viewport = new ExtendViewport(MIN_WIDTH, MIN_HEIGHT, camera);
    viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
  }

  public static OrthographicCamera getCamera() {
    if (camera == null) {
      init();
    }
    return camera;
  }

  public static ExtendViewport getViewport() {
    if (viewport == null) {
      init();
    }
    return viewport;
  }

  public static void update(int width, int height) {
    viewport.update(width, height);
    camera.update();
  }

}
