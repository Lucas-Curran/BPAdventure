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
		super.createLevel(15, 9, 1, 20, 10, texture);
		inOverworld = true;


		db.createDoor(15, 1.5f, -5, 95, BodyFactory.ICE, "DoorTo2", LevelDestination.LVL_2);
		
		NPC npc = new NPC();

		Map.getInstance().getEntityHandler().getPooledEngine().addEntity(npc.spawnNPC(new String[] {"Would you like to take a look at my wares?"}, 13, 1, Utilities.rightTextures.findRegion("popsicle"), true));
		Map.getInstance().getEntityHandler().getPooledEngine().addEntity(npc.spawnNPC(new String[] {
				"The overworld is much more peaceful than the dwellings of that cave...", 
				"They say you have to make it to the end without rest or death if you want to attain the final treasure.", 
				"The shop keeper over there sells things to upgrade your gear and heal your health.", 
				"I just figured out recently that you can scroll down to find more goods, I had no idea!",
				"Consider stopping by once you have a little extra money.",
				"Once you're ready, press R on the door to enter the cave. Good luck!"}, 17, 1, Utilities.soldierKnight, false));
		
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
