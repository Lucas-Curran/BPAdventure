package com.mygdx.game.levels;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.Map;
import com.mygdx.game.entities.NPC;
import com.mygdx.game.levels.Levels.LevelDestination;


public class IceDungeon extends LevelFactory implements ApplicationListener {
	boolean isCreated;

	private TextureRegion textureRegion;
	Texture texture = new Texture(Gdx.files.internal("terracotta_ground.png"));	
	float[] vertices;
	
	Body door;
	DoorBuilder db = DoorBuilder.getInstance();
	World world;

	public IceDungeon(World world) {
		this.world = world;
	}
	
	@Override
	public void create() {	
		super.createLevel(500, 100, 1, 50, 10, texture);
		isCreated = true;
		
		db.createDoor(512, 93, 26, 276, BodyFactory.STONE, "backTo4", LevelDestination.LVL_4);
		
		      			
		NPC npc = new NPC();
		Map.getInstance().getEntityHandler().getPooledEngine().addEntity(npc.spawnNPC(new String[] {"Welcome to the Ice Dungeon!", "Make it to the end for a great reward...or die trying."}, 485, 92, tex, false));
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


