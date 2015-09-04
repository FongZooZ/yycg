package com.dongbat.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.dongbat.game.registry.AbilityRegistry;
import com.dongbat.game.registry.BuffRegistry;
import com.dongbat.game.registry.UnitRegistry;
import com.dongbat.game.screen.GameScreen;
import com.dongbat.game.screen.SplashScreen;
import com.dongbat.game.util.AssetUtil;
import com.dongbat.game.util.GameUtil;
import com.dongbat.game.util.InputUtil;
import com.dongbat.game.util.ScreenUtil;

public class YCG extends Game {

  @Override
  public void create() {
    ScreenUtil.setGame(this);
    UnitRegistry.load();
    BuffRegistry.load();
    AbilityRegistry.load();
    InputUtil.init();
    GameUtil.init();
    resume();
    setScreen(new GameScreen());
  }

  @Override
  public void render() {

    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    super.render();
  }

  @Override
  public void resume() {
    boolean done = false;
    AssetUtil.loadAsset();
    while (!done) {
      done = AssetUtil.update();
    }
  }
}
