package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.mygdx.game.BpaAssetManager;
import com.mygdx.game.Map;
import com.mygdx.game.SqliteManager;

public class HealthBar extends Actor {

	private NinePatchDrawable healthBarBackground;
	private NinePatchDrawable healthBar;
	private TextureAtlas skinAtlas;
	SqliteManager sm = new SqliteManager();
	private float hpVal = 1;
	
	/**
	 * Health bar constructor
	 */
	public HealthBar() {
		skinAtlas = new TextureAtlas(Gdx.files.internal("uiskin.txt"));
		NinePatch healthBarBackgroundPatch = new NinePatch(skinAtlas.findRegion("default-round"), 5, 5, 4, 4);
		NinePatch healthBarPatch = new NinePatch(skinAtlas.findRegion("default-round-down"), 5, 5, 4, 4);
		
		setWidth(200);
		setHeight(10);
		
        healthBar = new NinePatchDrawable(healthBarPatch);
        healthBarBackground = new NinePatchDrawable(healthBarBackgroundPatch);
        hpVal = ((float) sm.getHealth()) / 100;
       
	}
	

	@Override
	public void draw(Batch batch, float parentAlpha) {
		healthBarBackground.draw(batch, getX(), getY(), getWidth() * getScaleX(), getHeight() * getScaleY());
		if (hpVal > 0) {
			healthBar.draw(batch, getX(), getY(), hpVal * getWidth() * getScaleX(), getHeight() * getScaleY()); 
		}
	}
	
	/**
	 * Gets HP of player
	 * @return - HP
	 */
	public int getHP() {
		return sm.getHealth();
	}
	
	/**
	 * Sets hp of player
	 * @param hpVal - HP
	 */
	public void setHP(int hpVal) {
		this.hpVal = (float) hpVal / 100;
		sm.updateHealth(hpVal);
	}
	
}
