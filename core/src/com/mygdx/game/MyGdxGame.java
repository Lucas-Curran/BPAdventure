package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends Game {
	
	Camera cam;
	public static Game game;
	public Screens screens;
	
	@Override
	public void create() {
		cam = new Camera();
		screens = new Screens(this);
		Screens.toMap(Map.getInstance());
	}

	@Override
	public void render() {
		ScreenUtils.clear(0, 0, 0, 1);
		super.render();
	}
	
	@Override
	public void dispose() {
		cam.dispose();
	}
	
	@Override
	public void resize(int width, int height) {
		cam.resize(width, height);
	}

}
