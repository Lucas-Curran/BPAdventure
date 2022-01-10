package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class BpaAssetManager {

	public final AssetManager manager = new AssetManager();
	
	public final String skin = "assets/uiskin.json";
	
	public void loadSkin() {
		SkinParameter params = new SkinParameter("assets/uiskin.txt");
		manager.load(skin, Skin.class, params);
	}
	
}
