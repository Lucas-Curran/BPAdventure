package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;

public class EnemyComponent implements Component {
	
	public boolean isDead = false;
	public float xPostCenter = -1;
	public float yPostCenter = -1;
	public boolean isGoingLeft = false;
	public boolean isGoingUp = false;
	public boolean isFalling = false;
	public int range = 2;
	public static enum EnemyState {
		PATROL		(0),
		BOUNCE		(1),
		VERTICAL	(2),
		JUMP		(3),
		STEERING	(4);	

		private final int value;
		
		EnemyState(int value) {
			this.value = value;
		}
		
		public int getValue() { return value; }
	}
	public EnemyState enemyMode = EnemyState.PATROL;
}
