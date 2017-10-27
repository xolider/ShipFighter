package com.xolider.shipfighter.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by clement on 27/10/17.
 */

public class SettingsType {

    protected float x, y, lineWidth, lineHeight;
    protected String text;

    protected BitmapFont font;

    public SettingsType(String text) {
        this.text = text;
        font = new BitmapFont(Gdx.files.internal("myfont.fnt"), true);
        GlyphLayout layout = new GlyphLayout(font, text);
        lineWidth = layout.width;
        lineHeight = layout.height;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
