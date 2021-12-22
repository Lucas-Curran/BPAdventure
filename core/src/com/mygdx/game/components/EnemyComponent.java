package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;

public class EnemyComponent implements Component {
	
	public boolean isDead = false;
	public float xPostCenter = -1;
	public float yPostCenter = -1;
	public boolean isGoingLeft = false;
	public boolean isFalling = false;
	public int range = 2;
}
