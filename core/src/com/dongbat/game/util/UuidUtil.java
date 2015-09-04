/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.UuidEntityManager;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class UuidUtil {

  public static UUID getUuid(Entity e) {
    return e.getWorld().getManager(UuidEntityManager.class).getUuid(e);
  }

  public static Entity getEntityByUuid(World world, UUID id) {
    return world.getManager(UuidEntityManager.class).getEntity(id);
  }

  public static void setUuid(Entity e, UUID id) {
    e.getWorld().getManager(UuidEntityManager.class).setUuid(e, id);
  }
}
