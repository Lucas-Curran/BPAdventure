package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class CollisionComponent implements Component {
	public Entity collisionEntity;
	public boolean hitSensor = false;
}
