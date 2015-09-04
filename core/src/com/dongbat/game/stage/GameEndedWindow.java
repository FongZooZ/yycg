package com.dongbat.game.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.dongbat.game.screen.MenuScreen;
import com.dongbat.game.util.AssetUtil;
import com.dongbat.game.util.ScreenUtil;

/**
 * Created by FongZooZ on 9/2/2015. Displayed when game is ended
 */
public class GameEndedWindow extends Window {

  private ImageButton backButton;
  private Image endTitle;

  public GameEndedWindow(Skin skin) {
    super("", skin);
    WindowStyle style = new WindowStyle(this.getStyle());

    Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
    pixmap.setColor(0, 0, 0, 0.75f);
    pixmap.fill();

    style.background = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));

    endTitle = new Image(new TextureRegion(AssetUtil.endTitle));
    endTitle.setSize(Gdx.graphics.getHeight(), Gdx.graphics.getHeight() / 7.2f);
    endTitle.setPosition((Gdx.graphics.getWidth() - endTitle.getWidth()) / 2, Gdx.graphics.getHeight() * 2 / 3 - endTitle.getHeight() / 2);

    Sprite s = new Sprite(AssetUtil.backButton);
    Sprite pressedS = new Sprite(AssetUtil.backDownButton);
    backButton = new ImageButton(new SpriteDrawable(s), new SpriteDrawable(pressedS));
    backButton.getImageCell().fill().expand();
    backButton.setSize(Gdx.graphics.getHeight(), Gdx.graphics.getHeight() / 7.8f);
    backButton.setPosition((Gdx.graphics.getWidth() - endTitle.getWidth()) / 2, Gdx.graphics.getHeight() / 3 - backButton.getHeight() / 2);
    backButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        ScreenUtil.setScreen(new MenuScreen());
      }
    });

    setStyle(style);
    this.addActor(endTitle);
    this.addActor(backButton);
  }
}
