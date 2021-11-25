package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

public class Hotbar {
	
	private Stage stage;
	private Table table;
	private DragAndDrop dragAndDrop;
	private final int HOTBAR_LENGTH = 9;
	private Image blankImage;
	private Utilities utilities;
	private Image image;
	private TextureAtlas atlas;
	
	public Hotbar() {
		stage = Map.getInstance().getInventory().getStage();
		table = new Table();
		table.setDebug(true);
		table.setBounds(335, 10, HOTBAR_LENGTH * 32, 32);
		stage.addActor(table);
		
		dragAndDrop = new DragAndDrop();
		utilities = new Utilities();
		
		atlas = new TextureAtlas("textures.txt");
		
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
			blankImage = new Image();
			table.getCells().get(i).setActor(blankImage);
			table.getCells().get(i).getActor().setName("blank");
			utilities.addToDragAndDrop(dragAndDrop, blankImage, table, false, true);
		}
	}
	
	public Image addItem() {
		image = new Image(atlas.findRegion("IceCharacter"));
		for (int i = 0; i < HOTBAR_LENGTH; i++) {
			if (table.getCells().get(i).getActor().getName() == "blank") {
				table.getCells().get(i).setActor(image);
				break;
			}
		}
		return image;
	}
	
	public Table getTable() {
		return table;
	}
	
	
	
}
