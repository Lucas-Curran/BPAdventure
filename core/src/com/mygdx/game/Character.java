package com.mygdx.game;

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
import com.mygdx.game.components.*;
import com.mygdx.game.systems.*;

public class Character implements ApplicationListener {

	Engine engine;
	PooledEngine pooledEngine;
	TextureRegion tex;
	BodyFactory bodyFactory;
	GameWorld gameWorld;
	RenderingSystem renderingSystem;
	SpriteBatch batch;
	Camera cam;
	TextureAtlas textureAtlas;
	
	public Character() {
		create();
	}
	
	@Override
	public void create() {
		engine = new Engine();
		gameWorld = new GameWorld();
		bodyFactory = BodyFactory.getInstance(gameWorld.getInstance());
		pooledEngine = engine.getInstance();
		cam = new Camera();
		textureAtlas = new TextureAtlas("atlasAdv.txt");
		tex = new TextureRegion(textureAtlas.findRegion("IceCharacter"));
		gameWorld.getInstance().setContactListener(new B2dContactListener());
		
		batch = new SpriteBatch();
		
		renderingSystem = new RenderingSystem(batch);
		
		batch.setProjectionMatrix(cam.getCombined());
		
		pooledEngine.addSystem(renderingSystem);
		pooledEngine.addSystem(new AnimationSystem());
		pooledEngine.addSystem(new PhysicsSystem(gameWorld.getInstance()));
		pooledEngine.addSystem(new PhysicsDebugSystem(gameWorld.getInstance(), cam.getCamera()));
		pooledEngine.addSystem(new CollisionSystem());
		pooledEngine.addSystem(new PlayerControlSystem());
	
		createPlayer();
		createEnemy();
		
	}

	@Override
	public void resize(int width, int height) {
		cam.resize(width, height);
	}

	@Override
	public void render() {
		pooledEngine.update(1/20f);
		cam.getCamera().position.set(new Vector3(getPositionX(), getPositionY(), 0));
		cam.getCamera().update();
//		for (Entity entity : pooledEngine.getEntities()) {
//			entity.getComponent(TransformComponent.class).position.set(getPositionX(), getPositionY(), 0);
//		}
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
	
	private void createPlayer(){
		
		// Create the Entity and all the components that will go in the entity
		Entity entity = pooledEngine.createEntity();
		B2dBodyComponent b2dbody = pooledEngine.createComponent(B2dBodyComponent.class);
		TransformComponent position = pooledEngine.createComponent(TransformComponent.class);
		TextureComponent texture = pooledEngine.createComponent(TextureComponent.class);
		PlayerComponent player = pooledEngine.createComponent(PlayerComponent.class);
		CollisionComponent colComp = pooledEngine.createComponent(CollisionComponent.class);
		TypeComponent type = pooledEngine.createComponent(TypeComponent.class);
		StateComponent stateCom = pooledEngine.createComponent(StateComponent.class);

		// create the data for the components and add them to the components
		b2dbody.body = bodyFactory.makeCirclePolyBody(cam.getCamera().position.x, cam.getCamera().position.y, 1, BodyFactory.OTHER, BodyType.DynamicBody,true);
		// set object position (x,y,z) z used to define draw order 0 first drawn
		position.position.set(b2dbody.body.getPosition().x, b2dbody.body.getPosition().x, 0);
		texture.region = tex;
		player.player = true;
		type.type = TypeComponent.PLAYER;
		stateCom.set(StateComponent.STATE_NORMAL);
		b2dbody.body.setUserData(entity);
		
		// add the components to the entity
		entity.add(b2dbody);
		entity.add(position);
		entity.add(texture);
		entity.add(player);
		entity.add(colComp);
		entity.add(type);
		entity.add(stateCom);

		// add the entity to the engine	
		pooledEngine.addEntity(entity);
		
	}
	
	private void createEnemy(){
		
		// Create the Entity and all the components that will go in the entity
		Entity entity = pooledEngine.createEntity();
		B2dBodyComponent b2dbody = pooledEngine.createComponent(B2dBodyComponent.class);
		TransformComponent position = pooledEngine.createComponent(TransformComponent.class);
		TextureComponent texture = pooledEngine.createComponent(TextureComponent.class);
		TypeComponent type = pooledEngine.createComponent(TypeComponent.class);
		PlayerComponent player = pooledEngine.createComponent(PlayerComponent.class);

		// create the data for the components and add them to the components
		b2dbody.body = bodyFactory.makeCirclePolyBody(cam.getCamera().position.x, cam.getCamera().position.y, 1, BodyFactory.OTHER, BodyType.DynamicBody,true);
		// set object position (x,y,z) z used to define draw order 0 first drawn
		position.position.set(b2dbody.body.getPosition().x, b2dbody.body.getPosition().y, 0);
		texture.region = tex;
		player.player = false;
		type.type = TypeComponent.ENEMY;
		b2dbody.body.setUserData(entity);
		
		// add the components to the entity
		entity.add(b2dbody);
		entity.add(position);
		entity.add(type);
		entity.add(player);
		entity.add(texture);

		// add the entity to the engine	
		pooledEngine.addEntity(entity);
		
	}
	
	
	
	public float getPositionX() {
		return pooledEngine.getEntities().get(0).getComponent(B2dBodyComponent.class).body.getPosition().x;
	}
	
	public float getPositionY() {
		return pooledEngine.getEntities().get(0).getComponent(B2dBodyComponent.class).body.getPosition().y;
	}
	
}
