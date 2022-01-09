package com.mygdx.game.levels;

//Any textures not credited are either either public domain or custom made.

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.Camera;
import com.mygdx.game.Map;
import com.mygdx.game.levels.Levels.LevelDestination;
import com.mygdx.game.ui.ShopWindow;

public class LevelOne extends LevelFactory implements ApplicationListener {
	//roomFactory.makeRectangleRoom(15, 9, 1, 100, 10);
	boolean isCreated;
	static boolean inLevelOne;
	private PolygonSpriteBatch polygonSpriteBatch;
	private ArrayList<PolygonSprite> polySprites;
	private Camera camera;
	
	private ArrayList<short[]> triangles;
	private ArrayList<Body> bodies;
	private ArrayList<PolygonShape> polygonShapes;
	
	private TextureRegion textureRegion;
	Texture texture = new Texture(Gdx.files.internal("newGround.png"));
	
	float[] vertices;
	private ShopWindow shopWindow;
	
	private DoorBuilder db = DoorBuilder.getInstance();
	
	@Override
	public void create() {
		super.createLevel(15, 0, 1, 100, 10, texture);
		camera = new Camera();
		inLevelOne = true;
		Texture texture = new Texture(Gdx.files.internal("newGround.png"));
		
		
		db.createDoor(15, 1.5f, -35, 188, BodyFactory.ICE, "DoorTo2", LevelDestination.LVL_3);

		
		bodyFactory.makeCirclePolyBody(1, 1, 2, BodyFactory.RUBBER, BodyType.StaticBody, false, false);
		bodyFactory.makeBoxPolyBody(10, 1, 5, 1, BodyFactory.STEEL, BodyType.StaticBody, true, false, new Texture(Gdx.files.internal("newGround.png")));
		Map.getInstance().getEntityHandler().spawnLevelOne();
		Map.getInstance().getEntityHandler().spawnShopNPC();
		
		shopWindow = new ShopWindow(Map.getInstance().getEntityHandler().getNPC().getShopWares(), Map.getInstance().getEntityHandler().getNPC().getShopWares(), Map.getInstance().getMoney());
		
		polygonSpriteBatch = new PolygonSpriteBatch();
		
		polySprites = new ArrayList<>();
		triangles = new ArrayList<>();
		bodies = new ArrayList<>();
		polygonShapes = new ArrayList<>();
		
		isCreated = true;
		
		
		textureRegion = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
		textureRegion.flip(false, true);
		texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge);
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);	
		
//		for (int i = 0; i < bodyFactory.getLevelOneBodies().size(); i++) {	
//			
//			Body body = bodyFactory.getLevelOneBodies().get(i);
//			Fixture fixture = body.getFixtureList().get(0);
//			PolygonShape shape = (PolygonShape) fixture.getShape();
//			
//			float[] vertices = calculateVertices(shape, body);		
//			short triangles[] = new EarClippingTriangulator().computeTriangles(vertices).toArray();
//			
//			bodies.add(body);
//			polygonShapes.add(shape);
//			this.triangles.add(triangles);
//	
//			//polySprites.add(newSprite);
//		}
	}
	

	@Override
	public void resize(int width, int height) {
		
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
	
	public ArrayList<PolygonSprite> getPolySprites() {
		return polySprites;
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
	
	public void setCameraPosition(Vector3 position) {
		camera.getCamera().position.set(position);
	}
	
	public ShopWindow getShopWindow() {
		return shopWindow;
	}
}
