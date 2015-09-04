/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.system.localSystem;

import com.artemis.BaseSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import static com.dongbat.game.util.FoodSpawningUtil.scaleX;
import static com.dongbat.game.util.FoodSpawningUtil.scaleY;
import com.dongbat.game.util.RenderUtil;
import com.dongbat.game.util.localUtil.PhysicsCameraUtil;

/**
 *
 * @author Admin
 */
public class BorderRenderSystem extends BaseSystem {

  private Batch batch;
  private ShapeRenderer render;
  private OrthographicCamera camera;

  public BorderRenderSystem() {
    batch = RenderUtil.getBatch();
    render = new ShapeRenderer();
    camera = PhysicsCameraUtil.getCamera();
  }

  @Override
  protected void processSystem() {
    camera.update();
    render.setProjectionMatrix(camera.combined);
    render.begin(ShapeType.Filled);
    render.setColor(69f / 255f, 90f / 255f, 100f / 255f, 1);
    render.rect(-scaleX, -scaleY, 1, 2 * scaleY);
    render.rect(-scaleX, -scaleY, 2 * scaleX, 1);
    render.rect(scaleX, -scaleY, 1, 2 * scaleY);
    render.rect(-scaleX, scaleY, 2 * scaleX, 1);
    render.end();

  }

}
