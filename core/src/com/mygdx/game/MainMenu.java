package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Align;

public class MainMenu implements Screen, InputProcessor {

	private Texture menuBackground;
	private SpriteBatch spriteBatch;
	private Camera cam;
	private Stage stage;
	private Table table;
	private TextButton startButton, continueButton, settingsButton, quitButton;
	private TextButtonStyle textButtonStyle;
	private Skin skin;
	private TextureAtlas textureAtlas;
	private BitmapFont font;
	private InputMultiplexer inputMultiplexer;
	private AudioManager am;
	
	public MainMenu() {
		menuBackground = new Texture(Gdx.files.internal("menu_bg.png"));
		spriteBatch = new SpriteBatch();
		cam = new Camera();
		stage = new Stage();
		table = new Table();
		font = new BitmapFont(Gdx.files.internal("font.fnt"));
		
		startButton = new TextButton("Start", Utilities.buttonStyles("default-rect", "default-rect-down"));
		continueButton = new TextButton("Continue", Utilities.buttonStyles("default-rect", "default-rect-down"));
		settingsButton = new TextButton("Settings", Utilities.buttonStyles("default-rect", "default-rect-down"));
		quitButton = new TextButton("Quit", Utilities.buttonStyles("default-rect", "default-rect-down"));
		
		Utilities.buttonSettings(startButton);
		Utilities.buttonSettings(continueButton);
		Utilities.buttonSettings(settingsButton);
		Utilities.buttonSettings(quitButton);
	
		table.left().bottom().padBottom(10);
		table.add(startButton).width(180).height(70).pad(10);
		table.row();
		table.add(continueButton).width(180).height(70).pad(10);
		table.row();
		table.add(settingsButton).width(180).height(70).pad(10);
		table.row();
		table.add(quitButton).width(180).height(70).pad(10);
		stage.addActor(table);
		am = new AudioManager();
	}
	
	@Override
	public void show() {
		
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(this);
		inputMultiplexer.addProcessor(stage);		
		
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	@Override
	public void render(float delta) {
		
		
		spriteBatch.setProjectionMatrix(cam.getCombined());
		spriteBatch.begin();
		spriteBatch.draw(menuBackground, 0, 0, cam.getViewport().getWorldWidth(), cam.getViewport().getWorldHeight());
		spriteBatch.end();		
		
		am.updateAll();
		am.playMenu();
		
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
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
		spriteBatch.dispose();	
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (startButton.isPressed()) {
			 am.stopAll();
			 Screens.toMap();
			 return true;
		 } else if (continueButton.isPressed()) {
			 
		 } else if (settingsButton.isPressed()) {
				 am.stopAll();
				 Screens.toSettings(new Settings());
		 } else if (quitButton.isPressed()) {
			 dispose();
			 Gdx.app.exit();
		 }
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

}
