package com.xolider.shipfighter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.xolider.shipfighter.screens.MenuScreen;
import com.xolider.shipfighter.screens.SplashScreen;
import com.xolider.shipfighter.utils.AdController;
import com.xolider.shipfighter.utils.Constants;

public class ShipFighterGame extends Game {

	public SpriteBatch batch;
	public AdController controller;

	public ShipFighterGame(AdController controller) {
        if(controller != null) {
            this.controller = controller;
        }
    }
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		this.setScreen(new SplashScreen(this));
		Timer timer = new Timer();
		timer.scheduleTask(new Timer.Task() {
			@Override
			public void run() {
				ShipFighterGame.this.setScreen(new MenuScreen(ShipFighterGame.this));
			}
		}, 3);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
