package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.ScreenUtils;

public class Character implements ApplicationListener {

	SpriteBatch batch;
	Texture texture;
	Sprite sprite;
	
	public Character() {
		create();
	}
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		texture = new Texture(Gdx.files.internal("IceCharacter.png"));
		sprite = new Sprite(texture);
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void render() {
		 ScreenUtils.clear(0.57f, 0.77f, 0.85f, 1);
		 if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			 moveLeft();
		 }
		 
		 if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			 moveRight();
		 }
		 
		 if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			 moveUp();
		 }
		 
		 if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			 moveDown();
		 }
		 
		 batch.begin();
	     sprite.draw(batch);
	     batch.end();
	     
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
	public void dispose() {
		batch.dispose();
		texture.dispose();
		
	}
	
	public void moveLeft() {
		sprite.translateX(-2f);
	}
	
	public void moveRight() {
		sprite.translateX(2f);
	}
	
	public void moveUp() {
		sprite.translateY(2f);
	}

	public void moveDown() {
		sprite.translateY(-2f);
	}
	
	public void setProjection(Matrix4 projection) {
		batch.setProjectionMatrix(projection);
	}
	
}
