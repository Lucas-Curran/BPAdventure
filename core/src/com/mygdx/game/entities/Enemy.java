package com.mygdx.game.entities;

import java.util.ArrayList;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.components.B2dBodyComponent;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.PlayerComponent;
import com.mygdx.game.components.TextureComponent;
import com.mygdx.game.components.TransformComponent;
import com.mygdx.game.components.TypeComponent;

public class Enemy extends EntityHandler {
	
	private ArrayList<Entity> enemies;
	
	public Enemy() {
		enemies = new ArrayList<Entity>();
	}
	
	private void createEnemy(int posx, int posy) {
		
		// Create the Entity and all the components that will go in the entity
		Entity entity = pooledEngine.createEntity();
		B2dBodyComponent b2dbody = pooledEngine.createComponent(B2dBodyComponent.class);
		TransformComponent position = pooledEngine.createComponent(TransformComponent.class);
		TextureComponent texture = pooledEngine.createComponent(TextureComponent.class);
		TypeComponent type = pooledEngine.createComponent(TypeComponent.class);
		CollisionComponent colComp = pooledEngine.createComponent(CollisionComponent.class);
		
		// create the data for the components and add them to the components
		b2dbody.body = bodyFactory.makeCirclePolyBody(posx, posy, 1, BodyFactory.OTHER, BodyType.KinematicBody,true, false);
		// set object position (x,y,z) z used to define draw order 0 first drawn
		position.position.set(b2dbody.body.getPosition().x, b2dbody.body.getPosition().y, 0);
		texture.region = tex;
		type.type = TypeComponent.ENEMY;
		b2dbody.body.setUserData(entity);
		
		// add the components to the entity
		entity.add(b2dbody);
		entity.add(position);
		entity.add(type);
		entity.add(texture);
		entity.add(colComp);

		enemies.add(entity);
	}
	
	public ArrayList<Entity> getLevelOne() {
		enemies.clear();
		createEnemy(5, 5);
		createEnemy(10, 5);
		return enemies;
	}
	
	public ArrayList<Entity> getLevelTwo() {	
		enemies.clear();
		createEnemy(0, 5);
		createEnemy(-5, 10);
		return enemies;
	}
	
}
