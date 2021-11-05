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
	
	public void createLevelOne() {
		roomFactory.makeSquareRoom(15, 0, 200, 2);
		
		roomFactory.makeRectangleRoom(30, 5, 1, 30, 4);
		
		bodyFactory.makeBoxPolyBody(4, 4, 2, 2, BodyFactory.STEEL, BodyType.StaticBody, false);
		bodyFactory.makeCirclePolyBody(1, 1, 2, BodyFactory.RUBBER, BodyType.StaticBody, false);
	}
	
}
