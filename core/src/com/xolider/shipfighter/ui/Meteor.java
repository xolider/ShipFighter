package com.xolider.shipfighter.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

/**
 * Created by clement on 21/10/17.
 */

public class Meteor extends Sprite {

    private TextureRegion region;
    protected int xTranslate, yTranslate, minXTranslate = -100, maxXTranslate = 100, minYTranslate = 100, maxYTranslate = 200, level;
    private float rotateAngle, minAngle = 40f, maxAngle = 80f;
    private int hit;

    public Meteor(TextureRegion region, int x, int y, int level) {
        super(region);
        this.region = region;
        this.setPosition(x, y);
        Random r = new Random();
        rotateAngle = minAngle + r.nextFloat() * (maxAngle - minAngle);
        xTranslate = r.nextInt((maxXTranslate - minXTranslate) + 1) + minXTranslate;
        yTranslate = r.nextInt((maxYTranslate - minYTranslate) + 1) + minYTranslate;
        this.level = level;
        hit = 0;
    }

    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    public void rotate(float delta) {
        this.setRotation(this.getRotation()+rotateAngle*delta);
    }

    public void updateMeteor(float delta) {
        this.setPosition(super.getX() + xTranslate*delta, super.getY() + yTranslate*delta);
        rotate(delta);
    }

    public int getLevel() {
        return level;
    }

    public void addHit() {
        hit++;
    }

    public int getHits() {
        return hit;
    }
}
