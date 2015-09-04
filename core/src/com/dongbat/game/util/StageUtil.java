package com.dongbat.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by FongZooZ on 9/2/2015. To store misc things of stage
 */
public class StageUtil {

  private static Skin skin;
  private static Stage stage;

  public static void init() {
    if (stage == null) {
      stage = new Stage();
    }
  }

  public static Stage getStage() {
    if (stage == null) {
      stage = new Stage();
    }
    return stage;
  }

  public static Skin getSkin() {

    if (skin == null) {
      skin = new Skin(Gdx.files.internal("skins/uiskin.json"), new TextureAtlas(Gdx.files.internal("skins/uiskin.atlas")));
    }
    return skin;
  }
}
