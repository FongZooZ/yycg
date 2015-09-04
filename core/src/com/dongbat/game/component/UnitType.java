/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.component;

import com.artemis.Component;

/**
 *
 * @author Admin
 */
public class UnitType extends Component {

  private String unitType;

  public UnitType() {
  }

  public UnitType(String unitType) {
    this.unitType = unitType;
  }

  public String getUnitType() {
    return unitType;
  }

  public void setUnitType(String unitType) {
    this.unitType = unitType;
  }
}
