package com.xolider.shipfighter.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.xolider.shipfighter.utils.Constants;

/**
 * Created by clement on 20/10/17.
 */

public class Button {

    private TextureRegion t;
    private float x, y, width, height, scale;
    private boolean alreadyTouch = false;

    public Button(TextureRegion t, float x, float y, float scale) {
        this.t = t;
        this.scale = scale;
        this.width = t.getRegionWidth()*this.scale;
        this.height = t.getRegionHeight()*this.scale;
        this.x = x;
        this.y = y;
        this.t.flip(false, true);
        this.t.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    public boolean isTouched(int index) {
        if(Gdx.input.isTouched(index)) {
            double x = Gdx.input.getX(index)/((double)Gdx.graphics.getWidth()/Constants.WIDTH);
            double y = Gdx.input.getY(index)/((double)Gdx.graphics.getHeight()/Constants.HEIGHT);
            if(x > this.x && x < this.x + width && y > this.y && y < this.y + height) {
                return true;
            }
        }
        return false;
    }

    public boolean isClicked(int index) {
        if(isTouched(index)) {
            if(!alreadyTouch) {
                alreadyTouch = true;
                return true;
            }
            return false;
        }
        alreadyTouch = false;
        return false;
    }

    public TextureRegion getTexture() {
        return t;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(this.t, x, y, width, height);
    }
}