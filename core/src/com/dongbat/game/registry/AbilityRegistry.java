/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.registry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ObjectMap;
import com.dongbat.game.ability.Ability;
import com.dongbat.game.ability.AbilityInfo;

/**
 * @author Admin
 */
public class AbilityRegistry {

  private static final ObjectMap<String, Entry> registry = new ObjectMap<String, Entry>();

  /**
   * Load Ability data from assest
   */
  public static void load() {
    FileHandle file = Gdx.files.internal("abilities");
    String[] abilities = file.readString().split("\\r?\\n\\r?\\n");

    for (String abilityString : abilities) {
      parseAbility(abilityString);
    }
  }

  /**
   * Get Ability info by name and level
   *
   * @param name Ability name
   * @param level Ability level
   * @return info of ability
   */
  public static AbilityInfo getAbility(String name, Integer level) {
    AbilityInfo abilityInfo = new AbilityInfo();
    Entry entry = registry.get(name.trim());
    int cooldown = entry.data.get(level).get("cooldown").isEmpty() ? 0 : Integer.parseInt(entry.data.get(level).get("cooldown"));
    String remove = entry.data.get(level).remove("cooldown");

    Ability ability = (Ability) ReflectionUtil.createObject(entry.clazz, entry.data.get(level));
    abilityInfo.setAbility(ability);
    abilityInfo.setCooldown(cooldown);
    abilityInfo.setLevel(level);
    entry.data.get(level).put("cooldown", remove);
    return abilityInfo;
  }

  /**
   * Parse ability info from String that was loaded from load() function
   *
   * @param abilityString ability String need to parse
   */
  private static void parseAbility(String abilityString) {
    String[] frags = abilityString.split("\\r?\\n");
    ObjectMap<String, String> common = new ObjectMap<String, String>();
    ObjectMap<Integer, ObjectMap<String, String>> levelMaps = new ObjectMap<Integer, ObjectMap<String, String>>();
    ObjectMap<String, String> abilityData;

    for (String frag : frags) {
      String[] split = frag.split(":");
      common.put(split[0].trim(), split[1].trim());
    }
    String name = common.remove("name");
    String clazz = common.remove("class");

    for (ObjectMap.Entry<String, String> entry : common.entries()) {
      String[] phongs = entry.value.split("\\|");
      abilityData = new ObjectMap<String, String>();
      for (String phong : phongs) {
        String[] minh = phong.split("=");
        abilityData.put(minh[0].trim(), minh[1].trim());
      }
      levelMaps.put(Integer.parseInt(entry.key.trim()), abilityData);
    }

    registry.put(name, new Entry(clazz, levelMaps));

  }

  /**
   * Entry contain class name and data of ability
   */
  private static class Entry {

    public String clazz;
    public ObjectMap<Integer, ObjectMap<String, String>> data;

    public Entry() {
    }

    public Entry(String clazz, ObjectMap<Integer, ObjectMap<String, String>> data) {
      this.clazz = clazz;
      this.data = data;
    }

  }
}
