package com.dongbat.game.ability.types;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.dongbat.game.ability.Ability;
import com.dongbat.game.util.BuffUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.WorldQueryUtil;

/**
 * Created by FongZooZ on 8/28/2015.
 */
public class Vacuum implements Ability {

  private float degree = 30;
  private float radius = 20;
  private float forceStrength = 10;
  private float modifier = 1;
  private boolean ignoreMass = true;

  /**
   * Get tooltip of ability
   *
   * @return tooltip in String
   */
  @Override
  public String getTooltip() {
    return "Suck anything smaller in radius.";
  }

  /**
   * Cast ability to the target when entity caster cast by using input on screen
   *
   * @param world artemis world
   * @param caster entity which use ability
   * @param target target of ability
   */
  @Override
  public void cast(World world, Entity caster, Vector2 target) {
    Vector2 playerPosition = PhysicsUtil.getPosition(world, caster);
    float sourceRadius = PhysicsUtil.getRadius(world, caster);
    Vector2 point = target.cpy();
    point.nor().scl(sourceRadius).add(playerPosition);

    Array<Entity> foundList = WorldQueryUtil.findAnyInRadius(world, playerPosition, radius + sourceRadius);
    Array<Entity> filterList = WorldQueryUtil.filterEntityInRay(world, foundList, playerPosition, target, degree);
    for (Entity e : filterList) {
      if (e != caster) {
        BuffUtil.addBuff(world, caster, e, "AttractedToSource", 1000, 1, "forceStrength", forceStrength * modifier, "ignoreMass", ignoreMass);
      }
    }
  }
}
