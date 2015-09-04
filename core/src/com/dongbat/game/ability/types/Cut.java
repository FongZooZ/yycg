/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.ability.types;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.game.ability.Ability;
import com.dongbat.game.ability.AbilityInfo;
import com.dongbat.game.component.AbilityComponent;
import com.dongbat.game.component.UnitMovement;
import com.dongbat.game.util.BuffUtil;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.factory.UnitFactory;

/**
 *
 * @author Admin
 */
public class Cut implements Ability {

  private int duration;

  @Override
  public String getTooltip() {
    return "saw saw";
  }

  @Override
  public void cast(World world, Entity caster, Vector2 target) {
    AbilityComponent playerAbilityList = EntityUtil.getComponent(world, caster, AbilityComponent.class);
    AbilityInfo info = playerAbilityList.getAbility("Cut");
    if (info == null) {
      return;
    }
    Vector2 pos = PhysicsUtil.getPosition(world, caster);
    UnitMovement ms = EntityUtil.getComponent(world, caster, UnitMovement.class);
    Entity bullet = UnitFactory.createProjectileUnit(world, caster, pos, 0.1f, false, false, false);

    BuffUtil.addBuff(world, caster, bullet, "Forced", -1, 1, "forceStrength", 1.5f, "direction", ms.getDirectionVelocity());
    BuffUtil.addBuff(world, caster, bullet, "CutProjectile", -1, 1);
    BuffUtil.addBuff(world, caster, bullet, "ToBeRemoved", 1000, 1);

  }

}
