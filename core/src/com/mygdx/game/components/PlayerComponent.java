package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;

public class PlayerComponent implements Component {
	public boolean player = false;
	
	public final int LEFT = 1;
	public final int RIGHT = 2;
	
	public int direction = 0;
}
