/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util.networkUtil;

import com.badlogic.gdx.physics.box2d.World;
import com.dongbat.game.util.box2d.serializer.Box2dHelper;
import com.dongbat.game.util.box2d.serializer.Box2dState;

/**
 *
 * @author tao
 */
public class Box2dStateUtil {

  private static Box2dHelper helper;

  public static Box2dHelper getHelper() {
    if (helper == null) {
      helper = new Box2dHelper();
    }
    helper.clear();
    return helper;
  }

  public static Box2dState fromWorld(World world) {
    return getHelper().fromWorld(world);
  }

  public static World toWorld(Box2dState state) {
    return getHelper().toWorld(state);
  }
}
