package com.dongbat.game.util.box2d.serializer;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author tao
 */
public class BodyState {

  private int type;
  private Vector2 position;
  private float angle;
  private Object userData;

  private Vector2 linearVelocity;
  private float angularVelocity;

  private float linearDamping;
  private float angularDamping;
  private float gravityScale;

  private boolean bullet;
  private boolean allowSleep;

  private boolean awake;
  private boolean active;
  private boolean fixedRotation;

  private float massDataMass;
  private Vector2 massDataCenter;
  private float massDataI;

  private Array<FixtureState> fixture;

  public BodyState() {
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public Vector2 getPosition() {
    return position;
  }

  public void setPosition(Vector2 position) {
    this.position = position;
  }

  public float getAngle() {
    return angle;
  }

  public void setAngle(float angle) {
    this.angle = angle;
  }

  public Object getUserData() {
    return userData;
  }

  public void setUserData(Object userData) {
    this.userData = userData;
  }

  public Vector2 getLinearVelocity() {
    return linearVelocity;
  }

  public void setLinearVelocity(Vector2 linearVelocity) {
    this.linearVelocity = linearVelocity;
  }

  public float getAngularVelocity() {
    return angularVelocity;
  }

  public void setAngularVelocity(float angularVelocity) {
    this.angularVelocity = angularVelocity;
  }

  public float getLinearDamping() {
    return linearDamping;
  }

  public void setLinearDamping(float linearDamping) {
    this.linearDamping = linearDamping;
  }

  public float getAngularDamping() {
    return angularDamping;
  }

  public void setAngularDamping(float angularDamping) {
    this.angularDamping = angularDamping;
  }

  public float getGravityScale() {
    return gravityScale;
  }

  public void setGravityScale(float gravityScale) {
    this.gravityScale = gravityScale;
  }

  public boolean isBullet() {
    return bullet;
  }

  public void setBullet(boolean bullet) {
    this.bullet = bullet;
  }

  public boolean isAllowSleep() {
    return allowSleep;
  }

  public void setAllowSleep(boolean allowSleep) {
    this.allowSleep = allowSleep;
  }

  public boolean isAwake() {
    return awake;
  }

  public void setAwake(boolean awake) {
    this.awake = awake;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public boolean isFixedRotation() {
    return fixedRotation;
  }

  public void setFixedRotation(boolean fixedRotation) {
    this.fixedRotation = fixedRotation;
  }

  public float getMassDataMass() {
    return massDataMass;
  }

  public void setMassDataMass(float massDataMass) {
    this.massDataMass = massDataMass;
  }

  public Vector2 getMassDataCenter() {
    return massDataCenter;
  }

  public void setMassDataCenter(Vector2 massDataCenter) {
    this.massDataCenter = massDataCenter;
  }

  public float getMassDataI() {
    return massDataI;
  }

  public void setMassDataI(float massDataI) {
    this.massDataI = massDataI;
  }

  public Array<FixtureState> getFixture() {
    return fixture;
  }

  public void setFixture(Array<FixtureState> fixture) {
    this.fixture = fixture;
  }

}
