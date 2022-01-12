package com.mygdx.game.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.item.InventoryItem;
import com.mygdx.game.item.InventoryItem.ItemTypeID;

public class ShopItem extends Image {
	
	private int itemAttributes;
	private int itemUseType;
	private ItemTypeID itemTypeID;
	private int defense;
	private int damage;
	private int cost;
	
	public ShopItem(InventoryItem item, int cost) {
		super(item.getDrawable());
		itemAttributes = item.getItemAttributes();
		itemUseType = item.getItemUseType();
		itemTypeID = item.getItemTypeID();
		defense = item.getDefense();
		damage = item.getDamage();
		this.cost = cost;
	}
	
	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getItemAttributes() {
		return itemAttributes;
	}

	public void setItemAttributes(int itemAttributes) {
		this.itemAttributes = itemAttributes;
	}

	public int getItemUseType() {
		return itemUseType;
	}

	public void setItemUseType(int itemUseType) {
		this.itemUseType = itemUseType;
	}

	public ItemTypeID getItemTypeID() {
		return itemTypeID;
	}

	public void setItemTypeID(ItemTypeID itemTypeID) {
		this.itemTypeID = itemTypeID;
	}
		
	
}
