package com.mygdx.game.levels;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.Camera;
import com.mygdx.game.Map;
import com.mygdx.game.entities.NPC;
import com.mygdx.game.levels.Levels.LevelDestination;

public class LevelFour extends LevelFactory implements ApplicationListener {
	boolean isCreated;
	World world;
	private Camera camera;
	DoorBuilder db = DoorBuilder.getInstance();
	Texture texture = new Texture(Gdx.files.internal("newGround.png"));
	Body[] platforms = new Body[25];
	
	
	public LevelFour(World world) {
		this.world = world;
	}
	
	
	public void create() {
		
		//Creates level 
		super.createLevel(15, 300, 1, 100, 20, texture);
		
		camera = new Camera(); 
		
		db.createDoor(45, 287, -35, 388, BodyFactory.STONE, "doorTo5", LevelDestination.LVL_5);
		
		platforms[0] = bodyFactory.makeBoxPolyBody(0, 283f, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
		platforms[0] = bodyFactory.makeBoxPolyBody(1, 285f, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
		platforms[0] = bodyFactory.makeBoxPolyBody(12, 287f, 20, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);

		
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
