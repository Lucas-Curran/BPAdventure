package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class TextBox  {
	
	private BitmapFont font;
	private SpriteBatch batch;
	private FreeTypeFontGenerator generator;
	private FreeTypeFontParameter parameter;
	private Label label;
	
	public TextBox(BitmapFont font) {
		this.font = font;
		batch = new SpriteBatch();
		generator = new FreeTypeFontGenerator(Gdx.files.internal("opensans-regular.ttf"));
		parameter = new FreeTypeFontParameter();
	}
	
	public void createTextBox(String text, LabelStyle labelStyle, float x, float y) {
//		parameter.size = 20;
//		font = generator.generateFont(parameter);
//		batch.begin();
//		font.draw(batch, text, x, y);
//		batch.end();
		label = new Label(text, labelStyle);
		label.setFontScale(.2f);	
	}
	
	public void dispose() {
		generator.dispose();
	}
	
}
