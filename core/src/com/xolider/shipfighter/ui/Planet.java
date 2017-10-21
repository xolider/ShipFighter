package com.xolider.shipfighter.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xolider.shipfighter.utils.Constants;

/**
 * Created by clement on 21/10/17.
 */

public class Planet extends Sprite {

    private TextureRegion region;
    private float rotateAngle = -0.2f;
    private float x;
    private float y;

    public Planet(TextureRegion region) {
        super(region);
        this.region = region;
        this.scale(0.5f);
        x = Constants.WIDTH/2-this.getWidth()/2;
        y = Constants.HEIGHT;
        this.setPosition(x, y);
    }

    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    public void rotate() {
        this.rotate(rotateAngle);
    }
}
