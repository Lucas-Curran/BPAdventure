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
		super("StatusUI", new WindowStyle(new BitmapFont(), Color.RED, null));
		Texture hpTex = new Texture(Gdx.files.internal("hpBar.png"));
		hpBar = new Image(hpTex);
		
		this.add(hpBar).top().left().padLeft(10).padTop(10);
		this.pack();
	}
	
	public void setHP(float hpVal) {
		this.hpVal = hpVal;
	}
	
	public float getHP() {
		return hpVal * 100;
	}
	
	public void calculateBar() {
		float widthRatio = hpVal * hpBar.getWidth();
		hpBar.setSize(widthRatio, hpBar.getHeight());
	}
	
	
	
}
