package com.mygdx.game;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Camera implements ApplicationListener {
	
	OrthographicCamera cam;
	Viewport viewport;

	public Camera() {
		create();
	}
	
	@Override
	public void create() {
		if (cam == null) {
			cam = new OrthographicCamera(Gdx.graphics.getWidth() / 25, Gdx.graphics.getHeight() / 25);
			cam.setToOrtho(false, cam.viewportWidth, cam.viewportHeight);
			viewport = new FillViewport(cam.viewportWidth, cam.viewportHeight, cam);
		}
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
