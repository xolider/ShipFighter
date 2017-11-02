package com.xolider.shipfighter.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xolider.shipfighter.ShipFighterGame;
import com.xolider.shipfighter.ui.Button;
import com.xolider.shipfighter.ui.Planet;
import com.xolider.shipfighter.ui.Ship;
import com.xolider.shipfighter.ui.TextButton;
import com.xolider.shipfighter.utils.Constants;

/**
 * Created by clement on 20/10/17.
 */

public class GameScreen implements Screen {

    private ShipFighterGame game;
    private OrthographicCamera camera;

    private TextureRegion bg;
    private Planet planet;
    private Button right;
    private Button left;
    private Button shot;
    private BitmapFont scoreFont;
    private Button pauseButton;
    private TextButton mainMenu;
    private BitmapFont overFont;
    private float lineOverWidth, lineOverHeight;
    private TextButton mainMenuOver;
    private TextButton mainMenuRestart;

    private Ship ship;

    public GameScreen(ShipFighterGame game) {
        this.game = game;
        bg = new TextureRegion(new Texture("space_bg.jpg"), Constants.WIDTH, Constants.HEIGHT);
        camera = new OrthographicCamera();
        camera.setToOrtho(true, Constants.WIDTH, Constants.HEIGHT);
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
        Texture btnTexture = new Texture("label_bg.png");
        TextureRegion right_arrow = new TextureRegion(new Texture("right_arrow.png"));
        TextureRegion left_arrow = new TextureRegion(new Texture("left_arrow.png"));
        TextureRegion shipRegion = new TextureRegion(new Texture("spaceship.png"));
        TextureRegion shotRegion = new TextureRegion(new Texture("shot.png"));
        TextureRegion pauseRegion = new TextureRegion(new Texture("pause.png"));
        TextureRegion planetRegion = new TextureRegion(new Texture("planet.png"));
        TextureRegion mainMenuRegion = new TextureRegion(btnTexture);
        TextureRegion mainMenuRestartRegion = new TextureRegion(btnTexture);
        planetRegion.flip(false, true);
        right = new Button(right_arrow, (7*Constants.WIDTH)/8+right_arrow.getRegionWidth()/2, Constants.HEIGHT-right_arrow.getRegionHeight()-30, 1.5f);
        left = new Button(left_arrow, (6.5f*Constants.WIDTH)/8-left_arrow.getRegionWidth()/2, Constants.HEIGHT-left_arrow.getRegionHeight()-30, 1.5f);
        shot = new Button(shotRegion, Constants.WIDTH/8, Constants.HEIGHT-shotRegion.getRegionHeight()-30, 1.5f);
        pauseButton = new Button(pauseRegion, Constants.WIDTH-pauseRegion.getRegionWidth(), 0, 0.7f);
        planet = new Planet(planetRegion);
        ship = new Ship(shipRegion, planetRegion.getRegionHeight()/4);
        scoreFont = new BitmapFont(Gdx.files.internal("myfont.fnt"), true);
        overFont = new BitmapFont(Gdx.files.internal("myfont.fnt"), true);
        mainMenu = new TextButton(mainMenuRegion, "Back to menu", Constants.WIDTH/2, Constants.HEIGHT/2, 2);
        mainMenuOver = new TextButton(mainMenuRegion, "Back to menu", (3*Constants.WIDTH)/4, Constants.HEIGHT-mainMenuRegion.getRegionHeight(), 2);
        mainMenuRestart = new TextButton(mainMenuRestartRegion, "Retry", Constants.WIDTH/4, Constants.HEIGHT-mainMenuRestartRegion.getRegionHeight(), 2);
    }

    @Override
    public void show() {
        scoreFont.setColor(Color.WHITE);
        overFont.setColor(Color.WHITE);
        overFont.getData().scale(3);
        overFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        GlyphLayout layout = new GlyphLayout(overFont, "Game Over");
        lineOverWidth = layout.width;
        lineOverHeight = layout.height;
    }

    @Override
    public void render(float delta) {
        handleInput(delta);
        if(Constants.isPlaying()) {
            ship.spawnMeteor();
            ship.createMissileUp();
        }
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(bg, 0, 0);
        if(Constants.isPlaying()) {
            planet.draw(game.batch);
            ship.draw(game.batch);
            if(!Constants.isDesktop && Constants.getPreferences().getBoolean("showHUD", true)) {
                right.draw(game.batch);
                left.draw(game.batch);
                shot.draw(game.batch);
            }
            scoreFont.draw(game.batch, "Score: " + ship.score, 0, 0);
        }
        if(!Constants.isOver()) {
            pauseButton.draw(game.batch);
        }
        if(!Constants.isPlaying() && !Constants.isOver()) {
            mainMenu.draw(game.batch);
        }
        if(Constants.isOver()) {
            overFont.draw(game.batch, "Game Over", Constants.WIDTH/2-lineOverWidth/2, Constants.HEIGHT/2-lineOverHeight/2);
            mainMenuOver.draw(game.batch);
            mainMenuRestart.draw(game.batch);
        }
        game.batch.end();

        if(Constants.isPlaying()) {
            ship.updateMissile(delta);
            planet.rotate(delta);
            ship.updateMeteor(delta, planet.getRegionHeight()/4);
            ship.updateMissileUp(delta);
        }
    }

    private void handleInput(float delta) {
        if(Constants.isPlaying()) {
            if(right.isTouched(0) || right.isTouched(1) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                if(ship.x >= Constants.WIDTH-ship.getRegion().getRegionWidth()) {
                    ship.x = Constants.WIDTH-ship.getRegion().getRegionWidth();
                }
                else {
                    ship.x += 700*delta;
                }
            }
            if(left.isTouched(0) || left.isTouched(1) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                if(ship.x <= 0) {
                    ship.x = 0;
                }
                else {
                    ship.x -= 700*delta;
                }
            }
            if(shot.isTouched(0) || shot.isTouched(1) || Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                ship.launchMissile();
            }
        }
        else if(Constants.isOver()) {
            if(mainMenuOver.isClicked(0)) {
                game.setScreen(new MenuScreen(game));
            }
            else if(mainMenuRestart.isClicked(0)) {
                ship.restart();
                Constants.state = Constants.State.PLAY;
            }
        }
        else {
            if(mainMenu.isClicked(0)) {
                game.setScreen(new MenuScreen(game));
                Constants.state = Constants.State.PLAY;
            }
        }
        if(pauseButton.isClicked(0) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            switch (Constants.state) {
                case PLAY:
                    Constants.state = Constants.State.PAUSE;
                    break;
                case PAUSE:
                    Constants.state = Constants.State.PLAY;
                    break;
            }
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
