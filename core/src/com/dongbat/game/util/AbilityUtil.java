/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.dongbat.game.ability.AbilityInfo;
import com.dongbat.game.component.AbilityComponent;
import com.dongbat.game.registry.AbilityRegistry;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class AbilityUtil {

  public static boolean isAvailable(World world, Entity entity, String name) {
    AbilityComponent abilityComponent = EntityUtil.getComponent(world, entity, AbilityComponent.class);
    if (abilityComponent == null) {
      return false;
    }
    AbilityInfo ability = abilityComponent.getAbility(name);
    if (ability == null) {
      return false;
    }
    long lastCast = ability.getLastCast();
    return lastCast == -1 || (TimeUtil.convertFrameToMillis(world, (int) (TimeUtil.getCurrentFrame(world) - lastCast)) > ability.getCooldown());
  }

  public static long getLastCast(World world, Entity entity, String name) {
    AbilityComponent abilityComponent = EntityUtil.getComponent(world, entity, AbilityComponent.class);
    if (abilityComponent == null) {
      return -1;
    }
    AbilityInfo ability = abilityComponent.getAbility(name);
    if (ability == null) {
      return -1;
    }
    long lastCast = ability.getLastCast();
    return lastCast;
  }

  public static long getCooldown(World world, Entity entity, String name) {
    AbilityComponent abilityComponent = EntityUtil.getComponent(world, entity, AbilityComponent.class);
    if (abilityComponent == null) {
      return -1;
    }
    AbilityInfo ability = abilityComponent.getAbility(name);
    if (ability == null) {
      return -1;
    }
    long cooldown = ability.getCooldown();
    return cooldown;
  }

  public static AbilityComponent abilityComponentFilled(String abilities, AbilityComponent component) {
    ObjectMap<String, AbilityInfo> abilityMapsByString = getAbilityMapsByString(abilities);
    component.setAbilities(abilityMapsByString);
    return component;
  }

  public static ObjectMap<String, AbilityInfo> getAbilityMapsByString(String abilities) {
    String[] abilityArray = abilities.split(",");
    ObjectMap<String, AbilityInfo> abilityMaps = new ObjectMap<String, AbilityInfo>();
    for (String abilityName : abilityArray) {
      AbilityInfo ability = AbilityRegistry.getAbility(abilityName, 1);
      abilityMaps.put(abilityName, ability);
    }
    return abilityMaps;
  }

  /**
   * Let entity execute this ability when ability is available
   *
   * @param world artemis world
   * @param entity entity that you want to use this ability
   * @param name name of ability in String
   * @param target destination of player pointer on the screen
   */
  public static void use(World world, Entity entity, String name, Vector2 target) {
    if (!isAvailable(world, entity, name)) {
      return;
    }
    AbilityComponent abilityComponent = EntityUtil.getComponent(world, entity, AbilityComponent.class);
    if (abilityComponent == null) {
      return;
    }
    AbilityInfo ability = abilityComponent.getAbility(name);
    if (ability == null) {
      return;
    }
    ability.getAbility().cast(world, entity, target);
    ability.setLastCast(TimeUtil.getCurrentFrame(world));
  }
}
