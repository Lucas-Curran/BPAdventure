package com.mygdx.game.levels;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.Camera;
import com.mygdx.game.Map;
import com.mygdx.game.entities.NPC;
import com.mygdx.game.levels.Levels.LevelDestination;

public class LevelFour extends LevelFactory implements ApplicationListener {
	boolean isCreated;
	World world;
	
	DoorBuilder db = DoorBuilder.getInstance();

	
	
	public LevelFour(World world) {
		this.world = world;
	}
	
	
	public void create() {
		
		//Creates level 
		super.createLevel(15, 300, 1, 100, 15);
		db.createDoor(45, 287, -35, 388, BodyFactory.STONE, "doorTo5", LevelDestination.LVL_5);
		
		Map.getInstance().getEntityHandler().spawnShopNPC();
		Map.getInstance().getEntityHandler().spawnLevelFour();
		
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
