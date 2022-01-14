package com.mygdx.game;

//import org.apache.logging.log4j.Logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.entities.EntityHandler;
import com.mygdx.game.item.InventoryItem;
import com.mygdx.game.item.InventoryItem.ItemAttribute;
import com.mygdx.game.item.InventoryItem.ItemTypeID;
import com.mygdx.game.item.InventoryItem.ItemUseType;
import com.mygdx.game.levels.Levels;
import com.mygdx.game.ui.Money;
import com.mygdx.game.ui.PauseBar;


public class Map implements Screen, InputProcessor {

	static Logger logger = LogManager.getLogger(Map.class.getName());

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
	private SqliteManager sm;
	private Weapon weapon;
	private boolean swing;
	
	public boolean gravitySwitch;
	public boolean createLevel;
	
	private PauseBar pauseBar;
	
	public void setGravitySwitch(boolean gravitySwitch) {
		this.gravitySwitch = gravitySwitch;
	}


	private Map() {
		cam = new Camera();
		stage = new Stage();
		font = new BitmapFont(Gdx.files.internal("font.fnt"));
		textBox = new TextBox(font, stage, Color.WHITE);
		
		money = new Money();
		pauseBar = new PauseBar();
		playerHUD = new PlayerHUD(money);
		
		levels = new Levels();
		entityHandler = new EntityHandler(levels);
		levels.createAllLevels(entityHandler.getWorld());
		
		am = new AudioManager();
		sm = new SqliteManager();
	
		textureAtlas = new TextureAtlas("bpaatlas.txt");

		mapBackground = new Texture(Gdx.files.internal("overworld_bg.png"));
		weapon = new Weapon();
		swing = false;
		
		stage.addActor(pauseBar.getTable());

		logger.info("Map Created.");
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

		if (entityHandler.getPlayer() == null) {
			entityHandler.create();
			logger.info("Entity handler created");
			weapon.createSword(entityHandler.getPlayer().getX(), entityHandler.getPlayer().getY());
		}
		if (!levels.getOverworld().isCreated()) {
			levels.getOverworld().create();
			logger.info("Overworld created");
		} 
		
		if (!levels.getIceDungeon().isCreated()) {
			levels.getIceDungeon().create();
			logger.info("Ice Dungeon created");
		}
		inputMultiplexer.addProcessor(levels.getOverworld().getShopWindow());
		inputMultiplexer.addProcessor(levels.getOverworld().getShopWindow().getStage());
		
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	@Override
	public void render(float delta) {
						
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

		levels.getOverworld().render();
		textBox.renderTextBox(delta);
		if (playerHUD.isShowing()) {
			if (levels.getOverworld().getShopWindow().isShopVisible()) {
				levels.getOverworld().getShopWindow().render(delta);
			}
			playerHUD.render(delta);
		}
		
		if (entityHandler.killZone == true) {
			death = true;
		}
		
		if (playerHUD.getInventory().isVisible()) {
			pauseBar.render();
			stage.act(delta);
			stage.draw();
		}
		
		levels.dispose(entityHandler.getCreatedLevel());
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
		levels.disposeAll();
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
			InventoryItem apple = new InventoryItem(textureAtlas.findRegion("IceCharacter"), ItemAttribute.CONSUMABLE.getValue(), ItemUseType.ARMOR_CHEST.getValue(), ItemTypeID.BODYARMOR01);
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
		
		// sets teleporting true so other classes know the player is ready to teleport
		if (Input.Keys.R == keycode && entityHandler.loadingZone == true && !inAction()) {
			teleporting = true;
			createLevel = true;
			return true;
		}
		
		// flips the players gravity
		
		if (Input.Keys.SPACE == keycode && entityHandler.gravityZone == true && !inAction()) {
			entityHandler.getPlayer().setGravityScale(-1);
			gravitySwitch = true;
			return true;
		}
		
		// lets other classes know the player is ready to be killed
		
		if(entityHandler.killZone == true && !inAction()) {
			death = true;
			return true;
		}
		
		if (Input.Keys.R == keycode && (entityHandler.talkingZone == true || textBox.isVisible()) && !textBox.isWriting() && !teleporting && !playerHUD.getInventory().isVisible()) {
			pauseBar.getTable().setVisible(false);
			if (textBox.isVisible()) {
				if (textBox.getText().length-1 != textBox.getTextSequence()) {
					textBox.setTextSequence(textBox.getTextSequence()+1);
				} else {
					textBox.hideTextBow();
				}
			} else {
				textBox.setText(entityHandler.getCurrentNPCText(), entityHandler.hasOptions());
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
			am.playSwordJab();
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
		
		if (levels.getOverworld().getShopWindow().isShopVisible()) {
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
	/**
	 * gets the EntityHandler
	 * @return the one true instance of the EntityHandler
	 */
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
	/**
	 * gets the levels class instance
	 * @return the levels instance
	 */
	public Levels getLevels() {
		return levels;
	}
	
	public AudioManager getAudioManager() {
		return am;
	}
	
	public SqliteManager getSqliteManager() {
		return sm;
	}
	
}
