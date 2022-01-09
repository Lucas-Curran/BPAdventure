package com.mygdx.game;

import java.util.HashMap;

import org.xml.sax.helpers.ParserFactory;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.components.NPCComponent;
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
import com.mygdx.game.ui.Money;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class Map implements Screen, InputProcessor {

	private TextBox textBox;

	private BitmapFont font;
	private Stage stage;
	private InputMultiplexer inputMultiplexer;
	private EntityHandler entityHandler;
	private static Map instance;
	private Levels levels;

	private Camera cam;

	public boolean teleporting, death;
	
	private TextureAtlas textureAtlas;

	private Hotbar hotbar;
	
	public Texture mapBackground;
	
	private PlayerHUD playerHUD;
	private Money money;
	
	private AudioManager am;
	private Weapon weapon;
	private boolean swing;
	
	boolean gravitySwitch;
	
	public void setGravitySwitch(boolean gravitySwitch) {
		this.gravitySwitch = gravitySwitch;
	}


	private Map() {
		cam = new Camera();
		stage = new Stage();
		font = new BitmapFont(Gdx.files.internal("font.fnt"));
		textBox = new TextBox(font, stage, Color.WHITE);
		money = new Money();
		playerHUD = new PlayerHUD(money);
		
		entityHandler = new EntityHandler();
		levels = new Levels(entityHandler.getWorld());
		am = new AudioManager();
	
		textureAtlas = new TextureAtlas("bpaatlas.txt");

		mapBackground = new Texture(Gdx.files.internal("overworld_bg.png"));
		weapon = new Weapon();
		swing = false;
	}
	
	static {
		instance = new Map();
	}
	
	public static Map getInstance() {
		return instance;
	}
	
	@Override
	public void show() {
		
		inputMultiplexer = new InputMultiplexer();	
		inputMultiplexer.addProcessor(this);
		inputMultiplexer.addProcessor(playerHUD.getStage());
		inputMultiplexer.addProcessor(textBox.getInstance());
		inputMultiplexer.addProcessor(textBox.getStage());
		
		Gdx.input.setInputProcessor(inputMultiplexer);
		
		if (entityHandler.getPlayer() == null) {
			entityHandler.create();
			weapon.createSword(entityHandler.getPlayer().getX(), entityHandler.getPlayer().getY());
		}
		if (!levels.getLevelOne().isCreated()) {
			levels.getLevelOne().create();
		} 
		if (!levels.getLevelTwo().isCreated()) {
			levels.getLevelTwo().create();
		}
		
		if (!levels.getLevelThree().isCreated()) {
			levels.getLevelThree().create();
		}
		
		if (!levels.getLevelSeven().isCreated()) {
			levels.getLevelSeven().create();
		}
	}

	@Override
	public void render(float delta) {
		
		//am.playCave();
				
		entityHandler.getBatch().setProjectionMatrix(cam.getCombined());
		entityHandler.getBatch().begin();
		entityHandler.getBatch().draw(mapBackground, 0, 0, cam.getViewport().getWorldWidth(), cam.getViewport().getWorldHeight());
		entityHandler.getBatch().end();
		
		entityHandler.render();
		weapon.positionSword(entityHandler.getPlayer().getX(), entityHandler.getPlayer().getY(), entityHandler.getPlayer().getDirection());
		
		if (swing) {
			weapon.swingSword();
			if (weapon.isSwingFinished()) {
				swing = false;
				weapon.setSwingFinished(false);
			}
		}
		
		
		levels.getLevelOne().render();
		textBox.renderTextBox(delta);
		if (playerHUD.isShowing()) {
			if (levels.getLevelOne().getShopWindow().isShopVisible()) {
				if (playerHUD.getStatusUI().getMoney().getParent() == playerHUD.getStatusUI()) {
					playerHUD.getStatusUI().getMoney().remove();
				}
			}
			playerHUD.render(delta);
		}
		
		if (entityHandler.killZone == true) {
			death = true;
		}
	}

	@Override
	public void resize(int width, int height) {
		cam.resize(width, height);
		playerHUD.resize(width, height);
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
			InventoryItem apple = new InventoryItem(textureAtlas.findRegion("IceCharacter"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_CHEST.getValue(), ItemTypeID.ARMOR01);
			playerHUD.getInventory().addItemToInventory(apple, "Apple");
		}
		
		if (Input.Keys.E == keycode) {
			InventoryItem banana = new InventoryItem(textureAtlas.findRegion("arrowAni"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_FEET.getValue(), ItemTypeID.BOOTS01);
			playerHUD.getInventory().addItemToInventory(banana, "Banana");
		}
		
		if (Input.Keys.ESCAPE == keycode && (!inAction() || playerHUD.getInventory().isVisible())) {
			playerHUD.popUpInventory();
			return true;
		}
		
		if (Input.Keys.R == keycode && entityHandler.loadingZone == true && !inAction()) {
			teleporting = true;
			return true;
		}
		
		
		
		if (Input.Keys.SPACE == keycode && entityHandler.gravityZone == true && !inAction()) {
			entityHandler.getPlayer().setGravityScale(-1);
			gravitySwitch = true;
			return true;
		}
		
		if(entityHandler.killZone == true && !inAction()) {
			death = true;
			return true;
		}
		
		if (Input.Keys.R == keycode && entityHandler.talkingZone == true && !textBox.isWriting() && !teleporting && !playerHUD.getInventory().isVisible()) {
			textBox.setOptions(true, "Shop", "Close");
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

		if (Input.Keys.SPACE == keycode && textBox.isWriting()) {
			textBox.setWritingSpeed(0.045f);
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
		if (button == Input.Buttons.LEFT && !inAction()) {
			swing = true;
			return true;
		}
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
		
		if (death) {
			return true;
		}

		
		if (textBox.isVisible()) {
			return true;
		}
		
		if (playerHUD.getInventory().isVisible()) {
			return true;
		}
		
		if (levels.getLevelOne().getShopWindow().isShopVisible()) {
			return true;
		}
		
		return false;
		
	}
	
	public Money getMoney() {
		return money;
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
	
	public PlayerHUD getPlayerHUD() {
		return playerHUD;
	}
	
	public Levels getLevels() {
		return levels;
	}
	
}
