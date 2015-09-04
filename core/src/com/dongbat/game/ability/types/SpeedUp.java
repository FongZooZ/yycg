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
import com.dongbat.game.util.BuffUtil;
import com.dongbat.game.util.EntityUtil;

/**
 *
 * @author MINHMINH
 */
public class SpeedUp implements Ability {

  private int duration;

  @Override
  public String getTooltip() {
    return "Speeddddddddd Up";
  }

  @Override
  public void cast(World world, Entity caster, Vector2 target) {
    AbilityComponent playerAbilityList = EntityUtil.getComponent(world, caster, AbilityComponent.class);
    AbilityInfo info = playerAbilityList.getAbility("SpeedUp");
    if (info == null) {
      return;
    }
    BuffUtil.addBuff(world, caster, caster, "SpeedUp", duration, info.getLevel());
  }

}
