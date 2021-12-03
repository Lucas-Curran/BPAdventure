package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.entities.EntityHandler;
import com.mygdx.game.entities.Player;
import com.mygdx.game.inventory.Inventory;
import com.mygdx.game.item.InventoryItem;
import com.mygdx.game.item.InventoryItem.ItemAttribute;
import com.mygdx.game.item.InventoryItem.ItemTypeID;
import com.mygdx.game.item.InventoryItem.ItemUseType;
import com.mygdx.game.levels.LevelFactory;
import com.mygdx.game.levels.LevelOne;
import com.mygdx.game.levels.Levels;

public class Map implements Screen, InputProcessor {

	private TextBox textBox;

	private BitmapFont font;
	private Stage stage;
	private InputMultiplexer inputMultiplexer;
	private EntityHandler entityHandler;
	private static Map instance;
	private Levels levels;

	private Camera cam;

	public boolean teleporting;
	
	private TextureAtlas textureAtlas;

	private Hotbar hotbar;
	
	private PlayerHUD playerHUD;
	
	private Map() {
		cam = new Camera();
		stage = new Stage();
		font = new BitmapFont(Gdx.files.internal("font.fnt"));
		textBox = new TextBox(font, stage, Color.WHITE);
		
		playerHUD = new PlayerHUD();
		inputMultiplexer = new InputMultiplexer();
		
		inputMultiplexer.addProcessor(this);
		inputMultiplexer.addProcessor(playerHUD.getStage());
		
		entityHandler = new EntityHandler();
		Gdx.input.setInputProcessor(inputMultiplexer);
		levels = new Levels();
	
		textureAtlas = new TextureAtlas("bpaatlas.txt");
		
		Skin skin = new Skin(textureAtlas);
		
		System.out.println(textureAtlas.findRegion("IceCharacter"));
	}
	
	static {
		instance = new Map();
	}
	
	public static Map getInstance() {
		return instance;
	}
	
	@Override
	public void show() {
		//hotbar = new Hotbar();
		//inputMultiplexer.addProcessor(inventory.getStage());
		entityHandler.create();
		levels.getLevelOne().create();
	}

	@Override
	public void render(float delta) {
		entityHandler.render();
		//hotbar.render();
		textBox.renderTextBox(delta);
	}

	@Override
	public void resize(int width, int height) {
		cam.resize(width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		textBox.dispose();
		entityHandler.dispose();
		stage.dispose();
		font.dispose();
		levels.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		if (Input.Keys.SPACE == keycode && textBox.isWriting()) {
			textBox.setWritingSpeed(0.01f);
			return true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {

		if (Input.Keys.T == keycode) {
			System.out.println("add");
			InventoryItem apple = new InventoryItem(textureAtlas.findRegion("IceCharacter"), ItemAttribute.CONSUMABLE.getValue(), ItemUseType.ITEM_RESTORE_HEALTH.getValue(), ItemTypeID.POTIONS01);
			playerHUD.getInventory().addItemToInventory(apple, "Apple");
		}
		
		if (Input.Keys.ESCAPE == keycode) {
			Screens.toHUD(playerHUD);
			return true;
		}
		
		if (Input.Keys.R == keycode && entityHandler.loadingZone == true && !inAction()) {
			teleporting = true;
			return true;
		}

		if (Input.Keys.SPACE == keycode && textBox.isWriting()) {
			textBox.setWritingSpeed(0.045f);
			return true;
		} 
		
		if (Input.Keys.R == keycode && !textBox.isWriting() && !teleporting) {
			if (textBox.isVisible()) {
				if (textBox.getText().length-1 != textBox.getTextSequence()) {
					textBox.setTextSequence(textBox.getTextSequence()+1);
				} else {
					textBox.hideTextBow();
				}
			} else {
				textBox.setColor(Color.FOREST);
				textBox.setText(new String[]{
						"This is the first sentence", 
						"This is the second sentence", 
						"This is the third sentence", 
						"This is the fourth sentence", 
						"This is the fifth sentence"});
			}
			return true;
		}
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean inAction() {
		
		if (teleporting) {
			return true;
		} 
		
		if (textBox.isVisible()) {
			return true;
		}
		
		return false;
		
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public TextBox getTextBox() {
		return textBox;
	}
	
	public EntityHandler getEntityHandler() {
		return entityHandler;
	}
	
	public InputMultiplexer getInputMultiplexer() {
		return inputMultiplexer;
	}
	
	public Hotbar getHotbar() {
		return hotbar;
	}
	
}
