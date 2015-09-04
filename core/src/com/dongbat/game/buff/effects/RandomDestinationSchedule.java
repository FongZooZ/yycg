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
import com.dongbat.game.component.UnitMovement;
import com.dongbat.game.util.ECSUtil;
import com.dongbat.game.util.EntityUtil;
import static com.dongbat.game.util.FoodSpawningUtil.scaleX;
import static com.dongbat.game.util.FoodSpawningUtil.scaleY;
import com.dongbat.game.util.PhysicsUtil;

/**
 *
 * @author Admin
 */
public class RandomDestinationSchedule implements BuffEffect {

  @Override
  public void durationStart(World world, Entity source, Entity target) {
    UnitMovement unitComponent = EntityUtil.getComponent(world, target, UnitMovement.class);
    float posX = (float) (ECSUtil.getRandom(world).getFloat(-1, 1) * scaleX);
    float posY = (float) (ECSUtil.getRandom(world).getFloat(-1, 1) * scaleY);
    Vector2 position = PhysicsUtil.getPosition(world, target);
    Vector2 destination = (new Vector2(posX, posY)).cpy().sub(position.cpy());
    unitComponent.setDirectionVelocity(destination.cpy().nor());
  }

  @Override
  public void update(World world, Entity source, Entity target) {
  }

  @Override
  public void durationEnd(World world, Entity source, Entity target) {
  }

}
