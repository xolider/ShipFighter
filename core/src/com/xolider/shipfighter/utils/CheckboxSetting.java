package com.xolider.shipfighter.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by clement on 27/10/17.
 */

public class CheckboxSetting extends SettingsType {

    private TextureRegion checked = new TextureRegion(new Texture("checkbox_checked.png"));
    private TextureRegion unchecked = new TextureRegion(new Texture("checkbox_unchecked.png"));
    private boolean check;
    private boolean alreadyTouch = false;

    public CheckboxSetting(String text) {
        super(text);
        checked.flip(false, true);
        unchecked.flip(false, true);
    }

    public void setChecked(boolean checked) {
        this.check = checked;
    }

    public boolean isChecked() {
        return check;
    }

    public void draw(SpriteBatch batch) {
        font.draw(batch, text, x, y);
        if(isChecked()) {
            batch.draw(checked, x+lineWidth+100, y-checked.getRegionHeight()/2+lineHeight/2);
        }
        else {
            batch.draw(unchecked, x+lineWidth+100, y-checked.getRegionHeight()/2+lineHeight/2);
        }
    }

    public boolean isTouched(int index) {
        if(Gdx.input.isTouched(index)) {
            int x = Gdx.input.getX(index);
            int y = Gdx.input.getY(index);
            if(x > this.x + lineWidth + 100 && x < this.x + lineWidth + 100 + checked.getRegionWidth() && y > this.y-checked.getRegionHeight()/2+lineHeight/2 && y < this.y - checked.getRegionHeight()/2 + lineHeight/2+checked.getRegionHeight()) {
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

    public float getTotalWidth() {
        return lineWidth+30+checked.getRegionWidth();
    }

    public float getTotalHeight() {
        return checked.getRegionHeight(); //checked texture >= lineheight
    }
}
