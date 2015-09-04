/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.component;

import com.artemis.Component;
import com.badlogic.gdx.utils.ObjectMap;
import com.dongbat.game.dataobject.CustomInput;

/**
 *
 * @author Admin
 */
public class Player extends Component {

  private ObjectMap<Long, CustomInput> inputs = new ObjectMap<Long, CustomInput>();

  private boolean beingStatic = false;
  private long lastDynamic = 0;

  private boolean beingDisabled = false;
  private long lastEnable = 0;

  private boolean goingToRespawn = true;

  public Player() {
  }

  public Player(boolean willRespawn) {
    this.goingToRespawn = willRespawn;
  }

  public boolean isGoingToRespawn() {
    return goingToRespawn;
  }

  public void setGoingToRespawn(boolean goingToRespawn) {
    this.goingToRespawn = goingToRespawn;
  }

  public ObjectMap<Long, CustomInput> getInputs() {
    return inputs;
  }

  public void setBeingStatic(boolean beingStatic) {
    this.beingStatic = beingStatic;
  }

  public void setLastDynamic(long lastDynamic) {
    this.lastDynamic = lastDynamic;
  }

  public boolean isBeingStatic() {
    return beingStatic;
  }

  public long getLastDynamic() {
    return lastDynamic;
  }

  public boolean isBeingDisabled() {
    return beingDisabled;
  }

  public void setBeingDisabled(boolean beingDisabled) {
    this.beingDisabled = beingDisabled;
  }

  public long getLastEnable() {
    return lastEnable;
  }

  public void setLastEnable(long lastEnable) {
    this.lastEnable = lastEnable;
  }

}
