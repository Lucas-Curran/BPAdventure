package com.mygdx.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.inventory.Inventory;
import com.mygdx.game.ui.Money;
import com.mygdx.game.ui.StatusUI;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerHUD extends Window {
	
	static Logger logger = LogManager.getLogger(PlayerHUD.class.getName());
	
	private Stage stage;
	private Inventory inventory;
	private StatusUI statusUI;
	private Viewport viewport;
	
	private static TextureRegion background = new TextureRegion(Utilities.UISKIN.getAtlas().findRegion("invBackground"));
	
	/**
	 * Instances the playerHUD with a new stage and inventory/statusUI tables
	 * @param money - the money instance to give to statusUI
	 */
	public PlayerHUD(Money money) {
		super("HUD", new WindowStyle(new BitmapFont(), Color.RED, new Image(background).getDrawable()));

		stage = new Stage();
		stage.setDebugAll(true);
		
		
		inventory = new Inventory();
		inventory.setKeepWithinStage(false);
		inventory.setMovable(false);
		inventory.setVisible(false);

		statusUI = new StatusUI(money);
		statusUI.setMovable(false);
		statusUI.setKeepWithinStage(false);

		stage.addActor(statusUI);
		stage.addActor(inventory);

		statusUI.validate();
		inventory.validate();	
		
		logger.info("PlayerHUD instanced.");
	}
	
	public void render(float delta) {		
		stage.act(delta);
		stage.draw();

		//if hp is less than or equal to 0, set death to true
		if (statusUI.getHealthBar().getHP() <= 0) {
			Map.getInstance().death = true;
		}
	}

	
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, false);
	}

	public void dispose() {
		stage.dispose();		
	}
	
	/**
	 * Sets hud window to visible
	 */
	public void showHUD() {
		this.setVisible(true);
	}
	
	/**
	 * Checks if the hud window is visible
	 * @return booleon describing window visibility
	 */
	public boolean isShowing() {
		return this.isVisible();
	}
	
	/**
	 * If inventory is visible, set to invisible, 
	 * if inventory is invisible, set to visible
	 */
	public void popUpInventory() {
		if (inventory.isVisible()) {
			logger.info("Inventory closed.");
			inventory.setVisible(false);		
		} else {
			inventory.toFront();
			logger.info("Inventory opened.");
			inventory.setVisible(true);
		}
	}
	
	/**
	 * Retrieves statusUI instance
	 * @return statusUI
	 */
	public StatusUI getStatusUI() {
		return statusUI;
	}
	
	/**
	 * Retrieves inventory instance
	 * @return inventory
	 */
	public Inventory getInventory() {
		return inventory;
	}
	
	/**
	 * Retrieves playerHUD stage
	 * @return stage
	 */
	public Stage getStage() {
		return stage;
	}
	
	/**
	 * Retrieves playerHUD instance
	 * @return playerHUD
	 */
	public PlayerHUD instance() {
		return this;
	}
	
}
