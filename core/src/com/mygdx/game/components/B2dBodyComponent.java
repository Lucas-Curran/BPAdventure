package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;

public class B2dBodyComponent implements Component {
	public Body body;
	public boolean isDead = false;
}
