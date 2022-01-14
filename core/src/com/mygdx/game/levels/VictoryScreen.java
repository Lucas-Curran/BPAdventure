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
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.Map;
import com.mygdx.game.Utilities;
import com.mygdx.game.entities.NPC;
import com.mygdx.game.levels.Levels.LevelDestination;


/**
 * Victory level after the player beats the final boss
 * Content includes NPC congratulations and door back to the Overworld
 *
 */
public class VictoryScreen extends LevelFactory implements ApplicationListener {
	boolean isCreated;
	Texture texture = new Texture(Gdx.files.internal("terracotta_ground.png"));

	Body door;
	DoorBuilder db = DoorBuilder.getInstance();
	World world;
	Body[] platforms = new Body[25];

	public VictoryScreen(World world) {
		this.world = world;
	}


	/**
	 * Creates the level at (700, 100) and sets width and height of level
	 * Creates the platforms leading up to door
	 */
	@Override
	public void create() {
		super.createLevel(700, 100, 1, 50, 10, texture);
		isCreated = true;

		db.createDoor(698, 97, 17, 1.5f, BodyFactory.STONE, "backToHome", LevelDestination.OVERWORLD);

		platforms[0] = bodyFactory.makeBoxPolyBody(694, 93, 2, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);
		platforms[1] = bodyFactory.makeBoxPolyBody(698, 95, 3, 1, BodyFactory.ICE, BodyType.StaticBody, false, false, texture);

		//Creates NPCs
		NPC npc = new NPC();
		Map.getInstance().getEntityHandler().getPooledEngine().addEntity(npc.spawnNPC(new String[] {"Congratulations! You are the first person to ever clear The Cave", "Thanks to you I am no longer trapped here, I never had any doubt in you my friend."
		}, 685, 92, Utilities.rightTextures.findRegion("squirrelMan"), false));

		Map.getInstance().getEntityHandler().getPooledEngine().addEntity(npc.spawnNPC(new String[] {"Thanks to you, the Slime Kingdom has been saved and we can now return to our homeland", "Let us leave together, after you my frie-", "No...my Hero."
		}, 689, 92, Utilities.otherTexturesAtlas.findRegion("slimeKing"), false));


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
