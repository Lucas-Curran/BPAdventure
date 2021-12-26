package com.mygdx.game.levels;

import java.util.ArrayList;

import com.badlogic.ashley.core.Entity;
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
import com.mygdx.game.components.B2dBodyComponent;
import com.mygdx.game.components.TextureComponent;
import com.mygdx.game.components.TransformComponent;
import com.mygdx.game.components.TypeComponent;

public class LevelOne extends LevelFactory implements ApplicationListener {
	
	boolean isCreated;
	private PolygonSpriteBatch polygonSpriteBatch;
	private ArrayList<PolygonSprite> polySprites;
	private Camera camera;
	
	@Override
	public void create() {
		super.createLevel();
		camera = new Camera();
		
		Texture doorTex = new Texture(Gdx.files.internal("redTextBox.png"));
		Body door = bodyFactory.makeBoxPolyBody(5, 3f, 2, 3, BodyFactory.STEEL, BodyType.StaticBody, false, false);
		door.setUserData("Door");
		bodyFactory.makeCirclePolyBody(1, 1, 2, BodyFactory.RUBBER, BodyType.StaticBody, false);
		bodyFactory.makeBoxPolyBody(10, 2, 5, 1, BodyFactory.STEEL, BodyType.StaticBody, true, false);
		Map.getInstance().getEntityHandler().spawnLevelOne();
		polygonSpriteBatch = new PolygonSpriteBatch();
		polySprites = new ArrayList<>();
		
		isCreated = true;
		
		for (int i = 0; i < bodyFactory.getBoxBodies().size(); i++) {
			
			Entity entity = pooledEngine.createEntity();
			B2dBodyComponent b2dbody = pooledEngine.createComponent(B2dBodyComponent.class);
			TransformComponent position = pooledEngine.createComponent(TransformComponent.class);
			TextureComponent textureComp = pooledEngine.createComponent(TextureComponent.class);
			TypeComponent type = pooledEngine.createComponent(TypeComponent.class);
			
			type.type = TypeComponent.SCENERY;
			
			b2dbody.body = bodyFactory.getBoxBodies().get(i);
			position.position.set(b2dbody.body.getPosition().x, b2dbody.body.getPosition().y, 0);
			Fixture fixture = b2dbody.body.getFixtureList().get(0);
			Vector2 mTmp = new Vector2();
			PolygonShape shape = (PolygonShape) fixture.getShape();
			//Texture texture = new TextureAtlas("bpaatlas.txt").findRegion("holderBoots").getTexture();
			Texture texture = new Texture(Gdx.files.internal("ground.txt"));
			texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
			int vertexCount = shape.getVertexCount();
			float[] vertices = new float[vertexCount * 2];
			for (int k = 0; k < vertexCount; k++) {
				shape.getVertex(k, mTmp);
				mTmp.rotateDeg(b2dbody.body.getAngle()*MathUtils.radiansToDegrees);
				mTmp.add(b2dbody.body.getPosition());
				vertices[k*2] = mTmp.x;
				vertices[k*2+1] = mTmp.y;
			}
			short triangles[] = new EarClippingTriangulator().computeTriangles(vertices).toArray();
			texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			TextureRegion textureRegion = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
			textureComp.region = textureRegion;
			PolygonRegion polyRegion = new PolygonRegion(textureRegion, vertices, triangles);
			
			entity.add(b2dbody);
			entity.add(position);
			entity.add(textureComp);
			entity.add(type);
			
			pooledEngine.addEntity(entity);
			
			polySprites.add(new PolygonSprite(polyRegion));
		}
	}
	

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void render() {
		
		for (Entity entity : pooledEngine.getEntities()) {
			entity.getComponent(TransformComponent.class).position.set(
					entity.getComponent(B2dBodyComponent.class).body.getPosition().x - camera.getCamera().position.x, 
					entity.getComponent(B2dBodyComponent.class).body.getPosition().y - camera.getCamera().position.y,
					0);
		}
		
//		camera.getCamera().update();
//		polygonSpriteBatch.setProjectionMatrix(camera.getCombined());
//		polygonSpriteBatch.begin();
//		for (int i = 0; i < polySprites.size(); i++) {
//			polySprites.get(i).draw(polygonSpriteBatch);
//		}
//		polygonSpriteBatch.end();
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
