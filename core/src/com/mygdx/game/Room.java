package com.mygdx.game;

public class Room {
	
	BodyFactory bodyFactory;
	GameWorld world;
	
	public Room() {
		bodyFactory = BodyFactory.getInstance(world.getInstance());
	}
	
	public void makeSquareRoom() {
		
	}
	
	public void makeRectangleRoom() {
		
	}
	
	
}
