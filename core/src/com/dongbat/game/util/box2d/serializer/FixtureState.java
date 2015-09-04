package com.dongbat.game.util.box2d.serializer;

/**
 *
 * @author tao
 */
public class FixtureState {

  private float resitution;
  private Object userData;
  private float friction;
  private float density;
  private boolean sensor;

  public boolean isSensor() {
    return sensor;
  }

  public void setSensor(boolean sensor) {
    this.sensor = sensor;
  }

  private short filterCategoryBits;
  private short filterMaskBits;
  private short filterGroupIndex;

  private CircleState circle;
  private EdgeState edge;
  private ChainState chain;
  private PolygonState polygon;

  public FixtureState() {
  }

  public float getResitution() {
    return resitution;
  }

  public void setResitution(float resitution) {
    this.resitution = resitution;
  }

  public Object getUserData() {
    return userData;
  }

  public void setUserData(Object userData) {
    this.userData = userData;
  }

  public float getFriction() {
    return friction;
  }

  public void setFriction(float friction) {
    this.friction = friction;
  }

  public float getDensity() {
    return density;
  }

  public void setDensity(float density) {
    this.density = density;
  }

  public short getFilterCategoryBits() {
    return filterCategoryBits;
  }

  public void setFilterCategoryBits(short filterCategoryBits) {
    this.filterCategoryBits = filterCategoryBits;
  }

  public short getFilterMaskBits() {
    return filterMaskBits;
  }

  public void setFilterMaskBits(short filterMaskBits) {
    this.filterMaskBits = filterMaskBits;
  }

  public short getFilterGroupIndex() {
    return filterGroupIndex;
  }

  public void setFilterGroupIndex(short filterGroupIndex) {
    this.filterGroupIndex = filterGroupIndex;
  }

  public CircleState getCircle() {
    return circle;
  }

  public void setCircle(CircleState circle) {
    this.circle = circle;
  }

  public EdgeState getEdge() {
    return edge;
  }

  public void setEdge(EdgeState edge) {
    this.edge = edge;
  }

  public ChainState getChain() {
    return chain;
  }

  public void setChain(ChainState chain) {
    this.chain = chain;
  }

  public PolygonState getPolygon() {
    return polygon;
  }

  public void setPolygon(PolygonState polygon) {
    this.polygon = polygon;
  }

}
