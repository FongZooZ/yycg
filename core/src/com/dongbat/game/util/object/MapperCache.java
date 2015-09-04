package com.dongbat.game.util.object;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.World;
import com.badlogic.gdx.utils.ObjectMap;

public class MapperCache {

  private ObjectMap<Class, ComponentMapper> cache = new ObjectMap<Class, ComponentMapper>();

  private World world;

  /**
   * Constructor of MapperCache
   *
   * @param world artemis world
   */
  public MapperCache(World world) {
    this.world = world;
  }

  /**
   * Get Mapper of specific Component class
   *
   * @param type Component type
   * @param <T> class type
   * @return Component class
   */
  public <T extends Component> ComponentMapper<T> getMapper(Class<T> type) {
    if (!cache.containsKey(type)) {
      cache.put(type, world.getMapper(type));
    }
    return cache.get(type);
  }
}
