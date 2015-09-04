/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util.localUtil;

import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

/**
 * @author Admin
 */
public class DebugRendererFactory {

  private static Box2DDebugRenderer debugRenderer;

  /**
   * Initialize box2d debug renderer
   */
  public static void init() {
    if (debugRenderer == null) {
      debugRenderer = new Box2DDebugRenderer();
    }
  }

  /**
   * Get box2d debug renderer
   *
   * @return
   */
  public static Box2DDebugRenderer getDebugRenderer() {
    if (debugRenderer == null) {
      init();
    }
    return debugRenderer;
  }
}
