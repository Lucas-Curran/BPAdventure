package com.my.gdx.game.inventory;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Item {
	
	String name;
	TextureRegion texture;
	
	public Item(String name, TextureRegion texture) {
		this.name = name;
		this.texture = texture;
	}	
}
