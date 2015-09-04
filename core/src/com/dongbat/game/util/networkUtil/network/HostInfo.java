/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util.networkUtil.network;

import com.badlogic.gdx.utils.Array;
import com.dongbat.game.util.networkUtil.NetworkUtil;
import java.net.InetAddress;

/**
 *
 * @author tao
 */
public class HostInfo {

  public String prefix = NetworkUtil.BROADCAST_PREFIX;

  private InetAddress address;
  private String hostName;
  private boolean started;
  private Array<String> playerNames;

  public HostInfo() {
  }

  public HostInfo(InetAddress address, String hostName, boolean started, Array<String> playerNames) {
    this.address = address;
    this.hostName = hostName;
    this.started = started;
    this.playerNames = playerNames;
  }

  public Array<String> getPlayerNames() {
    return playerNames;
  }

  public void setPlayerNames(Array<String> playerNames) {
    this.playerNames = playerNames;
  }

  public InetAddress getAddress() {
    return address;
  }

  public void setAddress(InetAddress address) {
    this.address = address;
  }

  public String getHostName() {
    return hostName;
  }

  public void setHostName(String hostName) {
    this.hostName = hostName;
  }

  public boolean isStarted() {
    return started;
  }

  public void setStarted(boolean started) {
    this.started = started;
  }

}
