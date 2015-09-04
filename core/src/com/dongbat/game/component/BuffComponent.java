/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.component;

import com.artemis.Component;
import com.badlogic.gdx.utils.ObjectMap;
import com.dongbat.game.buff.BuffInfo;

/**
 *
 * @author Admin
 */
public class BuffComponent extends Component {

  private ObjectMap<String, BuffInfo> buffs = new ObjectMap<String, BuffInfo>();

  public ObjectMap<String, BuffInfo> getBuffs() {
    return buffs;
  }

  public void setBuffs(ObjectMap<String, BuffInfo> buffs) {
    this.buffs = buffs;
  }

  public BuffInfo getBuffInfo(String s) {
    return buffs.get(s);
  }
}
