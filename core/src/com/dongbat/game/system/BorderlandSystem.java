/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.system;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.game.component.Physics;
import com.dongbat.game.component.UnitMovement;
import com.dongbat.game.util.BuffUtil;
import com.dongbat.game.util.EntityUtil;
import static com.dongbat.game.util.FoodSpawningUtil.scaleX;
import static com.dongbat.game.util.FoodSpawningUtil.scaleY;
import com.dongbat.game.util.PhysicsUtil;

/**
 *
 * @author Admin
 */
public class BorderlandSystem extends EntityProcessingSystem {

  boolean pushing = false;

  public BorderlandSystem() {
    super(Aspect.all(Physics.class));

  }

  @Override
  protected void process(Entity e) {
    Physics physics = EntityUtil.getComponent(world, e, Physics.class);
    UnitMovement movement = EntityUtil.getComponent(world, e, UnitMovement.class);
    Vector2 pos = physics.getBody().getPosition();
    float radius = PhysicsUtil.getRadius(world, e);
    if (radius < 0.5f) {
      radius = 0.5f;
    }
    if (movement == null) {
      return;
    }

    if (BuffUtil.hasBuff(world, e, "WallForced")) {
      return;
    }

    float x = 0;
    float y = 0;

    if (pos.x > scaleX) {
      x = -1;
    }

    if (pos.x < -scaleX) {
      x = 1;
    }

    if (pos.y < -scaleY) {
      y = 1;
    }

    if (pos.y > scaleY) {
      y = -1;
    }

    if (x != 0 || y != 0) {
      BuffUtil.addBuff(world, e, e, "WallForced", 500, 1, "forceStrength", radius, "direction", new Vector2(x, y));
    }
  }

}
