package com.dongbat.game.util;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;

/**
 * Created by FongZooZ on 9/1/2015.
 */
public class AnimationUtil {

  private static Animation hotAnimation;
  private static Animation coldAnimation;
  private static Animation unitAnimation;

  public static AnimatedSprite getHotFood() {
    if (hotAnimation == null) {
      hotAnimation = new Animation(0.1f, AssetUtil.getUnitAtlas().get("hot_food").getRegions());
      hotAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }
    return new AnimatedSprite(hotAnimation);
  }

  public static AnimatedSprite getColdFood() {
    if (coldAnimation == null) {
      coldAnimation = new Animation(0.1f, new TextureRegion(AssetUtil.cold));
      coldAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }
    return new AnimatedSprite(coldAnimation);
  }

  public static AnimatedSprite getUnitAnimation() {
    // TODO: more frame :( smoother needed

    if (unitAnimation == null) {
      unitAnimation = new Animation(0.1f, new TextureRegion(AssetUtil.move));
      unitAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }
    return new AnimatedSprite(unitAnimation);
  }
}
