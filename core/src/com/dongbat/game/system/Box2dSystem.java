/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.system;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.Entity;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.dongbat.game.component.CollisionComponent;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.UuidUtil;
import java.util.UUID;

/**
 * @author Admin
 */
public class Box2dSystem extends TimeSlicingSystem {

  private World physicWorld;
  private boolean uninitialized = true;

  public void setUninitialized(boolean uninitialized) {
    this.uninitialized = uninitialized;
  }

  public Box2dSystem(int frameSlicing) {
    super(frameSlicing);
  }

  private void setContactListener() {
    physicWorld.setContactListener(new ContactListener() {

      @Override
      public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        UUID userDataA = (UUID) fixtureA.getBody().getUserData();
        UUID userDataB = (UUID) fixtureB.getBody().getUserData();

        addCollidedEntity(userDataA, userDataB);

      }

      @Override
      public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        UUID userDataA = (UUID) fixtureA.getBody().getUserData();
        UUID userDataB = (UUID) fixtureB.getBody().getUserData();

        removeCollidedEntity(userDataA, userDataB);
      }

      @Override
      public void preSolve(Contact contact, Manifold oldManifold) {
      }

      @Override
      public void postSolve(Contact contact, ContactImpulse impulse) {
      }

    });
  }

  @Override
  protected void processTimeSlice() {
    physicWorld = PhysicsUtil.getPhysicsWorld(world);
    if (uninitialized) {
      uninitialized = false;
      setContactListener();
    }
    IntBag entities = world.getManager(AspectSubscriptionManager.class).get(Aspect.all(CollisionComponent.class)).getEntities();
    for (int i = 0; i < entities.size(); i++) {
      Entity e = world.getEntity(i);
      if (e == null) {
        continue;
      }
      CollisionComponent collision = EntityUtil.getComponent(world, e, CollisionComponent.class);
      collision.getJustCollidedList().clear();
    }
    physicWorld.step(world.getDelta(), 8, 3);
  }

  private void addCollidedEntity(UUID idA, UUID idB) {
    if (idA == null || idB == null) {
      return;
    }
    Entity a = UuidUtil.getEntityByUuid(world, idA);
    Entity b = UuidUtil.getEntityByUuid(world, idB);
    if (a == null || b == null || !a.isActive() || !b.isActive()) {
      return;
    }
    CollisionComponent collisionA = EntityUtil.getComponent(world, a, CollisionComponent.class);
    CollisionComponent collisionB = EntityUtil.getComponent(world, b, CollisionComponent.class);

    if (collisionA != null) {
      collisionA.getJustCollidedList().add(UuidUtil.getUuid(b));
      collisionA.getCollidedList().add(UuidUtil.getUuid(b));
    }
    if (collisionB != null) {
      collisionB.getJustCollidedList().add(UuidUtil.getUuid(a));
      collisionB.getCollidedList().add(UuidUtil.getUuid(a));
    }
  }

  private void removeCollidedEntity(UUID idA, UUID idB) {
    if (idA == null || idB == null) {
      return;
    }
    Entity a = UuidUtil.getEntityByUuid(world, idA);
    Entity b = UuidUtil.getEntityByUuid(world, idB);
    if (a == null || b == null || !a.isActive() || !b.isActive()) {
      return;
    }
    CollisionComponent collisionA = EntityUtil.getComponent(world, a, CollisionComponent.class);
    CollisionComponent collisionB = EntityUtil.getComponent(world, b, CollisionComponent.class);

    if (collisionA != null) {
      collisionA.getCollidedList().removeValue(UuidUtil.getUuid(b), false);
    }
    if (collisionB != null) {
      collisionB.getCollidedList().removeValue(UuidUtil.getUuid(a), false);
    }
  }
}
