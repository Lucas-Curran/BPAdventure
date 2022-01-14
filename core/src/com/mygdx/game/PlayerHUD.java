package com.mygdx.game;


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
			inventory.setVisible(false);		
		} else {
			inventory.toFront();
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
