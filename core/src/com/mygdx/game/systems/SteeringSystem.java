package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.GdxAI;
import com.mygdx.game.components.SteeringComponent;

public class SteeringSystem extends IteratingSystem {

	private ComponentMapper<SteeringComponent> sc;
	SteeringComponent sCom;
	
	@SuppressWarnings("unchecked")
	public SteeringSystem() {
		super(Family.all(SteeringComponent.class).get());
		sc = ComponentMapper.getFor(SteeringComponent.class);
	}
	
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		GdxAI.getTimepiece().update(deltaTime);
	}
	
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		sCom = sc.get(entity);
        sCom.update(deltaTime);

		
	}

}
