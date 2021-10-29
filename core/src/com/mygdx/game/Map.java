package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class Map implements Screen {

	private Stage stage;
	private Table table;
	private Label label;
	private BitmapFont font;
	private Game game;
	
	public Map(final MyGdxGame game) {
		this.game = game;
	}
	
	@Override
	public void show() {
		stage = new Stage();
		table = new Table();
		font = new BitmapFont(Gdx.files.internal("opensans-regular.ttf"));
		label = new Label("IM WRITING THIS", new LabelStyle(font, Color.WHITE));
	}

	@Override
	public void render(float delta) {
		
		table.top();
		table.setFillParent(true);
		table.add(label).center();
		
		stage.addActor(table);
		
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
	
}
