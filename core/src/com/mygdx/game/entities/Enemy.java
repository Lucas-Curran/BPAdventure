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
	private TextureRegion boss_8 = Utilities.lvl89Atlas.findRegion("7_boss");
	private TextureRegion rocketMob = Utilities.lvl89Atlas.findRegion("rocketMob");
	private TextureRegion spiderMob = Utilities.lvl89Atlas.findRegion("spider");
	private TextureRegion jumpingMob = Utilities.lvl89Atlas.findRegion("vertical");
	
	private TextureRegion boss_9 = Utilities.lvl89Atlas.findRegion("boss_enemy");
	private TextureRegion vertical_9 = Utilities.lvl89Atlas.findRegion("jumpingMob");
	private TextureRegion patrolMob = Utilities.lvl89Atlas.findRegion("patrol_mob");
	private TextureRegion spikeBouncer = Utilities.lvl89Atlas.findRegion("spikeBouncer");
	private TextureRegion steeringMob = Utilities.lvl89Atlas.findRegion("steeringMob");

	
	
	
	
	public B2dBodyComponent b2dbody;
	
	public Enemy() {
		enemies = new ArrayList<Entity>();
	}
	
	
	public Entity createEnemy(int posx, int posy, EnemyState enemyType, int range, float radius, TextureRegion entityTexture, int hp) {
		
		// Create the Entity and all the components that will go in the entity
		Entity entity = pooledEngine.createEntity();
		b2dbody = pooledEngine.createComponent(B2dBodyComponent.class);
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
		enemy.health = hp;
		
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
	

public Entity createEnemyShooter(float posx, float posy, int range, float radius, int bulletXDirection, int bulletYDirection, int bulletRange, int time, TextureRegion entityTexture, boolean random, int hp) {
		
		// Create the Entity and all the components that will go in the entity
		Entity entity = pooledEngine.createEntity();
		b2dbody = pooledEngine.createComponent(B2dBodyComponent.class);
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
		enemy.random = random;
		enemy.bulletXDirection = bulletXDirection;
		enemy.bulletYDirection = bulletYDirection;
		enemy.bulletRange = bulletRange;
		enemy.range = range;
		enemy.enemyMode = EnemyState.SHOOTER;
		enemy.timer = time;
		enemy.health = hp;
		
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
		createEnemy(15, 92, EnemyState.STEERING, 8, 1.3f, Utilities.rockMob, 1);

		createEnemy(15, 92, EnemyState.PATROL, 1, 1f, rockMob, 3);
		createEnemy(25, 93, EnemyState.PATROL, 1, 2f, spikyRockMob, 3);
		createEnemy(25, 95, EnemyState.BOUNCE, 1, 1f, rockMob, 3);
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
		createEnemy(12, 258, EnemyState.PATROL, 1, 1f, Utilities.iceMonster, 3);
		createEnemy(0, 266, EnemyState.PATROL, 1, 1.3f, Utilities.iceMonster, 3);
		createEnemy(2, 266, EnemyState.BOUNCE, 1, 1f, Utilities.iceBird, 3);
		createEnemy(19, 275, EnemyState.PATROL, 1, 1f, Utilities.iceMonster, 3);
		
		createEnemy(-14, 287, EnemyState.PATROL, 1, 1f, Utilities.iceMonster, 3);
		createEnemy(-16, 288, EnemyState.BOUNCE, 1, 3f, Utilities.iceBird, 3);
		createEnemyShooter(-16f, 287f, 1, 1f, 2, 0, 7, 7, Utilities.iceMonster,false, 5);
		return enemies;
	}
	
	public ArrayList<Entity> getLevelFive() {	
		enemies.clear();
		createEnemy(-10, 383, EnemyState.BOUNCE, 1, 1.4f, Utilities.flyingEye, 3);
		createEnemy(-9, 382, EnemyState.PATROL, 1, 1f, Utilities.mummyEnemy, 3); 
		
		createEnemy(41, 382, EnemyState.STEERING, 8, 1.3f, Utilities.flyingEye, 1);
		createEnemy(41, 382, EnemyState.STEERING, 8, 1.3f, Utilities.flyingEye, 1);
		createEnemy(40, 382, EnemyState.STEERING, 8, 1.3f, Utilities.flyingEye, 1);
		createEnemyShooter(42, 382, 1, 2f, -2, 0, 7, 7, Utilities.eyeBoss, false, 5);
		return enemies;
	}
	
	public ArrayList<Entity> getLevelSix() {	
		enemies.clear();
		createEnemy(14, 463, EnemyState.PATROL, 0, 4f, Utilities.slimeKing, 3);
		
		createEnemy(43, 466, EnemyState.STEERING, 1, 1.3f, Utilities.jungleDragon, 1);
		createEnemy(45, 466, EnemyState.STEERING, 1, 1.7f, Utilities.jungleDragon, 1);
	
		createEnemy(35, 477, EnemyState.STEERING, 1, 1f, Utilities.jungleDragon, 1);
		createEnemy(36, 477, EnemyState.STEERING, 1, 1f, Utilities.jungleDragon, 1);
		createEnemy(34, 477, EnemyState.STEERING, 1, 1f, Utilities.jungleDragon, 1);
		createEnemy(34, 479, EnemyState.BOUNCE, 1, 3f, Utilities.slimeKing, 7);
		return enemies;
	}
	
	public ArrayList<Entity> getLevelSeven() {
		enemies.clear();
		createEnemy(-32, 590, EnemyState.PATROL, 2, 1f, slimyMob, 3);
		createEnemyShooter(1, 593, 1, 1f, -2, 0, 7, 10, spikySlime, false, 5);
		createEnemy(11, 595, EnemyState.PATROL, 5, 1f, slimyMob, 3);
		
		
//		createEnemyShooter(52, 595.5f, 1, 1f, -2, 0, 7, 10, spikySlime);
		
//		createEnemyShooter(52, 589.5f, 1, 1f, -2, 0, 7, 10, spikySlime);
//		createEnemyShooter(2, 593, 1, 1f, -2, 0, 7, 10, spikySlime);
//		createEnemyShooter(2, 593, 1, 1f, -2, 0, 7, 10, spikySlime);
//		createEnemyShooter(2, 593, 1, 1f, -2, 0, 7, 10, spikySlime);
//		createEnemyShooter(2, 593, 1, 1f, -2, 0, 7, 10, spikySlime);

		return enemies;
	}
	
	public ArrayList<Entity> getLevelEight() {	
		enemies.clear();
		createEnemy(-16, 686, EnemyState.STEERING, 8, 1.3f, steeringMob, 1);
		createEnemy(-24, 686, EnemyState.STEERING, 8, 1.3f, steeringMob, 1);
		createEnemy(0, 686, EnemyState.VERTICAL, 3, 2f, spiderMob, 3);
		createEnemyShooter(7, 686, 1, 2f, -2, 5, 8, 4, spikySlime, true, 3);
		createEnemy(24, 686, EnemyState.STEERING, 3, 1, steeringMob, 1);
		createEnemy(28, 686, EnemyState.BOUNCE, 5, 2, spikeBouncer, 3);
		createEnemy(35, 686, EnemyState.BOSS, 10, 2, boss_8, 10);
		return enemies;
	}
	// shooting parameters - x, y, range, radius, bulletX, bulletY, bulletRange, time, texture, random?
	// regular parameters - x, y, state, range, size, texture
	
	public ArrayList<Entity> getLevelNine() {	
		enemies.clear();
		createEnemyShooter(-20, 797, 1, 1, -2, 0, 10, 8, spikySlime, false, 5);
		createEnemy(-10, 797, EnemyState.PATROL, 5, 1, patrolMob, 3);
		createEnemy(-10, 798, EnemyState.PATROL, 5, 1, patrolMob, 3);
		createEnemy(0, 797, EnemyState.STEERING, 3, 1, steeringMob, 1);
		createEnemy(0, 798, EnemyState.STEERING, 3, 1, steeringMob, 1);
		createEnemy(5, 797, EnemyState.VERTICAL, 1, 1, jumpingMob, 3);
//		createEnemyShooter(20, 797, 1, 1, -2, 0, 15, 8, spikySlime, false);
		createEnemy(25, 797, EnemyState.JUMP, 1, 1, slimyMob, 3);
		createEnemy(30, 797, EnemyState.JUMP, 1, 1, slimyMob, 3);
		
		createEnemy(50, 792, EnemyState.BOUNCE, 5, 1, spikeBouncer, 3);
		createEnemy(40, 792, EnemyState.BOUNCE, 5, 1, spikeBouncer, 3);
		createEnemyShooter(65, 792, 1, 1, -3, 0, 5, 8, spikySlime, true, 5);
		createEnemyShooter(55, 792, 1, 1, -3, 0, 5, 8, spikySlime, true, 5);
		createEnemy(45, 793, EnemyState.PATROL, 5, 1, patrolMob, 3);
		createEnemy(40, 792, EnemyState.PATROL, 5, 1, patrolMob, 3);
		createEnemy(35, 792, EnemyState.STEERING, 3, 1, steeringMob, 1);
		createEnemy(30, 792, EnemyState.JUMP, 1, 1, jumpingMob, 3);
		createEnemy(25, 792, EnemyState.VERTICAL, 2, 2, vertical_9,3 );
		createEnemy(10, 792, EnemyState.BOSS, 20, 2, boss_9, 7);
		createEnemy(0, 792, EnemyState.BOSS, 20, 2, boss_9, 7);
		createEnemy(-10, 792, EnemyState.JUMP, 1, 2, jumpingMob, 3);
		
		
		createEnemyShooter(-30, 787, 1, 1, -3, 0, 5, 8, spikySlime, true, 5);
		createEnemyShooter(-20, 787, 1, 1, -3, 0, 5, 8, spikySlime, true, 5);
		createEnemyShooter(-10, 787, 1, 1, -3, 0, 5, 8, spikySlime, true, 5);
		createEnemyShooter(0, 787, 1, 1, -3, 0, 5, 8, spikySlime, false, 5);
		createEnemyShooter(10, 788, 1, 1, -3, 0, 5, 8, spikySlime, false, 5);
		createEnemyShooter(20, 788, 1, 1, -3, 0, 5, 8, spikySlime, false, 5);
		createEnemyShooter(30, 789, 1, 1, -3, 0, 5, 8, spikySlime, true, 5);
		createEnemyShooter(40, 789, 1, 1, -3, 0, 5, 8, spikySlime, true, 5);
		createEnemy(50, 787, EnemyState.PATROL, 10, 2, jumpingMob, 3);
		
		
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
		createEnemy(495, 92, EnemyState.PATROL, 1, 1f, Utilities.spikyRockMob, 3);
		createEnemy(494, 93, EnemyState.BOUNCE, 1, 1f, Utilities.rockMob, 3);
		createEnemy(496, 92, EnemyState.BOUNCE, 1, 1f, Utilities.rockMob, 3);
		createEnemy(505, 93, EnemyState.PATROL, 1, 3f, Utilities.iceMonster, 3);
		return enemies;
	}

	
	public Entity returnEnemy(int id) {
		return enemies.get(id);
	}
	
	public ArrayList<Entity> geEnemies(){
		return enemies;
	}
	
}
