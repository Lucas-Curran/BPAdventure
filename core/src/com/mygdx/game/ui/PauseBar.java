package com.mygdx.game.ui;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.mygdx.game.CrashWriter;
import com.mygdx.game.Map;
import com.mygdx.game.Screens;
import com.mygdx.game.Utilities;
import com.mygdx.game.entities.EntityHandler;

public class PauseBar {

	static Logger logger = LogManager.getLogger(PauseBar.class.getName());
	
	private Table optionsTable;
	private TextButton backButton, saveButton, quitButton;
	
	public PauseBar() {
		try {
			optionsTable = new Table();

			optionsTable.background(Utilities.ACTUAL_UI_SKIN.getDrawable("default-pane"));

			backButton = new TextButton("Back to Menu", Utilities.buttonStyles("default-rect", "default-rect-down"));
			saveButton = new TextButton("Save", Utilities.buttonStyles("default-rect", "default-rect-down"));
			quitButton = new TextButton("Quit", Utilities.buttonStyles("default-rect", "default-rect-down"));

			optionsTable.add(backButton).width(150).height(50);
			optionsTable.add(saveButton).width(150).height(50);
			optionsTable.add(quitButton).width(150).height(50);

			optionsTable.setPosition(80, 0);

			optionsTable.setWidth(480);
			optionsTable.setHeight(80);
			logger.info("Pause Bar instanced.");
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
	
	public void render() {
		if (!optionsTable.isVisible())  {
			optionsTable.setVisible(true);
		}
		if (backButton.isPressed()) {
			Group tempParent = backButton.getParent();
			backButton.remove();
			tempParent.addActor(backButton);
			Map.getInstance().getEntityHandler().getPlayer().setPosition(17, 1.5f);
			Map.getInstance().mapBackground = new Texture(Gdx.files.internal("overworld_bg.png"));
			Map.getInstance().getPlayerHUD().getInventory().setVisible(false);
			Map.getInstance().getAudioManager().stopAll();
			Map.getInstance().getAudioManager().playMenu();
			Screens.toMenu(Screens.getMenu());		
		} else if (saveButton.isPressed()) {
			Group tempParent = saveButton.getParent();
			saveButton.remove();
			tempParent.addActor(saveButton);
			//peter help
		} else if (quitButton.isPressed()) {
			Gdx.app.exit();
		}
	}
	
	public Table getTable() {
		return optionsTable;
	}
	
}
