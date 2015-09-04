/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.utils.Array;
import com.dongbat.game.component.AiControl;
import com.dongbat.game.component.Detection;
import com.dongbat.game.component.Food;
import com.dongbat.game.component.Player;
import com.dongbat.game.component.Queen;
import com.dongbat.game.component.SubUnit;
import static com.dongbat.game.util.FoodSpawningUtil.scaleX;
import static com.dongbat.game.util.FoodSpawningUtil.scaleY;
import static com.dongbat.game.util.PhysicsUtil.getPhysicsWorld;
import static com.dongbat.game.util.PhysicsUtil.getPosition;
import java.util.UUID;

/**
 * @author Admin
 */
public class WorldQueryUtil {

  /**
   * Find any entity in radius
   *
   * @param world artemis world
   * @param location location to find
   * @param radius radius to find
   * @return Array of nearest Entity in radius
   */
  public static Array<Entity> findAnyInRadius(final com.artemis.World world, final Vector2 location, final float radius) {
    final Array<Entity> entities = new Array<Entity>();

    QueryCallback callback = new QueryCallback() {

      @Override
      public boolean reportFixture(Fixture fixture) {
        Body body = fixture.getBody();
        Entity entity = UuidUtil.getEntityByUuid(world, (UUID) body.getUserData());
        float distanceSq = new Vector2(body.getPosition()).sub(location).len2();
        if (distanceSq <= radius * radius) {
          entities.add(entity);
        }
        return true;
      }
    };
    Vector2 lowerLeft = new Vector2(location).sub(radius, radius);
    Vector2 upperRight = new Vector2(location).add(radius, radius);
    PhysicsUtil.getPhysicsWorld(world).QueryAABB(callback, lowerLeft.x, lowerLeft.y, upperRight.x, upperRight.y);

    return entities;
  }

  public static Array<Entity> filterEntityInRay(World world, Array<Entity> entities, Vector2 origin, Vector2 direction, float degree) {
    Array<Entity> filter = new Array<Entity>();
    for (Entity e : entities) {
      Vector2 entityPos = getPosition(world, e);
      float angle = entityPos.cpy().sub(origin.cpy()).angle(direction.cpy());
      if (angle <= degree && angle >= -degree) {
        filter.add(e);
      }
    }
    return filter;
  }

  /**
   * Find food in radius, centre is an point on map
   *
   * @param world artemis world
   * @param location centre of circle
   * @param radius radius to find
   * @return Food entity list
   */
  public static Array<Entity> findFoodInRadius(final com.artemis.World world, final Vector2 location, final float radius) {
    final Array<Entity> food = new Array<Entity>();

    QueryCallback callback = new QueryCallback() {

      @Override
      public boolean reportFixture(Fixture fixture) {
        Body body = fixture.getBody();
        Entity entity = UuidUtil.getEntityByUuid(world, (UUID) body.getUserData());
        if (entity != null) {
          if (isFood(world, entity.getId())) {
            float distanceSq = new Vector2(body.getPosition()).sub(location).len2();
            if (distanceSq <= radius * radius) {
              food.add(entity);
            }
          }
        }
        return true;
      }
    };
    Vector2 lowerLeft = new Vector2(location).sub(radius, radius);
    Vector2 upperRight = new Vector2(location).add(radius, radius);
    getPhysicsWorld(world).QueryAABB(callback, lowerLeft.x, lowerLeft.y, upperRight.x, upperRight.y);

    return food;
  }

  public static Array<Entity> findColdFoodInRadius(final com.artemis.World world, final Vector2 location, final float radius) {
    final Array<Entity> food = new Array<Entity>();

    QueryCallback callback = new QueryCallback() {

      @Override
      public boolean reportFixture(Fixture fixture) {
        Body body = fixture.getBody();
        Entity entity = UuidUtil.getEntityByUuid(world, (UUID) body.getUserData());
        if (entity != null) {
          if (isFood(world, entity.getId())) {
            float distanceSq = new Vector2(body.getPosition()).sub(location).len2();
            if (distanceSq <= radius * radius) {
              Food f = EntityUtil.getComponent(world, entity, Food.class);
              if (!f.isToxic()) {
                food.add(entity);
              }
            }
          }
        }
        return true;
      }
    };
    Vector2 lowerLeft = new Vector2(location).sub(radius, radius);
    Vector2 upperRight = new Vector2(location).add(radius, radius);
    getPhysicsWorld(world).QueryAABB(callback, lowerLeft.x, lowerLeft.y, upperRight.x, upperRight.y);

    return food;
  }

  /**
   * Find enemy in radius, centre is an point on map
   *
   * @param world artemis world
   * @param location centre of circle
   * @param radius radius to find
   * @return Enemy entity list
   */
  public static Array<Entity> findEnemyInRadius(final com.artemis.World world, final Vector2 location, final float radius) {
    // TODO: to be fixed
    final Array<Entity> enemy = new Array<Entity>();

    QueryCallback callback = new QueryCallback() {

      @Override
      public boolean reportFixture(Fixture fixture) {
        Body body = fixture.getBody();
        Entity entity = UuidUtil.getEntityByUuid(world, (UUID) body.getUserData());
        boolean isEnemy = false;
        if (isEnemy) {
          float distanceSq = new Vector2(body.getPosition()).sub(location).len2();
          if (distanceSq <= radius * radius) {
            enemy.add(entity);
          }
        }
        return true;
      }
    };

    Vector2 lowerLeft = new Vector2(location).sub(radius, radius);
    Vector2 upperRight = new Vector2(location).add(radius, radius);
    getPhysicsWorld(world).QueryAABB(callback, lowerLeft.x, lowerLeft.y, upperRight.x, upperRight.y);

    return enemy;
  }

  /**
   * Find nearest entity in list from a location
   *
   * @param world artemis world
   * @param location location
   * @param entityList entity list that you want to find
   * @return one entity
   */
  public static Entity findNearestEntityInList(com.artemis.World world, Vector2 location, Array<Entity> entityList) {
    Entity nearest = null;
    float closestDistanceSq = Float.MAX_VALUE;

    for (Entity e : entityList) {
      Vector2 position = getPosition(world, e);
      float distanceSq = new Vector2(position).sub(location).len2();
      if (distanceSq < closestDistanceSq) {
        nearest = e;
        closestDistanceSq = distanceSq;
      }
    }

    return nearest;
  }

  /**
   * Find player in radius from a location
   *
   * @param world artemis world
   * @param location location that you want to find
   * @param radius radius to find
   * @return Player entity list
   */
  public static Array<Entity> findPlayerNonAiInRadius(final com.artemis.World world, final Vector2 location, final float radius) {
    final Array<Entity> playerAndAIList = new Array<Entity>();

    QueryCallback callback = new QueryCallback() {

      @Override
      public boolean reportFixture(Fixture fixture) {
        Body body = fixture.getBody();
        Entity entity = UuidUtil.getEntityByUuid(world, (UUID) body.getUserData());
        if (isPlayerNonAiUnit(world, entity.getId())) {
          float distanceSq = new Vector2(body.getPosition()).sub(location).len2();
          if (distanceSq <= radius * radius) {
            playerAndAIList.add(entity);
          }
        }
        return true;
      }
    };
    Vector2 lowerLeft = new Vector2(location).sub(radius, radius);
    Vector2 upperRight = new Vector2(location).add(radius, radius);
    getPhysicsWorld(world).QueryAABB(callback, lowerLeft.x, lowerLeft.y, upperRight.x, upperRight.y);

    return playerAndAIList;
  }

  public static Array<Entity> findQueenInRadius(final com.artemis.World world, final Vector2 location, final float radius) {
    final Array<Entity> queenList = new Array<Entity>();

    QueryCallback callback = new QueryCallback() {

      @Override
      public boolean reportFixture(Fixture fixture) {
        Body body = fixture.getBody();
        Entity entity = UuidUtil.getEntityByUuid(world, (UUID) body.getUserData());
        if (isQueen(world, entity.getId())) {
          float distanceSq = new Vector2(body.getPosition()).sub(location).len2();
          if (distanceSq <= radius * radius) {
            queenList.add(entity);
          }
          return false;
        }
        return true;
      }
    };
    Vector2 lowerLeft = new Vector2(location).sub(radius, radius);
    Vector2 upperRight = new Vector2(location).add(radius, radius);
    getPhysicsWorld(world).QueryAABB(callback, lowerLeft.x, lowerLeft.y, upperRight.x, upperRight.y);

    return queenList;
  }

  public static IntBag getAllEntities(World world) {
    //TODO: world is not update, entities bag is not update when world progress 
    IntBag entities = world.getManager(AspectSubscriptionManager.class).get(Aspect.all()).getEntities();
    return entities;
  }

  public static IntBag getAllPlayerAndAi(World world) {
    IntBag entities = world.getManager(AspectSubscriptionManager.class).get(Aspect.all(Player.class)).getEntities();
    return entities;
  }

  public static IntBag getAllQueen(World world) {
    IntBag entities = world.getManager(AspectSubscriptionManager.class).get(Aspect.all(Queen.class)).getEntities();
    return entities;
  }

  public static int getEntityById(World world, int id) {
    //TODO: world is not update, entities bag is not update when world progress 
    IntBag entities = world.getManager(AspectSubscriptionManager.class).get(Aspect.all()).getEntities();
    return entities.get(id);
  }

  public static boolean isFood(World world, int id) {
    IntBag entities = world.getManager(AspectSubscriptionManager.class).get(Aspect.all(Food.class)).getEntities();
    return entities.contains(id);
  }

  public static boolean isPlayer(World world, int id) {
    IntBag entitiesA = world.getManager(AspectSubscriptionManager.class).get(Aspect.all(Player.class)).getEntities();
    return entitiesA.contains(id);
  }

  public static boolean isPlayerNonAiUnit(World world, int id) {
    IntBag entitiesA = world.getManager(AspectSubscriptionManager.class).get(Aspect.all(Player.class)).getEntities();
    IntBag entitiesB = world.getManager(AspectSubscriptionManager.class).get(Aspect.all(AiControl.class)).getEntities();

    if (entitiesB.contains(id)) {
      return false;
    }
    return entitiesA.contains(id);
  }

  public static boolean isAiUnit(World world, int id) {
    IntBag entities = world.getManager(AspectSubscriptionManager.class).get(Aspect.all(AiControl.class)).getEntities();
    return entities.contains(id);
  }

  public static boolean isQueen(World world, int id) {
    IntBag entities = world.getManager(AspectSubscriptionManager.class).get(Aspect.all(Queen.class)).getEntities();
    return entities.contains(id);
  }

  public static Array<Entity> findPlayerWithAiInRadius(final com.artemis.World world, final Entity e, final Vector2 location, final float radius) {
    final Array<Entity> entities = new Array<Entity>();

    QueryCallback callback = new QueryCallback() {

      @Override
      public boolean reportFixture(Fixture fixture) {
        Body body = fixture.getBody();
        Entity entity = UuidUtil.getEntityByUuid(world, (UUID) body.getUserData());
        if (EntityUtil.getComponent(world, e, SubUnit.class) != null || isPlayer(world, entity.getId()) && !e.equals(entity)) {
          float distanceSq = new Vector2(body.getPosition()).sub(location).len2();
          if (distanceSq <= radius * radius) {
            entities.add(entity);
          }
        }
        return true;
      }
    };
    Vector2 lowerLeft = new Vector2(location).sub(radius, radius);
    Vector2 upperRight = new Vector2(location).add(radius, radius);
    PhysicsUtil.getPhysicsWorld(world).QueryAABB(callback, lowerLeft.x, lowerLeft.y, upperRight.x, upperRight.y);

    return entities;
  }

  public static Vector2 getQueenPosition(final com.artemis.World world) {
    final Vector2 queenPosition = null;
    QueryCallback callback = new QueryCallback() {

      @Override
      public boolean reportFixture(Fixture fixture) {
        Body body = fixture.getBody();
        Entity entity = UuidUtil.getEntityByUuid(world, (UUID) body.getUserData());
        if (isQueen(world, entity.getId())) {
          queenPosition.x = body.getPosition().x;
          queenPosition.y = body.getPosition().y;
          return false;
        }
        return true;
      }

    };
    PhysicsUtil.getPhysicsWorld(world)
      .QueryAABB(callback, -scaleX, -scaleY, scaleX, scaleY);

    return queenPosition;

  }

  public static Array<Entity> findFood(final com.artemis.World world, final Vector2 location, final float radius) {
    final Array<Entity> entities = new Array<Entity>();

    QueryCallback callback = new QueryCallback() {

      @Override
      public boolean reportFixture(Fixture fixture) {
        Body body = fixture.getBody();
        Entity entity = UuidUtil.getEntityByUuid(world, (UUID) body.getUserData());
        if (isFood(world, entity.getId())) {
          float distanceSq = new Vector2(body.getPosition()).sub(location).len2();
          if (distanceSq <= radius * radius) {
            entities.add(entity);
          }
        }
        return true;
      }
    };
    Vector2 lowerLeft = new Vector2(location).sub(radius, radius);
    Vector2 upperRight = new Vector2(location).add(radius, radius);
    PhysicsUtil.getPhysicsWorld(world).QueryAABB(callback, lowerLeft.x, lowerLeft.y, upperRight.x, upperRight.y);

    return entities;
  }

  public static void addNearestPlayer(World world, Entity base, Entity newEntity) {
    if (isNearer(world, base, newEntity)) {
      EntityUtil.getComponent(world, base, Detection.class).setNearestPlayer(UuidUtil.getUuid(newEntity));
    }
  }

  public static void addNearestQueen(World world, Entity base, Entity newEntity) {
    if (isNearer(world, base, newEntity)) {
      EntityUtil.getComponent(world, base, Detection.class).setNearestQueen(UuidUtil.getUuid(newEntity));
    }
  }

  public static void addNearestFood(World world, Entity base, Entity newEntity) {
    if (isNearer(world, base, newEntity)) {
      EntityUtil.getComponent(world, base, Detection.class).setNearestFood(UuidUtil.getUuid(newEntity));
    }
  }

  public static boolean isNearer(World world, Entity base, Entity newEntity) {
    if (base == null || newEntity == null) {
      return false;
    }
    Detection detect = EntityUtil.getComponent(world, base, Detection.class);
    UUID lastNearestUuid = detect.getNearestPlayer();
    if (base.equals(newEntity)) {
      return false;
    }
    Entity lastNearestEntity = UuidUtil.getEntityByUuid(world, lastNearestUuid);
    if (lastNearestEntity == null) {
      return true;
    }
    Vector2 basePos = getPosition(world, base);
    Vector2 newEntityPos = getPosition(world, newEntity);
    Vector2 oldPos = getPosition(world, lastNearestEntity);
    float distanceFromOld = basePos.cpy().sub(oldPos.cpy()).len2();
    float distanceFromNew = basePos.cpy().sub(newEntityPos.cpy()).len2();
    return distanceFromNew <= distanceFromOld;
  }
}
