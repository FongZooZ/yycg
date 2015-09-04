/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/**
 *
 * @author Admin
 */
public class TestScreen implements Screen {

  private Stage stage = new Stage();
  private Skin skin = new Skin(Gdx.files.internal("skins/uiskin.json"), new TextureAtlas(Gdx.files.internal("skins/uiskin.atlas")));
  private Table table = new Table();

  private TextButton buttonPlay = new TextButton("Play", skin);
  private TextButton buttonExit = new TextButton("Exit", skin);
  private Label title = new Label("Game Title", skin);

  public TestScreen() {
  }

  @Override
  public void show() {
    stage.setViewport(new ExtendViewport(800, 480));
    buttonPlay.addListener(new ClickListener() {

      @Override
      public void clicked(InputEvent event, float x, float y) {
      }

    });

    buttonExit.addListener(new ClickListener() {

      @Override
      public void clicked(InputEvent event, float x, float y) {
      }

    });
    stage.setDebugAll(true);
    table.add(title).padBottom(40).row();
    table.add(buttonPlay).size(800, 60).expand(false, false).padBottom(20).align(Align.center).row();
    table.add(buttonExit).size(800, 60).padBottom(20).row();

    table.setWidth(800);
    table.setHeight(480);
    stage.addActor(table);

    Gdx.input.setInputProcessor(stage);
  }

  @Override
  public void render(float f) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    stage.act();
    stage.draw();
  }

  @Override
  public void resize(int i, int i1) {
    stage.getViewport().update(i1, i1, true);
  }

  @Override
  public void pause() {
  }

  @Override
  public void resume() {
  }

  @Override
  public void hide() {
  }

  @Override
  public void dispose() {
    stage.dispose();
    skin.dispose();
  }

}
