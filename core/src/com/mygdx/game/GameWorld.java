package com.mygdx.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;


public class GameWorld {
	
	static Logger logger = LogManager.getLogger(GameWorld.class.getName());
	
	private World instance;
	
	public GameWorld() {
		if (instance == null) {
			instance = new World(new Vector2(0, -10f), true);	
			logger.info("World instanced.");
		}
	}
	
	public World getInstance() {
		return instance;
	}
}
