/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.ability.types;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.dongbat.game.ability.Ability;
import com.dongbat.game.component.Food;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.WorldQueryUtil;

/**
 *
 * @author Admin
 */
public class Chill implements Ability {

  private float degree = 30;
  private float radius = 20;

  @Override
  public String getTooltip() {
    return "e";
  }

  @Override
  public void cast(World world, Entity caster, Vector2 target) {

    Vector2 playerPosition = PhysicsUtil.getPosition(world, caster);
    float sourceRadius = PhysicsUtil.getRadius(world, caster);

    Array<Entity> foundList = WorldQueryUtil.findAnyInRadius(world, playerPosition, radius + sourceRadius);
    Array<Entity> filterList = WorldQueryUtil.filterEntityInRay(world, foundList, playerPosition, target, degree);
    for (Entity e : filterList) {
      if (e != caster) {
        if (WorldQueryUtil.isFood(world, e.getId())) {
          EntityUtil.getComponent(world, e, Food.class).setToxic(false);
        }
      }
    }
  }

}
