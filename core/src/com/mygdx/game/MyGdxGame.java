package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
	
	Character character;
	Camera cam;
	
	@Override
	public void create () {

		character = new Character();
		cam = new Camera();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0.57f, 0.77f, 0.85f, 1);
		character.render();
	}
	
	@Override
	public void dispose () {
		
	}
	
	@Override
	public void resize(int width, int height) {
		cam.resize(width, height);
		character.setProjection(cam.getCombined());
	}
	
}
