package com.mygdx.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.components.B2dBodyComponent;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.TestComponent;
import com.mygdx.game.components.TextureComponent;
import com.mygdx.game.components.TransformComponent;
import com.mygdx.game.components.TypeComponent;

public class TestEntity extends EntityHandler {

	public Entity addTest() {
		Entity entity = pooledEngine.createEntity();
		TestComponent test = pooledEngine.createComponent(TestComponent.class);
		entity.add(test);
		return entity;
	}
	
}
