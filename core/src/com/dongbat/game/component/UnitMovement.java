/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.component;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Admin
 */
public class UnitMovement extends Component {

  // move sang unit state
  private Vector2 directionVelocity;
  private boolean disabled = false;

  public Vector2 getDirectionVelocity() {
    return directionVelocity;
  }

  public void setDirectionVelocity(Vector2 destination) {
    this.directionVelocity = destination;
  }

  // TODO: check this
  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
  }

  public boolean isDisabled() {
    return disabled;
  }

}
