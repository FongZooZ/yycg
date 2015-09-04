/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.system.localSystem;

import com.artemis.BaseSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.localUtil.PhysicsCameraUtil;

/**
 *
 * @author Admin
 */
public class Box2dDebugRendererSystem extends BaseSystem {

  private Box2DDebugRenderer box2DDebugRenderer;
  private com.badlogic.gdx.physics.box2d.World physicWorld;
  private OrthographicCamera camera;

  @Override
  protected void initialize() {
    box2DDebugRenderer = new Box2DDebugRenderer();
    physicWorld = PhysicsUtil.getPhysicsWorld(world);
    camera = PhysicsCameraUtil.getCamera();
  }

  @Override
  protected void processSystem() {
    physicWorld = PhysicsUtil.getPhysicsWorld(world);
    box2DDebugRenderer.render(physicWorld, camera.combined);
  }

  @Override
  protected boolean checkProcessing() {
    return true;
  }

}
