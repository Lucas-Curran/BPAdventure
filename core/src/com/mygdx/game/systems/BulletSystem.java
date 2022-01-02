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
	
	@SuppressWarnings("unchecked")
	public BulletSystem(){
		super(Family.all(BulletComponent.class).get());
		bodm = ComponentMapper.getFor(B2dBodyComponent.class);
		
	}

	
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		System.out.println("Bullet system");
		//get enemy entity
		enemy = super.getEngine().getEntities().first();
		//get box 2d body and bullet components
		B2dBodyComponent b2body = bodm.get(entity);
		BulletComponent bullet = bc.get(entity);
		
		//get position values
		float bx = b2body.body.getPosition().x;
		float by = b2body.body.getPosition().y;
		float ex = b2body.body.getPosition().x;
		float ey = b2body.body.getPosition().y;
		
		if (bx - ex > 20 || by - ey > 20) {
			bullet.isDead = true;
		}
		
		System.out.println(bullet.xVel);
		System.out.println(bullet.yVel);

		// apply bullet velocity to bullet body
		b2body.body.setLinearVelocity(bullet.xVel, bullet.yVel);
		
		//check if bullet is dead
		if(bullet.isDead){
			b2body.isDead = true;
		}
	}
}