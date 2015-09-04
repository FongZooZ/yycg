/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.screen.network;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.dongbat.game.util.ECSUtil;
import com.dongbat.game.util.networkUtil.KryoUtil;
import com.dongbat.game.util.networkUtil.NetworkUtil;
import com.dongbat.game.util.networkUtil.network.GameClient;
import com.dongbat.game.util.networkUtil.network.GameServer;
import com.dongbat.game.util.object.CustomWorld;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.KryoSerialization;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author implicit-invocation
 */
public class TestNetworkScreen extends ScreenAdapter {

  private Client client;
  private static final String[] loadings = {
    "loading", "loading.", "loading..", "loading..."
  };
  private Batch batch;
  private BitmapFont font;
  private boolean loadingDone = false;
  private boolean connected = false;
  private InetAddress hostAddress;
  private long playerJoined = 0;
  private GameServer gameServer;
  private boolean host = false;
  private Server server;
  private boolean starting = false;

  public TestNetworkScreen() {
  }

  @Override
  public void show() {
    NetworkUtil.reset();
    batch = new SpriteBatch();
    font = new BitmapFont();
    //
    client = new Client(65535, 65535, new KryoSerialization(KryoUtil.newKryo()));
    client.addListener(new Listener() {

      @Override
      public void received(Connection connection, Object object) {
        if (object instanceof Long) {
          playerJoined = (Long) object;
        }
      }

    });
    client.start();
    new Thread(new Runnable() {

      @Override
      public void run() {
        final InetAddress discoverHost = client.discoverHost(54777, 1000);
        Gdx.app.postRunnable(new Runnable() {

          @Override
          public void run() {
            loadingDone = true;
            hostAddress = discoverHost;
          }
        });
      }
    }).start();
  }

  @Override
  public void render(float delta) {
    if (!loadingDone) {
      int i = (int) (TimeUtils.millis() / 1000 % 4);
      String msg = loadings[i];
      batch.begin();
      font.draw(batch, msg, 100, 100);
      batch.end();

      return;
    }

    if (!connected) {
      if (hostAddress == null) {
        createServer();
      } else {
        try {
          client.connect(1000, hostAddress, 54555, 54777);
          GameClient gameClient = new GameClient(client);
          NetworkUtil.setClient(gameClient);
          connected = true;
        } catch (IOException ex) {
          Logger.getLogger(TestNetworkScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      return;
    }

    if (connected) {
      batch.begin();
      String msg = hostAddress.toString() + ": " + playerJoined + " players";
      if (host) {
        msg += "\ntap the screen to start";
      }
      font.draw(batch, msg, 100, 100);
      batch.end();
    }

    if (host) {
      if (Gdx.input.isTouched() && !starting) {

        starting = true;
        startGame();
      }
    }
  }

  private void createServer() {
    server = new Server(65535, 65535, new KryoSerialization(KryoUtil.newKryo()));
    try {
      server.bind(54555, 54777);
      host = true;
      hostAddress = InetAddress.getByName("localhost");
    } catch (IOException ex) {
      Logger.getLogger(TestNetworkScreen.class.getName()).log(Level.SEVERE, null, ex);
    }
    server.addListener(new Listener() {

      @Override
      public void connected(Connection connection) {
        server.sendToAllUDP(new Long(server.getConnections().length));
      }

    });
    gameServer = new GameServer(server, 200);
    NetworkUtil.setServer(gameServer);
    server.start();
  }

  private void startGame() {
    gameServer.startGame((CustomWorld) ECSUtil.createWorld(true));
  }

}
