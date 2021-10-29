package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends Game {

	Camera cam;
	Character character;
	BitmapFont font;
	Map map;
	
	@Override
	public void create() {
		cam = new Camera();
		character = new Character();
		font = new BitmapFont();
		this.setScreen(new Map(this));
	}

	@Override
	public void render() {
		ScreenUtils.clear(0, 0, 0, 1);
		character.render();
		super.render();
	}
	
	@Override
	public void dispose() {
		cam.dispose();
		character.dispose();
	}
	
	@Override
	public void resize(int width, int height) {
		cam.resize(width, height);
	}

}
