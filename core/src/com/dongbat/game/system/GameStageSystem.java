/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.system;

import com.artemis.BaseSystem;
import com.artemis.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dongbat.game.component.UnitMovement;
import com.dongbat.game.stage.AbilityButton;
import com.dongbat.game.stage.GameEndedWindow;
import com.dongbat.game.system.localSystem.LocalInputSystem;
import com.dongbat.game.util.AssetUtil;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.GameUtil;
import com.dongbat.game.util.InputUtil;
import com.dongbat.game.util.StageUtil;
import com.dongbat.game.util.UuidUtil;
import com.dongbat.game.util.localUtil.LocalPlayerUtil;
import java.util.UUID;

/**
 * @author Admin
 */
public class GameStageSystem extends BaseSystem {

  private Stage stage = new Stage();
  private Skin skin = StageUtil.getSkin();
//  private Label title = new Label("Full gameplay is coming soon, with more skills, multiplayer feature over WIFI and internet", skin);
  private Touchpad touchpad;
  private AbilityButton buttonFleeAbility;
  private AbilityButton buttonBlowAbility;
  private AbilityButton buttonSplitAbility;
  private AbilityButton buttonVacuumAbility;
  private GameEndedWindow gameEndedWindow;

  public GameStageSystem() {
  }

  @Override
  protected void dispose() {
    InputUtil.removeProcessor(stage);
  }

  @Override
  protected void initialize() {
    Viewport viewport = stage.getViewport();
    float worldHeight = viewport.getWorldHeight();
    float worldWidth = viewport.getWorldWidth();
    float width = worldWidth / 60;
    float height = worldHeight / 60;

    buttonFleeAbility = new AbilityButton("Flee", skin, "flee");
    buttonFleeAbility.setSize(width * 7, width * 7);
    buttonFleeAbility.setPosition(worldWidth - width * 7, height * 2);
    buttonBlowAbility = new AbilityButton("HotBlow", skin, "blow");
    buttonBlowAbility.setSize(width * 7, width * 7);
    buttonBlowAbility.setPosition(worldWidth - width * 7, height * 2 + width * 7);
    buttonSplitAbility = new AbilityButton("SplitAndJoin", skin, "split");
    buttonSplitAbility.setSize(width * 7, width * 7);
    buttonSplitAbility.setPosition(worldWidth - width * 7, height * 2 + width * 14);
    buttonVacuumAbility = new AbilityButton("Vacuum", skin, "vacuum");
    buttonVacuumAbility.setSize(width * 7, width * 7);
    buttonVacuumAbility.setPosition(worldWidth - width * 7, height * 2 + width * 21);

//    title.setPosition(worldWidth - width * 30, worldHeight - height * 2);
    Touchpad.TouchpadStyle touchpadStyle = new Touchpad.TouchpadStyle(skin.get(Touchpad.TouchpadStyle.class));
    SpriteDrawable bg = new SpriteDrawable(new Sprite(AssetUtil.joyBg));
    bg.setMinWidth(worldHeight / 2.5f);
    bg.setMinHeight(worldHeight / 2.5f);
    touchpadStyle.background = bg;

    SpriteDrawable knob = new SpriteDrawable(new Sprite(AssetUtil.joyKnob));
    knob.setMinWidth(worldHeight / 5f);
    knob.setMinHeight(worldHeight / 5f);
    touchpadStyle.knob = knob;

    touchpad = new Touchpad(10, touchpadStyle);
    touchpad.setBounds(15, 15, 200, 200);

    touchpad.setSize(worldHeight / 2.5f, worldHeight / 2.5f);

    stage.addActor(touchpad);
    stage.addActor(buttonVacuumAbility);
    stage.addActor(buttonSplitAbility);
    stage.addActor(buttonBlowAbility);
    stage.addActor(buttonFleeAbility);
//    stage.addActor(title);
    InputUtil.addProcessor(stage, 0);
  }

  private boolean ended = false;

  @Override
  protected void processSystem() {
    stage.act();

    if (GameUtil.isOver(world) && !ended) {
      ended = true;
      gameEndedWindow = new GameEndedWindow(skin);
      gameEndedWindow.setSize(stage.getWidth(), stage.getHeight());
      stage.addActor(gameEndedWindow);
      world.getSystem(LocalInputSystem.class).setEnabled(false);
      world.getSystem(Box2dSystem.class).setEnabled(false);

      return;
    }
    if (touchpad.isTouched()) {
      float x = touchpad.getKnobPercentX();
      float y = touchpad.getKnobPercentY();

      UUID localPlayerId = LocalPlayerUtil.getLocalPlayer();
      Entity e = UuidUtil.getEntityByUuid(world, localPlayerId);
      if (e == null || localPlayerId == null) {
        return;
      }
      UnitMovement move = EntityUtil.getComponent(world, e, UnitMovement.class);
      move.setDirectionVelocity(new Vector2(x, y));
    }

//    if (WorldQueryUtil.getAllQueen(world).size() == 0) {
//      stage.addActor(gameEndedWindow);
//    }
    stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    stage.draw();
  }

}
