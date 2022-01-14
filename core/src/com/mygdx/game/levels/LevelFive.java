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

/**
 * Level Five in the game and acts as the mid-game boss fight
 * Content includes multiple unique enemies with loot at the end of the boss fight
 *
 */
public class LevelFive extends LevelFactory implements ApplicationListener {
		boolean isCreated;


		Texture texture = new Texture(Gdx.files.internal("terracotta_ground.png"));	
		private Texture lootTexture = new Texture(Gdx.files.internal("purpleBlock.png"));	
		float[] vertices;
		
		Body door;
		Body[] blessings = new Body[25];
		DoorBuilder db = DoorBuilder.getInstance();
		World world;

		public LevelFive(World world) {
			this.world = world;
		}
		
		/**
		 * Creates the level at (15, 400) and sets width and height of level
		 * Creates "blessing" aka loot box for character at end of level
		 */
		@Override
		public void create() {	
			super.createLevel(15, 400, 1, 100, 20, texture);
			
			db.createDoor(62, 382.5f, -35, 470, BodyFactory.STONE, "doorTo6", LevelDestination.LVL_6);
			
			blessings[0] = bodyFactory.makeBoxPolyBody(60, 382, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, lootTexture);
			blessings[0].setUserData("levelFiveBlessing");
			
			
			//Creates NPC
			TextureRegion unknownBeing = Utilities.levelTwoAtlas.findRegion("BPA Characters/UnknownBeing");
			TextureRegion soldier = Utilities.otherTexturesAtlas.findRegion("soldierKnight");
			      			
			NPC npc = new NPC();
			Map.getInstance().getEntityHandler().getPooledEngine().addEntity(npc.spawnNPC(new String[] {"All my soldiers are gone...", "I don't know who you are but you're our last chance to make it out alive."}, -32, 382, soldier, false));
			Map.getInstance().getEntityHandler().getPooledEngine().addEntity(npc.spawnNPC(new String[] {"I can't go on...", "Go back while you still can, there's no end to this nightmare."}, 15, 382, soldier, false));
			Map.getInstance().getEntityHandler().getPooledEngine().addEntity(npc.spawnNPC(new String[] {"So you're alive!", "Honestly, that was impressive. Go on, don't worry this time it's actually a nice surpise."}, 58, 382, unknownBeing, false));

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
