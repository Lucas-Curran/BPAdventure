package com.mygdx.game;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.ui.ReportBugWindow;

public class MainMenu implements Screen, InputProcessor {

	static Logger logger = LogManager.getLogger(MainMenu.class.getName());
	
	private Texture menuBackground;
	private SpriteBatch spriteBatch;
	private Camera cam;
	private Stage stage;
	private Table table;
	private TextButton startButton, continueButton, settingsButton, quitButton, reportBugButton;
	private TextButtonStyle textButtonStyle;
	private Skin skin;
	private TextureAtlas textureAtlas;
	private BitmapFont font;
	private InputMultiplexer inputMultiplexer;
	private AudioManager am;
	private SqliteManager sm;
	private ReportBugWindow bugWindow;
	
	public MainMenu() {
		try {
			menuBackground = new Texture(Gdx.files.internal("menu_bg.png"));
			spriteBatch = new SpriteBatch();
			bugWindow = new ReportBugWindow();
			cam = new Camera();
			stage = new Stage();
			table = new Table();
			font = new BitmapFont(Gdx.files.internal("font.fnt"));

			startButton = new TextButton("Start", Utilities.buttonStyles("default-rect", "default-rect-down"));
			continueButton = new TextButton("Continue", Utilities.buttonStyles("default-rect", "default-rect-down"));
			settingsButton = new TextButton("Settings", Utilities.buttonStyles("default-rect", "default-rect-down"));
			quitButton = new TextButton("Quit", Utilities.buttonStyles("default-rect", "default-rect-down"));
			reportBugButton = new TextButton("Report Bug", Utilities.buttonStyles("default-rect", "default-rect-down"));

			reportBugButton.getLabelCell().padRight(20);

			Utilities.buttonSettings(startButton);
			Utilities.buttonSettings(continueButton);
			Utilities.buttonSettings(settingsButton);
			Utilities.buttonSettings(quitButton);
			Utilities.buttonSettings(reportBugButton);

			table.left().bottom().padBottom(10);
			table.add(startButton).width(180).height(70).pad(10);
			table.row();
			table.add(continueButton).width(180).height(70).pad(10);
			table.row();
			table.add(settingsButton).width(180).height(70).pad(10);
			table.row();
			table.add(quitButton).width(180).height(70).pad(10);
			table.add(reportBugButton).width(180).height(70).padLeft(250);
			stage.addActor(table);

			am = Map.getInstance().getAudioManager();
			sm = Map.getInstance().getSqliteManager();
			
			logger.info("Main Menu instanced.");
			
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
		
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(this);
		inputMultiplexer.addProcessor(stage);	
		inputMultiplexer.addProcessor(bugWindow.getStage());
		
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
		
		if (bugWindow.isBugWindowVisible()) {
			bugWindow.render(delta);
		}
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, false);
		bugWindow.resize(width, height);
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
			 Group tempParent = startButton.getParent();
			 startButton.remove();
			 tempParent.addActor(startButton);
			 am.stopAll();
			 if (sm.clearTable(sm.getVolume())) {
				 Map.getInstance().getPlayerHUD().getStatusUI().getHealthBar().setHP(sm.getHealth());
				 logger.info("Start button on menu pressed.");
				 Screens.toMap();
				 return true;
			 }
		 } else if (continueButton.isPressed()) {
			 Group tempParent = continueButton.getParent();
			 continueButton.remove();
			 tempParent.addActor(continueButton); 
			 am.stopAll();
			 logger.info("Continue button on menu pressed");
			 Screens.toMap();
			 return true;
		 } else if (settingsButton.isPressed()) {
				 am.stopAll();
				 logger.info("Settings button on menu pressed");
				 Screens.toSettings(new Settings());
		 } else if (quitButton.isPressed()) {
			 dispose();
			 logger.info("Quit button pressed, app exited.");
			 Gdx.app.exit();
		 } else if (reportBugButton.isPressed()) {
			 logger.info("Report Bug Button pressed.");
			 bugWindow.setBugWindowVisible(true);
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
