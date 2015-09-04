/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.system;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.dongbat.game.component.Player;
import com.dongbat.game.component.UnitMovement;
import com.dongbat.game.dataobject.CustomInput;
import com.dongbat.game.util.ECSUtil;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.localUtil.Constants;

/**
 *
 * @author Admin
 */
public class InputProcessorSystem extends EntityProcessingSystem {

  public InputProcessorSystem() {
    super(Aspect.all(Player.class));
  }

  @Override
  protected void process(Entity e) {
    Player playerComponent = EntityUtil.getComponent(world, e, Player.class);
    ObjectMap<Long, CustomInput> inputs = playerComponent.getInputs();
    CustomInput customInput = inputs.get(ECSUtil.getFrame(world));
    if (customInput != null) {
      if (customInput.getType() == Constants.InputType.MOVE) {
        Vector2 position = customInput.getPosition();
        UnitMovement move = EntityUtil.getComponent(world, e, UnitMovement.class);
//        if (position.cpy().dst2(PhysicsUtil.getPosition(world, e)) > (PhysicsUtil.getcollisionRadius(world, e) * PhysicsUtil.getcollisionRadius(world, e))) {
        move.setDirectionVelocity(position);
//        } else {
//          move.setDirectionVelocity(null);
//        }
      }
      if (customInput.getType() == Constants.InputType.ABILITY) {

      }
      if (customInput.getType() == Constants.InputType.PAUSE) {
        //TODO
      }
    }
  }

}
