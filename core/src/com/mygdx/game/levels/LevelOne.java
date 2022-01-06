package com.mygdx.game.levels;

import java.util.ArrayList;

import javax.swing.Box;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.BodyFactory.Level;
import com.mygdx.game.Camera;
import com.mygdx.game.GameWorld;
import com.mygdx.game.Map;
import com.mygdx.game.RoomFactory;
import com.mygdx.game.components.B2dBodyComponent;
import com.mygdx.game.components.TextureComponent;
import com.mygdx.game.components.TransformComponent;
import com.mygdx.game.components.TypeComponent;
import com.mygdx.game.entities.EntityHandler;
import com.mygdx.game.ui.ShopWindow;

public class LevelOne extends LevelFactory implements ApplicationListener {
	
	boolean isCreated;
	private Camera camera;
	float[] vertices;	
	Body door;	
	private ShopWindow shopWindow;
	
	@Override
	public void create() {
		super.createLevel();
		camera = new Camera();
		
		Texture texture = new Texture(Gdx.files.internal("newGround.png"));
		
		door = bodyFactory.makeBoxPolyBody(4, 20, 2, 2, BodyFactory.STEEL, BodyType.DynamicBody, Level.LEVELONE, false, false, texture);
		door.setUserData("Door");		
		bodyFactory.makeCirclePolyBody(1, 1, 2, BodyFactory.RUBBER, BodyType.StaticBody, false, false);
		bodyFactory.makeBoxPolyBody(10, 2, 5, 1, BodyFactory.STEEL, BodyType.StaticBody, Level.LEVELONE, true, false, texture);
		Map.getInstance().getEntityHandler().spawnLevelOne();
		Map.getInstance().getEntityHandler().spawnShopNPC();
		
		shopWindow = new ShopWindow(Map.getInstance().getEntityHandler().getNPC().getShopWares(), Map.getInstance().getEntityHandler().getNPC().getShopWares(), Map.getInstance().getMoney());
		
		isCreated = true;
	}
	

	@Override
	public void resize(int width, int height) {
		shopWindow.resize(width, height);
	}

	@Override
	public void render() {	
		shopWindow.render(Gdx.graphics.getDeltaTime());
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
