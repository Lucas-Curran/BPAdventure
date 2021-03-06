package com.mygdx.game;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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
	
	static Logger logger = LogManager.getLogger(BodyFactory.class.getName());
	
	private World world;
	private static BodyFactory thisInstance;
	private ArrayList<Object[]> bodies;
	
	public static final int STEEL = 0;
	public static final int WOOD = 1;
	public static final int RUBBER = 2;
	public static final int STONE = 3;
	public static final int OTHER = 4;
	public static final int ICE = 5;
	
	private BodyFactory(World world){
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
			logger.info("Body factory instanced.");
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
			fixtureDef.friction = 0.3f;
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
			fixtureDef.density = 1f;
			fixtureDef.friction = 0.0f;
			fixtureDef.restitution = 0.0f;
		case ICE:
			fixtureDef.density = 1f;
			fixtureDef.friction = 0.0f;
			fixtureDef.restitution = 5.0f;
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
		
		logger.debug("Circle poly body made.");
		
		return boxBody;
	}
	
	/**
	 * Creates a box box2d body
	 * @param posx - x coordinate of body
	 * @param posy - y coordinate of body
	 * @param width - width
	 * @param height - height
	 * @param material - material used for body
	 * @param texture - what it looks like, its texture
	 * @param bodyType - the type of body it is
	 * @param fixedRotation - whether it can rotate or not
	 * @param isSensor - whether it's being used a sensor or solid
	 * @return - returns the box as a Body
	 */
	public Body makeBoxPolyBody(float posx, float posy, float width, float height, int material, BodyType bodyType, boolean fixedRotation, boolean isSensor, Texture texture){
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
		
		logger.debug("Box poly body made.");
		
		return boxBody;
	}
	
	
	/**
	 * Creates a box2d body of any polygon type
	 * @param vertices - the vertices of the polygon
	 * @param posx - x coordinate of the polygon
	 * @param posy - y coordinate of the polygon
	 * @param material - material used for the body
	 * @param bodyType - the type of body it is
	 * @param fixedRotation - whether it can freely rotate or not
	 * @param isSensor - whether it's being used for sensing or solid
	 * @param texture - what it looks like, its texture
	 * @return - returns the completed polygon as a Body
	 */
	public Body makePolygonShapeBody(Vector2[] vertices, float posx, float posy, int material, BodyType bodyType, boolean fixedRotation, boolean isSensor, Texture texture){
		BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = bodyType;
		boxBodyDef.position.x = posx;
		boxBodyDef.position.y = posy;
		Body boxBody = world.createBody(boxBodyDef);
			
		PolygonShape polygon = new PolygonShape();
		polygon.set(vertices);
		boxBody.createFixture(makeFixture(material,polygon, false));
		polygon.dispose();
		
		bodies.add(Utilities.addPolygonTexture(texture, boxBody));
			
		logger.debug("Polygon shape body made.");
		
		return boxBody;
	}

	public void makeAllFixturesSensors(Body bod){
	for(Fixture fix :bod.getFixtureList()){
		fix.setSensor(true);
	}
}
	public ArrayList<Object[]> getBodies() {
		return bodies;
	}
	
}
