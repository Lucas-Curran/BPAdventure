package com.mygdx.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.components.*;
import com.mygdx.game.systems.*;


public class MyGdxGame extends ApplicationAdapter {
	
	Character character;
	Camera cam;
	World world;
	BodyFactory bodyFactory;
	BodyDef boxBodyDef;
	Body body;
	CircleShape circleShape;
	Box2DDebugRenderer debugRenderer;
	SpriteBatch batch;
	RenderingSystem renderingSystem;
	PooledEngine engine;
	
	@Override
	public void create () {

		character = new Character();
		cam = new Camera();
		world = new World(new Vector2(0, -50f), true);
		//world.setContactListener(new B2dContactListener());
		debugRenderer = new Box2DDebugRenderer();
		bodyFactory = BodyFactory.getInstance(world);
		
		bodyFactory.makeCirclePolyBody(90, 100, 100, BodyFactory.RUBBER, BodyType.DynamicBody, false);
		bodyFactory.makeBoxPolyBody(110, 100, 50, 50, BodyFactory.STEEL, BodyType.DynamicBody, false);
		
		batch = new SpriteBatch();
		
		renderingSystem = new RenderingSystem(batch);
		
		batch.setProjectionMatrix(cam.getCombined());
		
		engine = new PooledEngine();
		engine.addSystem(renderingSystem);
		engine.addSystem(new AnimationSystem());
		engine.addSystem(new PhysicsSystem(world));
		engine.addSystem(new PhysicsDebugSystem(world, cam.getCamera()));
		engine.addSystem(new CollisionSystem());
		engine.addSystem(new PlayerControlSystem());
		
		createPlayer();
		
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		//bodyFactory.makeCirclePolyBody(10, 10, 2, BodyFactory.RUBBER, BodyType.DynamicBody, false);
		character.render();
		
		engine.update(1/60f);
		
	}
	
	@Override
	public void dispose () {
		cam.dispose();
		character.dispose();
	}
	
	@Override
	public void resize(int width, int height) {
		cam.resize(width, height);
		character.setProjection(cam.getCombined());
	}

	private void createPlayer(){

		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.type = BodyDef.BodyType.StaticBody;
		groundBodyDef.position.set(new Vector2(85f, 0.0f));
		Body groundBody = world.createBody(groundBodyDef);
		PolygonShape groundShape = new PolygonShape();
		groundShape.setAsBox(150f, 2f);
		groundBody.createFixture(groundShape, 0.5f);
		groundShape.dispose();
		
		
		// Create the Entity and all the components that will go in the entity
		Entity entity = engine.createEntity();
		B2dBodyComponent b2dbody = engine.createComponent(B2dBodyComponent.class);
		TransformComponent position = engine.createComponent(TransformComponent.class);
		TextureComponent texture = engine.createComponent(TextureComponent.class);
		PlayerComponent player = engine.createComponent(PlayerComponent.class);
		CollisionComponent colComp = engine.createComponent(CollisionComponent.class);
		TypeComponent type = engine.createComponent(TypeComponent.class);
		StateComponent stateCom = engine.createComponent(StateComponent.class);

		// create the data for the components and add them to the components
		b2dbody.body = bodyFactory.makeCirclePolyBody(10,10,10, BodyFactory.STONE, BodyType.DynamicBody,true);
		// set object position (x,y,z) z used to define draw order 0 first drawn
		position.position.set(10,10,0);
		//texture.region = atlas.findRegion("player");
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
		engine.addEntity(entity);

	}
	
}
