package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {

	Camera cam;
	Character character;
	
	@Override
	public void create () {
		cam = new Camera();
		character = new Character();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		character.render();
	}
	
	@Override
	public void dispose () {
		cam.dispose();
		character.dispose();
	}
	
	@Override
	public void resize(int width, int height) {
		cam.resize(width, height);
	}

}
