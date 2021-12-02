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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Camera;
import com.mygdx.game.Map;
import com.mygdx.game.Utilities;
import com.mygdx.game.item.InventoryItem;
import com.mygdx.game.item.InventoryItem.ItemUseType;
import com.mygdx.game.item.Item;

public class Inventory extends Window {

	private static TextureRegion background = new TextureRegion(Utilities.UISKIN.getAtlas().findRegion("invBackground"));
	
	private Table equipmentTable;
	private Table slotsTable;
	
	private DragAndDrop dragAndDrop;
	
	private final int NUM_ROWS = 4;
	private final int NUM_COLUMNS = 5;
	private final int INVENTORY_SPACE = NUM_ROWS * NUM_COLUMNS;
	public final int HOTBAR_LENGTH = NUM_COLUMNS;
	
	private final int SLOT_WIDTH = 32;
	private final int SLOT_HEIGHT = 32;
	
	private Array<Actor> inventoryActors;
	
	private Array<Cell> cells;
	
	private ScreenViewport stageViewport;
	
	private ArrayList<Image> inventoryImages = new ArrayList<Image>();

	public Inventory() {
		super("Inventory", new WindowStyle(new BitmapFont(), Color.GREEN, new Image(background).getDrawable()));
		
		dragAndDrop = new DragAndDrop();
		inventoryActors = new Array<Actor>();
		
		slotsTable = new Table();
		slotsTable.setName("Slots_Table");
		
		equipmentTable = new Table();
		equipmentTable.setName("Equipment_Table");
		equipmentTable.defaults().space(10);
		
		InventorySlot headSlot = new InventorySlot(
				ItemUseType.ARMOR_HELMET.getValue(),
				new Image(Utilities.UISKIN.getRegion("holderHelmet")));
		
		InventorySlot leftArmSlot = new InventorySlot(
				ItemUseType.WEAPON_ONEHAND.getValue() |
				ItemUseType.WEAPON_TWOHAND.getValue(),
				new Image(Utilities.UISKIN.getRegion("holderSword")));
		
		InventorySlot rightArmSlot = new InventorySlot(
				ItemUseType.ARMOR_SHIELD.getValue(),
				new Image(Utilities.UISKIN.getRegion("holderShield")));
		
		InventorySlot chestSlot = new InventorySlot(
				ItemUseType.ARMOR_CHEST.getValue(),
				new Image(Utilities.UISKIN.getRegion("holderChest")));
		
		InventorySlot legsSlot = new InventorySlot(
				ItemUseType.ARMOR_LEGS.getValue(),
				new Image(Utilities.UISKIN.getRegion("holderLegs")));
		
		InventorySlot bootsSlot = new InventorySlot(
				ItemUseType.ARMOR_FEET.getValue(),
				new Image(Utilities.UISKIN.getRegion("holderBoots")));
		
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
						InventorySlot slot = (InventorySlot) event.getListenerActor();
						if (slot.hasItem()) {
							InventoryItem item = slot.getTopInventoryItem();
							if (item.isConsumable()) {
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
		
		equipmentTable.add();
		equipmentTable.add(headSlot).size(SLOT_WIDTH, SLOT_HEIGHT);
		equipmentTable.row();
		
		equipmentTable.add(leftArmSlot).size(SLOT_WIDTH, SLOT_HEIGHT);
		equipmentTable.add(chestSlot).size(SLOT_WIDTH, SLOT_HEIGHT);
		equipmentTable.add(rightArmSlot).size(SLOT_WIDTH, SLOT_HEIGHT);
		equipmentTable.row();
		
		equipmentTable.add();
		equipmentTable.right().add(legsSlot).size(SLOT_WIDTH, SLOT_HEIGHT);
		
		this.add(slotsTable).colspan(2);
		this.row();
		this.pack();
		
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
	
}
