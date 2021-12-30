package com.mygdx.game.systems;

import java.util.Random;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Map;
import com.mygdx.game.components.B2dBodyComponent;
import com.mygdx.game.components.EnemyComponent;
import com.mygdx.game.components.SteeringComponent;
import com.mygdx.game.components.TypeComponent;
import com.mygdx.game.entities.Player;

public class EnemySystem extends IteratingSystem {

	private ComponentMapper<EnemyComponent> ec;
	private ComponentMapper<B2dBodyComponent> bodm;
	private ComponentMapper<SteeringComponent> steering;
	Entity player;
	SteeringComponent sCom;
	EnemyComponent enemyCom;
	B2dBodyComponent bodyCom;
	B2dBodyComponent playerCom;
	
	Random rNum;
	
	static float timer = 10f;
    static int iteration = 1;
	
	public EnemySystem() {
		super(Family.all(EnemyComponent.class).get());
		ec = ComponentMapper.getFor(EnemyComponent.class);
		bodm = ComponentMapper.getFor(B2dBodyComponent.class);
		steering = ComponentMapper.getFor(SteeringComponent.class);
	}
	
	
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
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
			case VERTICAL:
				aiThree();
				break;
			case JUMP:
				aiFour();
				break;
			case STEERING:
				steeringTest(entity);
				break;
			default:
				steeringTest(entity);
				break;
		}
		
	}
	
	/**
	 * Normal repeated back and forth enemy movement
	 */
	private void aiOne() {
		float distFromOrigin = Math.abs(enemyCom.xPostCenter - bodyCom.body.getPosition().x);
		
		
		if (distFromOrigin > enemyCom.range) {
			//pause on edge
			if (timer > 0) {
				timer -= 0.1;
				return;
			}
			timer = 4f;
			//flip direction
			enemyCom.isGoingLeft = !enemyCom.isGoingLeft;
		} else {
			enemyCom.isGoingLeft = enemyCom.isGoingLeft;
		}
		
		float speed = enemyCom.isGoingLeft ? -0.05f : 0.05f;
		
		bodyCom.body.setTransform(bodyCom.body.getPosition().x + speed, bodyCom.body.getPosition().y, bodyCom.body.getAngle());

	}
	
	
	/**
	 * Bounce type enemy
	 */
	private void aiTwo() {
		float distFromOriginX = Math.abs(enemyCom.xPostCenter - bodyCom.body.getPosition().x);
		float distFromOriginY = Math.abs(enemyCom.yPostCenter - bodyCom.body.getPosition().y);
		
//		if (timer > 0) {
//			timer -= 0.1;
//			return;
//		}
		enemyCom.isGoingLeft = distFromOriginX > enemyCom.range ? !enemyCom.isGoingLeft : enemyCom.isGoingLeft;
		enemyCom.isFalling = distFromOriginY > 3 ? !enemyCom.isFalling : enemyCom.isFalling;
		float speedX = enemyCom.isGoingLeft ? -0.05f : 0.05f;
		float speedY = enemyCom.isFalling ? -0.05f : 0.05f;
		
		bodyCom.body.setTransform(bodyCom.body.getPosition().x + speedX, bodyCom.body.getPosition().y + speedY, bodyCom.body.getAngle());

	}
	
	/**
	 * Up/Down Floating type enemy
	 */
	private void aiThree() {
		float distFromOrigin = Math.abs(enemyCom.yPostCenter - bodyCom.body.getPosition().y);
		
		
		if (distFromOrigin > enemyCom.range) {
			//pause on peak/low
			if (timer > 0) {
				timer -= 0.1;
				return;
			}
			timer = 4f;
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
		if (timer > 0) {
			timer -= 0.1;
			return;
		}
		timer = 10f;
		
		bodyCom.body.applyForceToCenter(0, 0f,true);
		
		if (iteration <= enemyCom.range) {
			bodyCom.body.applyLinearImpulse(10f, 30f, bodyCom.body.getWorldCenter().x,bodyCom.body.getWorldCenter().y, true);
			System.out.println(iteration);
		} else if (iteration <= (enemyCom.range * 2)) {
			bodyCom.body.applyLinearImpulse(-10f, 30f, bodyCom.body.getWorldCenter().x,bodyCom.body.getWorldCenter().y, true);
			System.out.println(iteration);
		} else {
			iteration = 0;
			System.out.print(iteration);
		}
		
		iteration++;
		return;
	}
	
	/**
	 * 
	 */
	private void bossOne() {
		if (timer > 0) {
			timer -= 0.1;
			return;
		}
		timer = 10f;
		
		bodyCom.body.applyForceToCenter(0, 0f,true);
		
		int move = rNum.nextInt(10);
		
		switch (move) {
			
			case 0:
				//dash
				
				break;
			case 1:
				
				break;
			case 2:
				
				break;
			case 3:
				
				break;
			default:
				
				break;
		
		
		}
		
		
	}
	
	private void steeringTest(Entity entity) {
		float distance = playerCom.body.getPosition().dst(bodyCom.body.getPosition());
		System.out.println(distance);
		SteeringComponent playerSteering = steering.get(player);
		if(distance < 1 && sCom.currentMode != SteeringComponent.SteeringState.ARRIVE){
			sCom.steeringBehavior = SteeringPresets.getFlee(sCom, playerSteering);
			sCom.currentMode = SteeringComponent.SteeringState.FLEE;
		}else if(distance > 2 && distance < 10 && sCom.currentMode != SteeringComponent.SteeringState.ARRIVE){
			sCom.steeringBehavior = SteeringPresets.getArrive(sCom, playerSteering);
			sCom.currentMode = SteeringComponent.SteeringState.ARRIVE;
		}else if(distance > 10 && sCom.currentMode != SteeringComponent.SteeringState.WANDER){
			sCom.steeringBehavior  = SteeringPresets.getWander(sCom);
			sCom.currentMode = SteeringComponent.SteeringState.WANDER;
		}
	}
	
	
	
}
