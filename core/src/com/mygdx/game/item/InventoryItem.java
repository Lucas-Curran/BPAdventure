package com.mygdx.game.item;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class InventoryItem extends Image {
	
	public enum ItemAttribute {
		CONSUMABLE(1),
		EQUIPPABLE(2),
		STACKABLE(4);
		
		private int attribute;
		
		ItemAttribute(int attribute) {
			this.attribute = attribute;
		}
		
		public int getValue() {
			return attribute;
		}
		
	}
	
	public enum ItemUseType {
		ITEM_RESTORE_HEALTH(1),
	    ITEM_RESTORE_MP(2),
	    ITEM_DAMAGE(4),
	    WEAPON_ONEHAND(8),
	    WEAPON_TWOHAND(16),
	    ARMOR_SHIELD(32),
	    ARMOR_HELMET(64),
	    ARMOR_CHEST(128),
	    ARMOR_FEET(256),
		ARMOR_LEGS(512);
		
		private int itemUseType;
		
		ItemUseType(int itemUseType) {
			this.itemUseType = itemUseType;
		}
		
		public int getValue() {
			return itemUseType;
		}
	}
	
	public enum ItemTypeID {
		ARMOR01,
		BOOTS01,
		HELMET01,
		LEGS01,
		SHIELD01,
		WEAPON01,
		POTIONS01,
		NONE;
	}
	
	private int itemAttributes;
	private int itemUseType;
	private ItemTypeID itemTypeID;

	public InventoryItem(TextureRegion textureRegion, int itemAttributes, int itemUseType, ItemTypeID itemTypeID) {
		super(textureRegion);
		this.itemTypeID = itemTypeID;
		this.itemAttributes = itemAttributes;
		this.itemUseType = itemUseType;
	}
	
	public InventoryItem() {
		super();
	}
	
	public boolean isStackable() {
		return ((itemAttributes & ItemAttribute.STACKABLE.getValue()) == ItemAttribute.STACKABLE.getValue());
	}
	
	public boolean isConsumable() {
		return ((itemAttributes & ItemAttribute.CONSUMABLE.getValue()) == ItemAttribute.CONSUMABLE.getValue());
	}
	
	public boolean isSameItemType(InventoryItem inventoryItem) {
		return itemTypeID == inventoryItem.getItemTypeID();
	}
	
	public boolean isInventoryItemWeapon() {
		if ((itemUseType & ItemUseType.WEAPON_ONEHAND.getValue()) == ItemUseType.WEAPON_ONEHAND.getValue() ||
			 (itemUseType & ItemUseType.WEAPON_TWOHAND.getValue()) == ItemUseType.WEAPON_ONEHAND.getValue()) {
			return true;
		}
		return false;
	}
	
	public boolean isInventoryItemDefensive() {
		if ((itemUseType & ItemUseType.ARMOR_CHEST.getValue()) == ItemUseType.ARMOR_CHEST.getValue() ||
                (itemUseType & ItemUseType.ARMOR_HELMET.getValue()) == ItemUseType.ARMOR_HELMET.getValue() ||
                (itemUseType & ItemUseType.ARMOR_FEET.getValue()) == ItemUseType.ARMOR_FEET.getValue() ||
                (itemUseType & ItemUseType.ARMOR_SHIELD.getValue()) == ItemUseType.ARMOR_SHIELD.getValue() ||
                (itemUseType & ItemUseType.ARMOR_LEGS.getValue()) == ItemUseType.ARMOR_LEGS.getValue()) {
            return true;
		}
		return false;
	}
	
	public int getItemUseType() {
		return itemUseType;
	}
	
	public ItemTypeID getItemTypeID() {
		return itemTypeID;
	}
	
}
