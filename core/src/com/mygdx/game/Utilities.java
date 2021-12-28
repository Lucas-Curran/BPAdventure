package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.systems.RenderingSystem;

public class Utilities {
	
	private static TextureAtlas atlas = new TextureAtlas("bpaatlas.txt");
	private static TextureAtlas uiAtlas = new TextureAtlas("uiskin.txt");
	
	public static Skin UISKIN = new Skin(atlas);
	public static Skin ACTUAL_UI_SKIN = new Skin(Gdx.files.internal("uiskin.json"));
	
	private static TextButtonStyle textButtonStyle;
	private static Skin buttonSkin;
	private static TextureAtlas textureAtlasTest;
	private static BitmapFont font;

	public static TextureRegion[] spriteSheetToFrames(TextureRegion region, int FRAME_COLS, int FRAME_ROWS){
		// split texture region
		TextureRegion[][] tmp = region.split(region.getRegionWidth() / FRAME_COLS,
				region.getRegionHeight() / FRAME_ROWS);

		// compact 2d array to 1d array
		TextureRegion[] frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				frames[index++] = tmp[i][j];
			}
		}

		return frames;
	}
	
	public static TextButton buttonSettings(TextButton button) {
		button.getLabel().setAlignment(Align.left);
		button.getLabelCell().padLeft(35);
		button.getLabel().setFontScale(2,2);
		return button;
	}
	
	public static TextButtonStyle buttonStyles(String upStyle, String overStyle) {
		font = new BitmapFont();
		buttonSkin = new Skin(Gdx.files.internal("uiskin.json"));
		textureAtlasTest = new TextureAtlas("test.txt");
		buttonSkin.addRegions(textureAtlasTest);
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = font;
		textButtonStyle.fontColor = Color.WHITE;
		textButtonStyle.overFontColor = Color.YELLOW;
		textButtonStyle.up = buttonSkin.getDrawable(upStyle);
		textButtonStyle.over = buttonSkin.getDrawable(overStyle);
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -1;
		return textButtonStyle;
	}
	
	
}
	
