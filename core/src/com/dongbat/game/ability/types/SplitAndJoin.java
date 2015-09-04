/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.ability.types;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.dongbat.game.ability.Ability;
import com.dongbat.game.ability.AbilityInfo;
import com.dongbat.game.component.AbilityComponent;
import com.dongbat.game.component.UnitMovement;
import com.dongbat.game.util.BuffUtil;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.factory.UnitFactory;
import com.dongbat.game.util.localUtil.Constants;

/**
 *
 * @author Admin
 */
public class SplitAndJoin implements Ability {

  @Override
  public String getTooltip() {
    return "split and join";
  }

  @Override
  public void cast(World world, Entity caster, Vector2 target) {
    AbilityComponent playerAbilityList = EntityUtil.getComponent(world, caster, AbilityComponent.class);
    AbilityInfo info = playerAbilityList.getAbility("SplitAndJoin");
    if (info == null) {
      return;
    }
    Vector2 position = PhysicsUtil.getPosition(world, caster);
    UnitMovement ms = EntityUtil.getComponent(world, caster, UnitMovement.class);
    Vector2 direction = ms.getDirectionVelocity();
    if (direction == null) {
      return;
    }
    float radius = PhysicsUtil.getRadius(world, caster);
    if (radius <= Constants.PHYSICS.MIN_RADIUS * 2) {
      return;
    }
    float newRadius = radius / 1.41f;

//    BuffUtil.addBuff(world, caster, caster, "Forced", 1500, 1, "forceStrength", 100, "direction", direction.cpy().scl(-1));
    PhysicsUtil.setRadius(world, caster, newRadius);

    Entity bullet = UnitFactory.createProjectileUnit(
      world, caster, direction.cpy().nor().scl(radius + newRadius).add(position.cpy()), newRadius, true, true, true);
    ObjectMap<String, Object> args = new ObjectMap<String, Object>();
//    Display display = EntityUtil.getComponent(world, caster, Display.class);
//    TextureAtlas split = AssetUtil.getUnitAtlas().get("split");
//    Animation animation = new Animation(0.075f, split.getRegions());
//    animation.setPlayMode(Animation.PlayMode.LOOP);
//    display.setGetOverridenStart(TimeUtil.getCurrentFrame(world));
//    display.setOverridenDuration(1500);
//    display.setOverridenAnimation(new AnimatedSprite(animation));

//    args.put("forceStrength", 100);
//    args.put("direction", direction.cpy().scl(1));
//    BuffUtil.addBuff(world, caster, bullet, "DelayBuff", 0, 1, "buffName", "Forced", "duration", 3000, "args", args);
    BuffUtil.addBuff(world, caster, bullet, "Forced", 500, 1, "forceStrength", 20, "direction", direction.cpy().scl(1), "ignoreMass", true);

    args.put("feedPerSecond", 2f);
    BuffUtil.addBuff(world, caster, bullet, "DelayBuff", 1000, 1, "buffName", "Feed", "duration", -1, "args", args);
    BuffUtil.addBuff(world, bullet, bullet, "AttractFood", -1, 1);
//    BuffUtil.addBuff(world, bullet, bullet, "Feed", -1, 1, "feedPerSecond", 0.55f);
  }

}
