package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Utilities;

public class ReportBugWindow extends Window {
	
	private Table inputTable;
	private Stage stage;
	
	private static SpriteDrawable windowBg = new SpriteDrawable(new Sprite(Utilities.ACTUAL_UI_SKIN.getRegion("default-pane")));
	
	public ReportBugWindow() {
		super("Report Bug", new WindowStyle(new BitmapFont(), Color.RED, windowBg));
		stage = new Stage();
		
		inputTable = new Table();
		inputTable.setWidth(400);
		inputTable.setHeight(400);
		
		inputTable.setPosition(200, 50);
		
		inputTable.background(Utilities.ACTUAL_UI_SKIN.getDrawable("default-pane"));
		Label titleLabel = new Label("Report Bug", Utilities.ACTUAL_UI_SKIN);
		TextField subjectField = new TextField("", Utilities.ACTUAL_UI_SKIN);
		TextArea messageField = new TextArea("", Utilities.ACTUAL_UI_SKIN);
		subjectField.setMessageText("Subject");
		messageField.setMessageText("What's your bug?");
		messageField.setAlignment(Align.topLeft);

		titleLabel.setFontScale(2);
		
		inputTable.add(titleLabel).top();
		inputTable.row();
		inputTable.add(subjectField);
		inputTable.row();
		inputTable.add(messageField).width(300).height(200);
		
		stage.addActor(inputTable);

		stage.setDebugAll(true);

		pack();
		setVisible(false);
		setBugWindowVisible(false);
	}
	
	public void setBugWindowVisible(boolean isVisible) {
		inputTable.setVisible(isVisible);
	}
	
	public void render(float delta) {
		stage.draw();
		stage.act(delta);
	}
	
	public Stage getStage() {
		return stage;
	}
}
