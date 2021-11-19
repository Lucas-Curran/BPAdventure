package com.mygdx.game.item;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Weapon extends Item {
	
	int damage;
	
	public Weapon(String name, TextureRegion texture) {
		super(name, texture);
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public int getDamage() {
		return damage;
	}
	
}
