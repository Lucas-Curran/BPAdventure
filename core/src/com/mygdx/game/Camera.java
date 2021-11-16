package com.mygdx.game;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Camera implements ApplicationListener {
	
	OrthographicCamera cam;
	Viewport viewport;
	
	static final float PPM = 32.0f;
	
	static final float FRUSTUM_WIDTH = Gdx.graphics.getWidth()/PPM;
	static final float FRUSTUM_HEIGHT = Gdx.graphics.getHeight()/PPM;
	
	public Camera() {
		create();
	}
	
	@Override
	public void create() {
		if (cam == null) {						
			cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
			cam.setToOrtho(false, cam.viewportWidth, cam.viewportHeight);
			viewport = new FitViewport(cam.viewportWidth, cam.viewportHeight, cam);		
		}
	}

	@Override
	public void resize(int width, int height) {    
		viewport.update(width, height, false);	
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
	
	public Viewport getViewport() {
		return viewport;
	}
	
	
}
