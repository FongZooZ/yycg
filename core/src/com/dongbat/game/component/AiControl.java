/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.component;

import com.artemis.Component;
import com.artemis.Entity;
import com.badlogic.gdx.ai.btree.BehaviorTree;

/**
 * @author Admin
 */
public class AiControl extends Component {

  private BehaviorTree<Entity> tree;
  private String definitionPath;

  public AiControl() {
  }

  public AiControl(String definitionPath) {
    this.definitionPath = definitionPath;
  }

  public String getDefinitionPath() {
    return definitionPath;
  }

  public void setDefinitionPath(String definitionPath) {
    this.definitionPath = definitionPath;
  }

  public BehaviorTree<Entity> getTree() {
    return tree;
  }

  public void setTree(BehaviorTree<Entity> tree) {
    this.tree = tree;
  }
}
