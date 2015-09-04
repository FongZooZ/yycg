package com.dongbat.game.system.localSystem;

import com.artemis.BaseSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.game.util.AssetUtil;
import com.dongbat.game.util.RenderUtil;
import com.dongbat.game.util.localUtil.PhysicsCameraUtil;
import com.rahul.libgdx.parallax.ParallaxBackground;
import com.rahul.libgdx.parallax.TextureRegionParallaxLayer;

/**
 * Created by FongZooZ on 8/30/2015. Create Parallax Background Layer system
 */
public class ParallaxBackgroundSystem extends BaseSystem {

  private SpriteBatch batch;
  private OrthographicCamera camera;
  private ParallaxBackground background;

  public ParallaxBackgroundSystem() {
    batch = RenderUtil.getBatch();

    camera = PhysicsCameraUtil.getCamera();
    background = new ParallaxBackground();
    float worldWidth = camera.viewportWidth;
    float worldHeight = camera.viewportHeight;

    background.addLayers(new TextureRegionParallaxLayer(new TextureRegion(AssetUtil.bg00), worldWidth * 10, worldHeight * 10, new Vector2(.3f, .3f)));
    background.addLayers(new TextureRegionParallaxLayer(new TextureRegion(AssetUtil.bg01), worldWidth * 10, worldHeight * 10, new Vector2(.4f, .4f)));
//        background.addLayers(new TextureRegionParallaxLayer(new TextureRegion(new Texture(Gdx.files.internal("grid.png"))), worldWidth, worldHeight, new Vector2(.5f, .5f)));
    background.addLayers(new TextureRegionParallaxLayer(new TextureRegion(AssetUtil.bg02), worldWidth * 10, worldHeight * 10, new Vector2(.5f, .5f)));
    background.addLayers(new TextureRegionParallaxLayer(new TextureRegion(AssetUtil.bg03), worldWidth * 10, worldHeight * 10, new Vector2(.6f, .6f)));
  }

  /**
   * Any implementing entity system must implement this method.
   */
  @Override
  protected void processSystem() {
    camera.update();
    batch.begin();
    batch.setProjectionMatrix(camera.combined);
    background.draw(camera, batch);
    batch.end();
  }
}
