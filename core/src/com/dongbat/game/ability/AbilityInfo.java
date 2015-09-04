/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.ability;

/**
 *
 * @author Admin
 */
public class AbilityInfo {

  private Ability ability;
  private int level;
  private int cooldown;
  private long lastFrameCast = -1;

  public AbilityInfo() {
  }

  public AbilityInfo(Ability ability, int level, int cooldown) {
    this.ability = ability;
    this.level = level;
    this.cooldown = cooldown;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public Ability getAbility() {
    return ability;
  }

  public void setAbility(Ability ability) {
    this.ability = ability;
  }

  public int getCooldown() {
    return cooldown;
  }

  public void setCooldown(int cooldown) {
    this.cooldown = cooldown;
  }

  public long getLastCast() {
    return lastFrameCast;
  }

  public void setLastCast(long lastCast) {
    this.lastFrameCast = lastCast;
  }
}
