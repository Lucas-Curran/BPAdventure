package com.mygdx.game.levels;

import com.badlogic.gdx.ApplicationListener;
import com.mygdx.game.Map;
import com.mygdx.game.entities.Player;

public class LevelTwo extends LevelFactory implements ApplicationListener{

	
	@Override
	public void create() {
		super.createLevel(100, 9, 1, 100, 10);
		
		//super.createLevel(x, y, width, height, npcX);
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void render() {
		//render map
		//Map.getInstance().getEntityHandler().getBatch().draw();
		
	}

	@Override
	public void dispose() {
	
	}

	
}
