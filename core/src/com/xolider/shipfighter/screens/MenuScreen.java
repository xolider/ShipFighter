package com.xolider.shipfighter.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xolider.shipfighter.ShipFighterGame;
import com.xolider.shipfighter.ui.Button;
import com.xolider.shipfighter.ui.TextButton;
import com.xolider.shipfighter.utils.Constants;

/**
 * Created by clement on 20/10/17.
 */

public class MenuScreen implements Screen {

    private ShipFighterGame game;
    private OrthographicCamera camera;
    private TextureRegion bg;
    private TextButton textButton;
    private TextButton quitButton;
    private Button settings;
    private BitmapFont title;
    private Sprite settingsSprite;
    private float lineWidth, lineHeight;

    public MenuScreen(ShipFighterGame game) {
        this.game = game;
        bg = new TextureRegion(new Texture("space_bg.jpg"), Constants.WIDTH, Constants.HEIGHT);
        TextureRegion btnBg = new TextureRegion(new Texture("label_bg.png"));
        textButton = new TextButton(btnBg, "Jouer", Constants.WIDTH/2, (3*Constants.HEIGHT)/6,  3f);
        quitButton = new TextButton(btnBg, "Quitter", Constants.WIDTH/2, (5*Constants.HEIGHT)/6, 3f);
        TextureRegion settingsRegion = new TextureRegion(new Texture("settings.png"));
        settings = new Button(settingsRegion, Constants.WIDTH-settingsRegion.getRegionWidth()-10, 10, 1);
        title = new BitmapFont(Gdx.files.internal("myfont.fnt"), true);
        settingsSprite = new Sprite(settingsRegion);
        settingsSprite.setPosition(Constants.WIDTH-settingsRegion.getRegionWidth()-10, 10);
        camera = new OrthographicCamera();
        camera.setToOrtho(true, Constants.WIDTH, Constants.HEIGHT);
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
    }

    @Override
    public void show() {
        bg.flip(false, true);
        title.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        title.getData().scale(1.5f);
        GlyphLayout layout = new GlyphLayout(title, Constants.TITLE);
        lineWidth = layout.width;
        lineHeight = layout.height;
    }

    @Override
    public void render(float delta) {
        if(textButton.isClicked(0)) {
            game.setScreen(new GameScreen(game));
        }
        if(quitButton.isClicked(0)) {
            Gdx.app.exit();
        }
        if(settings.isClicked(0)) {
            game.setScreen(new SettingsScreen(game));
        }
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(bg, 0, 0);
        textButton.draw(game.batch);
        quitButton.draw(game.batch);
        title.draw(game.batch, Constants.TITLE, Constants.WIDTH/2-lineWidth/2, Constants.HEIGHT/6-lineHeight/2);
        settingsSprite.draw(game.batch);
        game.batch.end();

        settingsSprite.rotate(50f*delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
