/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.buff.effects;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.dongbat.game.buff.BuffEffect;
import com.dongbat.game.component.UnitMovement;
import com.dongbat.game.util.BuffUtil;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.WorldQueryUtil;

/**
 *
 * @author Admin
 */
public class HotBlow implements BuffEffect {

  private float radius = 5;
  private float degree = 30;
  private float forceStrength;
  private int duration;

  @Override
  public void durationStart(World world, Entity source, Entity target) {
  }

  @Override
  public void update(World world, Entity source, Entity target) {

    Vector2 playerPosition = PhysicsUtil.getPosition(world, source);
    UnitMovement unitMs = EntityUtil.getComponent(world, target, UnitMovement.class);
    if (unitMs.getDirectionVelocity() == null) {
      return;
    }
    Array<Entity> foundList = WorldQueryUtil.findAnyInRadius(world, playerPosition, radius);
    Array<Entity> filterList = WorldQueryUtil.filterEntityInRay(world, foundList, playerPosition, unitMs.getDirectionVelocity(), degree);
    for (Entity e : filterList) {
      if (e != source) {
        Vector2 victimPosition = PhysicsUtil.getPosition(world, e);
        Vector2 direction = victimPosition.cpy().sub(playerPosition).cpy().scl(-1).nor();
        BuffUtil.addBuff(world, source, e, "HotBlowForced", duration, 1, "forceStrength", forceStrength, "direction", direction.cpy().scl(-1));
        BuffUtil.addBuff(world, source, e, "ToxicFood", 400, 1);
      }
    }
  }

  @Override
  public void durationEnd(World world, Entity source, Entity target) {
  }

}
