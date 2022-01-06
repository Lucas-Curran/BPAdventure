package com.mygdx.game.entities;

import java.util.ArrayList;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.components.B2dBodyComponent;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.EnemyComponent;
import com.mygdx.game.components.EnemyComponent.EnemyState;
import com.mygdx.game.components.PlayerComponent;
import com.mygdx.game.components.SteeringComponent;
import com.mygdx.game.components.TextureComponent;
import com.mygdx.game.components.TransformComponent;
import com.mygdx.game.components.TypeComponent;
import com.mygdx.game.systems.SteeringPresets;

public class Enemy extends EntityHandler {
	
	private ArrayList<Entity> enemies;
	
	public Enemy() {
		enemies = new ArrayList<Entity>();
	}
	
	public Entity createEnemy(int posx, int posy, EnemyState enemyType, int range, float radius) {
		
		// Create the Entity and all the components that will go in the entity
		Entity entity = pooledEngine.createEntity();
		B2dBodyComponent b2dbody = pooledEngine.createComponent(B2dBodyComponent.class);
		TransformComponent position = pooledEngine.createComponent(TransformComponent.class);
		TextureComponent texture = pooledEngine.createComponent(TextureComponent.class);
		TypeComponent type = pooledEngine.createComponent(TypeComponent.class);
		PlayerComponent player = pooledEngine.createComponent(PlayerComponent.class);
		EnemyComponent enemy = pooledEngine.createComponent(EnemyComponent.class);
		SteeringComponent steering = pooledEngine.createComponent(SteeringComponent.class);
		CollisionComponent colComp = pooledEngine.createComponent(CollisionComponent.class);

		// create the data for the components and add them to the components
		BodyType bodyType = enemyType.getValue() <= 2 ? BodyType.KinematicBody : BodyType.DynamicBody;
		b2dbody.body = bodyFactory.makeCirclePolyBody(posx, posy, radius, BodyFactory.OTHER, bodyType, true, false);
		// set object position (x,y,z) z used to define draw order 0 first drawn
		position.position.set(b2dbody.body.getPosition().x, b2dbody.body.getPosition().y, 0);
		position.scale.set(radius, radius);
		texture.region = tex;
		type.type = TypeComponent.ENEMY;
		player.player = false;
		b2dbody.body.setUserData(entity);
		enemy.xPostCenter = b2dbody.body.getPosition().x;
		enemy.yPostCenter = b2dbody.body.getPosition().y;
		enemy.range = range;
		enemy.enemyMode = enemyType;
		
		b2dbody.body.setGravityScale(0.8f);
		b2dbody.body.setLinearDamping(0.3f);
		b2dbody.body.setUserData(entity);
		if (enemyType == EnemyState.STEERING) {
			b2dbody.body.setGravityScale(0f);
			//Set steering behavior
			steering.body = b2dbody.body;
			steering.steeringBehavior = SteeringPresets.getWander(steering);
			steering.currentMode = SteeringComponent.SteeringState.WANDER;
			entity.add(steering);

		}
		
		// add the components to the entity
		entity.add(b2dbody);
		entity.add(position);
		entity.add(type);
		entity.add(texture);
		entity.add(enemy);
		entity.add(colComp);

		enemies.add(entity);
		return entity;
	}
	
	public ArrayList<Entity> getLevelOne() {
		enemies.clear();
		createEnemy(5, 5, EnemyState.PATROL, 1, 1f);
		createEnemy(-10, 5, EnemyState.VERTICAL, 1, 1f);
		createEnemy(20, 5, EnemyState.BOUNCE, 1, 1f);
		createEnemy(8, 3, EnemyState.JUMP, 2, 1f);
		createEnemy(25, 4, EnemyState.STEERING, 0, 1f);
		createEnemy (30, 4, EnemyState.BOSS, 0, 2f);
		return enemies;
	}
	
	public ArrayList<Entity> getLevelTwo() {	
		enemies.clear();
		createEnemy(15, 92, EnemyState.PATROL, 1, 1f);
		createEnemy(25, 94, EnemyState.PATROL, 1, 2f);
		createEnemy(25, 95, EnemyState.BOUNCE, 1, 1f);
		return enemies;
	}
	
	public Entity returnEnemy(int id) {
		return enemies.get(id);
	}
	
}
