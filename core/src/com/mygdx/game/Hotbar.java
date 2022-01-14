package com.mygdx.game;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygdx.game.inventory.Inventory;

public class Hotbar {
	
	private Stage stage;
	private Table table;
	private DragAndDrop dragAndDrop;
	private final int HOTBAR_LENGTH = 5;
	private Image blankImage;
	private Utilities utilities;
	private Image image;
	private TextureAtlas atlas;
	private SpriteBatch batch;
	private Inventory inventory;
	
	public Hotbar() {
		
		stage = Map.getInstance().getStage();
		
		table = new Table();
		table.setDebug(true);
		table.setBounds(335, 10, HOTBAR_LENGTH * 32, 32);
		stage.addActor(table);
		
		dragAndDrop = new DragAndDrop();
		utilities = new Utilities();
		
		atlas = new TextureAtlas("textures.txt");
		
		batch = new SpriteBatch();
		
		createBar();
	}
	
	public void render() {
		stage.act();
		stage.draw();
	}
	
	public void dispose() {
		stage.dispose();
	}
	
	public void createBar() {
		for (int i = 0; i < HOTBAR_LENGTH; i++) {
			table.add().width(32).height(32);
			Image cellImage = inventory.getHotbarImages().get(i);
			String cellActorName = inventory.getHotbarItems().get(i).getActor().getName();
			table.getCells().get(i).setActor(cellImage);
			table.getCells().get(i).getActor().setName(cellActorName);
		}
	}
	
	public void setItems() {
		for (int i = 0; i < HOTBAR_LENGTH; i++) {
				String cellActorName = inventory.getHotbarItems().get(i).getActor().getName();
				if (cellActorName != "blank") {
					Image cellImage = new Image(atlas.findRegion(cellActorName));
					table.getCells().get(i).setActor(cellImage);
				} else {
					Image blankImage = new Image();
					table.getCells().get(i).setActor(blankImage);
				}
				table.getCells().get(i).getActor().setName(cellActorName);	
		}
	}
	
	public Table getTable() {
		return table;
	}
	
	
	
}
