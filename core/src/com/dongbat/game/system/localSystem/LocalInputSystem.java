/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.system.localSystem;

import com.artemis.BaseSystem;
import com.artemis.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.dongbat.game.component.Player;
import com.dongbat.game.component.UnitMovement;
import com.dongbat.game.dataobject.CustomInput;
import com.dongbat.game.util.AbilityUtil;
import com.dongbat.game.util.ECSUtil;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.InputUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.UuidUtil;
import com.dongbat.game.util.localUtil.LocalPlayerUtil;
import static com.dongbat.game.util.localUtil.PhysicsCameraUtil.getCamera;
import java.util.UUID;

/**
 * @author Admin
 */
public class LocalInputSystem extends BaseSystem implements InputProcessor {

  private Entity e;
  public static boolean touchDown;
  public static boolean skillOne;
  public static boolean skillTwo;
  public static boolean skillThree;
  public static boolean skillFour;
  public static boolean pause;

  public LocalInputSystem() {
    touchDown = false;
    skillOne = false;
    skillTwo = false;
    pause = false;
    InputUtil.addProcessor(this, 0);
  }

  @Override
  protected void dispose() {
    InputUtil.removeProcessor(this);
  }

  @Override
  protected void processSystem() {
    UUID localPlayerId = LocalPlayerUtil.getLocalPlayer();
    e = UuidUtil.getEntityByUuid(world, localPlayerId);
    if (e == null || localPlayerId == null) {
      return;
    }
    UnitMovement move = EntityUtil.getComponent(world, e, UnitMovement.class);
    Vector2 destination = move.getDirectionVelocity();
    Vector2 position = PhysicsUtil.getPosition(world, e);
    if (touchDown) {
      if (ECSUtil.getFrame(world) - EntityUtil.getLastPlayerMovementInput(world, e) > 10) {
        Vector3 vector = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        Vector3 unproject = getCamera().unproject(vector);
        long lastFrameIndex = ECSUtil.getFrame(world);
        Vector2 direction = new Vector2(unproject.x - position.x, unproject.y - position.y);
        CustomInput customInput = new CustomInput(direction);
        EntityUtil.getComponent(world, e, Player.class).getInputs().put(lastFrameIndex + 3, customInput);
      }
    }
    if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
      AbilityUtil.use(world, e, "Flee", destination);
    }
    if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
      AbilityUtil.use(world, e, "HotBlow", destination);
    }
    if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
      AbilityUtil.use(world, e, "SplitAndJoin", destination);
    }
    if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
      AbilityUtil.use(world, e, "Vacuum", destination);
    }
  }

  @Override
  public boolean keyDown(int keycode) {
    if (Input.Keys.F1 == keycode) {
      ECSUtil.getWorldProgress(world).setSave(true);
    }
    if (Input.Keys.F2 == keycode) {
      ECSUtil.getWorldProgress(world).setLoad(true);
    }
    if (Input.Keys.Q == keycode) {
      skillOne = true;
//      BuffUtil.addBuff(world, e, e, "SpeedUp", 1000, 2);
    }
    if (Input.Keys.W == keycode) {
      skillTwo = true;
      if (e == null) {
        return false;
      }
//      Body body = PhysicsUtil.getBody(world, e);
//      Array<Entity> players = findUnitAndPlayerInRadius(world, body.getPosition(), 100);
//      for (Entity player : players) {
//        System.out.println(player);
//        if (player.equals(e)) {
//          continue;
//        }
//        BuffUtil.addBuff(world, e, player, "SlowDown", 2000, 2);
//
//      }
    }
    if (Input.Keys.E == keycode) {
//			BuffUtil.addBuff(world, e, e, "DuplicateOnTouch", 2000, 2);
    }
    return true;
  }

  @Override
  public boolean keyUp(int keycode) {
    return true;
  }

  @Override
  public boolean keyTyped(char character) {
    return true;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    touchDown = true;
    return true;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    touchDown = false;
    return true;
  }

  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    return true;
  }

  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    return true;
  }

  @Override
  public boolean scrolled(int amount) {
    return true;
  }

}
