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
		
		slotsFull = 0;
		
		utilities = new Utilities();

	}
	
	public void render() {	
		stage.act();
		stage.draw();
	}
	
	public void createGrid() {
		for (int i = 0; i < NUM_ROWS; i++) {
			for (int j = 0; j < NUM_COLUMNS; j++) {
				table.add().size(32);
			}
			table.row();
		}
	}
	
	public Stage getStage() {
		return stage;
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
			slotsFull++;
			utilities.addToDragAndDrop(dragAndDrop, image, table);
		}
	}
}
