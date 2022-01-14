package com.mygdx.game;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

public class Settings implements Screen {
	
	static Logger logger = LogManager.getLogger(Settings.class.getName());
	
	Texture settingsBackground;
	TextButton creditsBtn, returnBtn;

	static Slider volumeSlider;
	Container<Slider> container;
	BitmapFont font;
	Label sliderLabel, volumeLabel;
	Table table;
	Skin skin;
	
	AudioManager am = Map.getInstance().getAudioManager();

	private SpriteBatch spriteBatch;
	private Camera cam;
	private Stage stage;
	private SqliteManager sm; 
	int sliderValue; //replace with value from database later
	
	/**
	 * Lays out settings screen
	 */
	public Settings() {
		try {
			settingsBackground = new Texture(Gdx.files.internal("SettingsMenu.png"));
			spriteBatch = new SpriteBatch();
			cam = new Camera();
			stage = new Stage();
			table = new Table();
			sm = Map.getInstance().getSqliteManager();
		} catch (Exception e) {
			logger.error(e.getMessage());
			try {
				CrashWriter cw = new CrashWriter(e);
				cw.writeCrash();
			} catch (IOException e1) {
				logger.error(e1.getMessage());
			}
		}
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		
		try {
			//creates slider and container for the slider
			volumeSlider = new Slider(0, 100, 1, false, Utilities.sliderStyles());
			container = new Container<Slider>(volumeSlider);
			container.setTransform(true);
			container.setScale(3f);

			//sets labels and font scales
			sliderValue = sm.getVolume();
			font = new BitmapFont();
			sliderLabel = new Label(String.valueOf(sliderValue), new Label.LabelStyle(font, Color.ROYAL));
			volumeLabel = new Label("Volume", new Label.LabelStyle(font, Color.ROYAL));
			volumeLabel.setFontScale(4f);
			sliderLabel.setFontScale(2.5f);
			volumeSlider.setValue(sliderValue);

			//creates buttons
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

			//adds slider and buttons to table
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
			//adds table to stage
			stage.addActor(table);

		} catch (Exception e) {
			logger.error(e.getMessage());
			try {
				CrashWriter cw = new CrashWriter(e);
				cw.writeCrash();
			} catch (IOException e1) {
				logger.error(e1.getMessage());
			}
		}
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		spriteBatch.setProjectionMatrix(cam.getCombined());
		spriteBatch.begin();
		spriteBatch.draw(settingsBackground, 0, 0, cam.getViewport().getWorldWidth(), cam.getViewport().getWorldHeight());
		spriteBatch.end();		
		
		sliderValue = sm.getVolume();
		am.playMenu();
		
		// shows the credits screen
		if (creditsBtn.isPressed()) {
			am.playButton();
			dispose();
			logger.info("Credits button pressed");
			Screens.toCredits(new Credits());
		}
		
		//Desposes settings screen and returns to menu screen
		if (returnBtn.isPressed()) {
			am.playButton();
			sm.updateVolume(sliderValue);
			dispose();
			logger.info("Return button pressed");
			Screens.toMenu(Screens.getMenu());
		}
		
		// changes volume in database and updates volume of music
		if (volumeSlider.isDragging()) {
			sliderValue = (int) volumeSlider.getValue();
			sliderLabel.setText(sliderValue);
			sm.updateVolume(sliderValue);
			am.updateAll();		
		}
		
		stage.act(delta);
		stage.draw();
	}
	@Override
	public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);   
		
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
		am.stopAll();
		table.clearActions();
		table.clearChildren();
		stage.dispose();
		cam.dispose();
		
	}
}
