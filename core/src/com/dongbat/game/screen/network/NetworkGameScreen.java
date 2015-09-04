/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.screen.network;

import com.badlogic.gdx.ScreenAdapter;
import com.dongbat.game.util.networkUtil.NetworkUtil;
import com.dongbat.game.util.networkUtil.network.GameClient;

/**
 *
 * @author implicit-invocation
 */
public class NetworkGameScreen extends ScreenAdapter {

  private GameClient client;

  public NetworkGameScreen(GameClient client) {
    this.client = client;
  }

  private float accumulate = 0;
  private float serverStep = 1f;

  @Override
  public void render(float delta) {
    if (!NetworkUtil.isInGame()) {
      return;
    }
//    if (NetworkUtil.isHost()) {
//      accumulate += delta;
//      while (accumulate > serverStep) {
//        NetworkUtil.getServer().process(serverStep);
//        accumulate -= serverStep;
//      }
//    }
    client.process(delta);
  }

  @Override
  public void dispose() {
    NetworkUtil.getServer().getWorld().dispose();
    client.getWorld().dispose();
  }

}
