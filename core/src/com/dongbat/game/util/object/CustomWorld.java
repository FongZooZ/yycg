/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util.object;

import com.artemis.World;
import com.artemis.WorldConfiguration;

/**
 *
 * @author Admin
 */
public class CustomWorld extends World {

  private boolean ignoringSystem = false;

  public boolean isIgnoringSystem() {
    return ignoringSystem;
  }

  public void setIgnoringSystem(boolean ignoringSystem) {
    this.ignoringSystem = ignoringSystem;
  }

  public CustomWorld(WorldConfiguration worldConfiguration) {
    super(worldConfiguration);
  }

}
