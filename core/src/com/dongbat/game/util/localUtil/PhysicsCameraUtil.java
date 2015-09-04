/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util.localUtil;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.dongbat.game.util.PhysicsUtil;
import static com.dongbat.game.util.localUtil.Constants.PHYSIC_CAMERA.DEFAULT_MIN_VIEWPORT;

/**
 * @author Admin
 */
public class PhysicsCameraUtil {

  private static OrthographicCamera camera;

  public static OrthographicCamera getCamera() {
    if (camera == null) {
      float ratio = (float) Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
      int viewportX;
      int viewportY;
      if (ratio <= 1) {
        viewportX = DEFAULT_MIN_VIEWPORT;
        viewportY = (int) (viewportX / ratio);
      } else {
        viewportY = DEFAULT_MIN_VIEWPORT;
        viewportX = (int) (viewportY * ratio);
      }
      camera = new OrthographicCamera(viewportX / 20, viewportY / 20);
    }
    return camera;
  }

  public static float getRatio() {
    int width = Gdx.graphics.getWidth();
    int height = Gdx.graphics.getHeight();
    return (float) (width <= height ? width : height) / DEFAULT_MIN_VIEWPORT;
  }

  public static float getZoomScale(World world, Entity e) {
    float zoom;
    float collisionRadius = PhysicsUtil.getRadius(world, e);
    zoom = 10 + collisionRadius * 8 / 20;
    if (zoom >= 20) {
      zoom = 20;
    }
    return zoom;
  }
}
