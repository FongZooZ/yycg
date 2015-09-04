/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dongbat.game.stage.SkillButton;
import com.dongbat.game.util.InputUtil;

/**
 *
 * @author Admin
 */
public class PickSkillScreen extends ScreenAdapter {

  private Stage stage = new Stage();
  SkillButton button = new SkillButton("Flee");
  SkillButton button1 = new SkillButton("");
  SkillButton button2 = new SkillButton("Fee");
  SkillButton button3 = new SkillButton("Fee");
  SkillButton button4 = new SkillButton("Fee");
  SkillButton button5 = new SkillButton("Fee");
  SkillButton button6 = new SkillButton("Fee");
  SkillButton button7 = new SkillButton("Fee");

  @Override
  public void dispose() {
    stage.dispose();
    InputUtil.removeProcessor(stage);
  }

  @Override
  public void show() {
    stage.setViewport(new ExtendViewport(800, 480));
    Viewport viewport = stage.getViewport();
    float worldHeight = viewport.getWorldHeight();
    float worldWidth = viewport.getWorldWidth();
    stage.getViewport().update((int) worldWidth, (int) worldHeight);

    button.setPosition(-400, 120);
    button.setSize(80, 80);
    button1.setPosition(-280, 120);
    button1.setSize(80, 80);
    button2.setPosition(-160, 120);
    button2.setSize(80, 80);
    button3.setPosition(-40, 120);
    button3.setSize(80, 80);
    button4.setPosition(-400, 0);
    button4.setSize(80, 80);
    button5.setPosition(-280, 0);
    button5.setSize(80, 80);
    button6.setPosition(-160, 0);
    button6.setSize(80, 80);
    button7.setPosition(-40, 0);
    button7.setSize(80, 80);

    stage.addActor(button);
    stage.addActor(button1);
    stage.addActor(button2);
    stage.addActor(button3);
    stage.addActor(button4);
    stage.addActor(button5);
    stage.addActor(button6);
    stage.addActor(button7);
    InputUtil.addProcessor(stage, 0);
  }

  @Override
  public void render(float delta) {
    stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);

    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    stage.act();
    stage.draw();
  }

}
