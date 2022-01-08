package com.mygdx.game.entities;

import java.util.HashMap;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.components.B2dBodyComponent;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.NPCComponent;
import com.mygdx.game.components.PlayerComponent;
import com.mygdx.game.components.TextureComponent;
import com.mygdx.game.components.TransformComponent;
import com.mygdx.game.components.TypeComponent;

public class NPC extends EntityHandler {
	
	public Entity spawnNPC(String[] text, int posx, int posy, TextureRegion npcTexture) {
		
				Entity entity = pooledEngine.createEntity();
				B2dBodyComponent b2dbody = pooledEngine.createComponent(B2dBodyComponent.class);
				TransformComponent position = pooledEngine.createComponent(TransformComponent.class);
				TextureComponent texture = pooledEngine.createComponent(TextureComponent.class);
				CollisionComponent colComp = pooledEngine.createComponent(CollisionComponent.class);
				TypeComponent type = pooledEngine.createComponent(TypeComponent.class);
				NPCComponent npcComp = pooledEngine.createComponent(NPCComponent.class);

				// create the data for the components and add them to the components
				b2dbody.body = bodyFactory.makeCirclePolyBody(posx, posy, 1, BodyFactory.OTHER, BodyType.KinematicBody,true, true);
				// set object position (x,y,z) z used to define draw order 0 first drawn
				position.position.set(b2dbody.body.getPosition().x, b2dbody.body.getPosition().y, 0);
				texture.region = npcTexture;
				type.type = TypeComponent.NPC;
				npcComp.text = text;
				b2dbody.body.setUserData(entity);
				
				// add the components to the entity
				entity.add(npcComp);
				entity.add(type);
				entity.add(colComp);
				entity.add(b2dbody);
				entity.add(position);
				entity.add(texture);
				
				return entity;
	}
	
	
	
}
