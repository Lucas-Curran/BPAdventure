package com.mygdx.game;


import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

public class Credits implements Screen {

	static Logger logger = LogManager.getLogger(Credits.class.getName());
	
	Camera cam;
	Texture background;
	Stage stage;
	TextButton returnBtn;
	private SpriteBatch spriteBatch;

	AudioManager am = Map.getInstance().getAudioManager();
	
	/**
	 * Credits screen to allow switching to credits
	 */
	public Credits() {
		cam = new Camera();
		spriteBatch = new SpriteBatch();
		background = new Texture("credits.png");
		logger.info("Credits instanced.");
	}

	@Override
	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		// creates button
		returnBtn = new TextButton("Return", Utilities.buttonStyles("default-rect", "default-rect-down"));
		returnBtn.getLabel().setFontScale(1f,1f);
		returnBtn.setPosition(20, 20);
		returnBtn.getLabel().setAlignment(Align.left);
		returnBtn.getLabelCell().padLeft(19);
		returnBtn.getLabel().setFontScale(2, 2);
		returnBtn.setTransform(true);
		returnBtn.setScale(2);
		returnBtn.setWidth(125);
		returnBtn.setHeight(40);
		
		// adds button to stage
		stage.addActor(returnBtn);
		am.playMenu();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		try {
			// sets camera
			spriteBatch.setProjectionMatrix(cam.getCombined());
			spriteBatch.begin();
			spriteBatch.draw(background, 0, 0, cam.getViewport().getWorldWidth(), cam.getViewport().getWorldHeight());		
			spriteBatch.end();
			
			// checks if return button is clicked
			if (returnBtn.isPressed()) {
				logger.info("Credits return to settings button clicked.");
				am.playButton();
				dispose();
				Screens.toSettings(new Settings());
			}

		stage.act(delta);
		stage.draw();
		
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
		am.stopAll();
		stage.dispose();
		cam.dispose();
	}

}