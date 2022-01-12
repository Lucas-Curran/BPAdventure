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


public class IceDungeon extends LevelFactory implements ApplicationListener {
	boolean isCreated;

	Texture texture = new Texture(Gdx.files.internal("terracotta_ground.png"));	
	private Texture lootTexture = new Texture(Gdx.files.internal("purpleBlock.png"));	
	
	float[] vertices;
	
	Body door;
	DoorBuilder db = DoorBuilder.getInstance();
	World world;
	Body[] loot = new Body[1];

	public IceDungeon(World world) {
		this.world = world;
	}
	
	
	@Override
	public void create() {	
		super.createLevel(500, 100, 1, 50, 10, texture);
		isCreated = true;
		
		db.createDoor(512, 93, 26, 276, BodyFactory.STONE, "backTo4", LevelDestination.LVL_4);
		loot[0] = bodyFactory.makeBoxPolyBody(514, 93.5f, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, lootTexture);

		TextureRegion normalMan = Utilities.levelTwoAtlas.findRegion("BPA Characters/normalMan");
		NPC npc = new NPC();
		Map.getInstance().getEntityHandler().getPooledEngine().addEntity(npc.spawnNPC(new String[] {"Welcome to the Ice Dungeon!", "Make it to the end for a great reward...or die trying."}, 485, 92, normalMan, false));
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


