/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.ability;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Admin
 */
public interface Ability {

  /**
   * Get tooltip of ability
   *
   * @return tooltip in String
   */
  public String getTooltip();

  /**
   * Cast ability to the target when entity caster cast by using input on screen
   *
   * @param world artemis world
   * @param caster entity which use ability
   * @param target target of ability
   */
  public void cast(World world, Entity caster, Vector2 target);
}
