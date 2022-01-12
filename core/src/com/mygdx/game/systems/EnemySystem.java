package com.mygdx.game.systems;

import java.util.Random;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Utilities;
import com.mygdx.game.components.B2dBodyComponent;
import com.mygdx.game.components.BulletComponent;
import com.mygdx.game.components.EnemyComponent;
import com.mygdx.game.components.SteeringComponent;
import com.mygdx.game.components.EnemyComponent.EnemyState;
import com.mygdx.game.entities.Bullet;
import com.mygdx.game.entities.Enemy;

public class EnemySystem extends IteratingSystem {

	private ComponentMapper<EnemyComponent> ec;
	private ComponentMapper<B2dBodyComponent> bodm;
	private ComponentMapper<SteeringComponent> steering;
	Entity player, enemy;
	SteeringComponent sCom;
	EnemyComponent enemyCom;
	B2dBodyComponent bodyCom;
	B2dBodyComponent playerCom;
	Bullet bullet;
	int randomBulletDirection;
	
	 private TextureAtlas textureAtlas;
	 private TextureRegion tex;
	
	Random rNum = new Random();
	
	
    static int iteration = 1;
    float timerReset = 0;
	
	public EnemySystem() {
		super(Family.all(EnemyComponent.class).get());
		ec = ComponentMapper.getFor(EnemyComponent.class);
		bodm = ComponentMapper.getFor(B2dBodyComponent.class);
		steering = ComponentMapper.getFor(SteeringComponent.class);
	}
	
	
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		enemy = entity;
		enemyCom = ec.get(entity);
		bodyCom = bodm.get(entity);
		sCom = steering.get(entity);
		EnemyComponent.EnemyState enemyState = enemyCom.enemyMode;
		player = super.getEngine().getEntities().first();
		playerCom = bodm.get(player);
		
		
		//Choose movement type based on ai type specified
		switch (enemyState) {
			case PATROL: 
				aiOne();
				break;
			case BOUNCE:
				aiTwo();
				break;
			case SHOOTER:
				aiBullet();
				break;
			case VERTICAL:
				aiThree();
				break;
			case JUMP:
				aiFour();
				break;
			case STEERING:
				aiSteering();
				break;
			case BOSS:
				boss();
				break;
			default:
				aiOne();
				break;
		}
		
		
		//Check if health is less than 0 and set enemy to dead
		if (enemyCom.health <= 0) {
			bodyCom.isDead = true;
		}
		
		
	}
	
	/**
	 * Normal repeated back and forth enemy movement
	 */
	private void aiOne() {
		float distFromOrigin = Math.abs(enemyCom.xPostCenter - bodyCom.body.getPosition().x);
		
		
		if (distFromOrigin > enemyCom.range) {
			//pause on edge
			if (enemyCom.timer > 0) {
				enemyCom.timer -= 0.1;
				return;
			}
			enemyCom.timer = 4f;
			//flip direction
			enemyCom.isGoingLeft = !enemyCom.isGoingLeft;
		} else {
			enemyCom.isGoingLeft = enemyCom.isGoingLeft;
		}
		
		float speed = enemyCom.isGoingLeft ? -0.05f : 0.05f;
		
		bodyCom.body.setTransform(bodyCom.body.getPosition().x + speed, bodyCom.body.getPosition().y, bodyCom.body.getAngle());

	}
	
	private void aiBullet() {
		timerReset = Math.max(timerReset, enemyCom.timer);
		if (enemyCom.timer > 0) {
			enemyCom.timer -= 0.1;
			return;
		}
		enemyCom.timer = timerReset;
		
		Bullet bullet = new Bullet();
		Random rNum = new Random();
		Vector2 aim;
		if (enemyCom.random) {
			enemyCom.bulletXDirection = -(1 + rNum.nextInt(10));
			enemyCom.bulletYDirection = (1 + rNum.nextInt(10));
		 
		}
		 aim = new Vector2(enemyCom.bulletXDirection, enemyCom.bulletYDirection);

		aim.scl(1);
		

		
		getEngine().addEntity(bullet.createBullet(bodyCom.body.getPosition().x, bodyCom.body.getPosition().y, aim.x, aim.y,
				enemyCom.bulletRange, BulletComponent.Owner.ENEMY, enemy));
		
		
	}


	
	
	/**
	 * Bounce type enemy
	 */
	private void aiTwo() {
		float distFromOriginX = Math.abs(enemyCom.xPostCenter - bodyCom.body.getPosition().x);
		float distFromOriginY = Math.abs(enemyCom.yPostCenter - bodyCom.body.getPosition().y);
		
		enemyCom.isGoingLeft = distFromOriginX > enemyCom.range ? !enemyCom.isGoingLeft : enemyCom.isGoingLeft;
		enemyCom.isFalling = distFromOriginY > 3 ? !enemyCom.isFalling : enemyCom.isFalling;
		float speedX = enemyCom.isGoingLeft ? -0.05f : 0.05f;
		float speedY = enemyCom.isFalling ? -0.05f : 0.05f;
		
		bodyCom.body.setTransform(bodyCom.body.getPosition().x + speedX, bodyCom.body.getPosition().y + speedY, bodyCom.body.getAngle());

	}
	
	/**
	 * Up/Down vertical type enemy
	 */
	private void aiThree() {
		float distFromOrigin = Math.abs(enemyCom.yPostCenter - bodyCom.body.getPosition().y);
		
		
		if (distFromOrigin > enemyCom.range) {
			//pause on peak/low
			if (enemyCom.timer > 0) {
				enemyCom.timer -= 0.1;
				return;
			}
			enemyCom.timer = 4f;
			//flip direction
			enemyCom.isGoingUp = !enemyCom.isGoingUp;
		} else {
			enemyCom.isGoingUp = enemyCom.isGoingUp;
		}
		
		float speed = enemyCom.isGoingUp ? -0.05f : 0.05f;
		
		bodyCom.body.setTransform(bodyCom.body.getPosition().x, bodyCom.body.getPosition().y + speed, bodyCom.body.getAngle());
	}
	
	/**
	 * Jump type enemy
	 */
	private void aiFour() {
		if (enemyCom.timer > 0) {
			enemyCom.timer -= 0.1;
			return;
		}
		enemyCom.timer = 10f;
		
		bodyCom.body.applyForceToCenter(0, 0f,true);
		
		if (iteration <= enemyCom.range) {
			bodyCom.body.applyLinearImpulse(10f, 30f, bodyCom.body.getWorldCenter().x,bodyCom.body.getWorldCenter().y, true);
		} else if (iteration <= (enemyCom.range * 2)) {
			bodyCom.body.applyLinearImpulse(-10f, 30f, bodyCom.body.getWorldCenter().x,bodyCom.body.getWorldCenter().y, true);
		} else {
			iteration = 0;
		}
		
		iteration++;
		return;
	}
	
	/**
	 * Chase type enemy using steering
	 */
	private void aiSteering() {
		float distance = playerCom.body.getPosition().dst(bodyCom.body.getPosition());
		SteeringComponent playerSteering = steering.get(player);
		if(distance < 1 && sCom.currentMode != SteeringComponent.SteeringState.ARRIVE){
			sCom.steeringBehavior = SteeringPresets.getFlee(sCom, playerSteering);
			sCom.currentMode = SteeringComponent.SteeringState.FLEE;
		} else if(distance > 2 && distance < enemyCom.range && sCom.currentMode != SteeringComponent.SteeringState.ARRIVE){
			sCom.steeringBehavior = SteeringPresets.getArrive(sCom, playerSteering);
			sCom.currentMode = SteeringComponent.SteeringState.ARRIVE;
		} else if(distance > enemyCom.range && sCom.currentMode != SteeringComponent.SteeringState.WANDER){
			sCom.steeringBehavior  = SteeringPresets.getWander(sCom);
			sCom.currentMode = SteeringComponent.SteeringState.WANDER;
		}
	}
	
	/**
	 * Boss enemy ai
	 */
	private void boss() {
		if (enemyCom.timer > 0) {
			enemyCom.timer -= 0.1;
			return;
		}
		enemyCom.timer = 15f;
		
		bodyCom.body.applyForceToCenter(0, 0f,true);
		
		int move = rNum.nextInt(21);
		
		if (move < 5) {
			//Shoot toward player
			shoot(0);
		} else if (move < 9) {
			//dash
			bodyCom.body.applyLinearImpulse(-150f, 0, bodyCom.body.getWorldCenter().x,bodyCom.body.getWorldCenter().y, true);
		} else if (move < 13) {
			//dash
			bodyCom.body.applyLinearImpulse(150f, 0, bodyCom.body.getWorldCenter().x,bodyCom.body.getWorldCenter().y, true);
		} else if (move < 17) {
			//Jump and shoot
			bodyCom.body.applyLinearImpulse(0, 150f, bodyCom.body.getWorldCenter().x,bodyCom.body.getWorldCenter().y, true);
			shoot(2f);
		} else if (move <= 20) {
			//Spawn a steering type enemy
			Enemy e = new Enemy();
			
			tex = Utilities.levelSevenAtlas.findRegion("bullet(left)");
			//getEngine().addEntity(e.createEnemy((int) bodyCom.body.getWorldCenter().x, (int) bodyCom.body.getWorldCenter().y + 2, EnemyState.STEERING, 0, 1f));
			getEngine().addEntity(e.createEnemy((int) bodyCom.body.getWorldCenter().x, (int) bodyCom.body.getWorldCenter().y + 2, EnemyState.STEERING, 0, 1f,tex, 0));
		}
		
		
	}
	
	/**
	 * 
	 */
	private void shoot(float offset) {
		Bullet bullet = new Bullet();
		Vector2 aim = Utilities.aimTo(bodyCom.body.getPosition(), playerCom.body.getPosition());
		aim.scl(5);
		
		getEngine().addEntity(bullet.createBullet(bodyCom.body.getPosition().x, bodyCom.body.getPosition().y + offset, aim.x, aim.y, 7, BulletComponent.Owner.ENEMY, enemy));
	}
	
	
}
