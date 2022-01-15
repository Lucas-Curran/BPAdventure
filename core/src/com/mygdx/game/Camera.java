package com.mygdx.game;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Camera implements ApplicationListener {
	
	static Logger logger = LogManager.getLogger(Camera.class.getName());
	
	OrthographicCamera cam;
	Viewport viewport;
	
	static final float PPM = 32.0f;
	
	static final float FRUSTUM_WIDTH = Gdx.graphics.getWidth()/PPM;
	static final float FRUSTUM_HEIGHT = Gdx.graphics.getHeight()/PPM;
	static final float textureWidth = Gdx.graphics.getWidth();
	static final float textureHeight = Gdx.graphics.getHeight();
	
	/**
	 * Instances the viewport and camera that sets an orthogonal view onto the screen
	 */
	public Camera() {
		create();
	}
	
	@Override
	public void create() {
		if (cam == null) {						
			cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
			cam.setToOrtho(false, cam.viewportWidth, cam.viewportHeight);
			viewport = new FitViewport(cam.viewportWidth, cam.viewportHeight, cam);	
			logger.info("Camera and viewport created.");
		}
	}

	@Override
	public void resize(int width, int height) { 
		//Update the viewport 
		viewport.update(width, height, false);	
		Map.getInstance().getTextBox().resize(width, height);
		if (Map.getInstance().getLevels().getOverworld().getShopWindow() != null) {
			Map.getInstance().getLevels().getOverworld().resize(width, height);
		}
		if (Screens.getGame().getScreen() == Screens.getMenu()) {
			Screens.getMenu().resize(width, height);
		} else if (Screens.getGame().getScreen() == Screens.getSettings()) {
			Screens.getSettings().resize(width, height);
		} else if (Screens.getGame().getScreen() == Screens.getCredits()) {
			Screens.getCredits().resize(width, height);
		} else if (Screens.getGame().getScreen() == Screens.getControlMenu()) {
			Screens.getControlMenu().resize(width, height);
		}
		
		Map.getInstance().getPlayerHUD().resize(width, height);
	}

	@Override
	public void render() {

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
	
	public float getX() {
		return cam.position.x;
	}
	
	
}
