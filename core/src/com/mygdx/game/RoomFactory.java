package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.levels.Levels.LevelDestination;

public class RoomFactory {
	
	BodyFactory bodyFactory;
	GameWorld world;
	Texture texture;
	
	public RoomFactory() {
		world = new GameWorld();
		bodyFactory = BodyFactory.getInstance(world.getInstance());
		
	}
	
	public void makeSquareRoom(float posx, float posy, float width, float height, Texture texture) {
		if (width > height) {
			bodyFactory.makeBoxPolyBody(posx, posy, width, height, BodyFactory.STEEL, BodyType.StaticBody, false, false, texture);
			bodyFactory.makeBoxPolyBody(posx - (width / 2) - (height/2), posy + height * ((width / height) / 2 - 0.5f), height, width, BodyFactory.STEEL, BodyType.StaticBody, false, false, texture);
			bodyFactory.makeBoxPolyBody(posx, posy + width - (height), width, height, BodyFactory.STEEL, BodyType.StaticBody, false, false, texture);
			bodyFactory.makeBoxPolyBody(posx + (width / 2) + (height/2), posy + height * ((width / height) / 2 - 0.5f), height, width, BodyFactory.STEEL, BodyType.StaticBody, false, false, texture);
			} 
	}
	
	public void makeRectangleRoom(float posx, float posy, float width, float height, float height2, Texture texture) {
		bodyFactory.makeBoxPolyBody(posx, posy, height, width, BodyFactory.STEEL, BodyType.StaticBody, false, false, texture);
		bodyFactory.makeBoxPolyBody(posx - (width / 2) - (height/2), posy + height2 * ((width / height2) / 2 - 0.5f), width, height2, BodyFactory.STEEL, BodyType.StaticBody, false, false, texture);
		bodyFactory.makeBoxPolyBody(posx, posy + width - (height2), height, width, BodyFactory.STEEL, BodyType.StaticBody, false, false, texture);
		bodyFactory.makeBoxPolyBody(posx + (width / 2) + (height/2), posy + height2 * ((width / height2) / 2 - 0.5f), width, height2, BodyFactory.STEEL, BodyType.StaticBody, false, false, texture);
		
	}
	
	
}
