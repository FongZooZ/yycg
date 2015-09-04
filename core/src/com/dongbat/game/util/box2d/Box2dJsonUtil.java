/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util.box2d;

import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * @author BINHDUONG
 */
public class Box2dJsonUtil {

  private static Jb2dJson json;

  public static Jb2dJson getJson() {
    if (json == null) {
      json = new Jb2dJson();
    }
    return json;
  }

  public static World toWorld(String data) {
    Jb2dJson json = getJson();
    json.clear();
    return json.readFromString(data, new StringBuilder());
  }

  public static String fromWorld(World world) {
    Jb2dJson json = getJson();
    json.clear();
    return json.worldToString(world, 0);
  }

}
