package com.xolider.shipfighter.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by clement on 28/10/17.
 */

public class MissileUp {

    private TextureRegion missileUpRegion;
    protected float x, y;

    protected MissileUp(float x, float y) {
        missileUpRegion = new TextureRegion(new Texture("missile_up.png"));
        this.x = x;
        this.y = y;
    }

    protected TextureRegion getTextureregion() {
        return missileUpRegion;
    }
}
