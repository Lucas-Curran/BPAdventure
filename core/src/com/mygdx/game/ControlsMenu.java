package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class ControlsMenu implements Screen {

	private Table table;
	private Stage stage;
	private TextButton returnBtn;
	public ControlsMenu() {
		stage = new Stage();
		table = new Table();
		Label interactLabel = new Label("To interact with NPCs and doors, press R", Utilities.ACTUAL_UI_SKIN);
		Label inventoryLabel = new Label("To open inventory and pause menu options, press escape", Utilities.ACTUAL_UI_SKIN);
		
		returnBtn = new TextButton("Return", Utilities.buttonStyles("default-rect", "default-rect-down"));
		
		table.setFillParent(true);
		table.add(interactLabel);
		table.row();
		table.add(inventoryLabel);
		table.row();
		table.add(returnBtn);
		
		stage.addActor(table);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {

		if (returnBtn.isPressed()) {
			Screens.toSettings(new Settings());
		}
		
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
		
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
		// TODO Auto-generated method stub
		
	}
	
}
