/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.buff.effects;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.game.buff.BuffEffect;
import com.dongbat.game.component.Stats;
import com.dongbat.game.component.UnitMovement;
import com.dongbat.game.util.ECSUtil;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.WorldQueryUtil;
import com.dongbat.game.util.localUtil.Constants;

/**
 *
 * @author Admin
 */
public class Respawn implements BuffEffect {

  private float minRadius = 3f;
  private Vector2 position;

  @Override
  public void durationStart(World world, Entity source, Entity target) {
    PhysicsUtil.setRadius(source.getWorld(), source, 0f);
    EntityUtil.getComponent(world, target, Stats.class).setAllowComsumming(false);
    EntityUtil.getComponent(world, target, Stats.class).setConsumable(false);
    PhysicsUtil.setVelocity(world, target, new Vector2());
    EntityUtil.getComponent(world, target, UnitMovement.class).setDisabled(true);
    if (WorldQueryUtil.isAiUnit(world, target.getId())) {
      PhysicsUtil.setPosition(target.getWorld(), target, new Vector2(150, 150));
    }
  }

  @Override
  public void update(World world, Entity source, Entity target) {
  }

  @Override
  public void durationEnd(World world, Entity source, Entity target) {
    EntityUtil.getComponent(world, target, Stats.class).setAllowComsumming(true);
    EntityUtil.getComponent(world, target, Stats.class).setConsumable(true);
    EntityUtil.getComponent(world, target, UnitMovement.class).setDisabled(false);

    PhysicsUtil.setRadius(target.getWorld(), target, minRadius);
    Vector2 randomPos
      = new Vector2(
        ECSUtil.getRandom(world).getFloat(-Constants.GAME.FRAME_WIDTH / 2, Constants.GAME.FRAME_WIDTH / 2),
        ECSUtil.getRandom(world).getFloat(-Constants.GAME.FRAME_HEIGHT / 2, Constants.GAME.FRAME_HEIGHT / 2));
    if (position == null) {
      position = randomPos;
    }
    PhysicsUtil.setPosition(target.getWorld(), target, position);
  }

}
