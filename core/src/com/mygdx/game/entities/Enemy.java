package com.mygdx.game.entities;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.Settings;
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
	
	static Logger logger = LogManager.getLogger(Enemy.class.getName());
	
	private static ArrayList<Entity> enemies;
	
	private ArrayList<Entity> levelTwoEnemies;
	private ArrayList<Entity> levelThreeEnemies;
	private ArrayList<Entity> levelFourEnemies;
	private ArrayList<Entity> iceDungeonEnemies;
	private ArrayList<Entity> levelFiveEnemies;
	private ArrayList<Entity> levelSixEnemies;
	private ArrayList<Entity> levelSevenEnemies;
	private ArrayList<Entity> levelEightEnemies;
	private ArrayList<Entity> levelNineEnemies;
	private ArrayList<Entity> levelTenEnemies;

	public B2dBodyComponent b2dbody;
	
	public Enemy() {
		levelTwoEnemies = new ArrayList<Entity>();
		levelThreeEnemies = new ArrayList<Entity>();
		levelFourEnemies = new ArrayList<Entity>();
		iceDungeonEnemies = new ArrayList<Entity>();
		levelFiveEnemies = new ArrayList<Entity>();
		levelSixEnemies = new ArrayList<Entity>();
		levelSevenEnemies = new ArrayList<Entity>();
		levelEightEnemies = new ArrayList<Entity>();
		levelNineEnemies = new ArrayList<Entity>();
		levelTenEnemies = new ArrayList<Entity>();
		logger.info("Enemies instanced.");
	}
	
	/**
	 * creates enemies in various levels
	 * @param posx - x coordinate
	 * @param posy - y coordinate
	 * @param enemyType - type of the enemy based on EnemyState enum
	 * @param range - how far the enemy moves
	 * @param radius - how big it is
	 * @param entityTexture - what it looks like
	 * @return - completed enemy as an Entity
	 */
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
		//enemy.health = hp;
		
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

		logger.info("Enemy created.");
		
		return entity;
	}
	
/**
 * creates enemies of the type Shooter in various levels
 * @param posx - x coordinate
 * @param posy - y coordinate
 * @param range - how far the enemy moves
 * @param radius - how big it is
 * @param bulletXDirection - the direction and speed of the bullet in x plane
 * @param bulletYDirection - direction and speed of the bullet in y plane
 * @param bulletRange - how far it goes before it dies
 * @param time - the time between bullet shots
 * @param entityTexture - what it looks like
 * @param random - whether the bullet shots will be in random directions or not
 * @return - the completed enemy shooter as an Entity
 */
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
		//enemy.health = hp;
		
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
		
		logger.info("Enemy shooter created.");
	
		return entity;
	}

	/**
	 * gets the enemies of level two
	 * @return an ArrayList of all the level two enemies
	 */
	public void addLevelTwoEnemies() {
		levelTwoEnemies.add(createEnemy(15, 92, EnemyState.STEERING, 8, 1.3f, rockMob, 1));
		levelTwoEnemies.add(createEnemy(15, 92, EnemyState.PATROL, 1, 1f, rockMob, 1));
		levelTwoEnemies.add(createEnemy(25, 93, EnemyState.PATROL, 1, 2f, spikyRockMob, 1));
		levelTwoEnemies.add(createEnemy(25, 95, EnemyState.BOUNCE, 1, 1f, rockMob, 1));
		levelTwoEnemies.add(createEnemy(15, 92, EnemyState.PATROL, 1, 1f, rockMob, 3));
		logger.info("Level two enemies added.");
	}
	
	/**
	 * gets the enemies of level three
	 * @return an ArrayList of all the level three enemies
	 */
	public void addLevelThreeEnemies() {	
		logger.info("Level three enemies added.");
	}
	
	/**
	 * gets the enemies of level four
	 * @return an ArrayList of all the level four enemies
	 */
	public void addLevelFourEnemies() {	
		levelFourEnemies.add(createEnemy(12, 258, EnemyState.PATROL, 1, 1f, Utilities.iceMonster, 3));
		levelFourEnemies.add(createEnemy(0, 266, EnemyState.PATROL, 1, 1.3f, Utilities.iceMonster, 3));
		levelFourEnemies.add(createEnemy(2, 266, EnemyState.BOUNCE, 1, 1f, Utilities.iceBird, 3));
		levelFourEnemies.add(createEnemy(19, 275, EnemyState.PATROL, 1, 1f, Utilities.iceMonster, 3));	
		levelFourEnemies.add(createEnemy(-14, 287, EnemyState.PATROL, 1, 1f, Utilities.iceMonster, 3));
		levelFourEnemies.add(createEnemy(-16, 288, EnemyState.BOUNCE, 1, 3f, Utilities.iceBird, 3));
		
		levelFourEnemies.add(createEnemyShooter(-16f, 287f, 1, 1f, 2, 0, 7, 7, Utilities.iceMonster,false, 5));
		logger.info("Level four enemies added.");
	}
	
	/**
	 * gets the enemies of level five
	 * @return an ArrayList of all the level five enemies
	 */
	public void addLevelFiveEnemies() {	
		
		levelFiveEnemies.add(createEnemy(-10, 383, EnemyState.BOUNCE, 1, 1.4f, Utilities.flyingEye, 3));
		levelFiveEnemies.add(createEnemy(-9, 382, EnemyState.PATROL, 1, 1f, Utilities.mummyEnemy, 3)); 
		
		levelFiveEnemies.add(createEnemy(41, 382, EnemyState.STEERING, 8, 1.3f, Utilities.flyingEye, 1));
		levelFiveEnemies.add(createEnemy(41, 382, EnemyState.STEERING, 8, 1.3f, Utilities.flyingEye, 1));
		levelFiveEnemies.add(createEnemy(40, 382, EnemyState.STEERING, 8, 1.3f, Utilities.flyingEye, 1));
		levelFiveEnemies.add(createEnemyShooter(42, 382, 1, 2f, -2, 0, 7, 7, Utilities.eyeBoss, false, 5));
		logger.info("Level five enemies added.");
	}
	
	/**
	 * gets the enemies of level six
	 * @return an ArrayList of all the level six enemies
	 */
	public void addLevelSixEnemies() {	
		
		levelSixEnemies.add(createEnemy(14, 463, EnemyState.PATROL, 0, 4f, Utilities.slimeKing, 3));
		
		levelSixEnemies.add(createEnemy(43, 466, EnemyState.STEERING, 1, 1.3f, Utilities.jungleDragon, 1));
		levelSixEnemies.add(createEnemy(45, 466, EnemyState.STEERING, 1, 1.7f, Utilities.jungleDragon, 1));
	
		levelSixEnemies.add(createEnemy(35, 477, EnemyState.STEERING, 1, 1f, Utilities.jungleDragon, 1));
		levelSixEnemies.add(createEnemy(36, 477, EnemyState.STEERING, 1, 1f, Utilities.jungleDragon, 1));
		levelSixEnemies.add(createEnemy(34, 477, EnemyState.STEERING, 1, 1f, Utilities.jungleDragon, 1));
		levelSixEnemies.add(createEnemy(34, 479, EnemyState.BOUNCE, 1, 3f, Utilities.slimeKing, 7));
		logger.info("Level six enemies added.");
	}
	
	/**
	 * gets the enemies of level seven
	 * @return an ArrayList of all the level seven enemies
	 */
	public void addLevelSevenEnemies() {
		
		levelSevenEnemies.add(createEnemy(11, 595, EnemyState.PATROL, 5, 1f, slimyMob, 3));
		levelSevenEnemies.add(createEnemy(-32, 590, EnemyState.PATROL, 2, 1f, slimyMob, 3));
		levelSevenEnemies.add(createEnemy(11, 595, EnemyState.PATROL, 5, 1f, slimyMob, 3));
		
		levelSevenEnemies.add(createEnemyShooter(1, 593, 1, 1f, -2, 0, 7, 10, spikySlime, false, 5));
		logger.info("Level seven enemies added.");
	}
	
	/**
	 * gets the enemies of level eight
	 * @return an ArrayList of all the level eight enemies
	 */
	public void addLevelEightEnemies() {	

		levelEightEnemies.add(createEnemy(-16, 686, EnemyState.STEERING, 8, 1.3f, Utilities.steeringMob, 1));
		levelEightEnemies.add(createEnemy(-24, 686, EnemyState.STEERING, 8, 1.3f, Utilities.steeringMob, 1));
		levelEightEnemies.add(createEnemy(0, 686, EnemyState.VERTICAL, 3, 2f, Utilities.spiderMob, 3));
		levelEightEnemies.add(createEnemyShooter(7, 686, 1, 2f, -2, 5, 8, 4, spikySlime, true, 3));
		levelEightEnemies.add(createEnemy(24, 686, EnemyState.STEERING, 3, 1, Utilities.steeringMob, 1));
		levelEightEnemies.add(createEnemy(28, 686, EnemyState.BOUNCE, 5, 2, Utilities.spikeBouncer, 3));
		levelEightEnemies.add(createEnemy(35, 686, EnemyState.BOSS, 10, 2, Utilities.boss_8, 10));
		logger.info("Level eight enemies added.");
	}
	
	// shooting parameters - x, y, range, radius, bulletX, bulletY, bulletRange, time, texture, random?
	// regular parameters - x, y, state, range, size, texture
	
	/**
	 * gets the enemies of level nine
	 * @return an ArrayList of all the level nine enemies
	 */
	public void addLevelNineEnemies() {	
		
		levelNineEnemies.add(createEnemyShooter(-20, 797, 1, 1, -2, 0, 10, 8, spikySlime, false, 5));
		levelNineEnemies.add(createEnemy(-10, 797, EnemyState.PATROL, 5, 1, Utilities.patrolMob, 3));
		levelNineEnemies.add(createEnemy(-10, 798, EnemyState.PATROL, 5, 1, Utilities.patrolMob, 3));
		levelNineEnemies.add(createEnemy(0, 797, EnemyState.STEERING, 3, 1, Utilities.steeringMob, 1));
		levelNineEnemies.add(createEnemy(0, 798, EnemyState.STEERING, 3, 1, Utilities.steeringMob, 1));
		levelNineEnemies.add(createEnemy(5, 797, EnemyState.VERTICAL, 1, 1, Utilities.jumpingMob, 3));
		levelNineEnemies.add(createEnemy(25, 797, EnemyState.JUMP, 1, 1, slimyMob, 3));
		levelNineEnemies.add(createEnemy(30, 797, EnemyState.JUMP, 1, 1, slimyMob, 3));
		
		levelNineEnemies.add(createEnemy(50, 792, EnemyState.BOUNCE, 5, 1, Utilities.spikeBouncer, 3));
		levelNineEnemies.add(createEnemy(40, 792, EnemyState.BOUNCE, 5, 1, Utilities.spikeBouncer, 3));
		levelNineEnemies.add(createEnemyShooter(65, 792, 1, 1, -3, 0, 5, 8, spikySlime, true, 5));
		levelNineEnemies.add(createEnemyShooter(55, 792, 1, 1, -3, 0, 5, 8, spikySlime, true, 5));
		levelNineEnemies.add(createEnemy(45, 793, EnemyState.PATROL, 5, 1, Utilities.patrolMob, 3));
		levelNineEnemies.add(createEnemy(40, 792, EnemyState.PATROL, 5, 1, Utilities.patrolMob, 3));
		levelNineEnemies.add(createEnemy(35, 792, EnemyState.STEERING, 3, 1, Utilities.steeringMob, 1));
		levelNineEnemies.add(createEnemy(30, 792, EnemyState.JUMP, 1, 1, Utilities.jumpingMob, 3));
		levelNineEnemies.add(createEnemy(25, 792, EnemyState.VERTICAL, 2, 2, Utilities.vertical_9,3 ));
		levelNineEnemies.add(createEnemy(10, 792, EnemyState.BOSS, 20, 2, Utilities.boss_9, 7));
		levelNineEnemies.add(createEnemy(0, 792, EnemyState.BOSS, 20, 2, Utilities.boss_9, 7));
		levelNineEnemies.add(createEnemy(-10, 792, EnemyState.JUMP, 1, 2, Utilities.jumpingMob, 3));	
		
		levelNineEnemies.add(createEnemyShooter(-30, 787, 1, 1, -3, 0, 5, 8, spikySlime, true, 5));
		levelNineEnemies.add(createEnemyShooter(-20, 787, 1, 1, -3, 0, 5, 8, spikySlime, true, 5));
		levelNineEnemies.add(createEnemyShooter(-10, 787, 1, 1, -3, 0, 5, 8, spikySlime, true, 5));
		levelNineEnemies.add(createEnemyShooter(0, 787, 1, 1, -3, 0, 5, 8, spikySlime, false, 5));
		levelNineEnemies.add(createEnemyShooter(10, 788, 1, 1, -3, 0, 5, 8, spikySlime, false, 5));
		levelNineEnemies.add(createEnemyShooter(20, 788, 1, 1, -3, 0, 5, 8, spikySlime, false, 5));
		levelNineEnemies.add(createEnemyShooter(30, 789, 1, 1, -3, 0, 5, 8, spikySlime, true, 5));
		levelNineEnemies.add(createEnemyShooter(40, 789, 1, 1, -3, 0, 5, 8, spikySlime, true, 5));
		levelNineEnemies.add(createEnemy(50, 787, EnemyState.PATROL, 10, 2, Utilities.jumpingMob, 3));
		logger.info("Level nine enemies added.");
	}
	
	/**
	 * gets the enemies of level ten
	 * @return an ArrayList of all the level ten enemies
	 */
	public void addLevelTenEnemies() {	
		logger.info("Level ten enemies added.");
	}
	
	/**
	 * gets the enemies of the ice dungeon
	 * @return an ArrayList of all the ice dungeon enemies
	 */
	public void addIceDungeonEnemies() {
		
		iceDungeonEnemies.add(createEnemy(495, 92, EnemyState.PATROL, 1, 1f, Utilities.spikyRockMob, 3));
		iceDungeonEnemies.add(createEnemy(494, 93, EnemyState.BOUNCE, 1, 1f, Utilities.rockMob, 3));
		iceDungeonEnemies.add(createEnemy(496, 92, EnemyState.BOUNCE, 1, 1f, Utilities.rockMob, 3));
		iceDungeonEnemies.add(createEnemy(505, 93, EnemyState.PATROL, 1, 2f, Utilities.iceMonster, 3));
		logger.info("Ice dungeon enemies added.");
	}

	
	public ArrayList<Entity> getLevelTwoEnemies() {
		return levelTwoEnemies;
	}

	public ArrayList<Entity> getLevelThreeEnemies() {
		return levelThreeEnemies;
	}

	public ArrayList<Entity> getLevelFourEnemies() {
		return levelFourEnemies;
	}
	
	public ArrayList<Entity> getIceDungeonEnemies() {
		return iceDungeonEnemies;
	}

	public ArrayList<Entity> getLevelFiveEnemies() {
		return levelFiveEnemies;
	}

	public ArrayList<Entity> getLevelSixEnemies() {
		return levelSixEnemies;
	}

	public ArrayList<Entity> getLevelSevenEnemies() {
		return levelSevenEnemies;
	}

	public ArrayList<Entity> getLevelEightEnemies() {
		return levelEightEnemies;
	}

	public ArrayList<Entity> getLevelNineEnemies() {
		return levelNineEnemies;
	}

	public ArrayList<Entity> getLevelTenEnemies() {
		return levelTenEnemies;
	}

	public Entity returnEnemy(int id) {
		return enemies.get(id);
	}
	
}
