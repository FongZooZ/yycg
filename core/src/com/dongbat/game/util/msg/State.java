/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util.msg;

/**
 *
 * @author implicit-invocation
 */
public class State {

  private byte[] worldState;

  public State() {
  }

  public State(byte[] worldState) {
    this.worldState = worldState;
  }

  public byte[] getWorldState() {
    return worldState;
  }

  public void setWorldState(byte[] worldState) {
    this.worldState = worldState;
  }

}
