package com.mygdx.game.levels;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.Camera;
import com.mygdx.game.Map;
import com.mygdx.game.entities.EntityHandler;
import com.mygdx.game.entities.NPC;
import com.mygdx.game.entities.Player;
import com.mygdx.game.levels.Levels.LevelDestination;

public class LevelTwo extends LevelFactory implements ApplicationListener{
	boolean isCreated;
    static boolean inLevelTwo;
	private NPC startNPC;
	private NPC endNPC;
	private Texture chestImage;
	private Rectangle chest;
	private Camera camera;
	private PolygonSpriteBatch polygonSpriteBatch;
	private DoorBuilder doorBuilderTwo = DoorBuilder.getInstance();
	
	
	Body[] chests = new Body[1];
	@Override
	public void create() {
		
		//Creates level 
		super.createLevel(15, 100, 1, 50, 10);
		isCreated = true;
		inLevelTwo = true;
        
		camera = new Camera();
		polygonSpriteBatch = new PolygonSpriteBatch();
		
		
		//Creates Level One NPCs
		NPC npc = Map.getInstance().getEntityHandler().getNPC();
		Map.getInstance().getEntityHandler().spawnLevelTwo();
		Map.getInstance().getEntityHandler().getPooledEngine().addEntity(npc.spawnNPC(new String[] {"I heard there's great treasure at the end of this cave..."}, 1, 92, normalMan));
		Map.getInstance().getEntityHandler().getPooledEngine().addEntity(npc.spawnNPC(new String[] {"So you're alive!", "Take this and good luck...hopefully you'll make it farther than that last o-", "Why are you still here? Go, hurry up!"}, 35, 92, normalMan));		
		
		//Creates door to Level 3
		DoorBuilder db = DoorBuilder.getInstance();
		db.createDoor(37, 92, -35, 188, BodyFactory.STEEL, "Door243", LevelDestination.LVL_3);
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void render() {
	
	}

	@Override
	public void dispose() {
	
	}
	
	public boolean isCreated() {
		return isCreated;
	}
	
	public boolean getInLevelTwo() {
		return inLevelTwo;
	}
	
	public void setInLevelTwo(boolean inLevelTwo) {
		this.inLevelTwo = inLevelTwo;
	}

	
}
