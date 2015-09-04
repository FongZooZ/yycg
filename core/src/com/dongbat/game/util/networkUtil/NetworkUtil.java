/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util.networkUtil;

import com.dongbat.game.util.networkUtil.network.GameClient;
import com.dongbat.game.util.networkUtil.network.GameServer;

/**
 *
 * @author tao
 */
public class NetworkUtil {

  private static GameServer server;
  private static GameClient client;
  private static boolean inGame = false;

  public static final String BROADCAST_PREFIX = "hook-9ca63cc0-4a3e-11e5-885d-feff819cdc9f";

  public static boolean isInGame() {
    return inGame;
  }

  public static void setInGame(boolean inGame) {
    NetworkUtil.inGame = inGame;
  }

  public static GameClient getClient() {
    return client;
  }

  public static void setClient(GameClient client) {
    NetworkUtil.client = client;
  }

  public static boolean isHost() {
    return server != null;
  }

  public static GameServer getServer() {
    return server;
  }

  public static void setServer(GameServer server) {
    NetworkUtil.server = server;
  }

  public static void reset() {
    server = null;
    client = null;
    inGame = false;
  }
}
