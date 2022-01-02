package com.mygdx.game.systems;

import com.mygdx.game.components.BulletComponent;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.PlayerComponent;
import com.mygdx.game.components.TypeComponent;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
 
public class CollisionSystem  extends IteratingSystem {
	 ComponentMapper<CollisionComponent> cm;
	 ComponentMapper<PlayerComponent> pm;
	 ComponentMapper<BulletComponent> bc;
 
	public CollisionSystem() {
		// only need to worry about player collisions
		super(Family.all(CollisionComponent.class,PlayerComponent.class).get());
		
		 cm = ComponentMapper.getFor(CollisionComponent.class);
		 pm = ComponentMapper.getFor(PlayerComponent.class);
		 bc = ComponentMapper.getFor(BulletComponent.class);
	}
 
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		// get player collision component
		CollisionComponent cc = cm.get(entity);
		
		Entity collidedEntity = cc.collisionEntity;
		if(collidedEntity != null){
			TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
			if(type != null){
				switch(type.type){
				case TypeComponent.ENEMY:
					//do player hit enemy thing
					System.out.println("player hit enemy");
					break;
				case TypeComponent.SCENERY:
					//do player hit scenery thing
					System.out.println("player hit scenery");
					break;
				case TypeComponent.BULLET:
					//do player hit bullet thing
					System.out.println("Player was shot by a bullet");
					break;
				case TypeComponent.OTHER:
					//do player hit other thing
					System.out.println("player hit other");
					break; //technically this isn't needed				
				}
				cc.collisionEntity = null; // collision handled reset component
			}
		}
		
	}
 
}