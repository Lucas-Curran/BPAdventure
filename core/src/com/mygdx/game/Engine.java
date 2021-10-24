package com.mygdx.game;

import com.badlogic.ashley.core.PooledEngine;

public class Engine {
	private PooledEngine instance;
	
	public Engine() {
		if (instance == null) {
			instance = new PooledEngine();
		}
	}
	
	public PooledEngine getInstance() {
		return instance;
	}
}
