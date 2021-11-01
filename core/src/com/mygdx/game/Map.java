package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
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

public class Map implements Screen, InputProcessor {

	private TextBox textBox;
	private static Game game;
	private BitmapFont font;
	private Stage stage;
	private InputMultiplexer inputMultiplexer;
	private Character character;
	private static Map instance;
	
	private Map() {
		stage = new Stage();
		font = new BitmapFont(Gdx.files.internal("font.fnt"));
		textBox = new TextBox(font, stage, "", Color.WHITE);
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(this);
		character = new Character();
		Gdx.input.setInputProcessor(inputMultiplexer);
	}
	
	static {
		instance = new Map();
	}
	
	public static Map getInstance() {
		return instance;
	}
	
	@Override
	public void show() {
		textBox.setText("you are big gay");
	}

	@Override
	public void render(float delta) {
		character.render();		
		textBox.renderTextBox(delta);
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
		textBox.dispose();
		character.dispose();
		stage.dispose();
		font.dispose();
		game.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		if (Input.Keys.SPACE == keycode && textBox.isWriting()) {
			textBox.setWritingSpeed(0.01f);
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (Input.Keys.SPACE == keycode && textBox.isWriting()) {
			textBox.setWritingSpeed(0.08f);
			return false;
		} 
		
		if (Input.Keys.R == keycode && !textBox.isWriting()) {
			textBox.hideTextBow();
		}
		
		if (Input.Keys.T == keycode && !textBox.isWriting() && !textBox.isVisible()) {
			textBox.setColor(Color.FOREST);
			textBox.setText("To my knowledge, Delta time is the time gap between "
					+ "the previous and current frame.");
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
		// TODO Auto-generated method stub
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
	
	public TextBox getTextBox() {
		return textBox;
	}
	
}
