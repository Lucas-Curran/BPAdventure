package com.my.gdx.game.inventory;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
	
	private Array<Cell> cells;

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

	}
	
	public void render() {
		dragAndDrop.setDragActorPosition(image.getWidth()/2 - (Gdx.graphics.getWidth() - 640)/2, -(image.getHeight()/2));
		
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
	
	public void arrangeInventory() {
		for (int position : items.keySet()) {
			System.out.println("Key: " + position + " Value: " + items.get(position).name);
			tex = items.get(position).texture;
			image = new Image(tex);
			
			for (Cell<?> cell : cells) {
				if (!cell.hasActor()) {
					cell.setActor(image);
					break;
				}
			}
			
			dragAndDrop.addSource(new Source(image) {
	
				float payloadX;
				float payloadY;
				
				@Override
				public Payload dragStart(InputEvent event, float x, float y, int pointer) {				
					Payload payload = new Payload();
					payload.setObject(image);
					payload.setDragActor(getActor());
					payloadX = payload.getDragActor().getX(Align.bottomLeft);
					payloadY = payload.getDragActor().getY(Align.bottomLeft);
					return payload;
				}

				@Override
				public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, Target target) {
					if (target != null) {
						payload.getDragActor().setPosition(target.getActor().getX(Align.bottomLeft), target.getActor().getY(Align.bottomLeft));
						target.getActor().setPosition(payloadX, payloadY);
					} else {
						payload.getDragActor().setPosition(payloadX, payloadY);
					}
					super.dragStop(event, x, y, pointer, payload, target);
				}
			});
			
			dragAndDrop.addTarget(new Target(image) {
				
				@Override
				public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
					getActor().setColor(Color.GREEN);
					return true;
				}

				@Override
				public void drop(Source source, Payload payload, float x, float y, int pointer) {
					System.out.println("On target");
	
				}

				@Override
				public void reset(Source source, Payload payload) {
					getActor().setColor(Color.WHITE);
					super.reset(source, payload);
				}
				
			});
			
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
		items.put(items.size(), item);
	}
	
}
