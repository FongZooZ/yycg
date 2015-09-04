/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.system;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.dongbat.game.buff.BuffEffect;
import com.dongbat.game.buff.BuffInfo;
import com.dongbat.game.component.BuffComponent;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.TimeUtil;
import com.dongbat.game.util.UuidUtil;
import java.util.UUID;

/**
 * @author Admin co tac dung mat 300 thang: BUG // TODO
 */
public class BuffSystem extends EntityProcessingSystem {

  private Array<String> toRemove;

  public BuffSystem() {
    super(Aspect.all(BuffComponent.class));
  }

  /**
   * Override to implement code that gets executed when systems are initialized.
   */
  @Override
  protected void initialize() {
    toRemove = new Array<String>();
  }

  @Override
  protected void process(Entity entity) {
    BuffComponent buffComponent = EntityUtil.getComponent(world, entity, BuffComponent.class);
    ObjectMap<String, BuffInfo> buffs = buffComponent.getBuffs();
    toRemove.clear();

    for (ObjectMap.Entry<String, BuffInfo> buff : buffs) {
      BuffEffect effect = buff.value.getEffect();
      UUID id = buff.value.getSource();
      Entity source = UuidUtil.getEntityByUuid(world, id);
      if (buff.value.getEndTime() <= TimeUtil.getCurrentFrame(world) && !buff.value.isPermanent()) {
        effect.durationEnd(world, source, entity);
        toRemove.add(buff.key);
        continue;
      }
      effect.update(world, source, entity);
    }

    for (String name : toRemove) {
      buffs.remove(name);
    }
  }

}
