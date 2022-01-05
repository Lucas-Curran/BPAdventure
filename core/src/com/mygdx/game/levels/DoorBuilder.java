package com.mygdx.game.levels;

import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.GameWorld;

public class DoorBuilder {
	
	private static DoorBuilder instance;
	
	public ArrayList<Body> doors = new ArrayList<>();
	public ArrayList<Float> destinationsX = new ArrayList<>();
	public ArrayList<Float> destinationsY = new ArrayList<>();
	public ArrayList<String> destinations = new ArrayList<>();
	
	BodyFactory bodyFactory = BodyFactory.getInstance(new GameWorld().getInstance());;
	
	public Body createDoor(float posx, float posy, float desX, float desY, int material, String name, String level) {
		Body door = bodyFactory.makeBoxPolyBody(posx, posy, 1.5f, 2.3f, material, BodyType.StaticBody, false, true);
		door.setUserData(name);
		doors.add(door);
		destinationsX.add(desX);
		destinationsY.add(desY);
		destinations.add(level);
		return door;
	}
	
	static {
		instance = new DoorBuilder();
	}
	
	public static DoorBuilder getInstance() {
		return instance;
	}
	
	

}
