package com.mygdx.game.levels;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.Map;
import com.mygdx.game.Utilities;
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

		db.createDoor(15, 1.5f, 27, 275.5f, BodyFactory.ICE, "DoorToLevel37897", LevelDestination.LVL_4);

		NPC npc = new NPC();

		Map.getInstance().getEntityHandler().getPooledEngine().addEntity(npc.spawnNPC(new String[] {"Would you like to take a look at my wares?"}, 13, 1, Utilities.rightTextures.findRegion("popsicle"), true));
		shopWindow = new ShopWindow(Map.getInstance().getEntityHandler().getNPC().getShopWares(), Map.getInstance().getPlayerHUD().getInventory().getItemsToSell(), Map.getInstance().getMoney());

		isCreated = true;
	}


	@Override
	public void resize(int width, int height) {
		shopWindow.resize(width, height);
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
