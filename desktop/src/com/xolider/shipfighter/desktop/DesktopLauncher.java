package com.xolider.shipfighter.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.xolider.shipfighter.ShipFighterGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "ShipFighter";
		config.fullscreen = true;
		new LwjglApplication(new ShipFighterGame(null), config);
	}
}
