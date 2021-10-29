package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class Map implements Screen {

	TextBox textBox;
	Game game;
	BitmapFont font;
	
	public Map(final MyGdxGame game) {
		this.game = game;
		font = new BitmapFont(Gdx.files.internal("font.fnt"));
		textBox = new TextBox(font);
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		textBox.createTextBox(delta, "Hello");
	}

	@Override
	public void resize(int width, int height) {
		//stage.setViewport(new ExtendViewport(width, height));
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
				
	}
	
}
