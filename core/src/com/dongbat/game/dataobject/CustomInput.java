/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.dataobject;

import com.badlogic.gdx.math.Vector2;
import com.dongbat.game.util.localUtil.Constants.InputType;

/**
 *
 * @author Admin
 */
public class CustomInput {

  private InputType type;
  private Vector2 position;
  private String abilityName;

  public CustomInput() {
  }

  public CustomInput(Vector2 position) {
    this.position = position;
    type = InputType.MOVE;
  }

  public CustomInput(String abilityName, Vector2 position) {
    this.type = InputType.ABILITY;
    this.position = position;
    this.abilityName = abilityName;
  }

  public InputType getType() {
    return type;
  }

  public void setType(InputType type) {
    this.type = type;
  }

  public String getAbilityName() {
    return abilityName;
  }

  public void setAbilityName(String abilityName) {
    this.abilityName = abilityName;
  }

  public Vector2 getPosition() {
    return position;
  }

  public void setPosition(Vector2 position) {
    this.position = position;
  }

}
