package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

public class Character implements ApplicationListener {

	SpriteBatch batch;
	Texture texture;
	Sprite sprite;
	float movementSpeed;
	
	public Character() {
		create();
	}
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		texture = new Texture(Gdx.files.internal("IceCharacter.png"));
		sprite = new Sprite(texture);
		movementSpeed = 2;
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void render() {
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
		 
		 if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			 
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
		sprite.translateX(-movementSpeed);
	}
	
	public void moveRight() {
		sprite.translateX(movementSpeed);
	}
	
	public void moveUp() {
		sprite.translateY(movementSpeed);
	}

	public void moveDown() {
		sprite.translateY(-movementSpeed);
	}
	
	public void setProjection(Matrix4 projection) {
		batch.setProjectionMatrix(projection);
	}
	
}
