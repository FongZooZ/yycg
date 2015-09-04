/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util;

import com.artemis.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.dongbat.game.component.CollisionComponent;
import com.dongbat.game.component.Physics;
import com.dongbat.game.util.localUtil.Constants;

/**
 * @author Admin
 */
public class PhysicsUtil {

  private static final ObjectMap<com.artemis.World, World> physicsWorldMap = new ObjectMap<com.artemis.World, World>();
  private static final ObjectMap<World, com.artemis.World> artemisWorldMap = new ObjectMap<World, com.artemis.World>();

  /**
   * Initialize a box2d world and put it into libGdx ObjectMap
   *
   * @param world artemis world
   */
  public static void init(com.artemis.World world) {
    if (physicsWorldMap.get(world) != null) {
      return;
    }
    World physicsWorld = new World(new Vector2(0, Constants.PHYSICS.DEFAULT_GRAVITY), false);
    physicsWorldMap.put(world, physicsWorld);
    artemisWorldMap.put(physicsWorld, world);

  }

  /**
   * Get artemis world of a box2d world
   *
   * @param world artemis world
   * @return artemis world
   */
  public static World getPhysicsWorld(com.artemis.World world) {
    if (!physicsWorldMap.containsKey(world)) {
      init(world);
    }
    return physicsWorldMap.get(world);
  }

  public static com.artemis.World getArtemisWorld(World world) {
    return artemisWorldMap.get(world);
  }

  public static void setPhysicsWorld(com.artemis.World world, World physicsWorld) {
    //TODO may be leak because of not dispose

    physicsWorldMap.put(world, physicsWorld);
    artemisWorldMap.put(physicsWorld, world);

  }

  /**
   * Get radius of an entity
   *
   * @param world artemis world
   * @param e entity that you want to get radius
   * @return radius of entity in float
   */
//  public static float getDetectionRadius(com.artemis.World world, Entity e) {
//    if (getBody(world, e).getFixtureList().size == 0) {
//      return 0;
//    }
//    Array<Fixture> fixtureList = getBody(world, e).getFixtureList();
//    for (Fixture fixture : fixtureList) {
//      if ("detection".equals(fixture.getUserData())) {
//        return fixture.getShape().getRadius();
//      }
//    }
//    return 0;
////    return getBody(world, e).getFixtureList().get(0).getShape().getRadius();
//  }
  public static float getRadius(com.artemis.World world, Entity e) {
    if (getBody(world, e).getFixtureList().size == 0) {
      return 0;
    }
    return getBody(world, e).getFixtureList().get(0).getShape().getRadius();

  }

  public static float getSquare(com.artemis.World world, Entity e) {
    float radius = getRadius(world, e);
    return getSquare(radius);
  }

  public static float getSquare(float radius) {
    return (float) (radius * radius * Math.PI);
  }

  public static void setSquare(com.artemis.World world, Entity e, float newSquare) {
    float newRadius = getRadiusBySquare(newSquare);
    setRadius(world, e, newRadius);
  }

  public static void setRadiusBySquare(com.artemis.World world, Entity e, float square) {
    float radius = getRadiusBySquare(square);
    setRadius(world, e, radius);
  }

  public static float getRadiusBySquare(float square) {
    float radius = (float) Math.sqrt(square / Math.PI);
    return radius;
  }

  public static void increaseRadius(com.artemis.World world, Entity e, float ammount) {
    float radius = getRadius(world, e);
    radius += ammount;
    if (radius <= 0) {
      UnitUtil.destroy(e);
      return;
    }
    setRadius(world, e, radius);
  }

  public static void increaseSquare(com.artemis.World world, Entity e, float ammount) {
    float square = getSquare(world, e);
    square += ammount;
    if (square <= 0) {
      UnitUtil.destroy(e);
      return;
    }
    setSquare(world, e, square);
  }

  /**
   * Set radius for an entity
   *
   * @param world artemis world
   * @param e entity that you want to set radius
   * @param r radius that you want to set for entity
   */
  public static void setRadius(com.artemis.World world, Entity e, float r) {
    Body body = PhysicsUtil.getBody(world, e);
    if (!body.isActive()) {
      return;
    }
    getBody(world, e).getFixtureList().get(0).getShape().setRadius(r);
  }

//  public static void setDetectionRadius(com.artemis.World world, Entity e, float r) {
//    Body body = PhysicsUtil.getBody(world, e);
//    if (!body.isActive()) {
//      return;
//    }
//    Array<Fixture> fixtureList = getBody(world, e).getFixtureList();
//    for (Fixture fixture : fixtureList) {
//      if ("detection".equals(fixture.getUserData())) {
//        fixture.getShape().setRadius(r);
//        return;
//      }
//    }
////    getBody(world, e).getFixtureList().get(0).getShape().setRadius(r);
//  }
  /**
   * Get box2d Body of an entity from artemis world
   *
   * @param world artemis world
   * @param entity entity that you want to get Body
   * @return box2d body
   */
  public static Body getBody(com.artemis.World world, Entity entity) {
    Physics component = EntityUtil.getComponent(world, entity, Physics.class);
    if (component == null) {
      return null;
    }
    return component.getBody();
  }

  /**
   * Set position for an entity
   *
   * @param world artemis world
   * @param entity entity that you want to set position
   * @param position position that you want to set for entity
   */
  public static void setPosition(com.artemis.World world, Entity entity, Vector2 position) {
    Body body = getBody(world, entity);
    body.setTransform(position, body.getAngle());
  }

  /**
   * Get position of an entity
   *
   * @param world artemis world
   * @param entity entity that you want to get position
   * @return return position of entity in Vector2
   */
  public static Vector2 getPosition(com.artemis.World world, Entity entity) {
    return getBody(world, entity).getPosition();
  }

  /**
   * Immediately set Velocity for an entity
   *
   * @param world artemis world
   * @param entity entity that you want to set velocity
   * @param velocity velocity that you want to set for entity
   */
  public static void setVelocity(com.artemis.World world, Entity entity, Vector2 velocity) {
    getBody(world, entity).setLinearVelocity(velocity);
  }

  /**
   * Get current velocity of an entity
   *
   * @param world artemis world
   * @param entity entity that you want to get velocity
   * @return velocity in Vector2
   */
  public static Vector2 getVelocity(com.artemis.World world, Entity entity) {
    return getBody(world, entity).getLinearVelocity();
  }

  /**
   * Apply Linear Impulse to an entity
   *
   * @param world artemis world
   * @param entity entity that you want to apply
   * @param impulse impulse amount
   */
  public static void applyImpulse(com.artemis.World world, Entity entity, Vector2 impulse) {
    Body body = getBody(world, entity);
    body.applyLinearImpulse(impulse, body.getWorldCenter(), true);
  }

  /**
   * Apply Froce to an entity
   *
   * @param world artemis world
   * @param entity entity that you want to apply
   * @param force force amount
   */
  public static void applyForce(com.artemis.World world, Entity entity, Vector2 force) {
    Body body = getBody(world, entity);
    body.applyForce(force, body.getWorldCenter(), true);
  }

  /**
   * Check that entity a is contain entity b or not When radius of entity a
   * greater than radius of entity b, it's called a contain b
   *
   * @param world artemis world
   * @param a entity a
   * @param b entity b
   * @return true if a contain b
   */
  public static boolean isBodyContain(com.artemis.World world, Entity a, Entity b) {
    if (getBody(world, b) == null || getBody(world, a) == null) {
      return false;
    }
    float len = getPosition(world, a).cpy().sub(getPosition(world, b).cpy()).len();

    return (getRadius(world, a) - getRadius(world, b)) > len;
  }

  /**
   * Check that entity a is collide with entity b or not
   *
   * @param world artemis world
   * @param a entity a
   * @param b entity b
   * @return true if a collide with b
   */
  public static boolean isBodyCollided(com.artemis.World world, Entity a, Entity b) {
    CollisionComponent component = EntityUtil.getComponent(world, a, CollisionComponent.class);
    if (component == null) {
      return false;
    }
    return component.getCollidedList().contains(UuidUtil.getUuid(b), true);
  }

  /**
   * Calculate radius of entity A when A eat B
   *
   * @param radiusA entity A
   * @param radiusB entity B
   * @param rate increase rate
   * @return radius after eating
   */
  public static float increaseRadius(float radiusA, float radiusB, float rate) {
    float volumeA = getSquare(radiusA);
    float volumeB = getSquare(radiusB) * rate;
    double newRadius = Math.sqrt((volumeA + volumeB) / Math.PI);

    return (float) newRadius;
  }

  /**
   * Create a box2d Body
   *
   * @param physicsWorld box2d world
   * @param position body position
   * @param radius body radius
   * @param e set user data to entity e
   * @return Body that was just created
   */
  public static Body createBody(World physicsWorld, Vector2 position, float radius, Entity e) {

    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyDef.BodyType.DynamicBody;
    bodyDef.position.set(position);

    Body body = physicsWorld.createBody(bodyDef);
    body.setUserData(UuidUtil.getUuid(e));

    CircleShape circle = new CircleShape();
    circle.setRadius(radius);

    //collision fixture
    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = circle;
    fixtureDef.density = 1;
    fixtureDef.isSensor = true;
//    fixtureDef.filter.maskBits = 1;
//    fixtureDef.filter.categoryBits = 2;
    Fixture collisionFixture = body.createFixture(fixtureDef);
    collisionFixture.setUserData("collision");

    CircleShape circle2 = new CircleShape();
    circle2.setRadius(15);
    // detection fixture
    FixtureDef fixtureDef2 = new FixtureDef();
    fixtureDef2.shape = circle2;
    fixtureDef2.density = 0;
    fixtureDef2.isSensor = true;
//    fixtureDef.filter.maskBits = 1;
//    fixtureDef.filter.categoryBits = 2;
//    Fixture detectionFixture = body.createFixture(fixtureDef2);
//    detectionFixture.setUserData("detection");

    circle.dispose();
    circle2.dispose();

    return body;
  }

  /**
   * Create box2d Edge, use for making world, floor, ...
   *
   * @param world artemis world
   * @param type Body type
   * @param x1 x start
   * @param y1 x finish
   * @param x2 y start
   * @param y2 y finish
   * @param density densisty of edge
   * @return Edge that was just created
   */
  public static Body createEdge(com.artemis.World world, BodyDef.BodyType type, float x1, float y1, float x2,
    float y2, float density) {
    BodyDef def = new BodyDef();
    def.type = type;
    Body box = getPhysicsWorld(world).createBody(def);

    EdgeShape poly = new EdgeShape();
    poly.set(new Vector2(0, 0), new Vector2(x2 - x1, y2 - y1));
//    box.createFixture(poly, density);
    box.setTransform(x1, y1, 0);
    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = poly;
//    fixtureDef.filter.maskBits = 2;
//    fixtureDef.filter.categoryBits = 2 | 1;
    box.createFixture(fixtureDef);
//    box.setUserData(new Box2dSteeringEntity(box, true, 0.1f));
    poly.dispose();
    return box;
  }

  /**
   * Create a dummy box to test
   *
   * @param world artemis world
   * @param type Body type
   * @param width box width
   * @param height box height
   * @param density box density
   * @return Box that was just created
   */
  public static Body createBox(com.artemis.World world, BodyDef.BodyType type, float width, float height, float density) {
    BodyDef def = new BodyDef();
    def.type = type;
    Body box = getPhysicsWorld(world).createBody(def);

    PolygonShape poly = new PolygonShape();
    poly.setAsBox(width, height);
    box.createFixture(poly, density);
    poly.dispose();

    return box;
  }

  public static boolean isBodyTouch(com.artemis.World world, Entity a, Entity b) {
    if (getBody(world, b) == null || getBody(world, a) == null) {
      return false;
    }
    float len = getPosition(world, a).cpy().sub(getPosition(world, b).cpy()).len();

    return (getRadius(world, a) + getRadius(world, b)) > len;
  }

}
