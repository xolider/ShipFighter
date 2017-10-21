package com.xolider.shipfighter.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.xolider.shipfighter.utils.Constants;

/**
 * Created by clement on 20/10/17.
 */

public class TextButton extends Button {

    private String text;
    private float x, y;
    private BitmapFont font;

    private float lineWidth, lineHeight;

    public TextButton(TextureRegion t, String text, float x, float y, float scale) {
        super(t, x-(t.getRegionWidth()*scale)/2, y-(t.getRegionHeight()*scale)/2, scale);
        this.text = text;
        this.x = x;
        this.y = y;
        font = new BitmapFont(Gdx.files.internal("myfont.fnt"), true);
        font.setColor(Color.BLACK);
        GlyphLayout layout = new GlyphLayout(font, text);
        lineWidth = layout.width;
        lineHeight = layout.height;
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        font.draw(batch, text, x-lineWidth/2, y-lineHeight/2);
    }
}
