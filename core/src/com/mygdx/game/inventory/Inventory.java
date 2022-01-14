package com.mygdx.game.inventory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Camera;
import com.mygdx.game.CrashWriter;
import com.mygdx.game.Map;
import com.mygdx.game.SqliteManager;
import com.mygdx.game.Utilities;
import com.mygdx.game.entities.Enemy;
import com.mygdx.game.item.InventoryItem;
import com.mygdx.game.item.InventoryItem.ItemAttribute;
import com.mygdx.game.item.InventoryItem.ItemTypeID;
import com.mygdx.game.item.InventoryItem.ItemUseType;
import com.mygdx.game.item.ShopItem;
import com.mygdx.game.ui.HealthBar;

public class Inventory extends Window {
	
	static Logger logger = LogManager.getLogger(Inventory.class.getName());

	private static TextureRegion background = new TextureRegion(Utilities.UISKIN.getAtlas().findRegion("invBackground"));
	
	private SqliteManager sm;
	
	private Table equipmentTable;
	private Table slotsTable;
	
	private DragAndDrop dragAndDrop;
	private InventorySlot headSlot, leftArmSlot, rightArmSlot, chestSlot, legsSlot, bootsSlot;
	
	private final int NUM_ROWS = 4;
	private final int NUM_COLUMNS = 5;
	private final int INVENTORY_SPACE = NUM_ROWS * NUM_COLUMNS;
	public final int HOTBAR_LENGTH = NUM_COLUMNS;
	
	private final int SLOT_WIDTH = 32;
	private final int SLOT_HEIGHT = 32;
	
	private Array<Actor> inventoryActors;
	
	private Array<Cell> cells;

	private ArrayList<Image> inventoryImages = new ArrayList<Image>();
	
	private Array<Cell> sourceCells;
	private Array<Cell> equipmentCells;

	/**
	 * Instance of the inventory
	 */
	public Inventory() {
		super("Inventory", new WindowStyle(new BitmapFont(), Color.RED, null));
	
		try {
			sm = new SqliteManager();

			dragAndDrop = new DragAndDrop();
			dragAndDrop.setKeepWithinStage(false);

			inventoryActors = new Array<Actor>();

			slotsTable = new Table();
			slotsTable.setName("Slots_Table");

			equipmentTable = new Table();
			equipmentTable.setName("Equipment_Table");
			equipmentTable.defaults().space(10);

			headSlot = new InventorySlot(
					ItemUseType.ARMOR_HELMET.getValue(),
					new Image(Utilities.UISKIN.getRegion("holderHelmet")), true);

			leftArmSlot = new InventorySlot(
					ItemUseType.WEAPON_ONEHAND.getValue() |
					ItemUseType.WEAPON_TWOHAND.getValue(),
					new Image(Utilities.UISKIN.getRegion("holderSword")), true);

			rightArmSlot = new InventorySlot(
					ItemUseType.ARMOR_SHIELD.getValue(),
					new Image(Utilities.UISKIN.getRegion("holderShield")), true);

			chestSlot = new InventorySlot(
					ItemUseType.ARMOR_CHEST.getValue(),
					new Image(Utilities.UISKIN.getRegion("holderChest")), true);

			legsSlot = new InventorySlot(
					ItemUseType.ARMOR_LEGS.getValue(),
					new Image(Utilities.UISKIN.getRegion("holderLegs")), true);

			bootsSlot = new InventorySlot(
					ItemUseType.ARMOR_FEET.getValue(),
					new Image(Utilities.UISKIN.getRegion("holderBoots")), true);

			dragAndDrop.addTarget(new InventorySlotTarget(headSlot));
			dragAndDrop.addTarget(new InventorySlotTarget(leftArmSlot));
			dragAndDrop.addTarget(new InventorySlotTarget(rightArmSlot));
			dragAndDrop.addTarget(new InventorySlotTarget(chestSlot));
			dragAndDrop.addTarget(new InventorySlotTarget(legsSlot));
			dragAndDrop.addTarget(new InventorySlotTarget(bootsSlot));
			
			// armor slots are each manually created to provide them unique inventory slot textures
			
			
			//sets the background of the slots table, creates a psuedo inventory layout
			slotsTable.setBackground(new Image(Utilities.UISKIN.getRegion("itemsBackground")).getDrawable());

			//loops through amount of inventory space (20) and adds an inventory slot to each one
			for (int i = 1; i <= INVENTORY_SPACE; i++) {
				InventorySlot inventorySlot = new InventorySlot();
				dragAndDrop.addTarget(new InventorySlotTarget(inventorySlot));
				slotsTable.add(inventorySlot).size(SLOT_WIDTH, SLOT_HEIGHT);
				inventorySlot.addListener(new ClickListener() {

					//Touch up listener is for double tapping consumables to eat them
					@Override
					public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
						super.touchUp(event, x, y, pointer, button);
						if (getTapCount() == 2) {
							setTapCount(0);
							InventorySlot slot = (InventorySlot) event.getListenerActor();
							if (slot.hasItem()) {
								InventoryItem item = slot.getTopInventoryItem();
								if (item.isConsumable()) {
									HealthBar health = Map.getInstance().getPlayerHUD().getStatusUI().getHealthBar();
									health.setHP(health.getHP() + item.getHpRestored());
									System.out.println(health.getHP());
									slot.removeActor(item);
									slot.remove(item);
								}
							}
						}
					}

				});
				//every multiple of 5 creates a new row in the inventory
				if (i % NUM_COLUMNS == 0) {
					slotsTable.row();
				}
			}	
			
			logger.info("Inventory slots created.");

			equipmentTable.padLeft(10);

			equipmentTable.add();
			equipmentTable.add(headSlot).size(SLOT_WIDTH, SLOT_HEIGHT);
			equipmentTable.row();

			equipmentTable.add(leftArmSlot).size(SLOT_WIDTH, SLOT_HEIGHT);
			equipmentTable.add(chestSlot).size(SLOT_WIDTH, SLOT_HEIGHT);
			equipmentTable.add(rightArmSlot).size(SLOT_WIDTH, SLOT_HEIGHT);
			equipmentTable.row();

			equipmentTable.add();
			equipmentTable.right().add(legsSlot).size(SLOT_WIDTH, SLOT_HEIGHT);
			equipmentTable.row();

			equipmentTable.add();
			equipmentTable.add(bootsSlot).size(SLOT_WIDTH, SLOT_HEIGHT);

			this.setFillParent(true);
			this.add(equipmentTable);
			this.add(slotsTable);
			this.getTitleTable().padTop(300).padLeft(178);
			this.pack();

			logger.info("Inventory layout finished.");

			sourceCells = slotsTable.getCells(); 
			equipmentCells = equipmentTable.getCells();

		} catch (Exception e) {
			logger.error(e.getMessage());
			try {
				CrashWriter cw = new CrashWriter(e);
				cw.writeCrash();
			} catch (IOException e1) {
				logger.error(e1.getMessage());
			}
		}
	}
	
	/**
	 * Any items in the inventory that aren't equipped, but can be, are equipped
	 */
	public void equipEquippableItems() {
		for (int i = 0; i < sourceCells.size; i++) {
			InventorySlot inventorySlot = ((InventorySlot) sourceCells.get(i).getActor());
			if (inventorySlot.hasItem()) {
				 InventoryItem tempItem = inventorySlot.getTopInventoryItem();
				 if (tempItem.isInventoryItemDefensive()) {
					 switch(tempItem.getItemUseType()) {
					 //shield
					 case 32:
						 if (!rightArmSlot.hasItem()) {
							 logger.info("Shield automatically equipped.");
							 rightArmSlot.add(tempItem);
						 }
						 break;		
					 //helmet
					 case 64:
						 if (!headSlot.hasItem()) {
							 logger.info("Helmet automatically equipped.");
							 headSlot.add(tempItem);
						 }
						 break;					 
					 //chest
					 case 128:
						 if (!chestSlot.hasItem()) {
							 logger.info("Chest armor automatically equipped.");
							 chestSlot.add(tempItem);
						 }
						 break;			 
					 //feet
					 case 256:
						 if (!bootsSlot.hasItem()) {
							 logger.info("Boots automatically equipped.");
							 bootsSlot.add(tempItem);
						 }
						 break;				 
					 //legs
					 case 512:
						 if (!legsSlot.hasItem()) {
							 logger.info("Legs automatically equipped.");
							 legsSlot.add(tempItem);
						 }
						 break;	 
					default: 
						 break;
					 }
				 } else if (tempItem.isInventoryItemWeapon()) {
					 if (!leftArmSlot.hasItem()) {
						 logger.info("Weapon automatically equipped.");
						 leftArmSlot.add(tempItem);
					 }
				 }
			}
		}
	}
	
	/**
	 * Any item id's sent into this method are automatically added to the inventory, meant to load id's from database
	 * @param id - id of item from database
	 */
	public void addItemFromDatabase(int id) {
		ItemTypeID item = ItemTypeID.getItem(id);
		switch(item) {
			case APPLE:
				InventoryItem apple = new InventoryItem(Utilities.itemsAtlas.findRegion("helmet01"), ItemAttribute.CONSUMABLE.getValue(), ItemUseType.ITEM_RESTORE_HEALTH.getValue(), ItemTypeID.APPLE);
				addItemToInventory(apple, "Apple");
				break;
			case BODYARMOR01:
				InventoryItem bodyArmor1 = new InventoryItem(Utilities.itemsAtlas.findRegion("armorchest01"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_CHEST.getValue(), ItemTypeID.BODYARMOR01);
				addItemToInventory(bodyArmor1, "Beginner Chestplate");
				break;
			case BODYARMOR02:
				InventoryItem bodyArmor2 = new InventoryItem(Utilities.itemsAtlas.findRegion("chestarmor02"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_CHEST.getValue(), ItemTypeID.BODYARMOR02);
				addItemToInventory(bodyArmor2, "The Green Man's Chestplate");
				break;
			case BODYARMOR03:
				InventoryItem bodyArmor3 = new InventoryItem(Utilities.itemsAtlas.findRegion("armorchest03"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_CHEST.getValue(), ItemTypeID.BODYARMOR03);
				addItemToInventory(bodyArmor3, "Shining Chestplate");
				break;
			case BOOTS01:
				InventoryItem boots1 = new InventoryItem(Utilities.itemsAtlas.findRegion("boots01"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_FEET.getValue(), ItemTypeID.BOOTS01);
				addItemToInventory(boots1, "Beginner Boots");
				break;
			case BOOTS02:
				InventoryItem boots2 = new InventoryItem(Utilities.itemsAtlas.findRegion("boots02"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_FEET.getValue(), ItemTypeID.BOOTS02);
				addItemToInventory(boots2, "Runner's Boots");
				break;
			case BOOTS03:
				InventoryItem boots3 = new InventoryItem(Utilities.itemsAtlas.findRegion("boots03"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_FEET.getValue(), ItemTypeID.BOOTS03);
				addItemToInventory(boots3, "Herme's Great Boots");
				break;
			case BURGER:
				InventoryItem burger = new InventoryItem(Utilities.itemsAtlas.findRegion("helmet01"), ItemAttribute.CONSUMABLE.getValue(), ItemUseType.ITEM_RESTORE_HEALTH.getValue(), ItemTypeID.BURGER);
				addItemToInventory(burger, "Burger");
				break;
			case CAKE:
				InventoryItem cake = new InventoryItem(Utilities.itemsAtlas.findRegion("helmet01"), ItemAttribute.CONSUMABLE.getValue(), ItemUseType.ITEM_RESTORE_HEALTH.getValue(), ItemTypeID.BURGER);
				addItemToInventory(cake, "Cake");
				break;
			case DESERTSHIELD:
				InventoryItem desertShield =  new InventoryItem(Utilities.otherTexturesAtlas.findRegion("desertShield"),ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_SHIELD.getValue(), ItemTypeID.DESERTSHIELD);
				addItemToInventory(desertShield, "Desert Shield");
				break;
			case DONUT:
				InventoryItem donut = new InventoryItem(Utilities.itemsAtlas.findRegion("helmet01"), ItemAttribute.CONSUMABLE.getValue(), ItemUseType.ITEM_RESTORE_HEALTH.getValue(), ItemTypeID.BURGER);
				addItemToInventory(donut, "Donut");
				break;
			case HELMET01:
				InventoryItem helmet1 = new InventoryItem(Utilities.itemsAtlas.findRegion("helmet01"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_HELMET.getValue(), ItemTypeID.HELMET01);
				addItemToInventory(helmet1, "Beginner Helmet");
				break;
			case HELMET02:
				InventoryItem helmet2 = new InventoryItem(Utilities.itemsAtlas.findRegion("helmet02"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_HELMET.getValue(), ItemTypeID.HELMET02);
				addItemToInventory(helmet2, "Helmet of the Forgotten Adventurer");
				break;
			case HELMET03:
				InventoryItem helmet3 = new InventoryItem(Utilities.itemsAtlas.findRegion("helmet03"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_HELMET.getValue(), ItemTypeID.HELMET03);
				addItemToInventory(helmet3, "Orc Warlord's Great Helm");
				break;
			case ICESHIELD:
				InventoryItem iceShield = new InventoryItem(Utilities.otherTexturesAtlas.findRegion("iceShield"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_SHIELD.getValue(), ItemTypeID.ICESHIELD);
				addItemToInventory(iceShield, "Ice Shield");
				break;
			case ICESWORD:
				InventoryItem iceSword = new InventoryItem(Utilities.otherTexturesAtlas.findRegion("iceSword"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.WEAPON_ONEHAND.getValue(), ItemTypeID.ICESWORD);
				addItemToInventory(iceSword, "Ice Sword");
				break;
			case JUNGLECHEST:
				InventoryItem jungleChest = new InventoryItem(Utilities.otherTexturesAtlas.findRegion("jungleChest"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_CHEST.getValue(), ItemTypeID.JUNGLECHEST);;
				addItemToInventory(jungleChest, "Jungle Chestplate");
				break;
			case JUNGLEHELMET:
				InventoryItem jungleHelmet = new InventoryItem(Utilities.otherTexturesAtlas.findRegion("jungleHelmet"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_HELMET.getValue(), ItemTypeID.JUNGLEHELMET);
				addItemToInventory(jungleHelmet, "Jungle Helmet");
				break;
			case JUNGLESTAFF:
				InventoryItem jungleStaff =  new InventoryItem(Utilities.otherTexturesAtlas.findRegion("jungleStaff"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.WEAPON_ONEHAND.getValue(), ItemTypeID.JUNGLESTAFF);
				addItemToInventory(jungleStaff, "Jungle Staff");
				break;
			case LEGS01:
				InventoryItem legs1 = new InventoryItem(Utilities.itemsAtlas.findRegion("legs01"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_LEGS.getValue(), ItemTypeID.LEGS01);
				addItemToInventory(legs1, "Beginner Legs");
				break;
			case LEGS02:
				InventoryItem legs2 = new InventoryItem(Utilities.itemsAtlas.findRegion("legs02"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_LEGS.getValue(), ItemTypeID.LEGS02);
				addItemToInventory(legs2, "Sailer's Legs");
				break;
			case LEGS03:
				InventoryItem legs3 = new InventoryItem(Utilities.itemsAtlas.findRegion("legs03"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_LEGS.getValue(), ItemTypeID.LEGS03);
				addItemToInventory(legs3, "Carnivorous Legs");
				break;
			case SHIELD01:
				InventoryItem shield1 = new InventoryItem(Utilities.itemsAtlas.findRegion("shield01"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_SHIELD.getValue(), ItemTypeID.SHIELD01);
				addItemToInventory(shield1, "Beginner Shield");
				break;
			case SHIELD02:
				InventoryItem shield2 = new InventoryItem(Utilities.itemsAtlas.findRegion("shield02"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_SHIELD.getValue(), ItemTypeID.SHIELD02);
				addItemToInventory(shield2, "Iron Shield");
				break;
			case SHIELD03:
				InventoryItem shield3 = new InventoryItem(Utilities.itemsAtlas.findRegion("shield03"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_SHIELD.getValue(), ItemTypeID.SHIELD03);
				addItemToInventory(shield3, "Unpenetrable Shield");
				break;
			case WEAPON01:
				InventoryItem weapon1 = new InventoryItem(Utilities.itemsAtlas.findRegion("sword01"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.WEAPON_ONEHAND.getValue(), ItemTypeID.WEAPON01);
				addItemToInventory(weapon1, "Beginner Sword");
				break;
			case WEAPON02:
				InventoryItem weapon2 = new InventoryItem(Utilities.itemsAtlas.findRegion("sword02"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.WEAPON_ONEHAND.getValue(), ItemTypeID.WEAPON02);
				addItemToInventory(weapon2, "Great Sword");
				break;
			case WEAPON03:
				InventoryItem weapon3 = new InventoryItem(Utilities.itemsAtlas.findRegion("sword03"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.WEAPON_ONEHAND.getValue(), ItemTypeID.WEAPON03);
				addItemToInventory(weapon3, "Legend's Sword");
				break;
			default:
				break;
		}
		equipEquippableItems();
	} 
	
	/**
	 * Provided inventory item is added to the inventory in the first available slot
	 * @param item - inventory item to add
	 * @param itemName - name of item
	 */
	 public void addItemToInventory(InventoryItem item, String itemName){
		 	sm.insertItem(item.getItemTypeID().getValue());		 
            for (int i = 0; i < sourceCells.size; i++) {
	                InventorySlot inventorySlot = ((InventorySlot) sourceCells.get(i).getActor());
	                if (inventorySlot == null)  {
	                	continue;            
	                }
	                int numItems = inventorySlot.getNumItems();
	                if (numItems == 0) {
	                    item.setName(itemName);
	                    logger.info(item.getName() + " added to inventory.");
	                    inventorySlot.add(item);
	                    dragAndDrop.addSource(new InventorySlotSource(inventorySlot, dragAndDrop));           
	                    break;
	                }
	            }
	    }
	 
	 /**
	  * Removes the given inventory item based off it's name and the first correlated name in the current inventory
	  * @param item - item to remove
	  */
	 public void removeItemFromInventory(InventoryItem item) {
		 sm.deleteItem(item.getItemTypeID().getValue());		
		 for (int i = 0; i < sourceCells.size; i++) {
			 InventorySlot inventorySlot = ((InventorySlot) sourceCells.get(i).getActor());
			 if (inventorySlot == null) {
				 continue;
			 }
			 if (inventorySlot.hasItem()) {
				 if (inventorySlot.getTopInventoryItem().getName().equals(item.getName())) {
					 logger.info(item.getName() + " removed to inventory.");
					 inventorySlot.getTopInventoryItem().remove();
					 break;
				 }
			 }
		 }
	 }
	 
	 /**
	  * Loops through all items equipped and in inventory and removes them from the correlated slot
	  */
	 public void removeAllItemsFromInventory() {
		 for (int i = 0; i < sourceCells.size; i++) {
			 InventorySlot inventorySlot = ((InventorySlot) sourceCells.get(i).getActor());
			 if (inventorySlot == null) {
				 continue;
			 }
			 if (inventorySlot.hasItem()) {
				 inventorySlot.getTopInventoryItem().remove();
			 }
		 }
		 for (int i = 0; i < equipmentCells.size; i++) {
			 InventorySlot inventorySlot = ((InventorySlot) equipmentCells.get(i).getActor());
			 if (inventorySlot == null) {
				 continue;
			 }
			 if (inventorySlot.hasItem()) {
				 inventorySlot.getTopInventoryItem().remove();
			 }
		 }
	 }
	 
	 /**
	  * Gets an array list of integers correlated to all the items in the current inventory
	  * @return item ID integer arraylist
	  */
	 public ArrayList<Integer> getAllItemIDs() {
		 ArrayList<Integer> itemIDs = new ArrayList<Integer>();
		 for (int i = 0; i < sourceCells.size; i++) {
			 InventorySlot inventorySlot = ((InventorySlot) sourceCells.get(i).getActor());
			 if (inventorySlot.hasItem()) {
				 InventoryItem tempItem = inventorySlot.getTopInventoryItem();
				 itemIDs.add(tempItem.getItemTypeID().getValue());
			 }
		 } 
		 logger.info("All item ID's in inventory retrieved.");
		return itemIDs;	 
	 }
	 
	 /**
	  * Gets the IDs of all equipped items
	  * @return equipped item ID integer arraylist
	  */
	 public ArrayList<Integer> getAllEquippedItemIDs() {
		 ArrayList<Integer> itemIDs = new ArrayList<Integer>();
		 for (int i = 0; i < equipmentCells.size; i++) {
			 InventorySlot inventorySlot = ((InventorySlot) equipmentCells.get(i).getActor());
			 if (inventorySlot.hasItem()) {
				 InventoryItem tempItem = inventorySlot.getTopInventoryItem();
				 itemIDs.add(tempItem.getItemTypeID().getValue());
			 }
		 } 
		 logger.info("All item ID's of equipped gear retrieved.");
		return itemIDs;	 
	 }
	 
	 /**
	  * Gets all the items in the inventory, but as a Label,ShopItem hashmap that is sent to the shop
	  * @return hashmap of labels and shopitems to sell
	  */
	 public HashMap<Label, ShopItem> getItemsToSell() {
		 HashMap<Label, ShopItem> items = new HashMap<Label, ShopItem>();
		 for (int i = 0; i < sourceCells.size; i++) {
			 InventorySlot inventorySlot = ((InventorySlot) sourceCells.get(i).getActor());
			 if (inventorySlot.hasItem()) {
				 //Label is the name of the inventory slot. It needs to be a label to use as an actor in the shop table
				 items.put(new Label(inventorySlot.getTopInventoryItem().getName(), 
						 Utilities.ACTUAL_UI_SKIN), new ShopItem(inventorySlot.getTopInventoryItem(), 10));
				 
			 }
		 }
		 logger.info("Items to sell retrieved.");
		return items;
	 }
	 
	 /**
	  * Calculates the players defense based off its currently equipped armor
	  * @return total player defense
	  */
	 public int getPlayerDefense() {
		 int defense = 0;
		 if (headSlot.hasItem()) {
			 defense += headSlot.getTopInventoryItem().getDefense();
		 }
		 if (rightArmSlot.hasItem()) {
			 defense += rightArmSlot.getTopInventoryItem().getDefense();
		 }
		 if (chestSlot.hasItem()) {
			 defense+= chestSlot.getTopInventoryItem().getDefense();
		 }
		 if (legsSlot.hasItem()) {
			 defense+= legsSlot.getTopInventoryItem().getDefense();
		 } 
		 if (bootsSlot.hasItem()) {
			 defense += bootsSlot.getTopInventoryItem().getDefense();
		 }
		 return defense;
	 }
	 
	 /**
	  * Calculates player damage based off equipped weapon
	  * @return total player damage
	  */
	 public int getPlayerDamage() {
		 int damage = 0;
		 if (leftArmSlot.hasItem()) {
			 damage += leftArmSlot.getTopInventoryItem().getDamage();
		 }
		 return damage;
	 }

	public Table getSlotsTable() {
		return slotsTable;
	}
	
}
