package com.mygdx.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.components.B2dBodyComponent;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.NPCComponent;
import com.mygdx.game.components.PlayerComponent;
import com.mygdx.game.components.TransformComponent;
import com.mygdx.game.components.TypeComponent;
import com.mygdx.game.entities.EntityHandler;
import com.mygdx.game.entities.Player;
import com.mygdx.game.levels.DoorBuilder;
import com.mygdx.game.levels.Levels;
import com.mygdx.game.levels.Levels.LevelDestination;
 
public class B2dContactListener implements ContactListener {
	
	private EntityHandler parent;
	DoorBuilder db = DoorBuilder.getInstance();
	
	
	public B2dContactListener(EntityHandler parent){ 
		this.parent = parent;
	}

	@Override
	public void beginContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();

		for (int i = 0; i < db.doors.size(); i++) {
			
			if (fa.getBody().getUserData() == db.doors.get(i).getUserData()) {
				if (fb.getBody().getUserData() instanceof Entity) {
					Entity entB = (Entity) fb.getBody().getUserData();
					if (entB.getComponent(PlayerComponent.class) != null) {
						parent.loadingZone = true;
						parent.setDestinationX(db.destinationsX.get(i));
						parent.setDestinationY(db.destinationsY.get(i));
						parent.setDestination(db.destinations.get(i));
						parent.setCreatedLevel(db.createdLevels.get(i));
						
						switch(db.createdLevels.get(i)) {
						case OVERWORLD:
//							levelNumber = 0;
							break;
						case LVL_2:
//							levelNumber = 1;
							break;
						case LVL_3:
//							levelNumber = 2;
							break;
						case LVL_4:
//							levelNumber = 3;
							break;
						case LVL_5:
//							levelNumber = 4;
							break;
						case LVL_6:
//							levelNumber = 5;
							break;
						case LVL_7:
//							levelNumber = 6;
							break;
						case LVL_8:
//							parent.getLevels().getLevelEight().create();
							break;
						case LVL_9:
//							levelNumber = 8;
							break;
						case LVL_10:
//							levelNumber = 9;
							break;

						}
						System.out.println(parent.getCreatedLevel());
						System.out.println(LevelDestination.LVL_8);
					}
				}
			} else if (fb.getBody().getUserData() == db.doors.get(i).getUserData()) {
				if (fa.getBody().getUserData() instanceof Entity) {
					Entity entA = (Entity) fa.getBody().getUserData();
					if (entA.getComponent(PlayerComponent.class) != null) {
						parent.loadingZone = true;
						parent.setDestinationX(db.destinationsX.get(i));
						parent.setDestinationY(db.destinationsY.get(i));
						parent.setDestination(db.destinations.get(i));
					}
				}
			}
		}

		if (fa.getBody().getUserData() == "gravityPillar") {
			System.out.println("Hit gravitySwitch");
			parent.gravityZone = true;
			
		} else if (fb.getBody().getUserData() == "gravityPillar") {
			System.out.println("Hit gravitySwitch");
			parent.gravityZone = true;

		}
		
		if (fa.getBody().getUserData() == "gravityPillar2") {
			System.out.println("Hit gravitySwitch");
			parent.gravityZone = false;
			
		} else if (fb.getBody().getUserData() == "gravityPillar2") {
			System.out.println("Hit gravitySwitch");
			parent.gravityZone = false;

		}
		
		if (fa.getBody().getUserData() == "lavaFloor" || fa.getBody().getUserData() == "lavaCeiling" || fa.getBody().getUserData() == "lavaCeiling2") {
			System.out.println("Hit lava");
			parent.killZone = true;
			parent.gravityZone = false;
			
		} else if (fb.getBody().getUserData() == "lavaFloor" || fb.getBody().getUserData() == "lavaCeiling" || fb.getBody().getUserData() == "lavaCeiling2") {
			System.out.println("Hit lava");
			parent.killZone = true;
			parent.gravityZone = false;

		}
		
		if (fa.getBody().getUserData() instanceof Entity){
			Entity ent = (Entity) fa.getBody().getUserData();
			entityCollision(ent,fb);
			return;
		} else if(fb.getBody().getUserData() instanceof Entity){
			Entity ent = (Entity) fb.getBody().getUserData();
			entityCollision(ent,fa);
			return;
		}
		
	}
 
	/**
	 * Detects whether an entity has collided with a box2d fixture
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
				if (colEnt.getComponent(TypeComponent.class).type == TypeComponent.NPC) {
					parent.npcX = colEnt.getComponent(B2dBodyComponent.class).body.getPosition().x;
					parent.npcY = colEnt.getComponent(B2dBodyComponent.class).body.getPosition().y;
					parent.talkingZone = true;
					parent.setCurrentNPCText(colEnt.getComponent(NPCComponent.class).text);
					parent.setHasOptions(colEnt.getComponent(NPCComponent.class).hasOptions);
				}
			}else if(colb != null){
				colb.collisionEntity = ent;
				if (ent.getComponent(TypeComponent.class).type == TypeComponent.NPC) {
					parent.npcX = ent.getComponent(B2dBodyComponent.class).body.getPosition().x;
					parent.npcY = ent.getComponent(B2dBodyComponent.class).body.getPosition().y;
					parent.talkingZone = true;
					parent.setCurrentNPCText(ent.getComponent(NPCComponent.class).text);
					parent.setHasOptions(ent.getComponent(NPCComponent.class).hasOptions);
				}
			}
		}
	}
 
	@Override
	public void endContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();

		for (int i = 0; i < db.doors.size(); i++) {
			if (fa.getBody().getUserData() == db.doors.get(i).getUserData()) {
				if (fb.getBody().getUserData() instanceof Entity) {
					Entity entB = (Entity) fb.getBody().getUserData();
					if (entB.getComponent(TypeComponent.class).type == TypeComponent.PLAYER) {
						parent.loadingZone = false;
					}
				}
			} else if (fb.getBody().getUserData() == db.doors.get(i).getUserData()) {
				if (fa.getBody().getUserData() instanceof Entity) {
					Entity entA = (Entity) fa.getBody().getUserData();
					if (entA.getComponent(TypeComponent.class).type == TypeComponent.PLAYER) {
						parent.loadingZone = false;
					}
				}
			}
		}
		
		if(fa.getBody().getUserData() instanceof Entity && fb.getBody().getUserData() instanceof Entity){
			Entity entA = (Entity) fa.getBody().getUserData();
			Entity entB = (Entity) fb.getBody().getUserData();
			if (entA.getComponent(TypeComponent.class).type == TypeComponent.NPC && entB.getComponent(TypeComponent.class).type == TypeComponent.PLAYER) {
				parent.talkingZone = false;
			} else if (entB.getComponent(TypeComponent.class).type == TypeComponent.NPC && entA.getComponent(TypeComponent.class).type == TypeComponent.PLAYER) {
				parent.talkingZone = false;
			}
		}
		
		if (fa.getBody().getUserData() == "lavaFloor" || fa.getBody().getUserData() == "lavaCeiling" || fa.getBody().getUserData() == "lavaCeiling2") {
			parent.killZone = false;
			
		} else if (fb.getBody().getUserData() == "lavaFloor" || fb.getBody().getUserData() == "lavaCeiling" || fb.getBody().getUserData() == "lavaCeiling2") {
			parent.killZone = false;
		}
		
	}
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {		
	}
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {		
	}
 
}