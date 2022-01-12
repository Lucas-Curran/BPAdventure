package com.mygdx.game.levels;

//Any textures not credited are either either public domain or custom made by the BPAdventure Team.

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.Camera;
import com.mygdx.game.Map;
import com.mygdx.game.entities.NPC;
import com.mygdx.game.levels.Levels.LevelDestination;
import com.mygdx.game.ui.ShopWindow;

public class Overworld extends LevelFactory implements ApplicationListener {
	boolean isCreated;
	static boolean inOverworld;
	Texture texture = new Texture(Gdx.files.internal("terracotta_ground.png"));
	float[] vertices;
	private ShopWindow shopWindow;
	private DoorBuilder db = DoorBuilder.getInstance();

	@Override
	public void create() {
		super.createLevel(15, 9, 1, 100, 10, texture);
		inOverworld = true;
		
		
//		db.createDoor(15, 1.5f, -5, 95, BodyFactory.ICE, "DoorTo2", LevelDestination.LVL_2);
		
		db.createDoor(15, 1.5f, -35, 688, BodyFactory.ICE, "DoorTo2", LevelDestination.LVL_8);
		
		NPC npc = new NPC();
		
		Map.getInstance().getEntityHandler().getPooledEngine().addEntity(npc.spawnNPC(new String[] {"Would you like to take a look at my wares?"}, 13, 1, tex, true));
		shopWindow = new ShopWindow(Map.getInstance().getEntityHandler().getNPC().getShopWares(), Map.getInstance().getPlayerHUD().getInventory().getAllItems(), Map.getInstance().getMoney());

		isCreated = true;
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

	public ShopWindow getShopWindow() {
		return shopWindow;
	}
}
