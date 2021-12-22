package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.components.B2dBodyComponent;
import com.mygdx.game.components.EnemyComponent;
import com.mygdx.game.components.TypeComponent;

public class EnemySystem extends IteratingSystem {

	private ComponentMapper<EnemyComponent> ec;
	private ComponentMapper<B2dBodyComponent> bodm;
	private ComponentMapper<TypeComponent> type;
	EnemyComponent enemyCom;
	B2dBodyComponent bodyCom;
	
	static float timer = 4f;
	
	public EnemySystem() {
		super(Family.all(EnemyComponent.class).get());
		ec = ComponentMapper.getFor(EnemyComponent.class);
		bodm = ComponentMapper.getFor(B2dBodyComponent.class);
		type = ComponentMapper.getFor(TypeComponent.class);
	}
	
	
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		enemyCom = ec.get(entity);
		bodyCom = bodm.get(entity);
		TypeComponent enemyType = type.get(entity);
		
		//Choose movement type based on ai type specified
		switch (enemyType.enemyAI) {
			case 0: 
				enemyOne();
				break;
			case 1:
				enemyTwo();
				break;
			
			default:
				enemyOne();
				break;
		}
		
	}
	
	/**
	 * Normal repeated back and forth enemy movement
	 */
	private void enemyOne() {
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
	 * 
	 */
	private void enemyTwo() {
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
	
	
	
}
