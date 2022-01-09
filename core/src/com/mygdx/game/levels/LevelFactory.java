package com.mygdx.game.levels;

import com.mygdx.game.BodyFactory;
import com.mygdx.game.GameWorld;
import com.mygdx.game.RoomFactory;
import com.mygdx.game.entities.EntityHandler;

public class LevelFactory extends EntityHandler {
	
	BodyFactory bodyFactory;
	RoomFactory roomFactory;
	EntityHandler entityHandler;
	
	public LevelFactory() {
		bodyFactory = BodyFactory.getInstance(new GameWorld().getInstance());
		roomFactory = new RoomFactory();
	}
	
	public void createLevel(float posx, float posy, float width, float height, float height2) {
		roomFactory.makeRectangleRoom( posx,  posy,  width,  height,  height2);
	}
	
	
}
