package com.mygdx.game.levels;

import java.util.ArrayList;

import javax.swing.Box;

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
import com.badlogic.gdx.math.Vector3;
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
import com.mygdx.game.entities.EntityHandler;
import com.mygdx.game.ui.ShopWindow;

public class LevelOne extends LevelFactory implements ApplicationListener {
	
	boolean isCreated;
	private PolygonSpriteBatch polygonSpriteBatch;
	private Camera camera;
	
	private ArrayList<short[]> triangles;
	private ArrayList<Body> bodies;
	private ArrayList<PolygonShape> polygonShapes;
	
	private TextureRegion textureRegion;
	
	float[] vertices;
	
	Body door;
	
	private ShopWindow shopWindow;
	
	@Override
	public void create() {
		super.createLevel();
		camera = new Camera();
		
		door = bodyFactory.makeBoxPolyBody(4, 2.0f, 2, 2, BodyFactory.STEEL, BodyType.StaticBody, false, true);
		door.setUserData("Door");
		
		bodyFactory.makeCirclePolyBody(1, 1, 2, BodyFactory.RUBBER, BodyType.StaticBody, false, false);
		
		Map.getInstance().getEntityHandler().spawnLevelOne();
		Map.getInstance().getEntityHandler().spawnShopNPC();
		
		shopWindow = new ShopWindow(Map.getInstance().getEntityHandler().getNPC().getShopWares(), Map.getInstance().getEntityHandler().getNPC().getShopWares(), Map.getInstance().getMoney());
		
		polygonSpriteBatch = new PolygonSpriteBatch();

		triangles = new ArrayList<>();
		bodies = new ArrayList<>();
		polygonShapes = new ArrayList<>();
		
		isCreated = true;
		
		Texture texture = new Texture(Gdx.files.internal("ground.txt"));
		textureRegion = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
		textureRegion.flip(false, true);
		texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge);
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);	
		
		for (int i = 0; i < bodyFactory.getBoxBodies().size(); i++) {	
			
			Body body = bodyFactory.getBoxBodies().get(i);
			Fixture fixture = body.getFixtureList().get(0);
			PolygonShape shape = (PolygonShape) fixture.getShape();
			
			float[] vertices = calculateVertices(shape, body);		
			short triangles[] = new EarClippingTriangulator().computeTriangles(vertices).toArray();
			
			bodies.add(body);
			polygonShapes.add(shape);
			this.triangles.add(triangles);

		}
	}
	

	@Override
	public void resize(int width, int height) {
		shopWindow.resize(width, height);
	}

	@Override
	public void render() {	
		camera.getCamera().update();
		polygonSpriteBatch.setProjectionMatrix(camera.getCombined());
		polygonSpriteBatch.begin();
		for (int i = 0; i < triangles.size(); i++) {
			vertices = calculateVertices(polygonShapes.get(i), bodies.get(i));
			PolygonRegion newRegion = new PolygonRegion(textureRegion, vertices, triangles.get(i));
			PolygonSprite newSprite = new PolygonSprite(newRegion);
			newSprite.draw(polygonSpriteBatch);
		}
		polygonSpriteBatch.end();
		shopWindow.render(Gdx.graphics.getDeltaTime());
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
	
	public float[] calculateVertices(PolygonShape shape, Body body) {
		Vector2 mTmp = new Vector2();
		int vertexCount = shape.getVertexCount();
		float[] vertices = new float[vertexCount * 2];
		for (int k = 0; k < vertexCount; k++) {
			shape.getVertex(k, mTmp);
			mTmp.rotateDeg(body.getAngle()*MathUtils.radiansToDegrees);
			mTmp.add(body.getPosition());
			vertices[k*2] = mTmp.x;
			vertices[k*2+1] = mTmp.y;
		}
		return vertices;
	}
	
	public ShopWindow getShopWindow() {
		return shopWindow;
	}
	
	public void setCameraPosition(Vector3 position) {
		camera.getCamera().position.set(position);
	}
	
}
