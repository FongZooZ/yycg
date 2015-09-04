package com.dongbat.game.util.msg;

import java.util.UUID;

/**
 *
 * @author tao
 */
public class InitState {

  private UUID clientId;
  private WorldState baseState;

  public InitState() {
  }

  public InitState(UUID clientId, WorldState baseState) {
    this.clientId = clientId;
    this.baseState = baseState;
  }

  public UUID getClientId() {
    return clientId;
  }

  public void setClientId(UUID clientId) {
    this.clientId = clientId;
  }

  public WorldState getBaseState() {
    return baseState;
  }

  public void setBaseState(WorldState baseState) {
    this.baseState = baseState;
  }

}
