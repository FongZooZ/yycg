/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.dongbat.game.dataobject.CustomInput;

/**
 *
 * @author tao
 */
public class InputUtil {

  private static InputMultiplexer multiplexer;

  public static void init() {
    multiplexer = new InputMultiplexer();
    Gdx.input.setInputProcessor(multiplexer);
  }

  public static void addProcessor(InputProcessor processor, int index) {
    multiplexer.addProcessor(index, processor);
  }

  public static void removeProcessor(InputProcessor processor) {
    multiplexer.removeProcessor(processor);
  }

  public static void issueInput(World world, Entity e, CustomInput input, boolean predict, long delay) {
    // add to player component
    // send to network if needed, ignore AI input
  }
}
