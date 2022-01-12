package com.mygdx.game.levels;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.GameWorld;
import com.mygdx.game.levels.Levels.LevelDestination;

public class DoorBuilder {
	
	
	
	private static DoorBuilder instance;
	
	public ArrayList<Body> doors = new ArrayList<>();
	public ArrayList<Float> destinationsX = new ArrayList<>();
	public ArrayList<Float> destinationsY = new ArrayList<>();
	public ArrayList<String> destinations = new ArrayList<>();
	public ArrayList<LevelDestination> createdLevels = new ArrayList<>();
	
	BodyFactory bodyFactory = BodyFactory.getInstance(new GameWorld().getInstance());
	Texture texture = new Texture(Gdx.files.internal("blackDoor.png"));
	
	public Body createDoor(float posx, float posy, float desX, float desY, int material, String name, LevelDestination level) {
		Body door = bodyFactory.makeBoxPolyBody(posx, posy, 1.5f, 2.3f, material, BodyType.StaticBody, false, true, texture);
		door.setUserData(name);
		doors.add(door);
		destinationsX.add(desX);	
		destinationsY.add(desY);
		destinations.add(level.getValue());
		createdLevels.add(level);
		return door;
	}
	
	public Body createInternalDoor(float posx, float posy, float desX, float desY, int material, String name) {
		Body door = bodyFactory.makeBoxPolyBody(posx, posy, 1.5f, 2.3f, material, BodyType.StaticBody, false, true, texture);
		door.setUserData(name);
		doors.add(door);
		destinationsX.add(desX);	
		destinationsY.add(desY);
		return door;
	}
	
	public void destroy(Body door) {
		(new GameWorld().getInstance()).destroyBody(door);
	}
	
	static {
		instance = new DoorBuilder();
	}
	
	public static DoorBuilder getInstance() {
		return instance;
	}
	
	

}
