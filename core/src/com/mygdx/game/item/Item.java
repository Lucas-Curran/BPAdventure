package com.mygdx.game.item;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Item {
	
	public String name;
	public TextureRegion texture;
	
	public Item(String name, TextureRegion texture) {
		this.name = name;
		this.texture = texture;
	}
	
	public boolean isFood() { return this instanceof Food; }
	public boolean isWeapon() { return this instanceof Weapon; }
	public boolean isArmor() { return this instanceof Armor; }
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setTexture(TextureRegion texture) {
		this.texture = texture;
	}
	
}
