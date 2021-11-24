package com.mygdx.game.inventory;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
	
	private Array<Cell> cells;
	
	private int slotsFull;
	private Utilities utilities;
	
	private ScreenViewport stageViewport;

	public Inventory() {
		stageViewport = new ScreenViewport();
		
		stage = new Stage(stageViewport);
		table = new Table();
		
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
		
		stage.addActor(table);
		stage.addActor(itemsTable);
		
		createGrid();
		
		cells = itemsTable.getCells();
		
		slotsFull = 0;
		
		utilities = new Utilities();

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
		if (items.size() != INVENTORY_SPACE) {
			items.put(items.size(), item);
			image = new Image(item.texture);			
			cells.get(slotsFull).setActor(image);
			cells.get(slotsFull).getActor().setDebug(true);
			slotsFull++;
			utilities.addToDragAndDrop(dragAndDrop, image, itemsTable);
		}
	}
	
	public void removeItem(Item item) {
		
	}
}
