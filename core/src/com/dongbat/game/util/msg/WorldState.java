/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util.msg;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.dongbat.game.util.object.EntityState;

/**
 *
 * @author Admin
 */
public class WorldState {

  private long currentFrame;
  private ObjectMap<String, Object> worldData;
  private Array<EntityState> entityStates;

  public WorldState(long currentFrame, ObjectMap<String, Object> worldData, Array<EntityState> entityStates) {
    this.currentFrame = currentFrame;
    this.worldData = worldData;
    this.entityStates = entityStates;
  }

  public WorldState() {
    worldData = new ObjectMap<String, Object>();
    entityStates = new Array<EntityState>();
  }

  public long getCurrentFrame() {
    return currentFrame;
  }

  public void setCurrentFrame(long currentFrame) {
    this.currentFrame = currentFrame;
  }

  public ObjectMap<String, Object> getWorldData() {
    return worldData;
  }

  public void addWorldData(String s, Object o) {
    worldData.put(s, o);
  }

  public void addEntityState(EntityState state) {
    entityStates.add(state);
  }

  public void removedEntityState(EntityState state) {
    entityStates.removeValue(state, false);
  }

  public void setWorldData(ObjectMap<String, Object> worldData) {
    this.worldData = worldData;
  }

  public Array<EntityState> getEntityStates() {
    return entityStates;
  }

  public void setEntityStates(Array<EntityState> entityStates) {
    this.entityStates = entityStates;
  }

}
