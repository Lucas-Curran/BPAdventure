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

	B2dBodyComponent b2dbody;
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
		
	
	}

	@Override
	public void resize(int width, int height) {
		cam.resize(width, height);
	}

	@Override
	public void render() {
		cam.getCamera().position.set(new Vector3(b2dbody.body.getPosition().x, b2dbody.body.getPosition().y, 0));
		cam.getCamera().update();
		pooledEngine.update(1/20f);
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

		BodyDef groundBodyDef = new BodyDef();	
		groundBodyDef.type = BodyDef.BodyType.StaticBody;
		groundBodyDef.position.set(new Vector2(10f, 0.0f));
		
		Body groundBody = gameWorld.getInstance().createBody(groundBodyDef);
		
		PolygonShape groundShape = new PolygonShape();
		FixtureDef fix = new FixtureDef();
		fix.restitution = 0f;
		fix.friction = 1f;
		fix.shape = groundShape;
		fix.density = 1f;
		
		
		groundShape.setAsBox(10f, 1f);
		groundBody.createFixture(fix);
		groundShape.dispose();
		
		
		// Create the Entity and all the components that will go in the entity
		Entity entity = pooledEngine.createEntity();
		b2dbody = pooledEngine.createComponent(B2dBodyComponent.class);
		TransformComponent position = pooledEngine.createComponent(TransformComponent.class);
		TextureComponent texture = pooledEngine.createComponent(TextureComponent.class);
		PlayerComponent player = pooledEngine.createComponent(PlayerComponent.class);
		CollisionComponent colComp = pooledEngine.createComponent(CollisionComponent.class);
		TypeComponent type = pooledEngine.createComponent(TypeComponent.class);
		StateComponent stateCom = pooledEngine.createComponent(StateComponent.class);

		// create the data for the components and add them to the components
		b2dbody.body = bodyFactory.makeCirclePolyBody(10, 6, 1, BodyFactory.OTHER, BodyType.DynamicBody,true);
		// set object position (x,y,z) z used to define draw order 0 first drawn
		position.position.set(cam.getCamera().position.x, cam.getCamera().position.y, 0);
		texture.region = tex;
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
	
	public float getPositionX() {
		return b2dbody.body.getPosition().x;
	}
	
	public float getPositionY() {
		return b2dbody.body.getPosition().y;
	}
	
}
