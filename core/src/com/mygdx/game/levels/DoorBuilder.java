package com.mygdx.game.levels;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.GameWorld;
import com.mygdx.game.levels.Levels.LevelDestination;

/**
 * creates all the doors responsible for teleports in the game
 * @author 00011598
 *
 */
public class DoorBuilder {
	
	private static DoorBuilder instance;
	
	/** these are parallel arrays of all the doors, 
	 * their destination coordinates, 
	 * the level they're going to, 
	 * and whether they've been touched
	 */
	public ArrayList<Body> doors = new ArrayList<>();
	public ArrayList<Float> destinationsX = new ArrayList<>();
	public ArrayList<Float> destinationsY = new ArrayList<>();
	public ArrayList<String> destinations = new ArrayList<>();
	public ArrayList<LevelDestination> createdLevels = new ArrayList<>();
	public ArrayList<Boolean> isTouched = new ArrayList<>();
	
	BodyFactory bodyFactory = BodyFactory.getInstance(new GameWorld().getInstance());
	Texture texture = new Texture(Gdx.files.internal("blackDoor.png"));
	/**
	 * creates a door
	 * @param posx - x coordinate of the door
	 * @param posy - y coordinate of the door
	 * @param desX - destination x coordinate
	 * @param desY - destination y coordinate
	 * @param material - the material it's made of
	 * @param name - what it's user data is
	 * @param level - the level its going to
	 * @return - returns a completed door
	 */
	public Body createDoor(float posx, float posy, float desX, float desY, int material, String name, LevelDestination level) {
		Body door = bodyFactory.makeBoxPolyBody(posx, posy, 1.5f, 2.3f, material, BodyType.StaticBody, false, true, texture);
		door.setUserData(name);
		doors.add(door);
		destinationsX.add(desX);	
		destinationsY.add(desY);
		destinations.add(level.getValue());
		createdLevels.add(level);
		isTouched.add(false);
		return door;
	}
	
	/*
	 * destroys a door
	 */
	public void destroy(Body door) {
		(new GameWorld().getInstance()).destroyBody(door);
	}
	
	static {
		instance = new DoorBuilder();
	}
	
	/**
	 * 
	 * @return the one true instance of the DoorBuilder
	 */
	public static DoorBuilder getInstance() {
		return instance;
	}
	
	

}
