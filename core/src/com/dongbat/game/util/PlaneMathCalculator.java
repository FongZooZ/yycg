/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util;

import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Admin
 */
public class PlaneMathCalculator {

  public static Vector2 getPerpendicularVector(Vector2 oldVector) {
    Vector2 newVector = new Vector2(oldVector.y, -oldVector.x);
    return newVector;
  }

  public static float getAlpha(Vector2 centerPosition, Vector2 collisionPosition, Vector2 bulletDirection) {
    Vector2 perpendicularVector = getPerpendicularVector(bulletDirection.cpy());
    Vector2 centerToConner = collisionPosition.cpy().sub(centerPosition.cpy());
    float angleRad = centerToConner.angleRad(perpendicularVector);
    return angleRad * 2;
  }

  public static float getPercentTaken(Vector2 centerPosition, Vector2 collisionPosition, Vector2 bulletDirection, float radius) {
    float alpha = getAlpha(centerPosition, collisionPosition, bulletDirection);
    float percentTaken = getPercentTaken(alpha);
    return percentTaken;
  }

  public static float getPercentTaken(float alpla) {
    return (float) ((alpla - Math.sin(alpla)) / (2 * Math.PI));
  }

  public static Vector2 giaiPtBacNhat2An(float a1, float b1, float c1, float a2, float b2, float c2) {
    float d = a1 * b2 - b1 * a2;
    float dX = c1 * b2 - b1 * c2;
    float dY = a1 * c2 - c1 * a2;
    if (d == 0) {
      //vo nghiem hoac vo so nghiem, ma ke no
      return null;
    }
    Vector2 newVector = new Vector2();
    if (d != 0) {
      newVector.x = dX / d;
      newVector.y = dY / d;
    }
    return newVector;
  }

}
