/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.dongbat.game.util.AssetUtil;
import com.dongbat.game.util.CameraUtil;
import com.dongbat.game.util.RenderUtil;
import com.dongbat.game.util.ScreenUtil;

/**
 * @author tao
 */
public class SplashScreen extends ScreenAdapter {

  private final Sprite logo;

  private final OrthographicCamera camera;
  private final SpriteBatch batch;
  private final float backgroundRatio = 872f / 352;
  private final Sprite background;

  private static final long DURATION = 3000;
  private long startTime = 0;
  private boolean done = false;

  public SplashScreen() {
    camera = CameraUtil.getCamera();
    batch = RenderUtil.getBatch();
    logo = new Sprite(AssetUtil.logo);
    logo.setSize(CameraUtil.MIN_WIDTH * .75f, CameraUtil.MIN_WIDTH * .75f / backgroundRatio);
    logo.setPosition(-logo.getWidth() / 2, -logo.getHeight() / 2);

    Pixmap pm1 = new Pixmap(1, 1, Format.RGB565);
    pm1.setColor(Color.WHITE);
    pm1.fill();
    background = new Sprite(new Texture(pm1));
    background.setSize(CameraUtil.MIN_WIDTH, CameraUtil.MIN_HEIGHT);
    background.setPosition(-background.getWidth() / 2, -background.getHeight() / 2);

    startTime = TimeUtils.millis();

    AssetUtil.loadAsset();
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(1, 1, 1, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    batch.setProjectionMatrix(camera.combined);
    batch.begin();
    background.draw(batch);
    logo.draw(batch);
    batch.end();

    long elapsed = TimeUtils.timeSinceMillis(startTime);
    if (!done) {
      done = AssetUtil.update();
    }

    if (elapsed > DURATION && done) {
      ScreenUtil.setScreen(new DbScreen());
    }
  }

  @Override
  public void dispose() {
    logo.getTexture().dispose();
    background.getTexture().dispose();
  }

}
