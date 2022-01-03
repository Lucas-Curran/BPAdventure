package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class GameWorld {
	
	private World instance;
	
	public GameWorld() {
		if (instance == null) {
			instance = new World(new Vector2(0, -10f), true);
		}
	}
	
	public World getInstance() {
		return instance;
	}
}
