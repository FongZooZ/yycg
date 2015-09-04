/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ObjectMap;
import com.dongbat.game.component.Display;

/**
 * @author implicit-invocation
 */
public class AssetUtil {

  private static AssetManager manager;
  private static TextureLoader.TextureParameter parameter;

  private static ObjectMap<String, TextureAtlas> unitAtlas;
  public static Texture db;
  public static Texture logo;
  private static TextureAtlas abilities;
  private static TextureAtlas usedAbilities;
  public static Texture cooldown;
  public static Texture joyBg;
  public static Texture joyKnob;
  public static Texture title;
  public static Texture singleButton;
  public static Texture multiButton;
  public static Texture storyButton;
  public static Texture pressedSingleButton;
  public static Texture pressedMultiButton;
  public static Texture backButton;
  public static Texture backDownButton;
  public static Texture endTitle;
  public static Texture move;

  public static TextureAtlas.AtlasRegion getAbilityTexture(String name) {
    return getAbilityTexture(name, false);
  }

  public static TextureAtlas.AtlasRegion getAbilityTexture(String name, boolean used) {
    if (used) {
      return usedAbilities.findRegion(name);
    }
    return abilities.findRegion(name);
  }

  public static ObjectMap<String, TextureAtlas> getUnitAtlas() {
    return unitAtlas;
  }

  public static void setUnitAtlas(ObjectMap<String, TextureAtlas> unitAtlas) {
    AssetUtil.unitAtlas = unitAtlas;
  }

  public static AssetManager getManager() {
    if (manager == null) {
      init();
    }
    return manager;
  }

  private static void init() {
    manager = new AssetManager(new ResolutionFileResolver(new InternalFileHandleResolver(), new ResolutionFileResolver.Resolution[]{
      new ResolutionFileResolver.Resolution(480, 800, "hdpi"), //                new ResolutionFileResolver.Resolution(720, 1280, "xhdpi"),
    //                new ResolutionFileResolver.Resolution(960, 1600, "xxhdpi")
    }));

    parameter = new TextureLoader.TextureParameter();
    parameter.minFilter = Texture.TextureFilter.Linear;
    parameter.magFilter = Texture.TextureFilter.Linear;

    unitAtlas = new ObjectMap<String, TextureAtlas>();
  }

  public static void loadAsset() {
    AssetManager manager = getManager();

    // in-game character
    manager.load("texture/unit/move/move.atlas", TextureAtlas.class);
    manager.load("texture/unit/split/split.atlas", TextureAtlas.class);
    manager.load("texture/queen/queen.atlas", TextureAtlas.class);
    manager.load("texture/food/hot/hot_food.atlas", TextureAtlas.class);
    manager.load("texture/food/cold/cold_food.png", Texture.class, parameter);

    // abilities
    manager.load("texture/abilities/abilities.atlas", TextureAtlas.class);
    manager.load("texture/abilities_used/abilities.atlas", TextureAtlas.class);
    manager.load("texture/cooldown_button/circle.png", Texture.class, parameter);

    // for touchpad joystick
    manager.load("texture/joypad/joy_bg.png", Texture.class, parameter);
    manager.load("texture/joypad/joy_knob.png", Texture.class, parameter);

    // for parallax
    manager.load("texture/background/bg00.png", Texture.class, parameter);
    manager.load("texture/background/bg01.png", Texture.class, parameter);
    manager.load("texture/background/bg02.png", Texture.class, parameter);
    manager.load("texture/background/bg03.png", Texture.class, parameter);

    // main menu
    manager.load("texture/menu/main/name.png", Texture.class, parameter);
    manager.load("texture/menu/main/single.png", Texture.class, parameter);
    manager.load("texture/menu/main/multi_disabled.png", Texture.class, parameter);
    manager.load("texture/menu/main/single_down.png", Texture.class, parameter);
    manager.load("texture/menu/main/multi_down.png", Texture.class, parameter);
    manager.load("texture/menu/main/story_disabled.png", Texture.class, parameter);

    // game ended
    manager.load("texture/menu/end/back.png", Texture.class, parameter);
    manager.load("texture/menu/end/back_down.png", Texture.class, parameter);
    manager.load("texture/menu/end/title.png", Texture.class, parameter);

    // logo
    manager.load("move.png", Texture.class, parameter);
    manager.load("db.png", Texture.class, parameter);
    manager.load("Bluebird logo.png", Texture.class, parameter);

  }

  public static Texture cold;
  public static Texture bg00;
  public static Texture bg01;
  public static Texture bg02;
  public static Texture bg03;

  public static boolean update() {
    AssetManager manager = getManager();
    boolean done = manager.update();

    if (done) {
      // in-game character
      unitAtlas.put("move", manager.get("texture/unit/move/move.atlas", TextureAtlas.class));
      unitAtlas.put("split", manager.get("texture/unit/split/split.atlas", TextureAtlas.class));
      unitAtlas.put("queen", manager.get("texture/queen/queen.atlas", TextureAtlas.class));
      unitAtlas.put("hot_food", manager.get("texture/food/hot/hot_food.atlas", TextureAtlas.class));
      cold = manager.get("texture/food/cold/cold_food.png", Texture.class);
      move = manager.get("move.png", Texture.class);

      // abilities
      abilities = manager.get("texture/abilities/abilities.atlas", TextureAtlas.class);
      usedAbilities = manager.get("texture/abilities_used/abilities.atlas", TextureAtlas.class);
      cooldown = manager.get("texture/cooldown_button/circle.png", Texture.class);

      // for touchpad joystick
      joyBg = manager.get("texture/joypad/joy_bg.png", Texture.class);
      joyKnob = manager.get("texture/joypad/joy_knob.png", Texture.class);

      // for parallax
      bg00 = manager.get("texture/background/bg00.png", Texture.class);
      bg01 = manager.get("texture/background/bg01.png", Texture.class);
      bg02 = manager.get("texture/background/bg02.png", Texture.class);
      bg03 = manager.get("texture/background/bg03.png", Texture.class);

      // main menu
      title = manager.get("texture/menu/main/name.png", Texture.class);
      singleButton = manager.get("texture/menu/main/single.png", Texture.class);
      multiButton = manager.get("texture/menu/main/multi_disabled.png", Texture.class);
      storyButton = manager.get("texture/menu/main/story_disabled.png", Texture.class);
      pressedSingleButton = manager.get("texture/menu/main/single_down.png", Texture.class);
      pressedMultiButton = manager.get("texture/menu/main/multi_down.png", Texture.class);

      // game ended
      backButton = manager.get("texture/menu/end/back.png");
      backDownButton = manager.get("texture/menu/end/back_down.png");
      endTitle = manager.get("texture/menu/end/title.png");

      // logo
      db = manager.get("db.png", Texture.class);
      logo = manager.get("Bluebird logo.png", Texture.class);
    }
    return done;
  }

  public static Animation getFoodAnimation(World world, Entity e) {
    Animation animation;
    Display display = EntityUtil.getComponent(world, e, Display.class);
    animation = display.getDefaultAnimation().getAnimation();
    return animation;
  }
}
