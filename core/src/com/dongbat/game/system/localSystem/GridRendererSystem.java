/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.system.localSystem;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.dongbat.game.util.localUtil.PhysicsCameraUtil;

/**
 *
 * @author Admin
 */
public class GridRendererSystem extends BaseSystem {

  ShapeRenderer renderer;

  @Override
  protected void initialize() {
    renderer = new ShapeRenderer();
  }

  @Override
  protected void processSystem() {
    OrthographicCamera camera = PhysicsCameraUtil.getCamera();
    renderer.setProjectionMatrix(camera.combined);
    Vector3 topLeft = camera.unproject(new Vector3(0, 0, 0));
    Vector3 bottomRight = camera.unproject(new Vector3(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0));
    int gridSize = 10;
    float minX = (float) Math.floor(topLeft.x / gridSize) * gridSize;
    float maxX = (float) Math.ceil(bottomRight.x / gridSize) * gridSize;
    float minY = (float) Math.floor(bottomRight.y / gridSize) * gridSize;
    float maxY = (float) Math.ceil(topLeft.y / gridSize) * gridSize;

    renderer.begin(ShapeRenderer.ShapeType.Line);
    renderer.setColor(0.15f, 0.15f, 0.15f, 1);
    for (float i = minX; i < maxX; i += gridSize) {
      renderer.line(i, minY, i, maxY);
    }

    for (float i = minY; i < maxY; i += gridSize) {
      renderer.line(minX, i, maxX, i);
    }

    renderer.end();
  }

}
