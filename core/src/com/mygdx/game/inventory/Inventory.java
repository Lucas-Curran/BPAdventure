package com.mygdx.game.inventory;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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
import com.mygdx.game.item.Item;

public class Inventory extends Stage {
	
	private Stage stage;
	private Table table;
	private TextureRegion tex;
	private TextureRegion background;
	private TextureAtlas atlas;
	private Image backgroundImage;
	private Image image;
	
	private Table itemsTable;
	
	private Camera cam;
	
	private HashMap<Integer, Item> items;
	
	private DragAndDrop dragAndDrop;
	
	private final float NUM_ROWS = 4;
	private final float NUM_COLUMNS = 5;
	private final float INVENTORY_SPACE = NUM_ROWS * NUM_COLUMNS;
	public final float HOTBAR_LENGTH = NUM_COLUMNS;
	
	private Array<Cell> cells;
	
	private int slotsFull;
	private Utilities utilities;
	
	private ScreenViewport stageViewport;
	
	private Image blankImage;
	
	private Group inventoryGroup;
	
	private ArrayList<Image> inventoryImages = new ArrayList<Image>();

	public Inventory() {
		stageViewport = new ScreenViewport();
		
		stage = new Stage(stageViewport);
		table = new Table();
		
		table.setFillParent(true);
		table.setDebug(true);
		table.pad(2);
		table.bottom();
		table.setFillParent(true);
		
		atlas = new TextureAtlas(Gdx.files.internal("textures.txt"));
		background = atlas.findRegion("inventory");
		backgroundImage = new Image(background);
		
		table.setBackground(backgroundImage.getDrawable());
		table.setVisible(false);
		
		TextureRegion itemsBackground = atlas.findRegion("itemsBackground");
		Image itemsImage = new Image(itemsBackground);
		
		itemsTable = new Table();
		itemsTable.setBackground(itemsImage.getDrawable());
		itemsTable.setBounds(0, 0, NUM_COLUMNS * 32, NUM_ROWS * 32);
		
		items = new HashMap<Integer, Item>();

		dragAndDrop = new DragAndDrop();
		
		inventoryGroup = new Group();
		
		inventoryGroup.addActor(itemsTable);
		
		stage.addActor(table);
		stage.addActor(inventoryGroup);

		cells = itemsTable.getCells();
		
		slotsFull = 0;
		
		utilities = new Utilities();
		
		createGrid();
		
	}
	
	public void render() {
		stageViewport.apply();
		stageViewport.setScreenBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage.act();
		stage.draw();
	}
	
	public void createGrid() {
		for (int i = 0; i < NUM_ROWS; i++) {
			for (int j = 0; j < NUM_COLUMNS; j++) {
				itemsTable.add().width(32).height(32);
				Image blankImageHolder = new Image();
				blankImage = new Image();
				cells.get(i * 5 + j).setActor(blankImage);
				inventoryImages.add(blankImageHolder);
				cells.get(i * 5 + j).getActor().setName("blank");
				utilities.addToDragAndDrop(dragAndDrop, blankImage, itemsTable, false, true);
			}
			
			itemsTable.row();
		}
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public void dispose() {
		stage.dispose();
		atlas.dispose();
		cam.dispose();
	}

	public void showInventory() {
		table.setVisible(true);
	}
	
	public void closeInventory() {
		table.setVisible(false);
	}
	
	public boolean isVisible() {
		if (table.isVisible()) {
			return true;
		} else {
			return false;
		}
	}
	
	public void addItem(Item item) {
		if (slotsFull != INVENTORY_SPACE) {
			
			image = new Image(item.texture);	
			Image holderImage = new Image(item.texture);
			
			outerfor:
			for (int i = 0; i < NUM_ROWS; i++) {
				for (int j = 0; j < NUM_COLUMNS; j++) {
					if (cells.get(i * 5 + j).getActor().getName() == "blank") {
						cells.get(i*5 + j).setActor(image);
						cells.get(i*5+j).getActor().setName(item.name);
						inventoryImages.set(i * 5 + j, holderImage);
						break outerfor;
					}
				}
			}
			
			slotsFull++;
			utilities.addToDragAndDrop(dragAndDrop, image, itemsTable, true, true);
		}
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
}
