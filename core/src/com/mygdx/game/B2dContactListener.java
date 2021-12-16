package com.mygdx.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.entities.EntityHandler;
import com.mygdx.game.entities.Player;
 
public class B2dContactListener implements ContactListener {
	
	private EntityHandler parent;
	
	public B2dContactListener(EntityHandler parent){ 
		this.parent = parent;
	}

	@Override
	public void beginContact(Contact contact) {
		System.out.println("Contact");
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		System.out.println(fa.getBody().getType()+" has hit "+ fb.getBody().getType());
		
		if(fa.getBody().getUserData() instanceof Entity){
			Entity ent = (Entity) fa.getBody().getUserData();
			entityCollision(ent,fb);
			return;
		}else if(fb.getBody().getUserData() instanceof Entity){
			Entity ent = (Entity) fb.getBody().getUserData();
			entityCollision(ent,fa);
			return;
		}
		
		if (fa.getBody().getUserData() == "Door") {
			System.out.println("Hit door");
			parent.loadingZone = true;
			
		} else if (fb.getBody().getUserData() == "Door") {
			System.out.println("Hit door");
			parent.loadingZone = true;

		}
		
	}
 
	/**
	 * Detects whether an ashley entity has collided with a box2d fixture
	 * @param ent - entity
	 * @param fb - fixture body
	 */
	private void entityCollision(Entity ent, Fixture fb) {
		if(fb.getBody().getUserData() instanceof Entity){
			Entity colEnt = (Entity) fb.getBody().getUserData();
			
			CollisionComponent col = ent.getComponent(CollisionComponent.class);
			CollisionComponent colb = colEnt.getComponent(CollisionComponent.class);
			
			if(col != null){
				col.collisionEntity = colEnt;
			}else if(colb != null){
				colb.collisionEntity = ent;
			}
		}
	}
 
	@Override
	public void endContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		if(fa.getBody().getUserData() == "Door"){
			parent.loadingZone = false;
			return;
		} else if(fb.getBody().getUserData() == "Door"){
			parent.loadingZone = false;
			return;
		}
	}
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {		
	}
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {		
	}
 
}