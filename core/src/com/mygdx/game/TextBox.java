package com.mygdx.game;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

public class TextBox implements InputProcessor {
	
	static Logger logger = LogManager.getLogger(TextBox.class.getName());
	
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
	
	/**
	 * Writes and keeps track of NPC dialog 
	 * @param font - font that is used for the textbox's text
	 * @param stage - stage that textbox will be added to
	 * @param color - color of the text
	 */
	public TextBox(BitmapFont font, Stage stage, Color color) {
		this.stage = stage;
		this.color = color;
		
		try {
			table = new Table();
			group = new Group();

			// time per character is the time between each character, 
			//numChars in the number of characters in the current string array index
			//ctimePerCharacter is incremented by delta (1/60f), and then reset once it reaches the value of time per character
			timePerCharacter = 0.045f;
			numChars = 0;
			ctimePerCharacter = 0f;

			//offsets are constants that work to contain the text inside the textbox png
			widthOffset = 60;
			heightOffset = 15;

			textureAtlas = new TextureAtlas("textures.txt");
			tex = new TextureRegion(textureAtlas.findRegion("textbox2"));
			img = new Image(tex);

			//buttons created, configured, and added to table
			//they are only visible if the current npc has options available
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
			//animation that plays after textbox is done writing the current string index, uses utilities sprite sheet to frames animation method
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
	
	/**
	 * Renders the textbox every frame
	 * @param delta - time between frames
	 */
	public void renderTextBox(float delta) {
		//rendering is called in map, and is called every frame, however the textbox won't appear unless set to visible
		if (isVisible()) {
			//a new string is made every frame that is a substring of the original text (at a certain sequence) that is set during the set method
			//as each frame happens, numChars goes up when ctimePerCharacter reaches timePerCharacter, and then the new substring is created
			String stringText = originalText[textSequence].substring(0, numChars);
			label.setColor(color);
			label.setText(stringText);
			
			//if the length of the current text sequence equals the number of characters in the temporary string text, this if statement is true
			if (originalText[textSequence].length() == numChars) {
				//writing set to false and options are shown if available to the NPC
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

			//Given that the current length of the sequence is still greater than the number of characters currently
			//time per character is incremented and number of characters goes up
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

			//if finished writing and there are no options, the animation displaying text being done writing is drawn to the screen
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
	
	/**
	 * Sets the text sequence of the textbox, resets last texts properties
	 * @param text - string array that displays which text should be displayed in what order
	 * @param hasOptions - boolean to check whether or not the text box will have a yes and no button
	 */
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
	
	/**
	 * Sets the text of the option buttons
	 * @param options - boolean for options true or false
	 * @param confirmText - confirm button text
	 * @param rejectText - reject button text
	 */
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
	
	/**
	 * Sets what the text string array should send to the textbox to write
	 * @param textSequence - position where string array should write
	 */
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
		//yes button clicked set textbox to invisible and open shop
		if (yesButton.isPressed()) {
			table.setVisible(false);
			yesButton.setVisible(false);
			noButton.setVisible(false);
			Map.getInstance().getLevels().getOverworld().getShopWindow().setShopVisible(true);
			Map.getInstance().getAudioManager().stopAll();
			Map.getInstance().getAudioManager().playShop();
			return true;			
		} // no button clicked, set textbox to invisible 
		else if (noButton.isPressed()) {
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
