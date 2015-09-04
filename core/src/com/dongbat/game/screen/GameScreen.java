/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.screen;

import com.artemis.ArtemisMultiException;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.game.registry.AbilityRegistry;
import com.dongbat.game.registry.BuffRegistry;
import com.dongbat.game.registry.UnitRegistry;
import com.dongbat.game.util.AdUtil;
import com.dongbat.game.util.BuffUtil;
import com.dongbat.game.util.ECSUtil;
import com.dongbat.game.util.GameUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.UuidUtil;
import com.dongbat.game.util.factory.EntityFactory;
import com.dongbat.game.util.factory.UnitFactory;
import com.dongbat.game.util.localUtil.LocalPlayerUtil;

/**
 *
 * @author Admin
 */
public class GameScreen implements Screen {

  private final World world;

  public GameScreen() {
    AdUtil.hideAd();
    world = ECSUtil.createWorld();
    UnitRegistry.load();
    BuffRegistry.load();
    AbilityRegistry.load();
    ECSUtil.init(world);
    PhysicsUtil.init(world);
    Entity localPlayer = EntityFactory.createPlayer(world, new Vector2(0, 80), "phong");
//    BuffUtil.addBuff(world, localPlayer, localPlayer, "AttractFood", -1, 1);
    BuffUtil.addBuff(world, localPlayer, localPlayer, "FeedSmaller", -1, 1);
    LocalPlayerUtil.setLocalPlayer(UuidUtil.getUuid(localPlayer));
    LocalPlayerUtil.setLocalWorld(world);

    UnitFactory.createQueen(world, new Vector2(0, 20), 20);
    // UnitFactory.createQueen(world, new Vector2(0, 20), 20);

    GameUtil.addGame(world);
    UnitFactory.createUnit(world, "phong", new Vector2(10, 60));
    UnitFactory.createUnit(world, "bao", new Vector2(10, -60));
    UnitFactory.createUnit(world, "phong", new Vector2(100, -60));
  }

  @Override
  public void show() {
  }

  @Override
  public void render(float f) {
    ECSUtil.process(world, f);
  }

  @Override
  public void resize(int i, int i1) {
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
    try {
      world.dispose();
    } catch (Exception ex) {
      ex.printStackTrace();
      if (ex instanceof ArtemisMultiException) {
        for (Throwable exception : ((ArtemisMultiException) ex).getExceptions()) {
          exception.printStackTrace();
        }
      }
    }
  }

}
