package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

public class TextBox  {
	
	private Stage stage;
	private Table table;
	private Label label;
	private Group group;
	private Image img;
	private Texture tex;
	private BitmapFont font;
	
	public TextBox(BitmapFont font) {
		this.font = font;
		stage = new Stage();
		table = new Table();
		label = new Label("If ur reading this, you stupid", new Label.LabelStyle(font, Color.WHITE));
		group = new Group();
		tex = new Texture(Gdx.files.internal("textbox2.png"));
		img = new Image(tex);
	}
	
	public void createTextBox(Float delta, String text) {
		stage.addActor(table);
		
		group.addActor(img);
		group.addActor(label);
		
		table.setPosition(0, 0);
		table.add(group).bottom().expandX().padBottom(20).padLeft(35);

		label.setPosition(img.getX() + 30, img.getY() + img.getHeight() / 3.4f);
		img.setPosition(0, 0);
		
		img.setScaleY(.5f);
		
		stage.act(delta);
		stage.draw();
	}
	
	public void dispose() {
		
	}
	
}
