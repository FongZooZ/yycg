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
import com.dongbat.game.util.BuffUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.WorldQueryUtil;

/**
 *
 * @author BINHDUONG
 */
public class AttractFood implements BuffEffect {

  private float radius = 20;

  @Override
  public void durationStart(World world, Entity source, Entity target) {
  }

  @Override
  public void update(World world, Entity source, Entity target) {
    Vector2 pos = PhysicsUtil.getPosition(world, target);
    float r = PhysicsUtil.getRadius(world, target);

    Array<Entity> foundList = WorldQueryUtil.findAnyInRadius(world, pos, radius + r);
    for (Entity e : foundList) {
      if (WorldQueryUtil.isFood(world, e.getId())) {
        if (BuffUtil.hasBuff(world, e, "AttractedToSource")) {
          continue;
        }
        // TODO: cannot use target here, must check why
        BuffUtil.addBuff(world, source, e, "AttractedToSource", 400, 1);
      }
    }
  }

  @Override
  public void durationEnd(World world, Entity source, Entity target) {
  }

}
