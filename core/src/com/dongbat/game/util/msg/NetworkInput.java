/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util.msg;

import com.dongbat.game.dataobject.CustomInput;
import java.util.UUID;

/**
 *
 * @author tao
 */
public class NetworkInput {

  private UUID id;
  private CustomInput input;
  private long frameIndex;

  public NetworkInput() {
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public CustomInput getInput() {
    return input;
  }

  public void setInput(CustomInput input) {
    this.input = input;
  }

  public long getFrameIndex() {
    return frameIndex;
  }

  public void setFrameIndex(long frameIndex) {
    this.frameIndex = frameIndex;
  }

}
