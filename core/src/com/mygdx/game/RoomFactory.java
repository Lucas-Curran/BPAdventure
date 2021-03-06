package com.mygdx.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class RoomFactory {
	
	static Logger logger = LogManager.getLogger(RoomFactory.class.getName());
	
	BodyFactory bodyFactory;
	GameWorld world;
	Texture texture;
	
	/**
	 * Allows for algorithmically creating perfectly square or rectangular rooms using four box2d bodies
	 */
	public RoomFactory() {
		world = new GameWorld();
		bodyFactory = BodyFactory.getInstance(world.getInstance());
		logger.info("Room factory instanced.");
	}
	/**
	 * makes a room in the world that is a square
	 * @param posx - x coordinate
	 * @param posy - y coordinate
	 * @param width - how wide it is
	 * @param height - how high it is
	 * @param texture - appearance of the rectangles used to make the room
	 */
	public void makeSquareRoom(float posx, float posy, float width, float height, Texture texture) {
		if (width > height) {
			bodyFactory.makeBoxPolyBody(posx, posy, width, height, BodyFactory.STEEL, BodyType.StaticBody, false, false, texture);
			bodyFactory.makeBoxPolyBody(posx - (width / 2) - (height/2), posy + height * ((width / height) / 2 - 0.5f), height, width, BodyFactory.STEEL, BodyType.StaticBody, false, false, texture);
			bodyFactory.makeBoxPolyBody(posx, posy + width - (height), width, height, BodyFactory.STEEL, BodyType.StaticBody, false, false, texture);
			bodyFactory.makeBoxPolyBody(posx + (width / 2) + (height/2), posy + height * ((width / height) / 2 - 0.5f), height, width, BodyFactory.STEEL, BodyType.StaticBody, false, false, texture);
			logger.info("Square room created.");
			} 
	}
	/**
	 * makes a rectangular room in the world
	 * @param posx - x coordinate
	 * @param posy - y coordinate
	 * @param width - width of the room
	 * @param height - height of the room
	 * @param height2
	 * @param texture - texture of the rectangles/walls of the room
	 */
	public void makeRectangleRoom(float posx, float posy, float width, float height, float height2, Texture texture) {
		bodyFactory.makeBoxPolyBody(posx, posy, height, width, BodyFactory.STEEL, BodyType.StaticBody, false, false, texture);
		bodyFactory.makeBoxPolyBody(posx - (width / 2) - (height/2), posy + height2 * ((width / height2) / 2 - 0.5f), width, height2, BodyFactory.STEEL, BodyType.StaticBody, false, false, texture);
		bodyFactory.makeBoxPolyBody(posx, posy + width - (height2), height, width, BodyFactory.STEEL, BodyType.StaticBody, false, false, texture);
		bodyFactory.makeBoxPolyBody(posx + (width / 2) + (height/2), posy + height2 * ((width / height2) / 2 - 0.5f), width, height2, BodyFactory.STEEL, BodyType.StaticBody, false, false, texture);
		logger.info("Rectangular room created.");
	}
	
	
}
