package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Align;

public class MainMenu implements Screen, InputProcessor {

	private Texture menuBackground;
	private SpriteBatch spriteBatch;
	private Camera cam;
	private Stage stage;
	private Table table;
	private TextButton startButton;
	private TextButtonStyle textButtonStyle;
	private Skin skin;
	private TextureAtlas textureAtlas;
	private BitmapFont font;
	private InputMultiplexer inputMultiplexer;
	
	public MainMenu() {
		menuBackground = new Texture(Gdx.files.internal("menu_bg.png"));
		spriteBatch = new SpriteBatch();
		cam = new Camera();
		stage = new Stage();
		table = new Table();
		font = new BitmapFont(Gdx.files.internal("font.fnt"));
		
		startButton = new TextButton("Start", buttonStyles("MenuRectangle", "MenuRectangle"));
		buttonSettings(startButton);
	
		table.left().padBottom(100);
		table.add(startButton).width(300).height(100);
		stage.addActor(table);
	}
	
	@Override
	public void show() {
		
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(this);
		inputMultiplexer.addProcessor(stage);
		
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	@Override
	public void render(float delta) {
			
		spriteBatch.setProjectionMatrix(cam.getCombined());
		spriteBatch.begin();
		spriteBatch.draw(menuBackground, 0, 0, cam.getViewport().getWorldWidth(), cam.getViewport().getWorldHeight());
		spriteBatch.end();		
		
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		stage.dispose();
		spriteBatch.dispose();	
	}
	
	public TextButton buttonSettings(TextButton button) {
		button.getLabel().setAlignment(Align.left);
		button.getLabelCell().padLeft(35);
		button.getLabel().setFontScale(4,4);
		return button;
	}
	
	public TextButtonStyle buttonStyles(String upStyle, String overStyle) {
		font = new BitmapFont();
		skin = new Skin();
		textureAtlas = new TextureAtlas("test.txt");
		skin.addRegions(textureAtlas);
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = font;
		textButtonStyle.fontColor = Color.WHITE;
		textButtonStyle.up = skin.getDrawable(upStyle);
		textButtonStyle.over = skin.getDrawable(overStyle);
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -1;
		return textButtonStyle;
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
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (startButton.isPressed()) {
			 Screens.toMap();
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
