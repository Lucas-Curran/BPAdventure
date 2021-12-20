package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.mygdx.game.BpaAssetManager;

public class HealthBar extends Actor {

	private NinePatchDrawable healthBarBackground;
	private NinePatchDrawable healthBar;
	private BpaAssetManager manager;
	private TextureAtlas skinAtlas;
	
	private float hpVal = 1;
	
	public HealthBar() {
		skinAtlas = new TextureAtlas(Gdx.files.internal("uiskin.txt"));
		NinePatch healthBarBackgroundPatch = new NinePatch(skinAtlas.findRegion("default-round"), 5, 5, 4, 4);
		NinePatch healthBarPatch = new NinePatch(skinAtlas.findRegion("default-round-down"), 5, 5, 4, 4);
		
		setWidth(200);
		setHeight(10);
		
        healthBar = new NinePatchDrawable(healthBarPatch);
        healthBarBackground = new NinePatchDrawable(healthBarBackgroundPatch);
       
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		healthBarBackground.draw(batch, getX(), getY(), getWidth() * getScaleX(), getHeight() * getScaleY());
		if (hpVal > 0) {
			healthBar.draw(batch, getX(), getY(), hpVal * getWidth() * getScaleX(), getHeight() * getScaleY()); 
		}
	}
	
	public float getHP() {
		return hpVal * 100;
	}
	
	public void setHP(float hpVal) {
		this.hpVal = hpVal / 100;
	}
	
}
