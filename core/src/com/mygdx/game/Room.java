package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Room {
	
	BodyFactory bodyFactory;
	GameWorld world;
	
	public Room() {
		world = new GameWorld();
		bodyFactory = BodyFactory.getInstance(world.getInstance());
	}
	
	public void makeSquareRoom(float posx, float posy, float width, float height) {
		if (width > height) {
			bodyFactory.makeBoxPolyBody(posx, posy, width, height, BodyFactory.STEEL, BodyType.StaticBody, false);
			bodyFactory.makeBoxPolyBody(posx - (width / 2) - (height/2), posy + height * ((width / height) / 2 - 0.5f), height, width, BodyFactory.STEEL, BodyType.StaticBody, false);
			bodyFactory.makeBoxPolyBody(posx, posy + width - (height), width, height, BodyFactory.STEEL, BodyType.StaticBody, false);
			bodyFactory.makeBoxPolyBody(posx + (width / 2) + (height/2), posy + height * ((width / height) / 2 - 0.5f), height, width, BodyFactory.STEEL, BodyType.StaticBody, false);
		} 
	}
	
	public void makeRectangleRoom() {
		
	}
	
	
}
