package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.mygdx.game.Utilities;

public class StatusUI extends Window {
	
	private Image hpBar;
	private Image mpBar;
	private Image coins;
	
	private float hpVal = 1;
	
	public StatusUI() {
		super("Status", new WindowStyle(new BitmapFont(), Color.RED, null));
		Texture hpTex = new Texture(Gdx.files.internal("hpBar.png"));
		HealthBar hpBar = new HealthBar();
		
		this.setFillParent(true);
		this.padTop(20);
		this.add(hpBar).padLeft(20);
		this.top().left();
		this.pack();
	}
	
	public void setHP(float hpVal) {
		this.hpVal = hpVal;
	}
	
	public void calculateBar() {
		float widthRatio = hpVal * hpBar.getWidth();
		hpBar.setSize(widthRatio, hpBar.getHeight());
	}
	
	
	
}
