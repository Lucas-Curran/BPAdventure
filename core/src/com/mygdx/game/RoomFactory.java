package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class RoomFactory {
	
	BodyFactory bodyFactory;
	GameWorld world;
	private Texture tex;
	
	public RoomFactory() {
		world = new GameWorld();
		bodyFactory = BodyFactory.getInstance(world.getInstance());
		tex = new Texture(Gdx.files.internal("redTextBox.png"));
	}
	
	public void makeSquareRoom(float posx, float posy, float width, float height) {
		if (width > height) {
			bodyFactory.makeBoxPolyBody(posx, posy, width, height, BodyFactory.STEEL, tex, BodyType.StaticBody, false, false);
			bodyFactory.makeBoxPolyBody(posx - (width / 2) - (height/2), posy + height * ((width / height) / 2 - 0.5f), height, width, BodyFactory.STEEL, tex, BodyType.StaticBody, false, false);
			bodyFactory.makeBoxPolyBody(posx, posy + width - (height), width, height, BodyFactory.STEEL, tex, BodyType.StaticBody, false, false);
			bodyFactory.makeBoxPolyBody(posx + (width / 2) + (height/2), posy + height * ((width / height) / 2 - 0.5f), height, width, BodyFactory.STEEL, tex, BodyType.StaticBody, false, false);
		} 
	}
	
	public void makeRectangleRoom(float posx, float posy, float width, float height, float height2) {
		bodyFactory.makeBoxPolyBody(posx, posy, height, width, BodyFactory.STEEL, tex, BodyType.StaticBody, false, false);
		bodyFactory.makeBoxPolyBody(posx - (width / 2) - (height/2), posy + height2 * ((width / height2) / 2 - 0.5f), width, height2, BodyFactory.STEEL, tex, BodyType.StaticBody, false, false);
		bodyFactory.makeBoxPolyBody(posx, posy + width - (height2), height, width, BodyFactory.STEEL, tex, BodyType.StaticBody, false, false);
		bodyFactory.makeBoxPolyBody(posx + (width / 2) + (height/2), posy + height2 * ((width / height2) / 2 - 0.5f), width, height2, BodyFactory.STEEL, tex, BodyType.StaticBody, false, false);
	}
	
	
}
