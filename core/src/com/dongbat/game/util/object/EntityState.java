/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util.object;

import com.artemis.Component;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class EntityState {

  private UUID uuid;
  private Array<Component> components;
  private ObjectMap<String, Object> data;

  public EntityState(UUID uuid, Array<Component> components, ObjectMap<String, Object> data) {
    this.uuid = uuid;
    this.components = components;
    this.data = data;
  }

  public EntityState() {
  }

  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  public Array<Component> getComponents() {
    return components;
  }

  public void setComponents(Array<Component> components) {
    this.components = components;
  }

  public ObjectMap<String, Object> getData() {
    return data;
  }

  public void setData(ObjectMap<String, Object> data) {
    this.data = data;
  }

}
