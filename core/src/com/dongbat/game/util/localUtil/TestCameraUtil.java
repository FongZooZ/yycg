/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util.localUtil;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * @author Admin
 */
public class TestCameraUtil {

  private static OrthographicCamera camera;

  public static OrthographicCamera getCamera() {
    if (camera == null) {
      float ratio = (float) Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
      int viewportX;
      int viewportY;
      if (ratio <= 1) {
        viewportX = Constants.PHYSIC_CAMERA.DEFAULT_MIN_VIEWPORT;
        viewportY = (int) (viewportX / ratio);
      } else {
        viewportY = Constants.PHYSIC_CAMERA.DEFAULT_MIN_VIEWPORT;
        viewportX = (int) (viewportY * ratio);
      }
      camera = new OrthographicCamera(PhysicsCameraUtil.getCamera().viewportWidth / 2, PhysicsCameraUtil.getCamera().viewportHeight / 2);
    }

    return camera;
  }

  public static float getRatio() {
    int width = Gdx.graphics.getWidth();
    int height = Gdx.graphics.getHeight();
    return (float) (width <= height ? width : height) / Constants.PHYSIC_CAMERA.DEFAULT_MIN_VIEWPORT;
  }
}
