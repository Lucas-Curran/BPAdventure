package com.mygdx.game.inventory;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
import com.mygdx.game.Camera;
import com.mygdx.game.Hotbar;
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
	
	private Camera cam;
	
	private HashMap<Integer, Item> items;
	
	private DragAndDrop dragAndDrop;
	
	private final float NUM_ROWS = 4;
	private final float NUM_COLUMNS = 5;
	private final float INVENTORY_SPACE = NUM_ROWS * NUM_COLUMNS;
	
	private Array<Cell> cells;
	
	private int slotsFull;
	private Utilities utilities;

	private Hotbar hotbar;
	
	private ScreenViewport stageViewport;
	
	private Image blankImage;

	public Inventory() {
		cam = new Camera();
		
		stage = new Stage();
		table = new Table();
		table.setFillParent(true);
		table.setDebug(true);
		table.pad(2);
		table.bottom();
		
		atlas = new TextureAtlas(Gdx.files.internal("adventureatlas.txt"));
		background = atlas.findRegion("inventory");
		backgroundImage = new Image(background);
		
		table.setBackground(backgroundImage.getDrawable());
		table.setVisible(false);
		
		items = new HashMap<Integer, Item>();

		dragAndDrop = new DragAndDrop();
		
		stage.addActor(table);	
		
		createGrid();
		
		cells = table.getCells();
		cells = itemsTable.getCells();
		
		slotsFull = 0;
		
		utilities = new Utilities();
		hotbar = Map.getInstance().getHotbar();
		
		createGrid();
		
	}
	
	public void render() {	
		stage.act();
		stage.draw();
		hotbar.render();
	}
	
	public void createGrid() {
		for (int i = 0; i < NUM_ROWS; i++) {
			for (int j = 0; j < NUM_COLUMNS; j++) {
				table.add().size(32);
				itemsTable.add().width(32).height(32);
				blankImage = new Image();
				cells.get(i * 5 + j).setActor(blankImage);
				cells.get(i * 5 + j).getActor().setName("blank");
				utilities.addToDragAndDrop(dragAndDrop, blankImage, itemsTable, false, true);
			}
			table.row();
		}
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public void dispose() {
		stage.dispose();
		atlas.dispose();
		cam.dispose();
		hotbar.dispose();
	}

	public void showInventory() {
		table.setVisible(true);
		if (hotbar == null) {
			hotbar = new Hotbar();
		}
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

		if (items.size() != INVENTORY_SPACE) {
			items.put(items.size(), item);
			image = new Image(item.texture);			
			cells.get(slotsFull).setActor(image);	
			slotsFull++;
			utilities.addToDragAndDrop(dragAndDrop, image, table);

		if (slotsFull != INVENTORY_SPACE) {
			
			image = new Image(item.texture);	
			
			outerfor:
			for (int i = 0; i < NUM_ROWS; i++) {
				for (int j = 0; j < NUM_COLUMNS; j++) {
					if (cells.get(i * 5 + j).getActor().getName() == "blank") {
						cells.get(i*5 + j).setActor(image);
						break outerfor;
					}
				}
			}
			
			slotsFull++;
			utilities.addToDragAndDrop(dragAndDrop, image, itemsTable, true, true);
			utilities.addToDragAndDrop(dragAndDrop, hotbar.addItem(), hotbar.getTable(), true, true);

		}
	}
}
