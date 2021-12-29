package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Used for generating box2d bodies with varied fixtures and shapes
 *
 */
public class BodyFactory {
	
	private World world;
	private static BodyFactory thisInstance;
	
	private ArrayList<Body> levelOneBodies;
	private ArrayList<Body> levelTwoBodies;
	private ArrayList<Body> levelThreeBodies;
	private ArrayList<Body> levelFourBodies;
	
	private ArrayList<Object[]> bodies;
	
	public static final int STEEL = 0;
	public static final int WOOD = 1;
	public static final int RUBBER = 2;
	public static final int STONE = 3;
	public static final int OTHER = 4;
	
	public enum Level {
		LEVELONE,
		LEVELTWO,
		LEVELTHREE,
		LEVELFOUR
	}
	
	private BodyFactory(World world){
		
		levelOneBodies = new ArrayList<>();
		levelTwoBodies = new ArrayList<>();
		levelThreeBodies = new ArrayList<>();
		levelFourBodies = new ArrayList<>();
		bodies = new ArrayList<>();
		
		this.world = world;
		
	}
	

	/**
	 * Gets the instance of the body factory
	 * @param world - the world wanting to use the bodies
	 * @return - the instance
	 */
	public static BodyFactory getInstance(World world) {
		if(thisInstance == null){
			thisInstance = new BodyFactory(world);
		}
		return thisInstance;
	}
	
	/**
	 * Makes the fixtures for box2d bodies with given properties
	 * @param material - constant for deciding density, friction, and restitution
	 * @param shape - shape of body
	 * @param isSensor - is the body a sensor
	 * @return fixtureDef 
	 */
	static public FixtureDef makeFixture(int material, Shape shape, boolean isSensor) {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.isSensor = isSensor;
		fixtureDef.shape = shape;

		switch(material){
		case STEEL:
			fixtureDef.density = 1f;
			fixtureDef.friction = .3f;
			fixtureDef.restitution = 0.0f;
			break;
		case WOOD:
			fixtureDef.density = 0.5f;
			fixtureDef.friction = 0.0f;
			fixtureDef.restitution = 0.0f;
			break;
		case RUBBER:
			fixtureDef.density = 1f;
			fixtureDef.friction = 0f;
			fixtureDef.restitution = 0f;
			break;
		case STONE:
			fixtureDef.density = 1f;
			fixtureDef.friction = 0.0f;
			fixtureDef.restitution = 0.00f;
		case OTHER:
			fixtureDef.density = 0f;
			fixtureDef.friction = 0.0f;
			fixtureDef.restitution = 0.0f;
		default:
			fixtureDef.density = 7f;
			fixtureDef.friction = 0.5f;
			fixtureDef.restitution = 0.0f;
		}
		return fixtureDef;
	}

	/**
	 * Creates a circle box2d body
	 * @param posx - x coordinate of body
	 * @param posy - y coordinate of body
	 * @param radius - radius of body
	 * @param material - material used for body
	 * @param bodyType - box2d body type
	 * @param fixedRotation - rotation
	 * @return body
	 */
	public Body makeCirclePolyBody(float posx, float posy, float radius, int material, BodyType bodyType, boolean fixedRotation, boolean isSensor){
		// create a definition
		BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = bodyType;
		boxBodyDef.position.x = posx;
		boxBodyDef.position.y = posy;
		boxBodyDef.fixedRotation = fixedRotation;

		//create the body to attach said definition
		Body boxBody = world.createBody(boxBodyDef);
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(radius /2);
		boxBody.createFixture(makeFixture(material,circleShape, isSensor));
		circleShape.dispose();
		return boxBody;
	}
	
	/**
	 * Creates a box box2d body
	 * @param posx - x coordinate of body
	 * @param posy - y coordinate of body
	 * @param width - width
	 * @param height - height
	 * @param material - material used for body
	 * @param texture - 
	 * @param bodyType
	 * @param fixedRotation
	 * @param isSensor
	 * @return
	 */
	public Body makeBoxPolyBody(float posx, float posy, float width, float height, int material, BodyType bodyType, Level level, boolean fixedRotation, boolean isSensor, Texture texture){
		// create a definition
		BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = bodyType;
		boxBodyDef.position.x = posx;
		boxBodyDef.position.y = posy;
		boxBodyDef.fixedRotation = fixedRotation;
			
		//create the body to attach said definition
		Body boxBody = world.createBody(boxBodyDef);
		PolygonShape poly = new PolygonShape();
		poly.setAsBox(width/2, height/2);
		boxBody.createFixture(makeFixture(material,poly,isSensor));
		poly.dispose();

		bodies.add(Utilities.addPolygonTexture(texture, boxBody));
		
		if (level == null) {
			return boxBody;
		}
		
		if (level == Level.LEVELONE) {
			levelOneBodies.add(boxBody);
		} else if (level == Level.LEVELTWO) {
			levelTwoBodies.add(boxBody);
		} else if (level == Level.LEVELTHREE) {
			levelThreeBodies.add(boxBody);
		} else if (level == Level.LEVELFOUR) {
			levelFourBodies.add(boxBody);
		} 
		
		return boxBody;
	}
	
	public ArrayList<Object[]> getBodies() {
		return bodies;
	}
	
	public ArrayList<Body> getLevelOneBodies() {
		return levelOneBodies;
	}
	
	public ArrayList<Body> getLevelTwoBodies() {
		return levelTwoBodies;
	}

	public ArrayList<Body> getLevelThreeBodies() {
		return levelThreeBodies;
	}
	
	public ArrayList<Body> getLevelFourBodies() {
		return levelFourBodies;
	}
	
}
