package com.dongbat.game.unit;

/**
 * Created by FongZooZ on 7/14/2015.
 */
public class UnitInfo {

  private String name;
  private float radius;
  private float baseSpeedRate;
  private String abilities;
  private String definitionPath;

  public UnitInfo() {
  }

  public UnitInfo(String name, float radius, float baseSpeedRate, String abilities, String className) {
    this.radius = radius;
    this.baseSpeedRate = baseSpeedRate;
    this.abilities = abilities;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public float getRadius() {
    return radius;
  }

  public void setRadius(float radius) {
    this.radius = radius;
  }

  public float getBaseSpeedRate() {
    return baseSpeedRate;
  }

  public void setBaseSpeedRate(float baseSpeedRate) {
    this.baseSpeedRate = baseSpeedRate;
  }

  public String getAbilities() {
    return abilities;
  }

  public void setAbilities(String abilities) {
    this.abilities = abilities;
  }

  public String getDefinitionPath() {
    return definitionPath;
  }

  public void setDefinitionPath(String definitionPath) {
    this.definitionPath = definitionPath;
  }
}
