package com.mygdx.game.levels;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.Map;
import com.mygdx.game.Utilities;
import com.mygdx.game.entities.NPC;
import com.mygdx.game.levels.Levels.LevelDestination;

/**
 * Class creates Level Two of the game
 * Content includes some beginner enemies for players to get used to combat
 */
public class LevelTwo extends LevelFactory implements ApplicationListener{
	boolean isCreated;
	private NPC startNPC;
	private NPC endNPC;
	private Texture chestImage;
	private Rectangle chest;
	private DoorBuilder db = DoorBuilder.getInstance();
	static boolean inLevelTwo;
	Texture texture = new Texture(Gdx.files.internal("crackedPillar.png"));
	private Texture lootTexture = new Texture(Gdx.files.internal("purpleBlock.png"));

	Body[] blessing = new Body[1];

	/**
	 * Creates the level at (15, 100) and sets width and height of level
	 * Creates "blessing" aka loot box for character at end of level
	 */

	@Override
	public void create() {

		super.createLevel(15, 100, 1, 50, 10, texture);
		inLevelTwo = true;
		isCreated = true;
		//Creates door to Level 3
		db.createDoor(39, 92.5f, -35, 188, BodyFactory.ICE, "DoorToLevel3", LevelDestination.LVL_3);
//		db.createDoor(39, 92.5f, -35, 588, BodyFactory.ICE, "DoorToLevel3", LevelDestination.LVL_7);


		//Creates Level One NPCs
		NPC npc = new NPC();
		TextureRegion normalMan = Utilities.levelTwoAtlas.findRegion("BPA Characters/normalMan");
		TextureRegion unknownBeing = Utilities.levelTwoAtlas.findRegion("BPA Characters/UnknownBeing");
		Map.getInstance().getEntityHandler().getPooledEngine().addEntity(npc.spawnNPC(new String[] {"I heard there's great treasure at the end of this cave..."}, 1, 92, normalMan, false));

		Map.getInstance().getEntityHandler().getPooledEngine().addEntity(npc.spawnNPC(new String[] {"So you're alive!", "Jump on top of that purple rock behind me to recieve your reward and exit through the door","...hopefully you'll make it farther than that last o-", "Why are you still here? Go, hurry up!"}, 33, 92, unknownBeing, false));


		blessing[0] = bodyFactory.makeBoxPolyBody(36, 92, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, lootTexture);
		blessing[0].setUserData("levelTwoBlessing");

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

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}




}
