/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util;

import com.dongbat.game.ad.AdProvider;

/**
 *
 * @author tao
 */
public class AdUtil {

  private static AdProvider provider;

  public static void setProvider(AdProvider provider) {
    AdUtil.provider = provider;
  }

  public static void hideAd() {
    if (provider == null) {
      return;
    }
    provider.hideAd();
  }

  public static void showAd() {
    if (provider == null) {
      return;
    }
    provider.showAd();
  }
}
