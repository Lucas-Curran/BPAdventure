package com.mygdx.game.levels;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.Map;
import com.mygdx.game.Utilities;
import com.mygdx.game.entities.NPC;
import com.mygdx.game.levels.Levels.LevelDestination;

/**
 * Level Six in the game
 * Content includes "rest stop" for player where they can receive extra rewards
 * Challenge levels provides players with more opportunity to earn stronger gear
 *
 */
public class LevelSix extends LevelFactory implements ApplicationListener {
		boolean isCreated;

		private TextureRegion textureRegion;
		private Texture lootTexture = new Texture(Gdx.files.internal("purpleBlock.png"));	
		Texture texture = new Texture(Gdx.files.internal("terracotta_ground.png"));	

		Body[] platforms = new Body[25];
		Body[] blessings = new Body[25];
		float[] vertices;
		
		Body door;
		DoorBuilder db = DoorBuilder.getInstance();
		World world;
		
		public LevelSix(World world) {
			this.world = world;
		}
		
		/**
		 * Creates the level at (15, 500) and sets width and height of level
		 * Creates platforms for characters and enemies 
		 * Creates "blessings" aka loot box for characters at end of challenge levels and in front of Slime King
		 */
		@Override
		public void create() {
			super.createLevel(15, 500, 1, 100, 50, texture);

			db.createDoor(21, 459, -35, 586, BodyFactory.STONE, "doorTo7", LevelDestination.LVL_7);
			
			platforms[0] = bodyFactory.makeBoxPolyBody(-10, 454f, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
			platforms[1] = bodyFactory.makeBoxPolyBody(-8, 455f, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
			platforms[2] = bodyFactory.makeBoxPolyBody(8, 457f, 30, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
			platforms[3] = bodyFactory.makeBoxPolyBody(8, 457f, 30, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
			platforms[4] = bodyFactory.makeBoxPolyBody(12, 460f, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
			platforms[5] = bodyFactory.makeBoxPolyBody(13, 460f, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
			platforms[6] = bodyFactory.makeBoxPolyBody(14, 460f, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
			platforms[7] = bodyFactory.makeBoxPolyBody(15, 460f, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
			platforms[8] = bodyFactory.makeBoxPolyBody(16, 460f, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);

			platforms[9] = bodyFactory.makeBoxPolyBody(25, 459f, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
			platforms[10] = bodyFactory.makeBoxPolyBody(27, 461f, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
			platforms[11] = bodyFactory.makeBoxPolyBody(29, 463f, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
			platforms[12] = bodyFactory.makeBoxPolyBody(46, 465f, 30, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);

			platforms[13] = bodyFactory.makeBoxPolyBody(56, 468f, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
			platforms[14] = bodyFactory.makeBoxPolyBody(54, 470f, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
			platforms[15] = bodyFactory.makeBoxPolyBody(52, 472f, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
			platforms[16] = bodyFactory.makeBoxPolyBody(50, 474f, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);		
			platforms[17] = bodyFactory.makeBoxPolyBody(32, 476f, 30, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);

			blessings[0] = bodyFactory.makeBoxPolyBody(60, 466.5f, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, lootTexture);
			blessings[0].setUserData("levelSixBlessing3");
			blessings[1] = bodyFactory.makeBoxPolyBody(19, 477.5f, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, lootTexture);
			blessings[1].setUserData("levelSixBlessing2");
			blessings[2] = bodyFactory.makeBoxPolyBody(8, 458f, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, lootTexture);
			blessings[2].setUserData("levelSixBlessing1");

			//Creates NPC
			TextureRegion slime = Utilities.slimeKing;
			NPC npc = new NPC();
			Map.getInstance().getEntityHandler().getPooledEngine().addEntity(npc.spawnNPC(new String[] {"Bow before the king and recieve your reward."}, 3, 458, slime, false));
			Map.getInstance().getEntityHandler().getPooledEngine().addEntity(npc.spawnNPC(new String[] {"Continue on to the next level or head up to take on the Jungle Challenges."}, 18, 458, slime, false));
			
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
