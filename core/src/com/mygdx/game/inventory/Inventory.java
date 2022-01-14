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

			slotsTable.setBackground(new Image(Utilities.UISKIN.getRegion("itemsBackground")).getDrawable());

			for (int i = 1; i <= INVENTORY_SPACE; i++) {
				InventorySlot inventorySlot = new InventorySlot();
				dragAndDrop.addTarget(new InventorySlotTarget(inventorySlot));
				slotsTable.add(inventorySlot).size(SLOT_WIDTH, SLOT_HEIGHT);
				inventorySlot.addListener(new ClickListener() {

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
	
	 public void addItemToInventory(InventoryItem item, String itemName){
//		 	sm.insertItem(item.getItemTypeID().getValue());		 
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
	 
	 public void removeItemFromInventory(InventoryItem item) {
//		 sm.deleteItem(item.getItemTypeID().getValue());		
		 for (int i = 0; i < sourceCells.size; i++) {
			 InventorySlot inventorySlot = ((InventorySlot) sourceCells.get(i).getActor());
			 if (inventorySlot == null) {
				 continue;
			 }
			 if (inventorySlot.hasItem()) {
				 if (inventorySlot.getTopInventoryItem().getName().equals(item.getName())) {
					 logger.info(item.getName() + " removed to inventory.");
					 inventorySlot.getTopInventoryItem().remove();
				 }
			 }
		 }
	 }
	 
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
	 
	 public HashMap<Label, ShopItem> getItemsToSell() {
		 HashMap<Label, ShopItem> items = new HashMap<Label, ShopItem>();
		 for (int i = 0; i < sourceCells.size; i++) {
			 InventorySlot inventorySlot = ((InventorySlot) sourceCells.get(i).getActor());
			 if (inventorySlot.hasItem()) {
				 items.put(new Label(inventorySlot.getTopInventoryItem().getName(), 
						 Utilities.ACTUAL_UI_SKIN), new ShopItem(inventorySlot.getTopInventoryItem(), 10));
				 
			 }
		 }
		 logger.info("Items to sell retrieved.");
		return items;
	 }
	 
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
