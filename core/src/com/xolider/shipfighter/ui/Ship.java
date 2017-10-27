package com.xolider.shipfighter.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
    private TextureRegion explodesRegion;
    private TextureRegion loaderRegion;
    public float x, y, decalY;

    private List<Missile> missiles;
    private List<Meteor> meteors;
    private List<MissileExplode> explodes;
    private long lastSpawnMeteor = 0;
    private Random randomMeteor;
    private Rectangle ship;
    private Random lvlMeteor;

    private Texture meteor1 = new Texture("meteor1.png");
    private Texture meteor2 = new Texture("meteor2.png");
    private TextureRegion[] regions = {new TextureRegion(meteor1), new TextureRegion(meteor2)};

    private BitmapFont reloadingFont;
    private String reloading = "Reloading...";
    private float rldFontWidth;
    private float rldFontHeight;

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
        randomMeteor = new Random();
        lvlMeteor = new Random();
        reloadingFont = new BitmapFont(Gdx.files.internal("myfont.fnt"), true);
        GlyphLayout layout = new GlyphLayout(reloadingFont, reloading);
        rldFontWidth = layout.width;
        rldFontHeight = layout.height;
        ammoFont = new BitmapFont(Gdx.files.internal("myfont.fnt"), true);
        ammoLayout = new GlyphLayout(ammoFont, "" + ammo);
        x = Constants.WIDTH/2-region.getRegionWidth()/2;
        y = Constants.HEIGHT-region.getRegionHeight()-decalY;
        ship = new Rectangle(x, y, region.getRegionWidth(), region.getRegionHeight());
        missileSound = Gdx.audio.newSound(Gdx.files.internal("missile_sound.ogg"));
        overSound = Gdx.audio.newSound(Gdx.files.internal("game_over.ogg"));
        ammo = 50;
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
        if(ammo == 0) {
            reloadingFont.draw(batch, reloading, x+region.getRegionWidth(), y+region.getRegionHeight()/2);
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
        if(System.nanoTime() - lastAmmo >= ammoReload && ammo == 0) {
            ammo = 50;
        }
    }

    public void launchMissile() {
        if((lastSpawn == 0 || System.nanoTime() - lastSpawn >= 200000000) && ammo != 0) {
            lastSpawn = System.nanoTime();
            Missile missile = new Missile(this.x + this.region.getRegionWidth()/2-10, Constants.HEIGHT - this.region.getRegionHeight()-decalY);
            missiles.add(missile);
            missileSound.play();
            ammo--;
            if(ammo == 0) {
                lastAmmo = System.nanoTime();
            }
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
            int level = lvlMeteor.nextInt(2);
            Meteor meteor = new Meteor(regions[level], randomMeteor.nextInt(Constants.WIDTH), randomMeteor.nextInt(Constants.HEIGHT/2)*(-1), level+1);
            meteors.add(meteor);
        }
    }

    public void restart() {
        missiles.clear();
        meteors.clear();
        score = 0;
        ammo = 50;
    }

    public TextureRegion getRegion() {
        return region;
    }
}
