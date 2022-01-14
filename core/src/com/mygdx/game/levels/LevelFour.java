package com.mygdx.game.levels;

/**
 * Any textures not credited are either public domain or custom made by the BPAdventure Team.
 * All textures used are free to use for any purpose including commercially
 */

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.Camera;
import com.mygdx.game.Map;
import com.mygdx.game.Utilities;
import com.mygdx.game.entities.NPC;
import com.mygdx.game.levels.Levels.LevelDestination;

/**
 * Level Four in the game
 * Content includes scaling the mountain while fighting enemy NPCs and a mini boss at the end
 * Level Four also includes an exclusive challenge dungeon
 *
 */
public class LevelFour extends LevelFactory implements ApplicationListener {
	boolean isCreated;
	World world;
	private Camera camera;
	DoorBuilder db = DoorBuilder.getInstance();
	Texture texture = new Texture(Gdx.files.internal("terracotta_ground.png"));
	private Texture lootTexture = new Texture(Gdx.files.internal("purpleBlock.png"));
	Body[] platforms = new Body[25];
	Body[] blessings = new Body[25];

	public LevelFour(World world) {
		this.world = world;
	}

	/**
	 * Creates the level at (15,300) and sets width and height of level
	 * Creates the platforms for characters to climb up on
	 */
	public void create() {

		super.createLevel(15, 300, 1, 100, 50, texture);

		camera = new Camera();


		db.createDoor(-33, 287.5f, -35, 388, BodyFactory.STONE, "doorTo5", LevelDestination.LVL_5);
		db.createDoor(27, 275.5f, 480, 95, BodyFactory.STONE, "doorToDungeon", LevelDestination.INTERNAL);


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

		blessings[0] = bodyFactory.makeBoxPolyBody(-30, 287, 1, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, lootTexture);
		blessings[0].setUserData("levelFourBlessing");


		//Creates NPC
		TextureRegion normalMan = Utilities.levelTwoAtlas.findRegion("BPA Characters/normalMan");
		TextureRegion tex = Utilities.tex;

		NPC npc = new NPC();
		TextureRegion soldier = Utilities.otherTexturesAtlas.findRegion("soldierKnight");
		Map.getInstance().getEntityHandler().getPooledEngine().addEntity(npc.spawnNPC(new String[] {"Having fun?", "Well I hope you like climbing because you'll need to make it to the top to get out!", "Just a tip: you'll need to jump onto the sides and climb up so I hope you have good grip strength."}, -28, 252, normalMan, false));
		Map.getInstance().getEntityHandler().getPooledEngine().addEntity(npc.spawnNPC(new String[] {"Brrr...who are you?", "Go back down before it's too late kid, you'll never get past the guardian", "I should have never entered that cursed cave..."}, 2, 275, soldier, false));
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


	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}


}
