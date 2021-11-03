package com.mygdx.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.mygdx.game.components.*;

public class TextBox {
	
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
	boolean writing;
	private TextureRegion textAni;
	private TextureAtlas textureAtlas;
	private float elapsedTime;
	private SpriteBatch batch;
	private Animation<TextureRegion> anim;
	private String[] text;
	private String[] originalText;
	private Color color;
	private int textSequence;
	
	public TextBox(BitmapFont font, Stage stage, Color color) {
		this.font = font;
		this.stage = stage;
		this.color = color;
		
		table = new Table();
		group = new Group();
		tex = new Texture(Gdx.files.internal("textbox2.png"));
		img = new Image(tex);
		
		timePerCharacter = 0.045f;
		numChars = 0;
		ctimePerCharacter = 0f;
		
		widthOffset = 60;
		heightOffset = 15;
		
		textureAtlas = new TextureAtlas("atlasAdv.txt");
		textAni = new TextureRegion(textureAtlas.findRegion("arrowAni"));
		label = new Label("", new Label.LabelStyle(font, null));
		label.setWrap(true);
		label.setWidth(img.getWidth() - widthOffset);
		label.setAlignment(Align.topLeft);
		
		batch = new SpriteBatch();
		anim = new Animation<TextureRegion>(.25f, Utilities.spriteSheetToFrames(textAni, 10, 1));
		
		writing = false;
		
		stage.addActor(table);
		
		group.addActor(img);
		group.addActor(label);
		
		group.setScale(.5f);
		
		table.setPosition(0, 0);
		table.add(group).bottom().padBottom(20).padLeft(35);

		label.setPosition(img.getX() + widthOffset / 2, img.getY() + img.getHeight() - heightOffset);
		img.setPosition(0, 0);
		
		textSequence = 0;
		
		table.setVisible(false);
	}
	
	public void renderTextBox(float delta) {
		if (isVisible()) {
			String stringText = originalText[textSequence].substring(0, numChars);
			label.setColor(color);
			label.setText(stringText);

			if (originalText[textSequence].length() == numChars) {
				writing = false;
			}

			if (originalText[textSequence].length() > numChars) {
				writing = true;
				ctimePerCharacter += delta;
				if (ctimePerCharacter >= timePerCharacter) {
					ctimePerCharacter = 0;
					numChars++;
				}
			} 

			stage.act(delta);
			stage.draw();

			if (!writing) {
				elapsedTime += Gdx.graphics.getDeltaTime();
				batch.begin();
				batch.draw(anim.getKeyFrame(elapsedTime, true), img.getX(Align.center) - 30, label.getHeight() + 8);
				batch.end(); 
			}
		}
	}
	
	public void dispose() {
		stage.dispose();
		tex.dispose();
		batch.dispose();
	}
	
	public boolean isWriting() {
		return writing;
	}
	
	public void setWritingSpeed(float timePerCharacter) {
		this.timePerCharacter = timePerCharacter;
	}
	
	public void hideTextBow() {
		table.setVisible(false);  
	}
	
	public void showTextBox() {
		table.setVisible(true);
	}
	
	public boolean isVisible() {
		return table.isVisible();
	}
	
	public void setText(String[] text) {
		numChars = 0;
		textSequence = 0;
		this.text = text;
		originalText = text;
		timePerCharacter = 0.045f;
		table.setVisible(true);
	}
	
	public String[] getText() {
		return text;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public int getTextSequence() {
		return textSequence;
	}
	
	public void setTextSequence(int textSequence) {
		numChars = 0;
		this.textSequence = textSequence;
		timePerCharacter = 0.045f;
	}
	
}
