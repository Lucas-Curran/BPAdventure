package com.mygdx.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.ashley.core.PooledEngine;

public class Engine {
	
	static Logger logger = LogManager.getLogger(Engine.class.getName());
	
	private PooledEngine instance;
	
	/**
	 * Engine instance for using Ashley Entity Component System
	 */
	public Engine() {
		if (instance == null) {
			instance = new PooledEngine();
			logger.info("Engine instanced.");
		}
	}
	
	public PooledEngine getInstance() {
		return instance;
	}
}
