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
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.inventory.Inventory;
import com.mygdx.game.ui.Money;
import com.mygdx.game.ui.PauseBar;
import com.mygdx.game.ui.StatusUI;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerHUD extends Window {
	
	static Logger logger = LogManager.getLogger(PlayerHUD.class.getName());
	
	private Stage stage;
	private Inventory inventory;
	private StatusUI statusUI;
	private Viewport viewport;
	
	private static TextureRegion background = new TextureRegion(Utilities.UISKIN.getAtlas().findRegion("invBackground"));
	
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
	
	public void showHUD() {
		this.setVisible(true);
	}
	
	public boolean isShowing() {
		return this.isVisible();
	}
	
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
	
	public StatusUI getStatusUI() {
		return statusUI;
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public PlayerHUD instance() {
		return this;
	}
	
}
