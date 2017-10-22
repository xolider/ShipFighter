package com.xolider.shipfighter.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xolider.shipfighter.ShipFighterGame;
import com.xolider.shipfighter.ui.Button;
import com.xolider.shipfighter.utils.Constants;

/**
 * Created by clement on 22/10/17.
 */

public class SettingsScreen implements Screen {

    private ShipFighterGame game;
    private OrthographicCamera camera;
    private Button returnBtn;

    public SettingsScreen(ShipFighterGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(true, Constants.WIDTH, Constants.HEIGHT);
        camera.position.set(Constants.WIDTH/2, Constants.HEIGHT/2, 0);
        returnBtn = new Button(new TextureRegion(new Texture("left_arrow.png")), 10, 10, 1);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        handleEvent();
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        returnBtn.draw(game.batch);
        game.batch.end();
    }

    private void handleEvent() {
        if(returnBtn.isClicked(0)) {
            game.setScreen(new MenuScreen(game));
        }
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
