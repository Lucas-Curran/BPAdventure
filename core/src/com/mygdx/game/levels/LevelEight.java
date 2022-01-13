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
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.Camera;
import com.mygdx.game.Map;
import com.mygdx.game.Utilities;
import com.mygdx.game.entities.Bullet;
import com.mygdx.game.entities.NPC;
import com.mygdx.game.levels.Levels.LevelDestination;
import com.mygdx.game.components.B2dBodyComponent;
import com.mygdx.game.components.BulletComponent;

public class LevelEight extends LevelFactory implements ApplicationListener {
		boolean isCreated;

		float[] vertices;
		
		
		
		
		DoorBuilder db = DoorBuilder.getInstance();
		World world;
		Texture texture = new Texture(Gdx.files.internal("ground_8.png"));
		Body[] pillars = new Body[3];
		Body[] platforms = new Body[20];
		
		public LevelEight(World world) {
			this.world = world;
		}
		
		@Override
		public void create() {
			super.createLevel(15, 700, 1, 100, 20, texture);
			
			db.createDoor(63, 682.5f, -35, 800, BodyFactory.STONE, "doorTo9", LevelDestination.LVL_9);
			
			makeSaw(-20, 685f);
			makeSaw(-12, 685f);
			makeSaw(-4, 685f);
			
			pillars[0] = bodyFactory.makeBoxPolyBody(15, 692, 1, 15, BodyFactory.STEEL, BodyType.StaticBody, false, false, texture);
			pillars[1] = bodyFactory.makeBoxPolyBody(22, 687.5f, 1, 13, BodyFactory.STEEL, BodyType.StaticBody, false, false, texture);
			platforms[0] = bodyFactory.makeBoxPolyBody(21, 683.8f, 1, 1, BodyFactory.STEEL, BodyType.StaticBody, false, false, texture);
			platforms[1] = bodyFactory.makeBoxPolyBody(16, 685.5f, 1, 1, BodyFactory.STEEL, BodyType.StaticBody, false, false, texture);
			platforms[2] = bodyFactory.makeBoxPolyBody(21, 687f, 1, 1, BodyFactory.STEEL, BodyType.StaticBody, false, false, texture);
			platforms[3] = bodyFactory.makeBoxPolyBody(16, 689f, 1, 1, BodyFactory.STEEL, BodyType.StaticBody, false, false, texture);
			platforms[4] = bodyFactory.makeBoxPolyBody(21, 691f, 1, 1, BodyFactory.STEEL, BodyType.StaticBody, false, false, texture);
			platforms[5] = bodyFactory.makeBoxPolyBody(16, 693.3f, 1, 1, BodyFactory.STEEL, BodyType.StaticBody, false, false, texture);
			pillars[2] = bodyFactory.makeBoxPolyBody(50, 682.5f, 1, 2, BodyFactory.STEEL, BodyType.StaticBody, false, false, texture);
			platforms[6] = bodyFactory.makeBoxPolyBody(50, 684f, 4, 1, BodyFactory.STEEL, BodyType.DynamicBody, false, false, texture);
			
	        RevoluteJointDef revoluteJointDef3 = new RevoluteJointDef();
	        revoluteJointDef3.initialize(platforms[6], pillars[2], platforms[6].getWorldCenter());

	        world.createJoint(revoluteJointDef3);
			
	        			
			NPC npc = new NPC();
			Map.getInstance().getEntityHandler().getPooledEngine().addEntity(npc.spawnNPC(new String[] {"Thank the Cave Lords... another soul!", 
					"We haven't seen anyone for decades.", "There's bits of Jack everywhere...but he's doing alright, I think.", 
					"This land is the most dangerous thus far Ice Cream...tread carefully."}, -32, 682, Utilities.levelSevenAtlas.findRegion("squirrelMan"), false));
			Map.getInstance().getEntityHandler().getPooledEngine().addEntity(npc.spawnNPC(new String[] {"Hurry you're almost there!" 
					}, 58, 682, Utilities.levelSevenAtlas.findRegion("oldMan"), false));
						
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
		
		public void makeSaw(float x, float y) {
			Body sawCenter, blade1, blade2;
			sawCenter = bodyFactory.makeCirclePolyBody(x, y, 2, BodyFactory.RUBBER, BodyType.StaticBody, false, false);
			
			Vector2 vertex1 = new Vector2(1, 3);
			Vector2 vertex2 = new Vector2(3, 4);
			Vector2 vertex3 = new Vector2(1, 5);
			
			Vector2 vertex11 = new Vector2(1, 3);
			Vector2 vertex12 = new Vector2(-1, 4);
			Vector2 vertex13 = new Vector2(1, 5);
			
			Vector2[] triangleVertices = {vertex1, vertex2, vertex3};
			Vector2[] triangleVertices2 = {vertex11, vertex12, vertex13};

			
			blade1 = bodyFactory.makePolygonShapeBody(triangleVertices, x, y-4, BodyFactory.STEEL, BodyType.DynamicBody, false, false, texture);
			blade2 = bodyFactory.makePolygonShapeBody(triangleVertices2, x-2, y-4, BodyFactory.STEEL, BodyType.DynamicBody,  false, false, texture);

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
		}
		
		public boolean isCreated() {
			return isCreated;
		}
}
