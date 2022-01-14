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
 * the father of all enemy levels, essentially a lot of bullets, and the last level before the big Boss
 * @author 00011598
 *
 */
public class LevelNine extends LevelFactory implements ApplicationListener {
		boolean isCreated;
		
		private TextureRegion textureRegion;
		
		float[] vertices;
		
		Body[] platforms = new Body[10];
		DoorBuilder db = DoorBuilder.getInstance();
		World world;
		
		Texture texture = new Texture(Gdx.files.internal("ground_9.png"));
		/**
		 * 
		 * @param world - sends the level the GameWorld
		 */
		public LevelNine(World world) {
			this.world = world;
		}
		/**
		 * creates the environment of the level and spawns the NPCs
		 */
		@Override
		public void create() {	
			super.createLevel(15, 800, 1, 100, 20, texture);
			
			
			
			NPC npc = new NPC();
			Map.getInstance().getEntityHandler().getPooledEngine().addEntity(npc.spawnNPC(new String[] {"I lied Ice Cream...", "Heh heh heh...HEH!", "More danger than you could possibly fathom awaits you now..." 
					}, -32, 797, Utilities.levelSevenAtlas.findRegion("oldMan"), false));
			
			platforms[0] = bodyFactory.makeBoxPolyBody(13, 796, 96, 1, BodyFactory.STEEL, BodyType.StaticBody,  false, false, texture);
			platforms[1] = bodyFactory.makeBoxPolyBody(17, 791, 96, 1, BodyFactory.STEEL, BodyType.StaticBody,  false, false, texture);
			platforms[2] = bodyFactory.makeBoxPolyBody(13, 786, 96, 1, BodyFactory.STEEL, BodyType.StaticBody,  false, false, texture);
			
			Map.getInstance().getEntityHandler().getPooledEngine().addEntity(npc.spawnNPC(new String[] {"Enjoy that little warm up?", "Don't worry, the rest is way worse." 
			}, 40, 797, Utilities.levelSevenAtlas.findRegion("oldMan"), false));
			
			Map.getInstance().getEntityHandler().getPooledEngine().addEntity(npc.spawnNPC(new String[] {"Still alive?", "How curious-", "Maybe you will meet my master after all..."
			}, -30, 792, Utilities.rightTextures.findRegion("oldMan"), false));
			
			platforms[3] = bodyFactory.makeBoxPolyBody(20, 787, 2, 1, BodyFactory.STEEL, BodyType.StaticBody,  false, false, texture);
			platforms[4] = bodyFactory.makeBoxPolyBody(30, 787.9f, 2, 1, BodyFactory.STEEL, BodyType.StaticBody,  false, false, texture);
			platforms[5] = bodyFactory.makeBoxPolyBody(40, 788.2f, 2, 1, BodyFactory.STEEL, BodyType.StaticBody,  false, false, texture);
			
			Map.getInstance().getEntityHandler().getPooledEngine().addEntity(npc.spawnNPC(new String[] {"Made it through?", "Then it is time.", "My master awaits, Ice Cream.", "Say your goodbyes ahead"
			}, 50, 782, Utilities.rightTextures.findRegion("oldMan"), false));
			
			Map.getInstance().getEntityHandler().getPooledEngine().addEntity(npc.spawnNPC(new String[] {"It's been an honor, friend", "Good Luck!"
			}, 30, 782, Utilities.rightTextures.findRegion("squirrelMan"), false));
			
			platforms[6] = bodyFactory.makeBoxPolyBody(-30, 782f, 1, 1, BodyFactory.STEEL, BodyType.StaticBody,  false, false, Utilities.giftBlock);
			platforms[6].setUserData("moneyBox9");
			
			// jin's NPCs
			// more NPCs?
			
			db.createDoor(-33, 782.5f, -35, 888, BodyFactory.STONE, "doorTo10", LevelDestination.LVL_10);
		
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
