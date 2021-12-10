package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.inventory.Inventory;
import com.mygdx.game.ui.StatusUI;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class PlayerHUD implements Screen {
	
	private Stage stage;
	private Inventory inventory;
	private StatusUI statusUI;
	private Viewport viewport;
	
	public PlayerHUD() {
		viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		stage = new Stage(viewport);
		stage.setDebugAll(true);
		
		inventory = new Inventory();
		inventory.setKeepWithinStage(false);
		inventory.setMovable(false);
		inventory.setVisible(true);
		
		statusUI = new StatusUI();
		statusUI.setMovable(false);

		stage.addActor(inventory);
		stage.addActor(statusUI);
		
		statusUI.validate();
		inventory.validate();
		
	}

	@Override
	public void show() {
	
	}

	@Override
	public void render(float delta) {
		stage.act(delta);
		stage.draw();	
	}

	@Override
	public void resize(int width, int height) {
		viewport.apply();
		viewport.setScreenBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage.getViewport().update(width, height, false);
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
		stage.dispose();		
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
