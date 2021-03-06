package com.mygdx.game.levels;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.entities.NPC;
import com.mygdx.game.levels.Levels.LevelDestination;

/**
 * Level Ten in the game and acts as the final boss fight
 * Content includes enemies taken from all levels of the game and a Victory Room at the end
 *
 */
public class LevelTen extends LevelFactory implements ApplicationListener {
		boolean isCreated;

		float[] vertices;
		
		Body door;
		DoorBuilder db = DoorBuilder.getInstance();
		World world;
		
		Texture texture = new Texture(Gdx.files.internal("newGround.png"));
		
		public LevelTen(World world) {
			this.world = world;
		}
		
		/**
		 * Creates the level at (15, 900) and sets width and height of level
		 * Creates door to Victory Level at end of level
		 */
		@Override
		public void create() {
			super.createLevel(15, 900, 1, 100, 20, texture);
					
			Texture texture = new Texture(Gdx.files.internal("newGround.png"));
			
			db.createDoor(60, 882.5f, 683, 93, BodyFactory.WOOD, "doorToVictory", LevelDestination.INTERNAL);
			
	        			
			NPC npc = new NPC();
			
			
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
