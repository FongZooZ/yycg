/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util.object;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author Admin
 */
public class PredictableRandom {

  private Random random;

  public PredictableRandom() {
    random = new Random();
  }

  public int getInt(int min, int max) {
    return random.nextInt(max - min) + min;
  }

  public float getFloat(float min, float max) {
    return random.nextFloat() * (max - min) + min;
  }

  public long getSeed() {
    long theSeed;
    try {
      Field field = Random.class.getDeclaredField("seed");
      field.setAccessible(true);
      AtomicLong scrambledSeed = (AtomicLong) field.get(random);
      theSeed = scrambledSeed.get();
    } catch (Exception e) {
      theSeed = 0;
    }
    return theSeed ^ 0x5DEECE66DL;
  }

  public void setSeed(long seed) {
    random.setSeed(seed);
  }

}
