package com.my.gdx.game.entities;

import java.util.ArrayList;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.B2dContactListener;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.Camera;
import com.mygdx.game.Engine;
import com.mygdx.game.GameWorld;
import com.mygdx.game.components.*;
import com.mygdx.game.systems.*;

public class EntityHandler implements ApplicationListener {

	Engine engine;
	PooledEngine pooledEngine;
	TextureRegion tex;
	BodyFactory bodyFactory;
	GameWorld gameWorld;
	RenderingSystem renderingSystem;
	SpriteBatch batch;
	Camera cam;
	TextureAtlas textureAtlas;
	
	private Player player;
	private Enemy enemies;
	
	public boolean loadingZone;
	
	
	public EntityHandler() {
		engine = new Engine();
		gameWorld = new GameWorld();
		bodyFactory = BodyFactory.getInstance(gameWorld.getInstance());
		pooledEngine = engine.getInstance();
		cam = new Camera();
		textureAtlas = new TextureAtlas("atlasAdv.txt");
		tex = new TextureRegion(textureAtlas.findRegion("IceCharacter"));
		gameWorld.getInstance().setContactListener(new B2dContactListener(this));
		
		batch = new SpriteBatch();
		
		renderingSystem = new RenderingSystem(batch);
		
		batch.setProjectionMatrix(cam.getCombined());
		
		pooledEngine.addSystem(renderingSystem);
		pooledEngine.addSystem(new AnimationSystem());
		pooledEngine.addSystem(new PhysicsSystem(gameWorld.getInstance()));
		pooledEngine.addSystem(new PhysicsDebugSystem(gameWorld.getInstance(), cam.getCamera()));
		pooledEngine.addSystem(new CollisionSystem());
		pooledEngine.addSystem(new PlayerControlSystem());
		
		loadingZone = false;
		
	}
	
	@Override
	public void create() {
		player = new Player();
		enemies = new Enemy();
		pooledEngine.addEntity(player.createPlayer(cam.getCamera().position.x, cam.getCamera().position.y));
	}

	@Override
	public void resize(int width, int height) {
		cam.resize(width, height);
	}

	@Override
	public void render() {
		pooledEngine.update(1/20f);
		updateCamera();
		updateEntities();
		checkLoadingZone();
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
		cam.dispose();
		batch.dispose();
	}
	
	private void updateCamera() {
		cam.getCamera().position.set(new Vector3(player.getX(), player.getY(), 0));
		cam.getCamera().update();
	}
	
	private void updateEntities() {
		for (Entity entity : pooledEngine.getEntities()) {
			entity.getComponent(TransformComponent.class).position.set(
					entity.getComponent(B2dBodyComponent.class).body.getPosition().x - cam.getCamera().position.x, 
					entity.getComponent(B2dBodyComponent.class).body.getPosition().y - cam.getCamera().position.y,
					0);
		}
	}		
	
	public void spawnLevelOne() {
		for (Entity enemy : enemies.getLevelOne()) {
			pooledEngine.addEntity(enemy);
		}
	}
	
	public void spawnLevelTwo() {
		for (Entity enemy : enemies.getLevelTwo()) {
			pooledEngine.addEntity(enemy);
		}
	}
	
	public void checkLoadingZone() {
		if (loadingZone) {
			player.fadePlayer(20f, 2.7f);
		}
	}
	
}
