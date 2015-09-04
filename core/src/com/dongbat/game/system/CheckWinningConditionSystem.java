package com.dongbat.game.system;

import com.artemis.BaseSystem;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.dongbat.game.util.GameUtil;
import com.dongbat.game.util.StageUtil;
import com.dongbat.game.util.WorldQueryUtil;

/**
 * Created by FongZooZ on 9/2/2015. System to check game is ended or not
 */
public class CheckWinningConditionSystem extends BaseSystem {

  private Skin skin = StageUtil.getSkin();
  private float worldHeight;
  private float worldWidth;
  private boolean ended = false;

  /**
   * Override to implement code that gets executed when systems are initialized.
   */
  @Override
  protected void initialize() {
  }

  /**
   * Any implementing entity system must implement this method.
   */
  @Override
  protected void processSystem() {
    if (WorldQueryUtil.getAllQueen(world).isEmpty()) {
      GameUtil.endGame(world);
    }
  }
}
