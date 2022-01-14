package com.mygdx.game.item;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.entities.EntityHandler;

public class InventoryItem extends Image {
	
	static Logger logger = LogManager.getLogger(InventoryItem.class.getName());
	
	private int damage;
	private int defense;
	private int hpRestored;

	//Enum for deciding whether its consumable, equippable, or stackable
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
	
	//enum for deciding how the item is used
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
	
	//enum to assign the item id
	public enum ItemTypeID {		
		BODYARMOR01(1),
		BOOTS01(2),
		HELMET01(3),
		LEGS01(4),
		SHIELD01(5),
		WEAPON01(6),
		BODYARMOR02(7),
		BOOTS02(8),
		HELMET02(9),
		LEGS02(10),
		SHIELD02(11),
		WEAPON02(12),
		BODYARMOR03(13),
		BOOTS03(14),
		HELMET03(15),
		LEGS03(16),
		SHIELD03(17),
		WEAPON03(18),
		KEY1(19),
		ICESWORD(20),
		ICESHIELD(21),
		DESERTSHIELD(22),
		JUNGLESTAFF(23),
		JUNGLEHELMET(24),
		JUNGLECHEST(25),
		KEYCARD01(26),
		KEYCARD02(27),
		KEYCARD03(28),
		KEYCARD04(29),
		KEYCARD05(30),
		APPLE(31),
		CAKE(32),
		BURGER(33),
		DONUT(34);
		
		private int itemTypeID;
		
		ItemTypeID(int itemTypeID) {
			this.itemTypeID = itemTypeID;
		}
		
		public int getValue() {
			return itemTypeID;
		}
		
		public static ItemTypeID getItem(int value) {
		    for (ItemTypeID e : values()) {
		        if (e.itemTypeID == value) {
		            return e;
		        }
		    }
		    return null;
		}
	}
	
	private int itemAttributes;
	private int itemUseType;
	private ItemTypeID itemTypeID;

	/**
	 * Inventory item is created with given texture, attributes, item use type, and id, and then assigned damage/defense given certain IDs 
	 * @param textureRegion - texture to give item
	 * @param itemAttributes - attributes to give item
	 * @param itemUseType - item use type to give item
	 * @param itemTypeID - id to give item
	 */
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
		case LEGS01:
			defense = 1;
			break;
		case LEGS02:
			defense = 2;
			break;
		case LEGS03:
			defense = 3;
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
		case APPLE:
			hpRestored = 25;
			break;
		case DONUT:
			hpRestored = 50;
			break;
		case CAKE:
			hpRestored = 75;
			break;
		case BURGER:
			hpRestored = 100;
			break;
		default:
			break;
		}
		
		logger.info("Inventory item created \n\tItemTypeID: " + itemTypeID);		
	}
	
	/**
	 * Copy constructor to create replicate item
	 * @param inventoryItem - item to replicate
	 */
	public InventoryItem(InventoryItem inventoryItem){
        super();
        this.itemTypeID = inventoryItem.getItemTypeID();
        this.itemAttributes = inventoryItem.getItemAttributes();
        this.itemUseType = inventoryItem.getItemUseType();
        this.defense = inventoryItem.getDefense();
        this.damage = inventoryItem.getDamage();
        logger.info("Inventory item created \n\tItemTypeID: " + itemTypeID);
    }
	
	/**
	 * Transfer shop item to inventory item
	 * @param shopItem - shop item to turn into inventory item
	 */
	public InventoryItem(ShopItem shopItem) {
        super(shopItem.getDrawable());
        this.itemTypeID = shopItem.getItemTypeID();
        this.itemAttributes = shopItem.getItemAttributes();
        this.itemUseType = shopItem.getItemUseType();
        this.damage = shopItem.getDamage();
        this.defense = shopItem.getDefense();
        this.hpRestored = shopItem.getHpRestored();
    }
	
	/**
	 * placeholder constructor
	 */
	public InventoryItem() {
		super();
	}
	
	/**
	 * Gets if the item is stackable
	 * @return stackable boolean
	 */
	public boolean isStackable() {
		return ((itemAttributes & ItemAttribute.STACKABLE.getValue()) == ItemAttribute.STACKABLE.getValue());
	}
	
	/**
	 * Gets if the item is consumable
	 * @return consumable boolean
	 */
	public boolean isConsumable() {
		return ((itemAttributes & ItemAttribute.CONSUMABLE.getValue()) == ItemAttribute.CONSUMABLE.getValue());
	}
	
	/**
	 * Checks whether the inventory item provided is the same as this items id
	 * @param inventoryItem - item to check with this one
	 * @return same type boolean
	 */
	public boolean isSameItemType(InventoryItem inventoryItem) {
		return itemTypeID == inventoryItem.getItemTypeID();
	}
	
	/**
	 * Checks whether item is a weapon
	 * @return is weapon boolean
	 */
	public boolean isInventoryItemWeapon() {
		if ((itemUseType & ItemUseType.WEAPON_ONEHAND.getValue()) == ItemUseType.WEAPON_ONEHAND.getValue() ||
			 (itemUseType & ItemUseType.WEAPON_TWOHAND.getValue()) == ItemUseType.WEAPON_ONEHAND.getValue()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks whether item is defensive
	 * @return is defensive boolean
	 */
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
	
	
	
	public int getHpRestored() {
		return hpRestored;
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
