package com.dongbat.game.system;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.game.component.Display;
import com.dongbat.game.component.Physics;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.PhysicsUtil;

/**
 * Created by FongZooZ on 8/31/2015.
 */
public class DisplayUpdateSystem extends EntityProcessingSystem {

  private float tightness = 0.5f;

  /**
   * Creates a new EntityProcessingSystem.
   */
  public DisplayUpdateSystem() {
    super(Aspect.all(Physics.class, Display.class));
  }

  /**
   * Process a entity this system is interested in.
   *
   * @param e the entity to process
   */
  @Override
  protected void process(Entity e) {

    // TODO: add new position forcing 
    Display display = EntityUtil.getComponent(world, e, Display.class);

    Vector2 position = PhysicsUtil.getPosition(world, e);
    Vector2 displayedPosition = display.getPosition();
    Vector2 displayedPos
      = displayedPosition.cpy().add(position.cpy().sub(displayedPosition.cpy()).scl(tightness));

//    display.setPosition(PhysicsUtil.getPosition(world, e));
    display.setPosition(displayedPos);

//    current.position = previous.position + (target.position - previous.position) * tightness;
//    current.orientation = slerp(previous.orientation, target.orientation, tightness);
    display.setRadius(PhysicsUtil.getRadius(world, e) * 2);
    display.setRotation(PhysicsUtil.getVelocity(world, e).angle());

  }
}
