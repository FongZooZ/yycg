/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 *
 * @author Admin
 */
public class SkillButton extends Group {

  public String ability;
  private Skin skin = new Skin(Gdx.files.internal("skins/uiskin.json"), new TextureAtlas(Gdx.files.internal("skins/uiskin.atlas")));
  public ImageButton imageButton;

  public SkillButton(String ability) {
    this.ability = ability;
    this.addActor(createNormalButton());
  }

  public ImageButton createNormalButton() {
    imageButton = new ImageButton(skin);
    imageButton.setFillParent(true);
    imageButton.addListener(new ClickListener() {

      @Override
      public void clicked(InputEvent event, float x, float y) {
        System.out.println("dcm");
      }

    });
    return imageButton;
  }

}
