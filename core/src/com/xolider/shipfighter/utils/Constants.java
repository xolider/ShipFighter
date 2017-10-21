package com.xolider.shipfighter.utils;

import com.badlogic.gdx.Gdx;

/**
 * Created by clement on 20/10/17.
 */

public class Constants {

    public enum State {
        PLAY,
        PAUSE,
        OVER
    }

    public static final int WIDTH = Gdx.graphics.getWidth();
    public static final int HEIGHT = Gdx.graphics.getHeight();
    public static State state = State.PLAY;

    public static boolean isPlaying() {
        return state == State.PLAY;
    }

    public static boolean isOver() {
        return state == State.OVER;
    }
}
