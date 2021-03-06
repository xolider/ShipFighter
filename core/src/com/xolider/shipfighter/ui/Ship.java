package com.xolider.shipfighter.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.xolider.shipfighter.utils.Constants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by clement on 20/10/17.
 */

public class Ship {

    private TextureRegion region;
    private TextureRegion missileTexture;
    private TextureRegion explodesRegion;
    private TextureRegion loaderRegion;
    public float x, y, decalY;
    private boolean isMissileUp;

    private List<Missile> missiles;
    private List<Meteor> meteors;
    private List<MissileExplode> explodes;
    private long lastSpawnMeteor = 0;
    private Rectangle ship;

    private MissileUp missileUp = null;

    private Texture meteor1 = new Texture("meteor1.png");
    private Texture meteor2 = new Texture("meteor2.png");
    private TextureRegion[] regions = {new TextureRegion(meteor1), new TextureRegion(meteor2)};

    private BitmapFont reloadingFont;
    private String reloading = "REchargement...";

    private BitmapFont ammoFont;

    private Sound missileSound;
    private Sound overSound;

    long lastSpawn = 0;

    public int score = 0;
    public int ammo;
    private float ammoReload = 2000000000, lastAmmo;
    private GlyphLayout ammoLayout;

    public Ship(TextureRegion region, int decalY) {
        this.region = region;
        this.region.flip(false, true);
        this.decalY = decalY;
        missileTexture = new TextureRegion(new Texture("missile.png"));
        missileTexture.flip(false, true);
        explodesRegion = new TextureRegion(new Texture("missile_explode.png"));
        loaderRegion = new TextureRegion(new Texture("loader.png"));
        loaderRegion.flip(false, true);
        missiles = new ArrayList<Missile>();
        meteors = new ArrayList<Meteor>();
        explodes = new ArrayList<MissileExplode>();
        reloadingFont = new BitmapFont(Gdx.files.internal("myfont.fnt"), true);
        ammoFont = new BitmapFont(Gdx.files.internal("myfont.fnt"), true);
        ammoLayout = new GlyphLayout(ammoFont, "" + ammo);
        x = Constants.WIDTH/2-region.getRegionWidth()/2;
        y = Constants.HEIGHT-region.getRegionHeight()-decalY;
        ship = new Rectangle(x, y, region.getRegionWidth(), region.getRegionHeight());
        missileSound = Gdx.audio.newSound(Gdx.files.internal("missile_sound.ogg"));
        overSound = Gdx.audio.newSound(Gdx.files.internal("game_over.ogg"));
        ammo = 50;
        isMissileUp = false;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(region, x, y);
        for(int i = 0; i < missiles.size(); i++) {
            batch.draw(missileTexture, missiles.get(i).x, missiles.get(i).y);
        }
        for(int i = 0; i < meteors.size(); i++) {
            meteors.get(i).draw(batch);
        }
        for(int i = 0; i < explodes.size(); i++) {
            batch.draw(explodesRegion, explodes.get(i).x, explodes.get(i).y);
            explodes.remove(i);
        }
        batch.draw(loaderRegion, Constants.WIDTH-loaderRegion.getRegionWidth()*1.5f, Constants.HEIGHT/2-(loaderRegion.getRegionHeight()*1.5f)/2, loaderRegion.getRegionWidth()*1.5f, loaderRegion.getRegionHeight()*1.5f);
        ammoLayout.setText(ammoFont, "" + ammo);
        float w = ammoLayout.width;
        float h = ammoLayout.height;
        ammoFont.draw(batch, "" + ammo, Constants.WIDTH-loaderRegion.getRegionWidth()*1.5f-w, Constants.HEIGHT/2-h/2);
        if(ammo <= 0) {
            reloadingFont.draw(batch, reloading, x+region.getRegionWidth()+10, y+region.getRegionHeight()/2);
        }
        if(missileUp != null) {
            batch.draw(missileUp.getTextureregion(), missileUp.x, missileUp.y);
        }
    }

    public void updateMissile(float delta) {
        Iterator<Missile> iter = missiles.iterator();
        while(iter.hasNext()) {
            Missile m = iter.next();
            m.y -= 800*delta;
            if(m.y <= 0) {
                iter.remove();
                return;
            }
            Rectangle r = new Rectangle(m.x, m.y, missileTexture.getRegionWidth(), missileTexture.getRegionHeight());
            for(int i = 0; i < meteors.size(); i++) {
                Meteor me = meteors.get(i);
                Rectangle mr = new Rectangle(me.getX(), me.getY(), me.getRegionWidth(), me.getRegionHeight());
                if(r.overlaps(mr)) {
                    iter.remove();
                    me.addHit();
                    explodes.add(new MissileExplode(m.x-explodesRegion.getRegionWidth()/2, m.y));
                    if(me.getHits() == me.getLevel()) {
                        meteors.remove(i);
                        score += me.getLevel()*100;
                    }
                }
            }
        }
        if(System.nanoTime() - lastAmmo >= ammoReload && ammo <= 0) {
            ammo = 50;
            isMissileUp = false;
        }
    }

    public void launchMissile() {
        if((lastSpawn == 0 || System.nanoTime() - lastSpawn >= 200000000) && ammo > 0) {
            lastSpawn = System.nanoTime();
            if(!isMissileUp) {
                Missile missile = new Missile(this.x + this.region.getRegionWidth()/2-missileTexture.getRegionWidth()/2, Constants.HEIGHT - this.region.getRegionHeight()-decalY);
                missiles.add(missile);
                ammo--;
            }
            else {
                Missile missile = new Missile(this.x+20, Constants.HEIGHT-this.region.getRegionHeight()-decalY);
                Missile missile1 = new Missile(this.x + this.region.getRegionWidth()-missileTexture.getRegionWidth()-20, Constants.HEIGHT-this.region.getRegionHeight()-decalY);
                missiles.add(missile);
                missiles.add(missile1);
                ammo -= 2;
            }
            if(ammo <= 0) {
                lastAmmo = System.nanoTime();
            }
            missileSound.play();
        }
        else {
            return;
        }
    }

    public void updateMeteor(float delta, float decal) { //decal == 1/4 of planet texture
        Iterator<Meteor> iter = meteors.iterator();
        while(iter.hasNext()) {
            Meteor m = iter.next();
            m.updateMeteor(delta);
            if((m.getX() >= Constants.WIDTH && m.getY() <= Constants.HEIGHT-decal) || (m.getX() <= 0-regions[m.getLevel()-1].getRegionWidth() && m.getY() <= Constants.HEIGHT-decal)) {
                iter.remove();
            }
            else if(m.getX() >= 0 && m.getX() <= Constants.WIDTH && m.getY() >= Constants.HEIGHT-decal) {
                overSound.play();
                Constants.state = Constants.State.OVER;
            }
            ship.set(x, y, region.getRegionWidth(), region.getRegionHeight());
            Rectangle met = new Rectangle(m.getX(), m.getY(), m.getRegionWidth(), m.getRegionHeight());
            if(ship.overlaps(met)) {
                overSound.play();
                Constants.state = Constants.State.OVER;
            }
        }
    }

    public void spawnMeteor() {
        if(lastSpawnMeteor == 0 || System.nanoTime() - lastSpawnMeteor >= 2000000000) {
            lastSpawnMeteor = System.nanoTime();
            int level = MathUtils.random(1);
            Meteor meteor = new Meteor(regions[level], MathUtils.random(Constants.WIDTH), MathUtils.random(Constants.HEIGHT/2)*(-1), level+1);
            meteors.add(meteor);
        }
    }

    public void restart() {
        missiles.clear();
        meteors.clear();
        score = 0;
        ammo = 50;
        isMissileUp = false;
    }

    public void createMissileUp() {
        int i = MathUtils.random(100);
        if(i == 1  && missileUp == null && !isMissileUp) {
            float spawn = MathUtils.random(Constants.WIDTH);
            missileUp = new MissileUp(spawn, (Constants.HEIGHT/2)*(-1));
        }
    }

    public void updateMissileUp(float delta) {
        if(missileUp != null) {
            missileUp.y += 500*delta;
            Rectangle mup = new Rectangle(missileUp.x, missileUp.y, missileUp.getTextureregion().getRegionWidth(), missileUp.getTextureregion().getRegionHeight());
            if(ship.overlaps(mup)) {
                isMissileUp = true;
                missileUp = null;
            }
            if(missileUp != null && missileUp.y >= Constants.HEIGHT) {
                missileUp = null;
            }
        }
    }

    public TextureRegion getRegion() {
        return region;
    }
}
