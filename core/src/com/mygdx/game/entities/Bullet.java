package com.mygdx.game.entities;


import java.util.ArrayList;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.Utilities;
import com.mygdx.game.components.B2dBodyComponent;
import com.mygdx.game.components.BulletComponent;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.TextureComponent;
import com.mygdx.game.components.TransformComponent;
import com.mygdx.game.components.TypeComponent;
import com.mygdx.game.components.BulletComponent.Owner;

public class Bullet extends EntityHandler {
	
	private ArrayList<Entity> bullets;
	
	public Bullet() {
		
		bullets = new ArrayList<Entity>();
	}
	/**
	 * 
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @param xVel - x speed and direction
	 * @param yVel - y speed and direction
	 * @param range - how far it goes before it dies
	 * @param owner
	 * @param ownerEntity - the entity it comes from
	 * @return - returns the bullet as an Entity
	 */
	public Entity createBullet(float x, float y, float xVel, float yVel, int range, Owner owner, Entity ownerEntity) {
		Entity entity = pooledEngine.createEntity();
		B2dBodyComponent b2dbody = pooledEngine.createComponent(B2dBodyComponent.class);
		TransformComponent position = pooledEngine.createComponent(TransformComponent.class);
		TextureComponent texture = pooledEngine.createComponent(TextureComponent.class);
		TypeComponent type = pooledEngine.createComponent(TypeComponent.class);
		CollisionComponent colComp = pooledEngine.createComponent(CollisionComponent.class);
		BulletComponent bul = pooledEngine.createComponent(BulletComponent.class);
		
		b2dbody.body = bodyFactory.makeCirclePolyBody(x,y,0.5f, BodyFactory.OTHER, BodyType.DynamicBody, true, true);
		b2dbody.body.setBullet(true); // increase physics computation to limit body traveling through other objects
		b2dbody.body.setGravityScale(0);
		bodyFactory.makeAllFixturesSensors(b2dbody.body); // make bullets sensors so they don't move player
		position.position.set(x,y,0);
		texture.region = Utilities.levelSevenAtlas.findRegion("bullet(left)");
		type.type = TypeComponent.BULLET;
		b2dbody.body.setUserData(entity);
		bul.xVel = xVel;
		bul.yVel = yVel;
		bul.range = range;
		bul.owner = owner;
		bul.ownerEntity = ownerEntity;

		entity.add(bul);
		entity.add(colComp);
		entity.add(b2dbody);
		entity.add(position);
		entity.add(texture);
		entity.add(type);	
		
		return entity;
	}
	
	public ArrayList<Entity> getBullets() {
		return bullets;
	}

}
