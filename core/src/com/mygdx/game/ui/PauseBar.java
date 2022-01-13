package com.mygdx.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.mygdx.game.Utilities;

public class PauseBar {

	private Table optionsTable;
	private TextButton backButton, saveButton, quitButton;
	
	public PauseBar() {
		optionsTable = new Table();
		
		optionsTable.background(Utilities.ACTUAL_UI_SKIN.getDrawable("default-pane"));
		
		backButton = new TextButton("Back to Menu", Utilities.buttonStyles("default-rect", "default-rect-down"));
		saveButton = new TextButton("Save", Utilities.buttonStyles("default-rect", "default-rect-down"));
		quitButton = new TextButton("Quit", Utilities.buttonStyles("default-rect", "default-rect-down"));
		
		optionsTable.add(backButton).width(150).height(50);
		optionsTable.add(saveButton).width(150).height(50);
		optionsTable.add(quitButton).width(150).height(50);
		
	}
	
	public Table getTable() {
		return optionsTable;
	}
	
}
