package com.dongbat.game.ability.types;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.game.ability.Ability;
import com.dongbat.game.component.UnitMovement;
import com.dongbat.game.util.BuffUtil;
import com.dongbat.game.util.ECSUtil;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.UuidUtil;
import com.dongbat.game.util.factory.EntityFactory;
import static com.dongbat.game.util.localUtil.Constants.PHYSICS.FOOD_RADIUS;
import static com.dongbat.game.util.localUtil.Constants.PHYSICS.MIN_SQUARE;

/**
 * Created by FongZooZ on 8/28/2015.
 */
public class Flee implements Ability {

  private int duration;
  private float forceStrength;
  private int foodNumber = 10;
  private float degree = 10;

  /**
   * Get tooltip of ability
   *
   * @return tooltip in String
   */
  @Override
  public String getTooltip() {
    return "Player will move faster but loss amount of food behind!";
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

    // TODO big unit, more food, more loss, one must pay the right price
    UnitMovement unitMovement = EntityUtil.getComponent(world, caster, UnitMovement.class);

    Vector2 destination = unitMovement.getDirectionVelocity();
    if (destination == null || destination.cpy().nor().len2() == 0) {
      return;
    }

    Vector2 position = PhysicsUtil.getPosition(world, caster);

    float totalSquare = PhysicsUtil.getSquare(FOOD_RADIUS) * foodNumber;
    if (totalSquare > PhysicsUtil.getSquare(world, caster) + MIN_SQUARE) {
      return;
    }
    PhysicsUtil.increaseSquare(world, caster, -totalSquare);
    for (int i = 0; i < foodNumber; i++) {
      float d = ECSUtil.getRandom(world).getFloat(-degree / 2, degree / 2);
      Vector2 direction = destination.cpy().scl(-1).nor().rotate(d);

      Entity food = EntityFactory.createFood(world, position, UuidUtil.getUuid(caster));
      // TODO: food expiring system
      BuffUtil.addBuff(world, caster, food, "ToBeRemoved", 2000, 1);
      BuffUtil.addBuff(world, caster, food, "ToxicFood", 400, 1);
      BuffUtil.addBuff(world, caster, food, "Forced", (int) (400 * ECSUtil.getRandom(world).getFloat(.5f, 1.25f)), 1, "forceStrength", forceStrength, "direction", direction);
    }
    BuffUtil.addBuff(world, caster, caster, "FleeForced", 500, 1, "forceStrength", forceStrength, "direction", target);
  }
}
