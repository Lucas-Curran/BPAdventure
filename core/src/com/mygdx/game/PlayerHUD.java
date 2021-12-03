package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.inventory.Inventory;

public class PlayerHUD implements Screen {
	
	private Stage stage;
	private Inventory inventory;
	private Camera cam;
	
	public PlayerHUD() {
		cam = new Camera();
		stage = new Stage();
		stage.setDebugAll(true);
		
		inventory = new Inventory();
		inventory.setKeepWithinStage(false);
		inventory.setMovable(false);
		inventory.setVisible(true);
		
		stage.addActor(inventory);
		
		Array<Actor> actors = inventory.getInventoryActors();
		
		for (Actor actor : actors) {
			stage.addActor(actor);
		}
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		stage.act(delta);
		stage.draw();	
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
		stage.dispose();		
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
}
