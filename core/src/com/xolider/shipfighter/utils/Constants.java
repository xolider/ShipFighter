package com.xolider.shipfighter.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by clement on 20/10/17.
 */

public class Constants {

    public enum State {
        PLAY,
        PAUSE,
        OVER
    }

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static State state = State.PLAY;
    public static boolean isDesktop = false;
    public static final String TITLE = "ShipFighter";
    public static long LAST_AD_SHOWN = 0;

    private static final Preferences PREFERENCES = Gdx.app.getPreferences("ShipFighter");

    public static boolean isPlaying() {
        return state == State.PLAY;
    }

    public static boolean isOver() {
        return state == State.OVER;
    }

    public static Preferences getPreferences() {
        return PREFERENCES;
    }
}
