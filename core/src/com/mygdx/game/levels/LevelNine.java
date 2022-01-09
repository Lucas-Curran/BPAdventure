package com.mygdx.game.levels;

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
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.Camera;
import com.mygdx.game.Map;
import com.mygdx.game.entities.Bullet;
import com.mygdx.game.entities.NPC;
import com.mygdx.game.levels.Levels.LevelDestination;
import com.mygdx.game.components.BulletComponent;

public class LevelNine extends LevelFactory implements ApplicationListener {
		boolean isCreated;
		private PolygonSpriteBatch polygonSpriteBatch;
		private ArrayList<PolygonSprite> polySprites;
		private Camera camera;
		
		private ArrayList<short[]> triangles;
		private ArrayList<Body> bodies;
		private ArrayList<PolygonShape> polygonShapes;
		
		private TextureRegion textureRegion;
		
		float[] vertices;
		
		Body door;
		DoorBuilder db = DoorBuilder.getInstance();
		World world;
		
		Texture texture = new Texture(Gdx.files.internal("newGround.png"));
		
		public LevelNine(World world) {
			this.world = world;
		}
		
		@Override
		public void create() {
			textureRegion = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
			textureRegion.flip(false, true);
			texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge);
			texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);	
			super.createLevel(15, 800, 1, 100, 20);
			camera = new Camera();
			
			Texture texture = new Texture(Gdx.files.internal("newGround.png"));
			
			db.createDoor(45, 787, -35, 888, BodyFactory.STONE, "doorTo10", LevelDestination.LVL_10);
			
	        			
			NPC npc = new NPC();
			
			Map.getInstance().getEntityHandler().spawnShopNPC();
			Map.getInstance().getEntityHandler().spawnLevelNine();
			
			polygonSpriteBatch = new PolygonSpriteBatch();
			
			polySprites = new ArrayList<>();
			triangles = new ArrayList<>();
			bodies = new ArrayList<>();
			polygonShapes = new ArrayList<>();
			
			isCreated = true;
		}
		

		@Override
		public void resize(int width, int height) {
			
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
}
