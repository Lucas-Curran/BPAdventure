package com.mygdx.game.levels;

//Any textures not credited are either either public domain or custom made.

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
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.Camera;
import com.mygdx.game.Map;
import com.mygdx.game.entities.Bullet;
import com.mygdx.game.entities.NPC;
import com.mygdx.game.levels.Levels.LevelDestination;
import com.mygdx.game.components.BulletComponent;

public class LevelEight extends LevelFactory implements ApplicationListener {
		boolean isCreated;

		float[] vertices;
		
		Body sawCenter, blade1, blade2;
		DoorBuilder db = DoorBuilder.getInstance();
		World world;
		Texture texture = new Texture(Gdx.files.internal("terracotta_ground.png"));	
		
		public LevelEight(World world) {
			this.world = world;
		}
		
		@Override
		public void create() {
			super.createLevel(15, 700, 1, 100, 20, texture);
			
			db.createDoor(45, 682.5f, -35, 788, BodyFactory.STONE, "doorTo9", LevelDestination.LVL_9);
			
			sawCenter = bodyFactory.makeCirclePolyBody(-20, 690f, 2, BodyFactory.RUBBER, BodyType.StaticBody, false, false);
			
			Vector2 vertex1 = new Vector2(1, 3);
			Vector2 vertex2 = new Vector2(3, 4);
			Vector2 vertex3 = new Vector2(1, 5);
			
			Vector2 vertex11 = new Vector2(1, 3);
			Vector2 vertex12 = new Vector2(-1, 4);
			Vector2 vertex13 = new Vector2(1, 5);
			
			Vector2[] triangleVertices = {vertex1, vertex2, vertex3};
			Vector2[] triangleVertices2 = {vertex11, vertex12, vertex13};

			
			blade1 = bodyFactory.makePolygonShapeBody(triangleVertices, -20, 686, BodyFactory.STEEL, BodyType.DynamicBody, false, false, texture);
			blade2 = bodyFactory.makePolygonShapeBody(triangleVertices2, -22, 686, BodyFactory.STEEL, BodyType.DynamicBody,  false, false, texture);

			blade1.setGravityScale(0);
			blade2.setGravityScale(0);
			
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

	        world.createJoint(revoluteJointDef);
	        world.createJoint(revoluteJointDef2);
			
	        			
			NPC npc = new NPC();

			Map.getInstance().getEntityHandler().spawnLevelEight();
			
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
