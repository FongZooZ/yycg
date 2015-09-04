/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util.object;

import com.artemis.World;
import com.badlogic.gdx.utils.Array;
import com.dongbat.game.util.msg.WorldState;

/**
 *
 * @author Admin
 */
public class WorldProgress {

  private long frame;
  private float step = 0.01f;
  private float accumulated;
  private Array<WorldState> recentStates;
  private boolean save;
  private boolean load;
  private WorldState copy;
  private boolean rewinding;

  public WorldProgress(float step) {
    save = false;
    copy = null;
    rewinding = false;
    load = false;
    frame = 0;
    this.step = step;
    accumulated = 0;
    recentStates = new Array<WorldState>();
  }

  public void advanced() {
    frame++;
  }

  public void increaseAccumulated(float delta) {
    accumulated += delta;
  }

  public void stepWorld(World world, float delta) {

    increaseAccumulated(delta);

    while (accumulated >= step) {
      increaseAccumulated(-step);
      world.setDelta(step);
      world.process();
      advanced();
    }
  }

  public long getFrame() {
    return frame;
  }

  public void setFrame(long frame) {
    this.frame = frame;
  }

  public float getStep() {
    return step;
  }

  public float getAccumulated() {
    return accumulated;
  }

  public void setAccumulated(float accumulated) {
    this.accumulated = accumulated;
  }

  public Array<WorldState> getRecentStates() {
    return recentStates;
  }

  public WorldState peekRecentStates() {
    if (recentStates.size <= 0) {
      return null;
    }
    return recentStates.peek();
  }

  public WorldState popRecentStates() {
    if (recentStates.size <= 0) {
      return null;
    }
    return recentStates.pop();
  }

  public void setRecentStates(Array<WorldState> recentStates) {
    this.recentStates = recentStates;
  }

  public boolean isSave() {
    return save;
  }

  public void setSave(boolean save) {
    this.save = save;
  }

  public boolean isLoad() {
    return load;
  }

  public void setLoad(boolean load) {
    this.load = load;
  }

  public WorldState getCopy() {
    return copy;
  }

  public void setCopy(WorldState copy) {
    this.copy = copy;
  }

  public boolean isRewinding() {
    return rewinding;
  }

  public void setRewinding(boolean rewinding) {
    this.rewinding = rewinding;
  }

}
