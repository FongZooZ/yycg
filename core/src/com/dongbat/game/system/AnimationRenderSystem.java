package com.dongbat.game.system;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.game.component.Display;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.RenderUtil;
import com.dongbat.game.util.TimeUtil;
import com.dongbat.game.util.localUtil.PhysicsCameraUtil;
import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;

/**
 * Created by FongZooZ on 8/31/2015. Render entities with animation
 */
public class AnimationRenderSystem extends EntityProcessingSystem {

  private SpriteBatch batch;

  /**
   * Creates a new EntityProcessingSystem.
   */
  public AnimationRenderSystem() {
    super(Aspect.all(Display.class));
  }

  /**
   * Override to implement code that gets executed when systems are initialized.
   */
  @Override
  protected void initialize() {
    batch = RenderUtil.getBatch();
  }

  /**
   * Called after the systems has finished processing.
   */
  @Override
  protected void end() {
    batch.end();
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
    Display display = EntityUtil.getComponent(world, e, Display.class);

    OrthographicCamera camera = PhysicsCameraUtil.getCamera();

    float w = camera.viewportWidth * camera.zoom;
    float h = camera.viewportHeight * camera.zoom;
    Vector2 rel = new Vector2(camera.position.x, camera.position.y);
    float r = display.getRadius();

    rel.sub(display.getPosition());

    if (rel.x < -w / 2 - r || rel.x > w / 2 + r || rel.y < -h / 2 - r || rel.y > h / 2 + r) {
      return;
    }

    if (display.getDefaultAnimation() != null && display.getOverridenAnimation() == null) {
      AnimatedSprite defaultAnimation = display.getDefaultAnimation();
      defaultAnimation.setSize(display.getRadius() * 2, display.getRadius() * 2);
      defaultAnimation.setPosition(display.getPosition().x - display.getRadius(), display.getPosition().y - display.getRadius());
      defaultAnimation.setOriginCenter();
      defaultAnimation.setRotation(display.getRotation());
      defaultAnimation.draw(batch);
    }
    if (display.getOverridenAnimation() != null) {
      AnimatedSprite overridenAnimation = display.getOverridenAnimation();
      overridenAnimation.setSize(display.getRadius() * 2, display.getRadius() * 2);
      overridenAnimation.setPosition(display.getPosition().x - display.getRadius(), display.getPosition().y - display.getRadius());
      overridenAnimation.setOriginCenter();
      overridenAnimation.setRotation(display.getRotation());
      overridenAnimation.draw(batch);
      if (TimeUtil.getCurrentFrameToMillis(world) - display.getGetOverridenStart() > display.getOverridenDuration()) {
        display.setOverridenAnimation(null);
      }
    }
  }
}
