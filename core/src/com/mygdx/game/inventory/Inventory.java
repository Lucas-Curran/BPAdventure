package com.mygdx.game.inventory;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Camera;
import com.mygdx.game.Map;
import com.mygdx.game.Utilities;
import com.mygdx.game.item.InventoryItem;
import com.mygdx.game.item.ShopItem;
import com.mygdx.game.item.InventoryItem.ItemUseType;
import com.mygdx.game.ui.HealthBar;
import com.mygdx.game.item.InventoryItem.ItemTypeID;

public class Inventory extends Window {

	private static TextureRegion background = new TextureRegion(Utilities.UISKIN.getAtlas().findRegion("invBackground"));
	
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

	public Inventory() {
		super("Inventory", new WindowStyle(new BitmapFont(), Color.RED, null));
	
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
		
		sourceCells = slotsTable.getCells(); 
	}
	
	 public void addItemToInventory(InventoryItem item, String itemName){
		 
            for (int i = 0; i < sourceCells.size; i++) {
	                InventorySlot inventorySlot = ((InventorySlot) sourceCells.get(i).getActor());
	                if (inventorySlot == null)  {
	                	continue;            
	                }
	                int numItems = inventorySlot.getNumItems();
	                if (numItems == 0) {
	                    item.setName(itemName);
	                    inventorySlot.add(item);
	                    dragAndDrop.addSource(new InventorySlotSource(inventorySlot, dragAndDrop));           
	                    break;
	                }
	            }
	    }
	 
	 public void removeItemFromInventory(InventoryItem item) {
		 for (int i = 0; i < sourceCells.size; i++) {
			 InventorySlot inventorySlot = ((InventorySlot) sourceCells.get(i).getActor());
			 if (inventorySlot == null) {
				 continue;
			 }
			 if (inventorySlot.hasItem()) {
				 if (inventorySlot.getTopInventoryItem().getName().equals(item.getName())) {
					 inventorySlot.getTopInventoryItem().remove();
				 }
			 }
		 }
	 }
	 
	 public HashMap<Label, ShopItem> getAllItems() {
		 HashMap<Label, ShopItem> items = new HashMap<Label, ShopItem>();
		 for (int i = 0; i < sourceCells.size; i++) {
			 InventorySlot inventorySlot = ((InventorySlot) sourceCells.get(i).getActor());
			 if (inventorySlot.hasItem()) {
				 items.put(new Label(inventorySlot.getTopInventoryItem().getName(), 
						 Utilities.ACTUAL_UI_SKIN), new ShopItem(inventorySlot.getTopInventoryItem(), 10));
				 
			 }
		 }
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
	 
	
	public ArrayList<Cell> getHotbarItems() {
		ArrayList<Cell> hotbarItems = new ArrayList<Cell>();
		for (int i = 0; i < HOTBAR_LENGTH; i++) {
			hotbarItems.add(i, cells.get(i));
		}
		return hotbarItems;
	}
	
	public ArrayList<Image> getHotbarImages() {
		return inventoryImages;
	}
	
	public Array<Actor> getInventoryActors() {
		return inventoryActors;
	}

	public Table getSlotsTable() {
		return slotsTable;
	}
	
}
