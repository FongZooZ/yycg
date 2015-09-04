/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

/**
 *
 * @author tao
 */
public class ScreenUtil {

  private static Game game;

  public static void setGame(Game game) {
    ScreenUtil.game = game;
  }

  public static void setScreen(Screen screen) {
    game.getScreen().dispose();
    game.setScreen(screen);
  }
}
