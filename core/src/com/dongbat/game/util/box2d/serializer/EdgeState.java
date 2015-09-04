package com.dongbat.game.util.box2d.serializer;

import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author tao
 */
public class EdgeState {

  private Vector2 vertex1;
  private Vector2 vertex2;

  public EdgeState() {
  }

  public Vector2 getVertex1() {
    return vertex1;
  }

  public void setVertex1(Vector2 vertex1) {
    this.vertex1 = vertex1;
  }

  public Vector2 getVertex2() {
    return vertex2;
  }

  public void setVertex2(Vector2 vertex2) {
    this.vertex2 = vertex2;
  }

}
