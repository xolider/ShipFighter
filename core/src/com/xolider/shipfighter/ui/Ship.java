package com.xolider.shipfighter.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.xolider.shipfighter.utils.Constants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by clement on 20/10/17.
 */

public class Ship {

    private TextureRegion region;
    private TextureRegion missileTexture;
    private TextureRegion meteorRegion;
    public int x, y, decalY;

    private List<Missile> missiles;
    private List<Meteor> meteors;
    private long lastSpawnMeteor = 0;
    private Random randomMeteor;

    long lastSpawn = 0;

    public int score = 0;

    public Ship(TextureRegion region, int decalY) {
        this.region = region;
        this.region.flip(false, true);
        this.decalY = decalY;
        missileTexture = new TextureRegion(new Texture("missile.png"));
        meteorRegion = new TextureRegion(new Texture("meteor.png"));
        missiles = new ArrayList<Missile>();
        meteors = new ArrayList<Meteor>();
        randomMeteor = new Random();
        x = Constants.WIDTH/2-region.getRegionWidth()/2;
        y = Constants.HEIGHT-region.getRegionHeight()-decalY;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(region, x, y);
        for(int i = 0; i < missiles.size(); i++) {
            batch.draw(missileTexture, missiles.get(i).x, missiles.get(i).y);
        }
        for(int i = 0; i < meteors.size(); i++) {
            meteors.get(i).draw(batch);
        }
    }

    public void updateMissile(float delta) {
        Iterator<Missile> iter = missiles.iterator();
        while(iter.hasNext()) {
            Missile m = iter.next();
            m.y -= 800*delta;
            if(m.y <= 0) iter.remove();
            Rectangle r = new Rectangle(m.x, m.y, missileTexture.getRegionWidth(), missileTexture.getRegionHeight());
            for(int i = 0; i < meteors.size(); i++) {
                Meteor me = meteors.get(i);
                if(r.overlaps(new Rectangle(me.getX(), me.getY(), me.getRegionWidth(), me.getRegionHeight()))) {
                    iter.remove();
                    meteors.remove(i);
                    score += 100;
                }
            }
        }
    }

    public void launchMissile() {
        if(lastSpawn == 0 || System.nanoTime() - lastSpawn >= 500000000) {
            lastSpawn = System.nanoTime();
            Missile missile = new Missile(this.x + this.region.getRegionWidth()/2-10, Constants.HEIGHT - this.region.getRegionHeight()-decalY);
            missiles.add(missile);
        }
        else {
            return;
        }
    }

    public void updateMeteor(float delta, float decal) {
        Iterator<Meteor> iter = meteors.iterator();
        while(iter.hasNext()) {
            Meteor m = iter.next();
            m.updateMeteor(delta);
            if((m.getX() >= Constants.WIDTH && m.getY() <= Constants.HEIGHT-decal) || (m.getX() <= 0 && m.getY() <= Constants.HEIGHT-decal)) {
                iter.remove();
            }
            else if(m.getX() >= 0 && m.getX() <= Constants.WIDTH && m.getY() >= Constants.HEIGHT-decal) {
                Constants.state = Constants.State.OVER;
            }
        }
    }

    public void spawnMeteor() {
        if(lastSpawnMeteor == 0 || System.nanoTime() - lastSpawnMeteor >= 2000000000) {
            lastSpawnMeteor = System.nanoTime();
            Meteor meteor = new Meteor(meteorRegion, randomMeteor.nextInt(Constants.WIDTH), randomMeteor.nextInt(Constants.HEIGHT/2)*(-1));
            meteors.add(meteor);
        }
    }

    public TextureRegion getRegion() {
        return region;
    }
}
