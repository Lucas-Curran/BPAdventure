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

public class LevelSix extends LevelFactory implements ApplicationListener {
		boolean isCreated;

		private TextureRegion textureRegion;
		Texture texture = new Texture(Gdx.files.internal("terracotta_ground.png"));	
		Body[] platforms = new Body[25];
		float[] vertices;
		
		Body door;
		DoorBuilder db = DoorBuilder.getInstance();
		World world;
		
		public LevelSix(World world) {
			this.world = world;
		}
		
		@Override
		public void create() {
			super.createLevel(15, 500, 1, 100, 50, texture);

			db.createDoor(-30, 458, -35, 586, BodyFactory.STONE, "doorTo7", LevelDestination.LVL_7);
			
			platforms[0] = bodyFactory.makeBoxPolyBody(-10, 454f, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
			platforms[1] = bodyFactory.makeBoxPolyBody(-8, 455f, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
			platforms[2] = bodyFactory.makeBoxPolyBody(8, 457f, 20, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
			
			
	        			
			NPC npc = new NPC();
			
			Map.getInstance().getEntityHandler().spawnLevelSix();

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
