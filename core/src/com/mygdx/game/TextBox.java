package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

public class TextBox  {
	
	private Stage stage;
	private Table table;
	private Label label;
	private Group group;
	private Image img;
	private Texture tex;
	private BitmapFont font;
	private int numChars;
	private float timePerCharacter;
	private float ctimePerCharacter;
	private float widthOffset;
	private float heightOffset;
	
	
	public TextBox(BitmapFont font, Stage stage) {
		this.font = font;
		this.stage = stage;
		table = new Table();
		group = new Group();
		tex = new Texture(Gdx.files.internal("textbox2.png"));
		img = new Image(tex);
		
		timePerCharacter = 0.08f;
		numChars = 0;
		ctimePerCharacter = 0f;
		
		widthOffset = 50;
		heightOffset = 50;
		
		label = new Label("", new Label.LabelStyle(font, null));
		label.setWrap(true);
		label.setWidth(img.getWidth() - widthOffset);
		label.setAlignment(Align.topLeft);
		label.setScaleX(.1f);
	}
	
	public void createTextBox(float delta, String text, Color color) {
		
		if (numChars < text.length()) {
			ctimePerCharacter += delta;
			if (ctimePerCharacter >= timePerCharacter) {
				ctimePerCharacter = 0;
				numChars++;
			}
		}
		
		
		text = text.substring(0, numChars);
		
		label.setColor(color);
		label.setText(text);
		
		
		stage.addActor(table);
		
		group.addActor(img);
		group.addActor(label);
		
		group.setScale(.5f);
		
		table.setPosition(0, 0);
		table.add(group).bottom().expandX().padBottom(20).padLeft(35);

		label.setPosition(img.getX() + 30, img.getY() + img.getHeight() - 15);
		img.setPosition(0, 0);

		stage.act(delta);
		stage.draw();
	}
	
	public void dispose() {
		stage.dispose();
		tex.dispose();
	}
	
}
