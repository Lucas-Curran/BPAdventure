package com.mygdx.game.levels;

/**
 * Any textures not credited are either public domain or custom made by the BPAdventure Team. 
 * All textures used are free to use for any purpose including commercially 
 */

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.Camera;
import com.mygdx.game.GameWorld;
import com.mygdx.game.Map;
import com.mygdx.game.components.B2dBodyComponent;
import com.mygdx.game.entities.Player;
import com.mygdx.game.levels.Levels.LevelDestination;
/**
 * the mother of all parkour levels
 * includes a neat gravity switch feature
 * @author 00011598
 *
 */
public class LevelThree extends LevelFactory implements ApplicationListener{
	Texture texture = new Texture(Gdx.files.internal("terracotta_ground.png"));	
	boolean isCreated;

	GameWorld gameWorld;
	World world;
	Box2DDebugRenderer renderer;
	
	float[] vertices;
	
	Body[] pillars = new Body[20];
	Body[] safeSpaces = new Body[5];
	Body sawCenter, rotator, blade1, blade2, slide, slideJump, level3EndDoor, lavaFloor, lavaCeiling, lavaCeiling2;
	
	public LevelThree(World world) {
		this.world = world;
	}
	
	@Override
	public void create() {			
		super.createLevel(15, 200, 1, 100, 15, texture);
		
		DoorBuilder db = DoorBuilder.getInstance();
		
		Texture texture = new Texture(Gdx.files.internal("crackedPillar.png"));
		Texture texture2 = new Texture(Gdx.files.internal("lava.png"));
		// Cracked Pillar by FunwithPixels licensed CC-BY 4.0 https://opengameart.org/content/cracked-pillar
		
		pillars[1] = bodyFactory.makeBoxPolyBody(-33, 187f, 1, 1, BodyFactory.STEEL, BodyType.StaticBody, false, false, texture);
		pillars[2] = bodyFactory.makeBoxPolyBody(-30, 188f, 1, 3, BodyFactory.STEEL, BodyType.StaticBody,  false, false, texture);
		pillars[3] = bodyFactory.makeBoxPolyBody(-25, 187f, 1, 3, BodyFactory.STEEL, BodyType.StaticBody,  false, false, texture);
		pillars[4] = bodyFactory.makeBoxPolyBody(-14, 188f, 1, 3, BodyFactory.STEEL, BodyType.StaticBody,  false, false, texture);
		pillars[5] = bodyFactory.makeBoxPolyBody(-8, 187f, 1, 1, BodyFactory.STEEL, BodyType.StaticBody,  false, false, texture);
		pillars[5].setUserData("gravityPillar");
		pillars[6] = bodyFactory.makeBoxPolyBody(-5, 199f, 1, 3, BodyFactory.STEEL, BodyType.StaticBody, false, false, texture);
		pillars[7] = bodyFactory.makeBoxPolyBody(0, 198f, 1, 4, BodyFactory.STEEL, BodyType.StaticBody,  false, false, texture);
		pillars[8] = bodyFactory.makeBoxPolyBody(5, 197f, 1, 5, BodyFactory.STEEL, BodyType.StaticBody,  false, false, texture);
		pillars[9] = bodyFactory.makeBoxPolyBody(5, 195f, 4, 1, BodyFactory.STEEL, BodyType.DynamicBody,  false, false, texture);
		pillars[10] = bodyFactory.makeBoxPolyBody(10, 198f, 1, 4, BodyFactory.STEEL, BodyType.StaticBody,  false, false, texture);
		pillars[11] = bodyFactory.makeBoxPolyBody(14, 197f, 1, 5, BodyFactory.STEEL, BodyType.StaticBody,  false, false, texture);
		pillars[12] = bodyFactory.makeBoxPolyBody(18, 196f, 1, 7, BodyFactory.STEEL, BodyType.StaticBody,  false, false, texture);
		pillars[13] = bodyFactory.makeBoxPolyBody(40, 196f, 1, 1, BodyFactory.STEEL, BodyType.StaticBody,  false, false, texture);
		pillars[13].setUserData("gravityPillar2");
		db.createDoor(45, 187.5f, -35, 258, BodyFactory.STEEL, "DoorTo4", LevelDestination.LVL_4);
		lavaFloor = bodyFactory.makeBoxPolyBody(5, 186.1f, 77, 1, BodyFactory.STEEL, BodyType.StaticBody,  false, false, texture2);
		lavaFloor.setUserData("lavaFloor");
		lavaCeiling = bodyFactory.makeBoxPolyBody(-5, 199.9f, 60, 1, BodyFactory.STEEL, BodyType.StaticBody,  false, false, texture2);
		lavaCeiling.setUserData("lavaCeiling");
		lavaCeiling2 = bodyFactory.makeBoxPolyBody(50, 199.9f, 30, 1, BodyFactory.STEEL, BodyType.StaticBody,  false, false, texture2);
		lavaCeiling2.setUserData("lavaCeiling2");		
		
		sawCenter = bodyFactory.makeCirclePolyBody(-20, 190f, 2, BodyFactory.RUBBER, BodyType.StaticBody, false, false);
		
		// creates vertices for the triangle polygons
		Vector2 vertex1 = new Vector2(1, 3);
		Vector2 vertex2 = new Vector2(3, 4);
		Vector2 vertex3 = new Vector2(1, 5);
		
		Vector2 vertex11 = new Vector2(1, 3);
		Vector2 vertex12 = new Vector2(-1, 4);
		Vector2 vertex13 = new Vector2(1, 5);
		
		Vector2 vertex21 = new Vector2(1, -1);
		Vector2 vertex22 = new Vector2(5, 5);
		Vector2 vertex23 = new Vector2(1, 5);
		
		Vector2 vertex31 = new Vector2(1, -1);
		Vector2 vertex32 = new Vector2(-5, 5);
		Vector2 vertex33 = new Vector2(1, 5);
		
		Vector2[] triangleVertices = {vertex1, vertex2, vertex3};
		Vector2[] triangleVertices2 = {vertex11, vertex12, vertex13};
		Vector2[] triangleVertices3 = {vertex21, vertex22, vertex23};
		Vector2[] triangleVertices4 = {vertex31, vertex32, vertex33};
		
		blade1 = bodyFactory.makePolygonShapeBody(triangleVertices, -20, 186, BodyFactory.STEEL, BodyType.DynamicBody, false, false, texture);
		blade2 = bodyFactory.makePolygonShapeBody(triangleVertices2, -22, 186, BodyFactory.STEEL, BodyType.DynamicBody,  false, false, texture);
		slide = bodyFactory.makePolygonShapeBody(triangleVertices3, 21, 195, BodyFactory.ICE, BodyType.StaticBody,  false, false, texture);
		slideJump = bodyFactory.makePolygonShapeBody(triangleVertices4, 35, 195, BodyFactory.ICE, BodyType.StaticBody,  false, false, texture);
		slide.setGravityScale(0);
		blade1.setGravityScale(0);
		blade2.setGravityScale(0);
		
		// defines new revolute joints for each part of the spinning saws
		RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.initialize(sawCenter, blade1, sawCenter.getWorldCenter());
        revoluteJointDef.enableMotor = true;
        revoluteJointDef.motorSpeed = -3;
        revoluteJointDef.maxMotorTorque = 3000;
        
        RevoluteJointDef revoluteJointDef2 = new RevoluteJointDef();
        revoluteJointDef2.initialize(sawCenter, blade2, sawCenter.getWorldCenter());
        revoluteJointDef2.enableMotor = true;
        revoluteJointDef2.motorSpeed = -3;
        revoluteJointDef2.maxMotorTorque = 3000;
        
        RevoluteJointDef revoluteJointDef3 = new RevoluteJointDef();
        revoluteJointDef3.initialize(pillars[8], pillars[9], pillars[9].getWorldCenter());

        // adds the joints to the world
        world.createJoint(revoluteJointDef);
        world.createJoint(revoluteJointDef2);
        world.createJoint(revoluteJointDef3);

		isCreated = true;
	}
	
	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void render() {	
		 
		 
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		
	}
	
	public boolean isCreated() {
		return isCreated;
	}
	
}
