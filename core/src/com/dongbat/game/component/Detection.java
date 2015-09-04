/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.component;

import com.artemis.Component;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class Detection extends Component {

  private UUID nearestQueen;
  private UUID nearestPlayer;
  private UUID nearestFood;

  public Detection() {
  }

  public UUID getNearestQueen() {
    return nearestQueen;
  }

  public void setNearestQueen(UUID nearestQueen) {
    this.nearestQueen = nearestQueen;
  }

  public UUID getNearestPlayer() {
    return nearestPlayer;
  }

  public void setNearestPlayer(UUID nearestPlayer) {
    this.nearestPlayer = nearestPlayer;
  }

  public UUID getNearestFood() {
    return nearestFood;
  }

  public void setNearestFood(UUID nearestFood) {
    this.nearestFood = nearestFood;
  }

}
