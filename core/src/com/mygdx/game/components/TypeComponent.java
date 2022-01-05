package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;

public class TypeComponent implements Component {
	public static final int PLAYER = 0;
	public static final int ENEMY = 1;
	public static final int SCENERY = 2;
	public static final int NPC = 3;
	public static final int BULLET = 4;
	public static final int OTHER = 5;
	
	public int type = OTHER;
	
}
