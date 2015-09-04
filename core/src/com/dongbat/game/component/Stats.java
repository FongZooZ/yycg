/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.component;

import com.artemis.Component;
import com.badlogic.gdx.utils.ObjectMap;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class Stats extends Component {

  private float baseRateSpeed;
  private float baseMovementSpeed;
  private float modifierSpeed;
  private boolean allowComsumming = true;
  private boolean consumable = true;
  private UUID parent = null;
  private ObjectMap<String, Object> userData;
  private boolean feedParent = false;

  public boolean isFeedParent() {
    return feedParent;
  }

  public void setFeedParent(boolean feedParent) {
    this.feedParent = feedParent;
  }

  public Stats() {
  }

  public UUID getParent() {
    return parent;
  }

  public void setParent(UUID parent) {
    this.parent = parent;
  }

  public boolean isAllowComsumming() {
    return allowComsumming;
  }

  public void setAllowComsumming(boolean allowComsumming) {
    this.allowComsumming = allowComsumming;
  }

  public boolean isConsumable() {
    return consumable;
  }

  public void setConsumable(boolean consumable) {
    this.consumable = consumable;
  }

  public ObjectMap<String, Object> getUserData() {
    return userData;
  }

  public void setUserData(ObjectMap<String, Object> userData) {
    this.userData = userData;
  }

  public float getBaseMovementSpeed() {
    return baseMovementSpeed;
  }

  public void setBaseMovementSpeed(float baseMovementSpeed) {
    this.baseMovementSpeed = baseMovementSpeed;
  }

  public float getModifierSpeed() {
    return modifierSpeed;
  }

  public float getBaseRateSpeed() {
    return baseRateSpeed;
  }

  public void setBaseRateSpeed(float baseRateSpeed) {
    this.baseRateSpeed = baseRateSpeed;
  }

  public void setModifierSpeed(float modifierSpeed) {
    this.modifierSpeed = modifierSpeed;
  }

}
