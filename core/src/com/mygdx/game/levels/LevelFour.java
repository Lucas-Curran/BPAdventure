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
	Texture texture = new Texture(Gdx.files.internal("terracotta_ground.png"));
	Body[] platforms = new Body[25];
	
	
	public LevelFour(World world) {
		this.world = world;
	}
	
	
	public void create() {
		
		//Creates level 
		super.createLevel(15, 300, 1, 100, 50, texture);
		
		camera = new Camera(); 
		
		db.createDoor(45, 282.5f, -35, 388, BodyFactory.STONE, "doorTo5", LevelDestination.LVL_5);
		db.createDoor(-33, 287.5f, -35, 388, BodyFactory.STONE, "doorTo5", LevelDestination.LVL_5);
		db.createDoor(27, 276f, 480, 95, BodyFactory.STONE, "doorToDungeon", LevelDestination.LVL_2);
		
	
		platforms[0] = bodyFactory.makeBoxPolyBody(0, 254f, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
		platforms[1] = bodyFactory.makeBoxPolyBody(1, 255f, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
		
		platforms[2] = bodyFactory.makeBoxPolyBody(12, 257f, 20, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
		platforms[3] = bodyFactory.makeBoxPolyBody(19, 260f, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
		platforms[4] = bodyFactory.makeBoxPolyBody(17, 262f, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
		platforms[5] = bodyFactory.makeBoxPolyBody(15, 264f, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
		
		platforms[6] = bodyFactory.makeBoxPolyBody(0, 265f, 30, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
		platforms[7] = bodyFactory.makeBoxPolyBody(-7, 268f, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
		platforms[8] = bodyFactory.makeBoxPolyBody(-5, 270f, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
		platforms[9] = bodyFactory.makeBoxPolyBody(-3, 272f, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
		
		platforms[10] = bodyFactory.makeBoxPolyBody(14, 274f, 30, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
		platforms[11] = bodyFactory.makeBoxPolyBody(12, 277f, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
		platforms[12] = bodyFactory.makeBoxPolyBody(10, 279f, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
		platforms[13] = bodyFactory.makeBoxPolyBody(8, 281f, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
		platforms[14] = bodyFactory.makeBoxPolyBody(6, 283f, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
		platforms[15] = bodyFactory.makeBoxPolyBody(-16, 286f, 40, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
		
		
		
		//Map.getInstance().getEntityHandler().spawnShopNPC();
		NPC npc = new NPC();
		Map.getInstance().getEntityHandler().getPooledEngine().addEntity(npc.spawnNPC(new String[] {"Having fun?", "Well I hope you like climbing because you'll need to make it to the top to get out!"}, -28, 252, normalMan, false));
		Map.getInstance().getEntityHandler().getPooledEngine().addEntity(npc.spawnNPC(new String[] {"Brrr...who are you?", "Go back down before it's too late kid, you'll never get past the guardian", "I should have never entered that cursed cave..."}, 2, 275, tex, false));
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
