/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util;

import com.artemis.Entity;
import com.artemis.World;
import com.dongbat.game.buff.BuffEffect;
import com.dongbat.game.buff.BuffInfo;
import com.dongbat.game.component.BuffComponent;
import com.dongbat.game.registry.BuffRegistry;

/**
 * @author Admin
 */
public class BuffUtil {

  /**
   * Add a buff to an entity BuffComponent will contain source, target to make
   * the target entity is added from which entity
   *
   * @param world artemis world
   * @param source entity that affect a buff to target
   * @param target entity that receive a buff from source
   * @param name name of BuffComponent in String
   * @param duration duration of BuffComponent in millisecond
   * @param level level of BuffComponent
   * @param args optional arguments
   */
  public static void addBuff(World world, Entity source, Entity target, String name, int duration, int level, Object... args) {
    BuffComponent buffComponent = EntityUtil.getComponent(world, target, BuffComponent.class);
    if (buffComponent == null) {
      return;
    }
    
    BuffEffect effect = BuffRegistry.getEffect(name, level);
    BuffRegistry.setBuffData(effect, args);
    if (buffComponent.getBuffInfo(name) != null) {
      BuffInfo buffInfo = buffComponent.getBuffInfo(name);
      long convertMilisToFrame = TimeUtil.convertMillisToFrame(world, duration);
      //SUPER TODO: sua loi ...
      buffInfo.setEndTime(ECSUtil.getFrame(world) + convertMilisToFrame);
      return;
    }
    BuffInfo buffInfo = new BuffInfo(world, effect, TimeUtil.getCurrentFrame(world), duration, UuidUtil.getUuid(source));
    buffComponent.getBuffs().put(name, buffInfo);
    // start effect
    effect.durationStart(world, source, target);
  }

  /**
   * Check an entity has a buff or not
   *
   * @param world artemis world
   * @param entity entity you want to check
   * @param name name of BuffComponent in String
   * @return return true if entity has that buff
   */
  public static boolean hasBuff(World world, Entity entity, String name) {
    BuffComponent buffComponent = EntityUtil.getComponent(world, entity, BuffComponent.class);
    if (buffComponent == null) {
      return false;
    }
    BuffInfo buffInfo = buffComponent.getBuffs().get(name);
    if (buffInfo == null) {
      return false;
    }
    return true;
  }
}
