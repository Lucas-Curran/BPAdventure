package com.mygdx.game.entities;

import java.util.ArrayList;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
	
	public Entity createEnemy(int posx, int posy, EnemyState enemyType, int range, float radius, TextureRegion entityTexture) {
		
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
		texture.region = entityTexture;
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
	
public Entity createEnemyShooter(float posx, float posy, int range, float radius, int bulletXDirection, int bulletYDirection, int bulletRange,  TextureRegion entityTexture) {
		
		// Create the Entity and all the components that will go in the entity
		Entity entity = pooledEngine.createEntity();
		B2dBodyComponent b2dbody = pooledEngine.createComponent(B2dBodyComponent.class);
		TransformComponent position = pooledEngine.createComponent(TransformComponent.class);
		TextureComponent texture = pooledEngine.createComponent(TextureComponent.class);
		TypeComponent type = pooledEngine.createComponent(TypeComponent.class);
		PlayerComponent player = pooledEngine.createComponent(PlayerComponent.class);
		EnemyComponent enemy = pooledEngine.createComponent(EnemyComponent.class);
		CollisionComponent colComp = pooledEngine.createComponent(CollisionComponent.class);

		// create the data for the components and add them to the components
		b2dbody.body = bodyFactory.makeCirclePolyBody(posx, posy, radius, BodyFactory.OTHER, BodyType.DynamicBody, true, false);
		// set object position (x,y,z) z used to define draw order 0 first drawn
		position.position.set(b2dbody.body.getPosition().x, b2dbody.body.getPosition().y, 0);
		position.scale.set(radius, radius);
		texture.region = entityTexture;
		type.type = TypeComponent.ENEMY;
		player.player = false;
		b2dbody.body.setUserData(entity);
		enemy.xPostCenter = b2dbody.body.getPosition().x;
		enemy.yPostCenter = b2dbody.body.getPosition().y;
		enemy.bulletXDirection = bulletXDirection;
		enemy.bulletYDirection = bulletYDirection;
		enemy.bulletRange = bulletRange;
		enemy.range = range;
		enemy.enemyMode = EnemyState.SHOOTER;
		
		b2dbody.body.setGravityScale(0.8f);
		b2dbody.body.setLinearDamping(0.3f);
		b2dbody.body.setUserData(entity);
		
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
	
	public ArrayList<Entity> getLevelTwo() {	
		enemies.clear();
		createEnemy(15, 92, EnemyState.PATROL, 1, 1f, rockMob);
		createEnemy(25, 93, EnemyState.PATROL, 1, 2f, spikyRockMob);
		createEnemy(25, 95, EnemyState.BOUNCE, 1, 1f, rockMob);
		return enemies;
	}
	
	public ArrayList<Entity> getLevelThree() {	
		enemies.clear();
//		createEnemy(15, 92, EnemyState.PATROL, 1, 1f);
//		createEnemy(25, 92, EnemyState.PATROL, 1, 1.3f);
//		createEnemy(25, 95, EnemyState.BOUNCE, 1, 1f);
		return enemies;
	}
	
	public ArrayList<Entity> getLevelFour() {	
		enemies.clear();
//		createEnemy(15, 92, EnemyState.PATROL, 1, 1f);
//		createEnemy(25, 92, EnemyState.PATROL, 1, 1.3f);
//		createEnemy(25, 95, EnemyState.BOUNCE, 1, 1f);
		return enemies;
	}
	
	public ArrayList<Entity> getLevelFive() {	
		enemies.clear();
//		createEnemy(15, 92, EnemyState.PATROL, 1, 1f);
//		createEnemy(25, 92, EnemyState.PATROL, 1, 1.3f);
//		createEnemy(25, 95, EnemyState.BOUNCE, 1, 1f);
		return enemies;
	}
	
	public ArrayList<Entity> getLevelSix() {	
		enemies.clear();
//		createEnemy(15, 92, EnemyState.PATROL, 1, 1f);
//		createEnemy(25, 92, EnemyState.PATROL, 1, 1.3f);
//		createEnemy(25, 95, EnemyState.BOUNCE, 1, 1f);
		return enemies;
	}
	
	public ArrayList<Entity> getLevelSeven() {
		enemies.clear();
		createEnemy(-32, 590, EnemyState.PATROL, 2, 1f, slimyMob);
		createEnemyShooter(2, 593, 1, 1f, -2, 0, 7, spikySlime);
		createEnemy(10, 595, EnemyState.PATROL, 5, 1f, slimyMob);
		return enemies;
	}
	
	public ArrayList<Entity> getLevelEight() {	
		enemies.clear();
//		createEnemy(15, 92, EnemyState.PATROL, 1, 1f);
//		createEnemy(25, 92, EnemyState.PATROL, 1, 1.3f);
//		createEnemy(25, 95, EnemyState.BOUNCE, 1, 1f);
		return enemies;
	}
	
	public ArrayList<Entity> getLevelNine() {	
		enemies.clear();
//		createEnemy(15, 92, EnemyState.PATROL, 1, 1f);
//		createEnemy(25, 92, EnemyState.PATROL, 1, 1.3f);
//		createEnemy(25, 95, EnemyState.BOUNCE, 1, 1f);
		return enemies;
	}
	
	public ArrayList<Entity> getLevelTen() {	
		enemies.clear();
//		createEnemy(15, 92, EnemyState.PATROL, 1, 1f);
//		createEnemy(25, 92, EnemyState.PATROL, 1, 1.3f);
//		createEnemy(25, 95, EnemyState.BOUNCE, 1, 1f);
		return enemies;
	}

	
	public Entity returnEnemy(int id) {
		return enemies.get(id);
	}
	
	
}
