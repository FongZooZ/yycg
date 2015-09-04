/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.system.localSystem;

import com.artemis.BaseSystem;
import com.artemis.Entity;
import com.dongbat.game.component.Display;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.UuidUtil;
import com.dongbat.game.util.localUtil.LocalPlayerUtil;
import com.dongbat.game.util.localUtil.PhysicsCameraUtil;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class CameraUpdateSystem extends BaseSystem {

  @Override
  protected void initialize() {
  }

  @Override
  protected void processSystem() {

    UUID localPlayerId = LocalPlayerUtil.getLocalPlayer();
    Entity e = UuidUtil.getEntityByUuid(world, localPlayerId);
    //TODO: not check here
    if (e == null) {
      return;
    }
    Display display = EntityUtil.getComponent(world, e, Display.class);
    PhysicsCameraUtil.getCamera().position.set(display.getPosition(), 0);
    PhysicsCameraUtil.getCamera().zoom = PhysicsCameraUtil.getZoomScale(world, e);
//    PhysicsCameraUtil.getCamera().zoom = 6f;
    PhysicsCameraUtil.getCamera().update();

  }

}
