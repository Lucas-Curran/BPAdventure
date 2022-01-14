package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.components.NPCComponent;

public class NPCSystem extends IteratingSystem {

	ComponentMapper<NPCComponent> nm;
	
	//Unused system, NPC AI unimplemented
	
	public NPCSystem(Family family) {
		super(Family.all(NPCComponent.class).get());
		nm = ComponentMapper.getFor(NPCComponent.class);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		NPCComponent nc = nm.get(entity);
		
	}

}
