package com.mygdx.game.levels;

//Any textures not credited are either either public domain or custom made by the BPAdventure Team.

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


public class VictoryScreen extends LevelFactory implements ApplicationListener {
	boolean isCreated;

	
	
	Body door;
	DoorBuilder db = DoorBuilder.getInstance();
	World world;
	

	public VictoryScreen(World world) {
		this.world = world;
	}
	
	
	@Override
	public void create() {	
//		super.createLevel(500, 100, 1, 50, 10, texture);
//		isCreated = true;
//		
//		db.createDoor(512, 92.5f, 26, 276, BodyFactory.STONE, "backTo4", LevelDestination.INTERNAL);
//		
//
//		
//		NPC npc = new NPC();
//		Map.getInstance().getEntityHandler().getPooledEngine().addEntity(npc.spawnNPC(new String[] { false));
//		
		
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


