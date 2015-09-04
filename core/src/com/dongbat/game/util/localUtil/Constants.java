/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util.localUtil;

import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Admin
 */
public class Constants {

  public static enum InputType {

    MOVE,
    ABILITY,
    PAUSE
  }

  public static class GAME {

    public static final float EPSILON = 0.01f;
    public static final int FRAME_WIDTH = 100;
    public static final int FRAME_HEIGHT = 100;
    public static final String TITLE = "Network";
    public static final boolean DEBUG = true;
    public static final boolean DRAW_PHYSICS_DEBUG = true;
  }

  public class GROUP_MANAGER {

    public static final String PLAYER = "player";
    public static final String PLAYER_UNIT = "playerUnit";
    public static final String AI_UNIT = "ai";
    public static final String FOOD = "food";
    public static final String UNIT = "unit";
  }

  public static class PHYSIC_CAMERA {

    public static final int DEFAULT_MIN_VIEWPORT = 100;
    public static final int DEFAULT_CAMERA_X = -40;
    public static final int DEFAULT_CAMERA_Y = 20;
    public static final Vector2 DEFAULT_CAMERA_POSITION = new Vector2(DEFAULT_CAMERA_X, DEFAULT_CAMERA_Y);
  }

  public static class PHYSICS {

    public static final float DEFAULT_DENSITY = 1;
    public static final float DEFAULT_GRAVITY = 0;
    public static final float METER_PIXEL_RATIO = 10;
    public static final int GROUND_BEGIN_X = -120;
    public static final int GROUND_END_X = 30;
    public static final int GROUND_Y = -15;
    public static final float MIN_RADIUS = 0.1f;
    public static final float MIN_SQUARE = 0.1f;
    public static final float FOOD_RADIUS = 0.25f;
    public static final float MAX_VELOCITY = 25000f;
    public static final float MIN_DETECTION_RADIUS = 10;
  }

  public static class UNIT {

    public static final float DEFAULT_MIN_RADIUS = 0;
    public static final float DEFAULT_MODIFIER_SPEED = 1;
  }

  public static class FOOD {

    public static final float DEFAULT_RADIUS = 1.5f;
  }

  public static class BUFF {

    public static class SHRINK {

      public static final int DURATION = 3000;
    }
  }
}
