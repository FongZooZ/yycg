package com.dongbat.game.util.box2d.serializer;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author tao
 */
public class PolygonState {

  private Array<Vector2> vertices;

  public PolygonState() {
  }

  public Array<Vector2> getVertices() {
    return vertices;
  }

  public void setVertices(Array<Vector2> vertices) {
    this.vertices = vertices;
  }
}
