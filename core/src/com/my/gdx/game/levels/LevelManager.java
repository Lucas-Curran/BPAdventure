package com.my.gdx.game.levels;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.GameWorld;
import com.mygdx.game.RoomFactory;

public class LevelManager {
	
	BodyFactory bodyFactory;
	RoomFactory roomFactory;
	
	public LevelManager() {
		bodyFactory = BodyFactory.getInstance(new GameWorld().getInstance());
		roomFactory = new RoomFactory();
	}
	
	public void createLevel() {
		roomFactory.makeRectangleRoom(15, 10, 1, 100, 10);
	}
	
}
