/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.system;

import com.artemis.BaseSystem;
import com.dongbat.game.util.FoodSpawningUtil;

/**
 *
 * @author Admin
 */
public class SpawnningFoodSystem extends BaseSystem {

  @Override
  protected void processSystem() {
    FoodSpawningUtil.spawnFood(world);
  }

}
