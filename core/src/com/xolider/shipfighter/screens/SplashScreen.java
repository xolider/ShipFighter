package com.xolider.shipfighter.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xolider.shipfighter.ShipFighterGame;
import com.xolider.shipfighter.utils.Constants;

/**
 * Created by clement on 14/10/17.
 */

public class SplashScreen implements Screen {

    private ShipFighterGame game;
    private TextureRegion splash;
    private OrthographicCamera camera;

    private BitmapFont madeByFont;
    private String madeBy = "Made by";

    private float width, height, lineWidth;

    public SplashScreen(ShipFighterGame game) {
        this.game = game;
        splash = new TextureRegion(new Texture("splash.png"));
        camera = new OrthographicCamera();
        camera.setToOrtho(true, Constants.WIDTH, Constants.HEIGHT);
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);

        madeByFont = new BitmapFont(Gdx.files.internal("myfont.fnt"), true);
        madeByFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        madeByFont.getData().setScale(2f);
    }

    @Override
    public void show() {
        splash.flip(false, true);
        height = Constants.HEIGHT*1.2f;
        float diff = height-splash.getRegionHeight();
        width = splash.getRegionWidth()+diff;

        GlyphLayout layout = new GlyphLayout(madeByFont, madeBy);
        lineWidth = layout.width;
    }

    @Override
    public void render(float delta) {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(splash, Constants.WIDTH/2-width/2, Constants.HEIGHT/2-height/2, width, height);
        madeByFont.draw(game.batch, madeBy, Constants.WIDTH/2-lineWidth/2, (Constants.HEIGHT/2-splash.getRegionHeight()/2)/6);
        game.batch.end();
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
        splash.getTexture().dispose();
    }
}
