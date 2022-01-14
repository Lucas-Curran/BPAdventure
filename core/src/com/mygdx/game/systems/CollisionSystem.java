package com.mygdx.game.systems;

import com.mygdx.game.Map;
import com.mygdx.game.SqliteManager;
import com.mygdx.game.components.BulletComponent;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.PlayerComponent;
import com.mygdx.game.components.TypeComponent;
import com.mygdx.game.ui.HealthBar;
import com.mygdx.game.ui.StatusUI;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class CollisionSystem  extends IteratingSystem {
	 ComponentMapper<CollisionComponent> cm;
	 ComponentMapper<PlayerComponent> pm;
	 ComponentMapper<BulletComponent> bc;
	 HealthBar hb;
	 SqliteManager sm = Map.getInstance().getSqliteManager();
 
	public CollisionSystem() {
		// only need to worry about player collisions
		super(Family.all(CollisionComponent.class,PlayerComponent.class).get());
		
		 cm = ComponentMapper.getFor(CollisionComponent.class);
		 pm = ComponentMapper.getFor(PlayerComponent.class);
		 bc = ComponentMapper.getFor(BulletComponent.class);
		 hb = Map.getInstance().getPlayerHUD().getStatusUI().getHealthBar();
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		// get player collision component
		CollisionComponent cc = cm.get(entity);	
		Entity collidedEntity = cc.collisionEntity;
		TypeComponent thisType = entity.getComponent(TypeComponent.class);

		if(thisType.type == TypeComponent.PLAYER){
			if (collidedEntity != null) {
				TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
				if (type != null) {
					switch(type.type){
					case TypeComponent.ENEMY:
						//do player hit enemy thing
						
						hb.setHP(hb.getHP() - 25);
					
						System.out.println("player hit enemy");
						break;
					case TypeComponent.SCENERY:
						//do player hit scenery thing
						System.out.println("player hit scenery");
						break;
					case TypeComponent.NPC:
						System.out.println("player hit npc");
						if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
							System.out.println("talk");
						}
						break;
					case TypeComponent.BULLET:
						//do player hit bullet thing
						hb.setHP(hb.getHP() - 20);
						System.out.println("player hit bullet");
						break;
					case TypeComponent.OTHER:
						//do player hit other thing
						System.out.println("player hit other");
						break; //technically this isn't needed				
					}
					cc.collisionEntity = null; // collision handled reset component
				} else {
					System.out.println("type is null");
				}
			}
		} else if (thisType.type == TypeComponent.ENEMY) {
			if(collidedEntity != null){
				TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
				if(type != null){
					switch(type.type){
					case TypeComponent.PLAYER:
						System.out.println("enemy hit player");
						break;
					case TypeComponent.ENEMY:
						System.out.println("enemy hit enemy");
						break;
					case TypeComponent.SCENERY:
						System.out.println("enemy hit scenery");
						break;	
					case TypeComponent.OTHER:
						System.out.println("enemy hit other");
						break; 
					default:
						System.out.println("No matching type found");
					}
					cc.collisionEntity = null; // collision handled reset component
				}else{
					System.out.println("type is null");
				}
			}
		} else if (thisType.type == TypeComponent.NPC) {
			if(collidedEntity != null){
				TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
				if(type != null){
					switch(type.type){
					case TypeComponent.PLAYER:
						System.out.println("npc hit player");
						break;
					case TypeComponent.ENEMY:
						System.out.println("npc hit enemy");
						break;
					case TypeComponent.SCENERY:
						System.out.println("npc hit scenery");
						break;	
					case TypeComponent.OTHER:
						System.out.println("npc hit other");
						break; 
					default:
						System.out.println("No matching type found");
					}
					cc.collisionEntity = null; // collision handled reset component
				}else{
					System.out.println("type is null");
				}
			}
		}
	}
}