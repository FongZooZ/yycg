/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.component;

import com.artemis.Component;
import com.badlogic.gdx.utils.Array;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class CollisionComponent extends Component {

  private Array<UUID> collidedList;
  private Array<UUID> justCollidedList;

  public CollisionComponent() {
    collidedList = new Array<UUID>();
    justCollidedList = new Array<UUID>();
  }

  public Array<UUID> getCollidedList() {
    return collidedList;
  }

  public void setCollidedList(Array<UUID> collidedList) {
    this.collidedList = collidedList;
  }

  public Array<UUID> getJustCollidedList() {
    return justCollidedList;
  }

  public void setJustCollidedList(Array<UUID> justCollidedList) {
    this.justCollidedList = justCollidedList;
  }

}
