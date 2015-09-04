package com.dongbat.game.util.box2d.serializer;

import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author tao
 */
public class JointState {

  private int bodyA;
  private int bodyB;

  private Object userData;

  private String type;

  private Vector2 anchorA;
  private Vector2 anchorB;

  private float refAngle;
  private float jointSpeed;
  private boolean enableLimit;

  private float lowerLimit;
  private float upperLimit;

  private boolean enableMotor;
  private float motorSpeed;
  private float maxMotorTorque;

  private float length;
  private float frequency;
  private float dampingRatio;

  private Vector2 groundAnchorA;
  private Vector2 groundAnchorB;

  private float lengthA;
  private float lengthB;

  private float ratio;

  private Vector2 target;
  private float maxForce;
  private float maxTorque;
  private float maxLength;

  private boolean collideConnected;

  public boolean isCollideConnected() {
    return collideConnected;
  }

  public void setCollideConnected(boolean collideConnected) {
    this.collideConnected = collideConnected;
  }

  public JointState() {
  }

  public int getBodyA() {
    return bodyA;
  }

  public void setBodyA(int bodyA) {
    this.bodyA = bodyA;
  }

  public int getBodyB() {
    return bodyB;
  }

  public void setBodyB(int bodyB) {
    this.bodyB = bodyB;
  }

  public Object getUserData() {
    return userData;
  }

  public void setUserData(Object userData) {
    this.userData = userData;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Vector2 getAnchorA() {
    return anchorA;
  }

  public void setAnchorA(Vector2 anchorA) {
    this.anchorA = anchorA;
  }

  public Vector2 getAnchorB() {
    return anchorB;
  }

  public void setAnchorB(Vector2 anchorB) {
    this.anchorB = anchorB;
  }

  public float getRefAngle() {
    return refAngle;
  }

  public void setRefAngle(float refAngle) {
    this.refAngle = refAngle;
  }

  public float getJointSpeed() {
    return jointSpeed;
  }

  public void setJointSpeed(float jointSpeed) {
    this.jointSpeed = jointSpeed;
  }

  public boolean isEnableLimit() {
    return enableLimit;
  }

  public void setEnableLimit(boolean enableLimit) {
    this.enableLimit = enableLimit;
  }

  public float getLowerLimit() {
    return lowerLimit;
  }

  public void setLowerLimit(float lowerLimit) {
    this.lowerLimit = lowerLimit;
  }

  public float getUpperLimit() {
    return upperLimit;
  }

  public void setUpperLimit(float upperLimit) {
    this.upperLimit = upperLimit;
  }

  public boolean isEnableMotor() {
    return enableMotor;
  }

  public void setEnableMotor(boolean enableMotor) {
    this.enableMotor = enableMotor;
  }

  public float getMotorSpeed() {
    return motorSpeed;
  }

  public void setMotorSpeed(float motorSpeed) {
    this.motorSpeed = motorSpeed;
  }

  public float getMaxMotorTorque() {
    return maxMotorTorque;
  }

  public void setMaxMotorTorque(float maxMotorTorque) {
    this.maxMotorTorque = maxMotorTorque;
  }

  public float getLength() {
    return length;
  }

  public void setLength(float length) {
    this.length = length;
  }

  public float getFrequency() {
    return frequency;
  }

  public void setFrequency(float frequency) {
    this.frequency = frequency;
  }

  public float getDampingRatio() {
    return dampingRatio;
  }

  public void setDampingRatio(float dampingRatio) {
    this.dampingRatio = dampingRatio;
  }

  public Vector2 getGroundAnchorA() {
    return groundAnchorA;
  }

  public void setGroundAnchorA(Vector2 groundAnchorA) {
    this.groundAnchorA = groundAnchorA;
  }

  public Vector2 getGroundAnchorB() {
    return groundAnchorB;
  }

  public void setGroundAnchorB(Vector2 groundAnchorB) {
    this.groundAnchorB = groundAnchorB;
  }

  public float getLengthA() {
    return lengthA;
  }

  public void setLengthA(float lengthA) {
    this.lengthA = lengthA;
  }

  public float getLengthB() {
    return lengthB;
  }

  public void setLengthB(float lengthB) {
    this.lengthB = lengthB;
  }

  public float getRatio() {
    return ratio;
  }

  public void setRatio(float ratio) {
    this.ratio = ratio;
  }

  public Vector2 getTarget() {
    return target;
  }

  public void setTarget(Vector2 target) {
    this.target = target;
  }

  public float getMaxForce() {
    return maxForce;
  }

  public void setMaxForce(float maxForce) {
    this.maxForce = maxForce;
  }

  public float getMaxTorque() {
    return maxTorque;
  }

  public void setMaxTorque(float maxTorque) {
    this.maxTorque = maxTorque;
  }

  public float getMaxLength() {
    return maxLength;
  }

  public void setMaxLength(float maxLength) {
    this.maxLength = maxLength;
  }

}
