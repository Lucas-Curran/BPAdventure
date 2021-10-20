package com.mygdx.game;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class Camera implements ApplicationListener {
	
	OrthographicCamera cam;
	ExtendViewport viewport;

	public Camera() {
		create();
	}
	
	@Override
	public void create() {
		cam = new OrthographicCamera();
		viewport = new ExtendViewport(10, 10, cam);
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);	
	}

	@Override
	public void render() {
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
	public void dispose() {
		
	}
	
	public OrthographicCamera getCamera() {
		return cam;
	}
	
	public Matrix4 getCombined() {
		return cam.combined;
	}
	
	
}
