package com.dongbat.game.component;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;
import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;

/**
 * Created by FongZooZ on 8/31/2015. Display component for texture rendering
 */
public class Display extends Component {

  private Vector2 position;
  private float radius;
  private float rotation;
  private float scale;
  private AnimatedSprite defaultAnimation;
  private AnimatedSprite overridenAnimation;
  private long overridenDuration;
  private long getOverridenStart;

  public Display() {
  }

  public Display(Vector2 position, float radius, float rotation, float scale, AnimatedSprite defaultAnimation, AnimatedSprite overridenAnimation, long overridenDuration, long getOverridenStart) {
    this.position = position;
    this.radius = radius;
    this.rotation = rotation;
    this.scale = scale;
    this.defaultAnimation = defaultAnimation;
    this.overridenAnimation = overridenAnimation;
    this.overridenDuration = overridenDuration;
    this.getOverridenStart = getOverridenStart;
  }

  public Vector2 getPosition() {

    return position;
  }

  public void setPosition(Vector2 position) {
    this.position = position;
  }

  public float getRadius() {
    return radius;
  }

  public void setRadius(float radius) {
    this.radius = radius;
  }

  public float getRotation() {
    return rotation;
  }

  public void setRotation(float rotation) {
    this.rotation = rotation;
  }

  public float getScale() {
    return scale;
  }

  public void setScale(float scale) {
    this.scale = scale;
  }

  public AnimatedSprite getDefaultAnimation() {
    return defaultAnimation;
  }

  public void setDefaultAnimation(AnimatedSprite defaultAnimation) {
    this.defaultAnimation = defaultAnimation;
  }

  public AnimatedSprite getOverridenAnimation() {
    return overridenAnimation;
  }

  public void setOverridenAnimation(AnimatedSprite overridenAnimation) {
    this.overridenAnimation = overridenAnimation;
  }

  public long getOverridenDuration() {
    return overridenDuration;
  }

  public void setOverridenDuration(long overridenDuration) {
    this.overridenDuration = overridenDuration;
  }

  public long getGetOverridenStart() {
    return getOverridenStart;
  }

  public void setGetOverridenStart(long getOverridenStart) {
    this.getOverridenStart = getOverridenStart;
  }
}
