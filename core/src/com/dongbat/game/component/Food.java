/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.component;

import com.artemis.Component;

/**
 * @author Admin
 */
public class Food extends Component {

  private boolean toxic;
  private boolean dirty = false;

  public Food() {
    toxic = false;
  }

  public Food(boolean isToxic) {
    this.toxic = isToxic;
  }

  public boolean isToxic() {
    return toxic;
  }

  public void setToxic(boolean isToxic) {
    this.toxic = isToxic;
    this.dirty = true;
  }

  public boolean isDirty() {
    return dirty;
  }

  public void setDirty(boolean dirty) {
    this.dirty = dirty;
  }
}
