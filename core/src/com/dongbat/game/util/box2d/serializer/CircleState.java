package com.dongbat.game.util.box2d.serializer;

import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author tao
 */
public class CircleState {

  private float radius;
  private Vector2 center;

  public CircleState() {
  }

  public float getRadius() {
    return radius;
  }

  public void setRadius(float radius) {
    this.radius = radius;
  }

  public Vector2 getCenter() {
    return center;
  }

  public void setCenter(Vector2 center) {
    this.center = center;
  }

}
