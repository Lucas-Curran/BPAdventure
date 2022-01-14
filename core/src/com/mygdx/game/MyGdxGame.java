package com.mygdx.game;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends Game {
	
	private Camera cam;
	public Screens screens;
	private CrashWriter crashWriter;
	
	static Logger logger = LogManager.getLogger(MyGdxGame.class.getName());
	
	@Override
	public void create() {
		//Desktop launchers start class that sends screen to main menu
		logger.info("Game started");
		cam = new Camera();
		screens = new Screens(this);
		Screens.toMenu(new MainMenu());
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
