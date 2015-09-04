/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.registry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ObjectMap;
import com.dongbat.game.buff.BuffEffect;

/**
 * @author Admin
 */
public class BuffRegistry {

  private static final ObjectMap<String, Entry> registry = new ObjectMap<String, Entry>();

  /**
   * Load BuffEffect data from assest
   */
  public static void load() {
    FileHandle file = Gdx.files.internal("buffs");
    String[] buffs = file.readString().split("\\r?\\n\\r?\\n");

    for (String buffString : buffs) {
      parseBuffs(buffString);
    }
  }

  /**
   * Parse buff info from String that was loaded from load() function
   *
   * @param buffString buff String need to parse
   */
  public static void parseBuffs(String buffString) {
    String[] lines = buffString.split("\\r?\\n");
    ObjectMap<String, String> common = new ObjectMap<String, String>();
    ObjectMap<Integer, ObjectMap<String, String>> levelMaps = new ObjectMap<Integer, ObjectMap<String, String>>();
    ObjectMap<String, String> buffData;

    for (String line : lines) {
      String[] split = line.split(":");
      common.put(split[0], split[1]);
    }
    String name = common.remove("name");
    String clazz = common.remove("class");

    for (ObjectMap.Entry<String, String> entry : common.entries()) {
      String[] phongs = entry.value.split("\\|");
      buffData = new ObjectMap<String, String>();
      for (String phong : phongs) {
        String[] minh = phong.split("=");
        buffData.put(minh[0].trim(), minh[1].trim());
      }
      levelMaps.put(Integer.parseInt(entry.key.trim()), buffData);
    }

    registry.put(name, new Entry(clazz, levelMaps));
  }

  /**
   * Get BuffEffect by name and level
   *
   * @param name BuffEffect name
   * @param level BuffEffect level
   * @return BuffEffect
   */
  public static BuffEffect getEffect(String name, int level) {
    Entry entry = registry.get(name);
    return (BuffEffect) ReflectionUtil.createObject(entry.clazz, entry.data.get(level));
  }

  /**
   * Set Buff data
   *
   * @param effect Buff type
   * @param data Data need to set
   */
  public static void setBuffData(BuffEffect effect, Object... data) {
    for (int i = 0; i < data.length; i += 2) {
      String field = (String) data[i];
      Object value = data[i + 1];

      ReflectionUtil.setFieldValue(effect.getClass(), field, effect, value);
    }
  }

  /**
   * Entry contain class name and data of buff effect
   */
  private static class Entry {

    public String clazz;
    public ObjectMap<Integer, ObjectMap<String, String>> data;

    public Entry(String clazz, ObjectMap<Integer, ObjectMap<String, String>> data) {
      this.clazz = clazz;
      this.data = data;
    }
  }
}
