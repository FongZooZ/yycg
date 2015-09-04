package com.dongbat.game.registry;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ObjectMap;
import com.dongbat.game.component.Stats;
import com.dongbat.game.unit.UnitInfo;
import com.dongbat.game.util.EntityUtil;

/**
 * Created by FongZooZ on 7/11/2015.
 */
public class UnitRegistry {

  private static final ObjectMap<String, UnitInfo> unitRegistry = new ObjectMap<String, UnitInfo>();

  /**
   * Load unit data from assest
   */
  public static void load() {
    FileHandle internal = Gdx.files.internal("unit");
    System.out.println(internal.list().length);
    for (FileHandle file : internal.list()) {
      if (file.isDirectory()) {
        continue;
      }
      String content = file.readString();
      UnitInfo unitInfo = getUnitInfo(content);
      if (unitInfo != null) {
        unitRegistry.put(file.nameWithoutExtension(), unitInfo);
      }
    }
  }

  /**
   * Get UnitInfo by name
   *
   * @param name Unit name
   * @return info of unit
   */
  public static UnitInfo get(String name) {
    return unitRegistry.get(name);
  }

  /**
   * Set data for unit
   *
   * @param world artemis world
   * @param unit unit want to set data
   * @param args data to set
   */
  public static void setUnitData(World world, Entity unit, Object... args) {
    Stats unitStats = EntityUtil.getComponent(world, unit, Stats.class);

    for (int i = 0; i < args.length; i += 2) {
      String field = (String) args[i];
      Object obj = args[i + 1];
      unitStats.getUserData().put(field, obj);
    }
  }

  /**
   * Private function to get UnitInfo from string
   *
   * @param info String to parse
   * @return UnitInfo
   */
  private static UnitInfo getUnitInfo(String info) {
    return (UnitInfo) ReflectionUtil.parseData(UnitInfo.class, info);
  }

}
