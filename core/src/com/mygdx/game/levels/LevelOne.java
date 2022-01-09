package com.mygdx.game.levels;

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
import com.mygdx.game.levels.Levels.LevelDestination;
import com.mygdx.game.ui.ShopWindow;

public class LevelOne extends LevelFactory implements ApplicationListener {
	//roomFactory.makeRectangleRoom(15, 9, 1, 100, 10);
	boolean isCreated;
	static boolean inLevelOne;
	
	float[] vertices;
	private ShopWindow shopWindow;
	
	private DoorBuilder db = DoorBuilder.getInstance();
	
	@Override
	public void create() {
		super.createLevel(15, 0, 1, 100, 10);
		inLevelOne = true;
		Texture texture = new Texture(Gdx.files.internal("newGround.png"));
		
		
		db.createDoor(15, 1.5f, -35, 588, BodyFactory.ICE, "DoorTo2", LevelDestination.LVL_7);

		
		bodyFactory.makeCirclePolyBody(1, 1, 2, BodyFactory.RUBBER, BodyType.StaticBody, false, false);
		bodyFactory.makeBoxPolyBody(10, 1, 5, 1, BodyFactory.STEEL, BodyType.StaticBody, true, false, texture);
		Map.getInstance().getEntityHandler().spawnLevelOne();
		Map.getInstance().getEntityHandler().spawnShopNPC();
		
		shopWindow = new ShopWindow(Map.getInstance().getEntityHandler().getNPC().getShopWares(), Map.getInstance().getEntityHandler().getNPC().getShopWares(), Map.getInstance().getMoney());

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
