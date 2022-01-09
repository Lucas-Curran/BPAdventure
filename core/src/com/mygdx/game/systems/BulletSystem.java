package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.components.B2dBodyComponent;
import com.mygdx.game.components.BulletComponent;
 
public class BulletSystem extends IteratingSystem{
	
	private ComponentMapper<B2dBodyComponent> bodm;
	private ComponentMapper<BulletComponent> bc;
	Entity enemy;
	B2dBodyComponent bulletCom, enemyCom;
	int range;
	
	@SuppressWarnings("unchecked")
	public BulletSystem(){
		super(Family.all(BulletComponent.class).get());
		bodm = ComponentMapper.getFor(B2dBodyComponent.class);
		bc =  ComponentMapper.getFor(BulletComponent.class);
	}

	
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		//get enemy entity
		enemy = super.getEngine().getSystem(EnemySystem.class).enemy;
		//get box 2d body and bullet components
		B2dBodyComponent b2body = bodm.get(entity);
		BulletComponent bullet = bc.get(entity);
		B2dBodyComponent enemyBody = bodm.get(enemy);
		range = bullet.range;
		
		//get position values
		float bx = b2body.body.getPosition().x;
		float by = b2body.body.getPosition().y;
		float ex = enemyBody.body.getPosition().x;
		float ey = enemyBody.body.getPosition().y;
		
		if (Math.abs(bx - ex) > 7 || Math.abs(by - ey) > 7) {
			bullet.isDead = true;
		}
		
		// apply bullet velocity to bullet body
		b2body.body.setLinearVelocity(bullet.xVel, bullet.yVel);
		
		//check if bullet is dead
		if(bullet.isDead){
			b2body.isDead = true;
		}
	}
}