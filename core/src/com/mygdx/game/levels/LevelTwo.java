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

public class LevelTwo extends LevelFactory implements ApplicationListener{
	boolean isCreated;
    static boolean inLevelTwo;
	private NPC npc;
	private Texture chestImage;
	private Rectangle chest;
	private Camera camera;
	private PolygonSpriteBatch polygonSpriteBatch;
	
	Body[] chests = new Body[1];
	@Override
	public void create() {
		super.createLevel(15, 100, 1, 100, 10);
		isCreated = true;
		inLevelTwo = true;
        
		camera = new Camera();
		polygonSpriteBatch = new PolygonSpriteBatch();
		
		chests[0] = bodyFactory.makeBoxPolyBody(-32, 92f, 1, 1, BodyFactory.STEEL, BodyType.StaticBody, false, false);
		Map.getInstance().getEntityHandler().spawnLevelTwo();
//		npc = new NPC();
//		npc.spawnNPC(new String[] {"I heard there's great treasure at the end of this cave..."}, -32, 92);
		
		chestImage = new Texture(Gdx.files.internal("chest.png"));
		chest = new Rectangle();
		chest.x= -32;
		chest.y = 92;
		chest.width = 64;
		chest.height = 64;
		
		
		//super.createLevel(x, y, width, height, npcX);
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void render() {
		//render map
		//camera.getCamera().update();
		camera.getCamera().update();
		polygonSpriteBatch.setProjectionMatrix(camera.getCombined());
		polygonSpriteBatch.begin();
		polygonSpriteBatch.draw(chestImage, chest.x, chest.y);
		polygonSpriteBatch.end();
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
