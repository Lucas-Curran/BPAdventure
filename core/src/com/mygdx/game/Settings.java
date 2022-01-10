package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

public class Settings implements Screen, InputProcessor {
	Texture settingsBackground;
	TextButton creditsBtn, returnBtn;

	Slider volumeSlider;
	Container<Slider> container;
	BitmapFont font;
	Label sliderLabel, volumeLabel;
	Table table;
	Skin skin;
	
	AudioManager audioManager = new AudioManager();

	private SpriteBatch spriteBatch;
	private Camera cam;
	private Stage stage;
	private InputMultiplexer inputMultiplexer;
	private AudioManager am;
	private SqliteManager sm; 
	int sliderValue; //replace with value from database later
	
	/**
	 * Sets camera and game
	 * @param game
	 */
	public Settings() {
		settingsBackground = new Texture(Gdx.files.internal("SettingsMenu.png"));
		spriteBatch = new SpriteBatch();
		cam = new Camera();
		stage = new Stage();
		table = new Table();
		sm = new SqliteManager();
		sliderValue = sm.getVolume();
		
	}
	
	/**
	 * Creates stage and actors
	 */
	@Override
	public void show() {
		
		
		volumeSlider = new Slider(0, 100, 1, false, Utilities.sliderStyles());
		container = new Container<Slider>(volumeSlider);
		container.setTransform(true);
		container.setScale(3f);
		
		font = new BitmapFont();
		sliderLabel = new Label(String.valueOf(sliderValue), new Label.LabelStyle(font, Color.ROYAL));
		volumeLabel = new Label("Volume", new Label.LabelStyle(font, Color.ROYAL));
		volumeLabel.setFontScale(4f);
		sliderLabel.setFontScale(2.5f);
		volumeSlider.setValue(sliderValue);
		
		creditsBtn = new TextButton("Credits", Utilities.buttonStyles("default-rect", "default-rect-down"));
		returnBtn = new TextButton("Return", Utilities.buttonStyles("default-rect", "default-rect-down"));
		creditsBtn.getLabel().setAlignment(Align.left);
		creditsBtn.getLabelCell().padLeft(8);
		creditsBtn.getLabel().setFontScale(2,2);
		creditsBtn.setTransform(true);
		creditsBtn.scaleBy(1);
		returnBtn.getLabel().setAlignment(Align.left);
		returnBtn.getLabelCell().padLeft(8);
		returnBtn.getLabel().setFontScale(2,2);
		returnBtn.setTransform(true);
		returnBtn.scaleBy(1);
		
		table = new Table(skin);
		table.left().bottom().pad(80);
		table.add(volumeLabel).colspan(2).padBottom(50).right().padRight(43);
		table.row();
		table.add(container).colspan(2).left().padLeft(25);
		table.row();
		table.add(sliderLabel).colspan(2).center().padLeft(100);
		table.row().padTop(80);
		table.add(returnBtn).width(110);
		table.add(creditsBtn).padLeft(150).width(110);
		table.setFillParent(true);
		
//		table.debug();
		stage.addActor(table);
		
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(this);
		inputMultiplexer.addProcessor(stage);		
		
		Gdx.input.setInputProcessor(inputMultiplexer);
	}
	
	/**
	 * Runs stage and actors
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		spriteBatch.setProjectionMatrix(cam.getCombined());
		spriteBatch.begin();
		spriteBatch.draw(settingsBackground, 0, 0, cam.getViewport().getWorldWidth(), cam.getViewport().getWorldHeight());
		spriteBatch.end();		
		
		
		
		if (creditsBtn.isPressed()) {
			//Add in transition to credits
		}
		
		if (returnBtn.isPressed()) {
			Screens.toMenu(Screens.getMenu());
			sm.updateVolume(sliderValue);
		}
		
		if (volumeSlider.isDragging()) {
			sliderValue = (int) volumeSlider.getValue();
			sliderLabel.setText(sliderValue);
			sm.updateVolume(sliderValue);
			audioManager.updateAll();
			
		}
		
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
		// TODO Auto-generated method stub
		
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
}
