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
import com.my.gdx.game.entities.EntityHandler;
import com.my.gdx.game.entities.Player;
import com.my.gdx.game.levels.LevelOne;

public class Map implements Screen, InputProcessor {

	private TextBox textBox;
	private static Game game;
	private BitmapFont font;
	private Stage stage;
	private InputMultiplexer inputMultiplexer;
	private EntityHandler entityHandler;
	private static Map instance;
	private LevelOne levelOne;
	
	private Map() {
		stage = new Stage();
		font = new BitmapFont(Gdx.files.internal("font.fnt"));
		textBox = new TextBox(font, stage, Color.WHITE);
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(this);
		entityHandler = new EntityHandler();
		Gdx.input.setInputProcessor(inputMultiplexer);
		levelOne = new LevelOne();
	}
	
	static {
		instance = new Map();
	}
	
	public static Map getInstance() {
		return instance;
	}
	
	@Override
	public void show() {
		levelOne.create();
		entityHandler.create();
		entityHandler.spawnLevelOne();
	}

	@Override
	public void render(float delta) {
		entityHandler.render();
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
		entityHandler.dispose();
		stage.dispose();
		font.dispose();
		game.dispose();
		levelOne.dispose();
		
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
		if (Input.Keys.SPACE == keycode && textBox.isWriting()) {
			textBox.setWritingSpeed(0.045f);
			return true;
		} 
		
		if (Input.Keys.R == keycode && !textBox.isWriting()) {
			if (textBox.isVisible()) {
				if (textBox.getText().length-1 != textBox.getTextSequence()) {
					textBox.setTextSequence(textBox.getTextSequence()+1);
				} else {
					textBox.hideTextBow();
				}
			} else {
				textBox.setColor(Color.FOREST);
				textBox.setText(new String[]{
						"This is the first sentence", 
						"This is the second sentence", 
						"This is the third sentence", 
						"This is the fourth sentence", 
						"This is the fifth sentence"});
			}
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
