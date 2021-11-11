package com.my.gdx.game.levels;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.GameWorld;
import com.mygdx.game.Map;
import com.mygdx.game.RoomFactory;

public class LevelOne extends LevelFactory implements ApplicationListener {
	
	@Override
	public void create() {
		super.createLevel();

		bodyFactory.makeBoxPolyBody(4, 4, 2, 2, BodyFactory.STEEL, BodyType.StaticBody, false);
		bodyFactory.makeCirclePolyBody(1, 1, 2, BodyFactory.RUBBER, BodyType.StaticBody, false);
		Map.getInstance().getEntityHandler().spawnLevelTwo();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
	
}
