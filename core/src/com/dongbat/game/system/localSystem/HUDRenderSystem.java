/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.system.localSystem;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dongbat.game.util.ECSUtil;
import com.dongbat.game.util.PhysicsUtil;

/**
 *
 * @author Admin
 */
public class HUDRenderSystem extends BaseSystem {

  private final BitmapFont bitmapFont;
  private final SpriteBatch batch;

  public HUDRenderSystem() {
    bitmapFont = new BitmapFont();
    batch = new SpriteBatch();
  }

  @Override
  protected void begin() {
    batch.begin();
  }

  @Override
  protected void processSystem() {
//    UUID localPlayerId = LocalPlayerUtil.getLocalPlayer(world);
//    Entity localPlayer = UuidUtil.getEntityByUuid(world, localPlayerId);
//    if (localPlayer == null) {
//      return;
//    }
    bitmapFont.draw(batch, "fps: " + Gdx.graphics.getFramesPerSecond(), 100, 100);
    bitmapFont.draw(batch, "body count: " + PhysicsUtil.getPhysicsWorld(world).getBodyCount(), 100, 75);
//    bitmapFont.draw(batch, "speed: " + MovementUtil.calculalteDesiredSpeed(world, localPlayer), 100, 25);
//    bitmapFont.draw(batch, "zoom: " + PhysicsCameraUtil.getZoomScale(world, localPlayer), 100, 50);
    bitmapFont.draw(batch, "frame " + ECSUtil.getFrame(world), 100, 125);

//    Vector2 queenPosition = EntityUtil.getQueenPosition(world);
//    if (queenPosition != null) {
//      Vector2 position = PhysicsUtil.getPosition(world, localPlayer);
//      bitmapFont.draw(batch, "queen pos " + queenPosition, 200, 25);
//    }
  }

  @Override
  protected void end() {
    batch.end();
  }

}
