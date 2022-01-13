package com.mygdx.game.item;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.ui.ShopItem;

public class InventoryItem extends Image {
	
	private int damage;
	private int defense;
	
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
	    ITEM_RESTORE_MP    (2),
	    ITEM_DAMAGE        (4),
	    WEAPON_ONEHAND     (1),
	    WEAPON_TWOHAND    (16),
	    ARMOR_SHIELD      (32),
	    ARMOR_HELMET      (64),
	    ARMOR_CHEST      (128),
	    ARMOR_FEET       (256),
		ARMOR_LEGS       (512);
	
		private int itemUseType;
		
		ItemUseType(int itemUseType) {
			this.itemUseType = itemUseType;
		}
		
		public int getValue() {
			return itemUseType;
		}
	}
	
	public enum ItemTypeID {		
		BODYARMOR01,
		BOOTS01,
		HELMET01,
		LEGS01,
		SHIELD01,
		WEAPON01,
		POTIONS01,
		BODYARMOR02,
		BOOTS02,
		HELMET02,
		LEGS02,
		SHIELD02,
		WEAPON02,
		POTIONS02,
		BODYARMOR03,
		BOOTS03,
		HELMET03,
		LEGS03,
		SHIELD03,
		WEAPON03,
		POTIONS03,
		KEY1,
		ICESWORD,
		ICESHIELD,
		DESERTSHIELD,
		JUNGLESTAFF,
		JUNGLEHELMET,
		JUNGLECHEST,
		KEYCARD01,
		KEYCARD02,
		KEYCARD03,
		KEYCARD04,
		KEYCARD05,
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
		
		switch(itemTypeID) {
		
		case BODYARMOR01:
			defense = 1;
			break;
		case BODYARMOR02:
			defense = 2;
			break;
		case BODYARMOR03:
			defense = 3;
			break;
		case BOOTS01:
			defense = 1;
			break;
		case BOOTS02:
			defense = 2;
			break;
		case BOOTS03:
			defense = 3;
			break;
		case DESERTSHIELD:
			defense = 3;
			break;
		case HELMET01:
			defense = 1;
			break;
		case HELMET02:
			defense = 2;
			break;
		case HELMET03:
			defense = 3;
			break;
		case ICESHIELD:
			defense = 2;
			break;
		case ICESWORD:
			damage = 3;
			break;
		case JUNGLECHEST:
			break;
		case JUNGLEHELMET:
			defense = 2;
			break;
		case JUNGLESTAFF:
			break;
		case KEY1:
			break;
		case LEGS01:
			defense = 1;
			break;
		case LEGS02:
			defense = 2;
			break;
		case LEGS03:
			defense = 3;
			break;
		case NONE:
			break;
		case POTIONS01:
			break;
		case POTIONS02:
			break;
		case POTIONS03:
			break;
		case SHIELD01:
			defense = 1;
			break;
		case SHIELD02:
			defense = 2;
			break;
		case SHIELD03:
			defense = 3;
			break;
		case WEAPON01:
			damage = 2;
			break;
		case WEAPON02:
			damage = 3;
			break;
		case WEAPON03:
			damage = 4;
			break;
		case KEYCARD01:
			break;
		case KEYCARD02:
			break;
		case KEYCARD03:
			break;
		case KEYCARD04:
			break;
		case KEYCARD05:
			break;
		default:
			break;
		}
	}
	
	public InventoryItem(InventoryItem inventoryItem){
        super();
        this.itemTypeID = inventoryItem.getItemTypeID();
        this.itemAttributes = inventoryItem.getItemAttributes();
        this.itemUseType = inventoryItem.getItemUseType();
        this.defense = inventoryItem.getDefense();
        this.damage = inventoryItem.getDamage();
    }
	
	public InventoryItem(ShopItem shopItem) {
        super(shopItem.getDrawable());
        this.itemTypeID = shopItem.getItemTypeID();
        this.itemAttributes = shopItem.getItemAttributes();
        this.itemUseType = shopItem.getItemUseType();
        this.damage = shopItem.getDamage();
        this.defense = shopItem.getDefense();
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
	
	public int getItemAttributes() {
		return itemAttributes;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public int getDefense() {
		return defense;
	}
	
}
