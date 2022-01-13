package com.mygdx.game.levels;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.GameWorld;
import com.mygdx.game.RoomFactory;
import com.mygdx.game.entities.EntityHandler;

public class LevelFactory extends EntityHandler {
	
	BodyFactory bodyFactory;
	RoomFactory roomFactory;
	EntityHandler entityHandler;
	
	/**
	 * initializes a new RoomFactory and gets the one true BodyFactory
	 */
	public LevelFactory() {
		bodyFactory = BodyFactory.getInstance(new GameWorld().getInstance());
		roomFactory = new RoomFactory();
	}
	
	/**
	 * creates a rectangle of rectangles as a new level
	 * @param posx - x position of the level
	 * @param posy - y position
	 * @param width - how wide it is
	 * @param height - how tall it is
	 * @param height2
	 * @param texture - what the rectangles look like
	 */
	public void createLevel(float posx, float posy, float width, float height, float height2, Texture texture) {
		roomFactory.makeRectangleRoom(posx,  posy,  width,  height,  height2, texture);
	}
	
	
}
