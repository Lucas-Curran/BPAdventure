package com.mygdx.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
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
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.components.*;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class TextBox implements InputProcessor {
	
	private Stage stage;
	private Table table;
	private Label label;
	private Group group;
	private Image img;
	private TextureRegion tex;
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
	private boolean options;
	private TextButton yesButton, noButton;
	
	public TextBox(BitmapFont font, Stage stage, Color color) {
		this.stage = stage;
		this.color = color;

		table = new Table();
		group = new Group();
		
		timePerCharacter = 0.045f;
		numChars = 0;
		ctimePerCharacter = 0f;
		
		widthOffset = 60;
		heightOffset = 15;
		
		textureAtlas = new TextureAtlas("textures.txt");
		tex = new TextureRegion(textureAtlas.findRegion("textbox2"));
		img = new Image(tex);
		
		yesButton = new TextButton("Yes", Utilities.buttonStyles("default-rect", "default-rect-down"));
		noButton = new TextButton("No", Utilities.buttonStyles("default-rect", "default-rect-down"));
		
		Utilities.buttonSettings(yesButton);
		Utilities.buttonSettings(noButton);
		
		yesButton.getLabel().setStyle(new LabelStyle(font, null));
		noButton.getLabel().setStyle(new LabelStyle(font, null));
		
		yesButton.getLabel().setFontScale(0.7f);
		noButton.getLabel().setFontScale(0.7f);
		
		yesButton.setVisible(false);
		noButton.setVisible(false);
		
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
		group.addActor(yesButton);
		group.addActor(noButton);
		
		group.setScale(.5f);
		
		table.setPosition(0, 0);
		table.add(group).bottom().padBottom(20).padLeft(35);

		label.setPosition(img.getX() + widthOffset / 2, img.getY() + img.getHeight() - heightOffset);
		img.setPosition(0, 0);
		
		yesButton.setPosition(70, 20);
		noButton.setPosition(200, 20);
		
		yesButton.setHeight(40);
		noButton.setHeight(40);
		
		yesButton.setWidth(120);
		noButton.setWidth(120);
		
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
				if (options) {
					
					if (noButton.isOver()) {
						noButton.remove();
						group.addActor(noButton);
					} else if (yesButton.isOver()) {
						yesButton.remove();
						group.addActor(yesButton);
					}
					
					yesButton.setVisible(true);
					noButton.setVisible(true);
					options = false;				
				}
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

			if (!writing && !yesButton.isVisible() && !noButton.isVisible()) {
				elapsedTime += Gdx.graphics.getDeltaTime();
				batch.begin();
				batch.draw(anim.getKeyFrame(elapsedTime, true), img.getX(Align.center) - 30, label.getHeight() + 8);
				batch.end(); 
			}
		}
	}
	
	public void dispose() {
		stage.dispose();
		batch.dispose();
	}

	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
	}
	
	public boolean isWriting() {
		return writing;
	}
	
	public void setWritingSpeed(float timePerCharacter) {
		this.timePerCharacter = timePerCharacter;
	}
	
	public TextBox getInstance() {
		return this;
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
	
	public void setText(String[] text, boolean hasOptions) {
		options = false;
		yesButton.setVisible(false);
		noButton.setVisible(false);
		if (hasOptions) {
			options = true;
		}
		numChars = 0;
		textSequence = 0;
		this.text = text;
		originalText = text;
		timePerCharacter = 0.045f;
		table.setVisible(true);
	}
	
	public void setOptions(boolean options, String confirmText, String rejectText) {
		this.options = options;
		yesButton.setText(confirmText);
		noButton.setText(rejectText);
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
	
	public Stage getStage() {
		return stage;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (yesButton.isPressed()) {
			table.setVisible(false);
			yesButton.setVisible(false);
			noButton.setVisible(false);
			Map.getInstance().getLevels().getOverworld().getShopWindow().setShopVisible(true);
			return true;
		} else if (noButton.isPressed()) {
			table.setVisible(false);
			yesButton.setVisible(false);
			noButton.setVisible(false);
			return true;
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
