package com.mygdx.game.entities;

import java.util.ArrayList;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.Utilities;
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
	
	private static ArrayList<Entity> enemies;
	
	TextureRegion rockMob = Utilities.levelTwoAtlas.findRegion("RockMobEnemy");
	TextureRegion spikyRockMob = Utilities.levelTwoAtlas.findRegion("SpikyRockEnemy");
	TextureRegion spikySlime = Utilities.levelSevenAtlas.findRegion("spikySlime");
	TextureRegion slimyMob = Utilities.levelSevenAtlas.findRegion("slimyMob");
	
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
	

public Entity createEnemyShooter(float posx, float posy, int range, float radius, int bulletXDirection, int bulletYDirection, int bulletRange, int time, TextureRegion entityTexture) {
		
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
		enemy.timer = time;
		
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

public ArrayList<Entity> getOverworld() {	
	enemies.clear();
	
	return enemies;
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
		createEnemy(12, 258, EnemyState.PATROL, 1, 1f, Utilities.iceMonster);
		createEnemy(0, 266, EnemyState.PATROL, 1, 1.3f, Utilities.iceMonster);
		createEnemy(2, 266, EnemyState.BOUNCE, 1, 1f, Utilities.iceBird);
		createEnemy(19, 275, EnemyState.PATROL, 1, 1f, Utilities.iceMonster);
		
		createEnemy(-14, 287, EnemyState.PATROL, 1, 1f, Utilities.iceMonster);
		createEnemy(-16, 288, EnemyState.BOUNCE, 1, 3f, Utilities.iceBird);
		createEnemyShooter(-16f, 287f, 1, 1f, 2, 0, 7, 7, Utilities.iceMonster);
		return enemies;
	}
	
	public ArrayList<Entity> getLevelFive() {	
		enemies.clear();
		createEnemy(-10, 383, EnemyState.BOUNCE, 1, 1.4f, Utilities.flyingEye);
		createEnemy(-9, 382, EnemyState.PATROL, 1, 1f, Utilities.mummyEnemy); //mummy
		
		createEnemy(41, 382, EnemyState.STEERING, 1, 1.3f, Utilities.flyingEye);
		createEnemy(41, 382, EnemyState.STEERING, 1, 1.3f, Utilities.flyingEye);
//		createEnemyShooter(42, 382, 1, 1f, -2, 0, 7, 7, Utilities.tex);
		createEnemyShooter(42, 382, 1, 4f, -2, 0, 7, 7, Utilities.eyeBoss);
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
//		createEnemy(-32, 590, EnemyState.PATROL, 2, 1f, slimyMob);
//		createEnemyShooter(1, 593, 1, 1f, -2, 0, 7, 10, spikySlime);
//		createEnemy(11, 595, EnemyState.PATROL, 5, 1f, slimyMob);
//		createEnemyShooter(52, 595.5f, 1, 1f, -2, 0, 7, 10, spikySlime);
//		createEnemyShooter(52, 598.5f, 1, 1f, -2, 0, 7, 10, spikySlime);
//		createEnemyShooter(52, 592.5f, 1, 1f, -2, 0, 7, 10, spikySlime);
//		createEnemyShooter(52, 589.5f, 1, 1f, -2, 0, 7, 10, spikySlime);
//		createEnemyShooter(2, 593, 1, 1f, -2, 0, 7, 10, spikySlime);
//		createEnemyShooter(2, 593, 1, 1f, -2, 0, 7, 10, spikySlime);
//		createEnemyShooter(2, 593, 1, 1f, -2, 0, 7, 10, spikySlime);
//		createEnemyShooter(2, 593, 1, 1f, -2, 0, 7, 10, spikySlime);

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
	
	public ArrayList<Entity> getIceDungeon() {
		enemies.clear();
		createEnemy(495, 92, EnemyState.PATROL, 1, 1f, Utilities.spikyRockMob);
		createEnemy(494, 93, EnemyState.BOUNCE, 1, 1f, Utilities.rockMob);
		createEnemy(496, 92, EnemyState.BOUNCE, 1, 1f, Utilities.rockMob);
		createEnemy(505, 93, EnemyState.PATROL, 1, 3f, Utilities.iceMonster);
		return enemies;
	}

	
	public Entity returnEnemy(int id) {
		return enemies.get(id);
	}
	
	public ArrayList<Entity> geEnemies(){
		return enemies;
	}
	
}
