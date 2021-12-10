package com.mygdx.game.levels;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.Camera;
import com.mygdx.game.GameWorld;
import com.mygdx.game.Map;
import com.mygdx.game.RoomFactory;

public class LevelOne extends LevelFactory implements ApplicationListener {
	
	boolean isCreated;
	private PolygonSpriteBatch polygonSpriteBatch;
	private ArrayList<PolygonSprite> polySprites;
	private Camera camera;
	
	@Override
	public void create() {
		super.createLevel();
		Texture doorTex = new Texture(Gdx.files.internal("redTextBox.png"));
		Body door = bodyFactory.makeBoxPolyBody(4, 2.7f, 2, 2, BodyFactory.STEEL, doorTex, BodyType.StaticBody, false, true);
		door.setUserData("Door");
		bodyFactory.makeCirclePolyBody(1, 1, 2, BodyFactory.RUBBER, BodyType.StaticBody, false);
		Map.getInstance().getEntityHandler().spawnLevelOne();
		polygonSpriteBatch = new PolygonSpriteBatch();
		polySprites = new ArrayList<>();
		camera = new Camera();
		
		isCreated = true;
		
		for (int i = 0; i < bodyFactory.getBoxBodies().size(); i++) {
			Body boxBody = bodyFactory.getBoxBodies().get(i);
			Fixture fixture = boxBody.getFixtureList().get(0);
			Vector2 mTmp = new Vector2();
			PolygonShape shape = (PolygonShape) fixture.getShape();
			//Texture texture = new TextureAtlas("bpaatlas.txt").findRegion("holderBoots").getTexture();
			Texture texture = new Texture(Gdx.files.internal("ground.txt"));
			texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
			int vertexCount = shape.getVertexCount();
			float[] vertices = new float[vertexCount * 2];
			for (int k = 0; k < vertexCount; k++) {
				shape.getVertex(k, mTmp);
				mTmp.rotateDeg(boxBody.getAngle()*MathUtils.radiansToDegrees);
				mTmp.add(boxBody.getPosition());
				vertices[k*2] = mTmp.x;
				vertices[k*2+1] = mTmp.y;
			}
			short triangles[] = new EarClippingTriangulator().computeTriangles(vertices).toArray();
			texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			TextureRegion textureRegion = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
			textureRegion.flip(false, true);
			PolygonRegion polyRegion = new PolygonRegion(textureRegion, vertices, triangles);
			polySprites.add(new PolygonSprite(polyRegion));
		}
		
		
		
	}
	

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void render() {
		camera.getCamera().update();
		polygonSpriteBatch.setProjectionMatrix(camera.getCombined());
		polygonSpriteBatch.enableBlending();
		polygonSpriteBatch.begin();
		for (int i = 0; i < polySprites.size(); i++) {
			polySprites.get(i).draw(polygonSpriteBatch);
		}
		polygonSpriteBatch.end();
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
	
	public boolean isCreated() {
		return isCreated;
	}
	
	public PolygonSpriteBatch getPolygonSpriteBatch() {
		return polygonSpriteBatch;
	}
	
}
