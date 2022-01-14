package com.mygdx.game.levels;

/**
 * Any textures not credited are either public domain or custom made by the BPAdventure Team.
 * All textures used are free to use for any purpose including commercially
 */

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.Map;
import com.mygdx.game.Utilities;
import com.mygdx.game.entities.NPC;
import com.mygdx.game.levels.Levels.LevelDestination;


/**
 * Ice Dungeon in the game
 * Separate challenge area for the player to further test their combat abilities 
 *
 */

public class IceDungeon extends LevelFactory implements ApplicationListener {
	boolean isCreated;

	Texture texture = new Texture(Gdx.files.internal("terracotta_ground.png"));	
	private Texture lootTexture = new Texture(Gdx.files.internal("purpleBlock.png"));	
	
	float[] vertices;
	
	Body door;
	DoorBuilder db = DoorBuilder.getInstance();
	World world;

	public IceDungeon(World world) {
		this.world = world;
	}
	
	/**
	 * Creates the dungeon at (500,100) and sets width and height of level
	 */
	@Override
	public void create() {	
		super.createLevel(500, 100, 1, 50, 10, texture);
		isCreated = true;
		
		db.createDoor(512, 92.5f, 26, 276, BodyFactory.STONE, "backTo4", LevelDestination.INTERNAL);

		TextureRegion normalMan = Utilities.levelTwoAtlas.findRegion("BPA Characters/normalMan");
		NPC npc = new NPC();
		Map.getInstance().getEntityHandler().getPooledEngine().addEntity(npc.spawnNPC(new String[] {"Welcome to the Ice Dungeon!", "Make it to the end...or die trying."}, 485, 92, normalMan, false));
		Map.getInstance().getEntityHandler().spawnIceDungeon();
		
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


