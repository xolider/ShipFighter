package com.xolider.shipfighter.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

/**
 * Created by clement on 21/10/17.
 */

public class Meteor extends Sprite {

    private TextureRegion region;
    private int xTranslate, yTranslate, minXTranslate = -100, maxXTranslate = 100, minYTranslate = 100, maxYTranslate = 300;
    private float rotateAngle, minAngle = 0.1f, maxAngle = 1f;

    public Meteor(TextureRegion region, int x, int y) {
        super(region);
        this.region = region;
        this.setPosition(x, y);
        Random r = new Random();
        rotateAngle = minAngle + r.nextFloat() * (maxAngle - minAngle);
        xTranslate = r.nextInt((maxXTranslate - minXTranslate) + 1) + minXTranslate;
        yTranslate = r.nextInt((maxYTranslate - minYTranslate) + 1) + minYTranslate;
    }

    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    public void rotate() {
        this.rotate(rotateAngle);
    }

    public void updateMeteor(float delta) {
        this.setPosition(super.getX() + xTranslate*delta, super.getY() + yTranslate*delta);
        rotate();
    }
}
