package com.my.gdx.game.levels;

import com.my.gdx.game.entities.EntityHandler;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.GameWorld;
import com.mygdx.game.RoomFactory;

public class LevelFactory {
	
	BodyFactory bodyFactory;
	RoomFactory roomFactory;
	EntityHandler entityHandler;
	
	public LevelFactory() {
		entityHandler = new EntityHandler();
		bodyFactory = BodyFactory.getInstance(new GameWorld().getInstance());
		roomFactory = new RoomFactory();
	}
	
	public void createLevel() {
		roomFactory.makeRectangleRoom(15, 10, 1, 100, 10);
	}
	
}
