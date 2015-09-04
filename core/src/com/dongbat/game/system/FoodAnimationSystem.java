package com.dongbat.game.system;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dongbat.game.component.Display;
import com.dongbat.game.component.Food;
import com.dongbat.game.util.AnimationUtil;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.RenderUtil;

/**
 * Created by FongZooZ on 8/31/2015. Render animation of Food
 */
public class FoodAnimationSystem extends EntityProcessingSystem {

  private static SpriteBatch batch;

  /**
   * Creates a new EntityProcessingSystem.
   */
  public FoodAnimationSystem() {
    super(Aspect.all(Food.class, Display.class));
  }

  /**
   * Called after the systems has finished processing.
   */
  @Override
  protected void end() {
    batch.end();
  }

  /**
   * Override to implement code that gets executed when systems are initialized.
   */
  @Override
  protected void initialize() {
    batch = RenderUtil.getBatch();
  }

  /**
   * Called before system processing begins.
   * <p>
   * <b>Nota Bene:</b> Any entities created in this method won't become active
   * until the next system starts processing or when a new processing rounds
   * begins, whichever comes first.
   * </p>
   */
  @Override
  protected void begin() {
    batch.begin();
  }

  /**
   * Process a entity this system is interested in.
   *
   * @param e the entity to process
   */
  @Override
  protected void process(Entity e) {
    Food food = EntityUtil.getComponent(world, e, Food.class);
    Display display = EntityUtil.getComponent(world, e, Display.class);
    if (!food.isDirty()) {
      return;
    }
    food.setDirty(false);
    if (!food.isToxic()) {
      display.setDefaultAnimation(AnimationUtil.getColdFood());
    } else {
      display.setDefaultAnimation(AnimationUtil.getHotFood());
    }
  }
}
