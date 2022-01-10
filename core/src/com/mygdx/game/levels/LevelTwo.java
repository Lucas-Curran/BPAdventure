package com.mygdx.game.levels;

//Any textures not credited are either either public domain or custom made.

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
	private NPC startNPC;
	private NPC endNPC;
	private Texture chestImage;
	private Rectangle chest;
	private DoorBuilder db = DoorBuilder.getInstance();
	Texture texture = new Texture(Gdx.files.internal("crackedPillar.png"));
	
	Body[] chests = new Body[1];
	@Override
	public void create() {
		
		//Creates level 
		super.createLevel(15, 100, 1, 50, 10, texture);
		isCreated = true;        
		
		//Creates Level One NPCs
		Map.getInstance().getEntityHandler().spawnLevelTwo();
		NPC npc = new NPC();
		Map.getInstance().getEntityHandler().getPooledEngine().addEntity(npc.spawnNPC(new String[] {"I heard there's great treasure at the end of this cave..."}, 1, 92, tex));
		Map.getInstance().getEntityHandler().getPooledEngine().addEntity(npc.spawnNPC(new String[] {"So you're alive!", "Take this and good luck...hopefully you'll make it farther than that last o-", "Why are you still here? Go, hurry up!"}, 35, 92, tex));
		
		//Creates door to Level 3
		db.createDoor(37, 92.5f, 50, 188, BodyFactory.ICE, "DoorToLevel3", LevelDestination.LVL_3);
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
	


	
}
