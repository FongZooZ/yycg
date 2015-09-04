package com.dongbat.game.util.box2d.serializer;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author tao
 */
public class Box2dState {

  private Vector2 gravity;
  private boolean autoClearForces;

  private Array<BodyState> body;
  private Array<JointState> joint;

  public Box2dState() {
    body = new Array<BodyState>();
    joint = new Array<JointState>();
  }

  public Vector2 getGravity() {
    return gravity;
  }

  public void setGravity(Vector2 gravity) {
    this.gravity = gravity;
  }

  public boolean isAutoClearForces() {
    return autoClearForces;
  }

  public void setAutoClearForces(boolean autoClearForces) {
    this.autoClearForces = autoClearForces;
  }

  public Array<BodyState> getBody() {
    return body;
  }

  public void setBody(Array<BodyState> body) {
    this.body = body;
  }

  public Array<JointState> getJoint() {
    return joint;
  }

  public void setJoint(Array<JointState> joint) {
    this.joint = joint;
  }

}
