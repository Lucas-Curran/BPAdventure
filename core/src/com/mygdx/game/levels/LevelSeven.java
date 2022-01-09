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

public class LevelSeven extends LevelFactory implements ApplicationListener {
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
		Body[] platforms = new Body[25];
		Body[] papers = new Body[20];
		Body[] spikes = new Body[7];
		Body slide;
		
		Texture texture = new Texture(Gdx.files.internal("newGround.png"));
		
		public LevelSeven(World world) {
			this.world = world;
		}
		
		@Override
		public void create() {
			textureRegion = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
			textureRegion.flip(false, true);
			texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge);
			texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);	
			super.createLevel(15, 600, 1, 100, 20);
			camera = new Camera();
			
			platforms[0] = bodyFactory.makeBoxPolyBody(-25, 583.5f, 6, 1, BodyFactory.ICE, BodyType.StaticBody, false, false);
			platforms[1] = bodyFactory.makeBoxPolyBody(-32, 586f, 8, 1, BodyFactory.STEEL, BodyType.StaticBody,  false, false);
			platforms[2] = bodyFactory.makeBoxPolyBody(-32, 588.5f, 7, 1, BodyFactory.STEEL, BodyType.StaticBody,  false, false);
			papers[0] = bodyFactory.makeBoxPolyBody(-34, 590f, 0.25f, 0.5f, BodyFactory.STEEL, BodyType.StaticBody,  false, true);
			platforms[3] = bodyFactory.makeBoxPolyBody(-27, 590f, 1, 1, BodyFactory.STEEL, BodyType.StaticBody,  false, false);
			platforms[4] = bodyFactory.makeBoxPolyBody(-25, 592f, 0.5f, 1, BodyFactory.STEEL, BodyType.StaticBody,  false, false);
			platforms[5] = bodyFactory.makeBoxPolyBody(-23, 594f, 0.5f, 1, BodyFactory.STEEL, BodyType.StaticBody, false, false);
			platforms[6] = bodyFactory.makeBoxPolyBody(-25, 596f, 0.25f, 1f, BodyFactory.STEEL, BodyType.StaticBody,  false, false);
			platforms[7] = bodyFactory.makeBoxPolyBody(-22, 598f, 4f, 0.1f, BodyFactory.STEEL, BodyType.StaticBody,  false, false);
			papers[1] = bodyFactory.makeBoxPolyBody(-21, 599f, 0.25f, 0.5f, BodyFactory.STEEL, BodyType.StaticBody,  false, true);
			platforms[8] = bodyFactory.makeBoxPolyBody(-12, 590f, 4f, 0.1f, BodyFactory.STEEL, BodyType.StaticBody,  false, false);
			
			
			Vector2 vertex1 = new Vector2(3, 0);
			Vector2 vertex2 = new Vector2(6, -2);
			Vector2 vertex3 = new Vector2(1, 5);
			
			Vector2[] triangleVertices = {vertex1, vertex2, vertex3};
			slide = bodyFactory.makePolygonShapeBody(triangleVertices, -20, 592, BodyFactory.ICE, BodyType.StaticBody,  false, false);
			
			platforms[9] = bodyFactory.makeBoxPolyBody(-5, 592f, 6f, 1f, BodyFactory.STEEL, BodyType.StaticBody,  false, false);
			platforms[10] = bodyFactory.makeBoxPolyBody(1, 592f, 2f, 1f, BodyFactory.STEEL, BodyType.StaticBody,  false, false);
			platforms[11] = bodyFactory.makeBoxPolyBody(1, 594.5f, 3f, 0.5f, BodyFactory.STEEL, BodyType.StaticBody,  false, false);
			papers[2] = bodyFactory.makeBoxPolyBody(2, 598, 0.25f, 0.5f, BodyFactory.STEEL, BodyType.StaticBody,  false, true);
			platforms[12] = bodyFactory.makeBoxPolyBody(9.5f, 592f, 1f, 1f, BodyFactory.STEEL, BodyType.StaticBody,  false, false);
			platforms[13] = bodyFactory.makeBoxPolyBody(15f, 592f, 3f, 1f, BodyFactory.STEEL, BodyType.StaticBody,  false, false);
			papers[3] = bodyFactory.makeBoxPolyBody(16, 593, 0.25f, 0.5f, BodyFactory.STEEL, BodyType.StaticBody,  false, true);
			platforms[14] = bodyFactory.makeBoxPolyBody(25.5f, 592f, 8f, 1f, BodyFactory.STEEL, BodyType.StaticBody,  false, false);
			
			db.createDoor(28.5f, 593.5f, 43, 599, BodyFactory.RUBBER, "doorLvl7Teleport", LevelDestination.LVL_7);
			
			platforms[15] = bodyFactory.makeBoxPolyBody(43f, 597f, 5f, 1f, BodyFactory.STEEL, BodyType.StaticBody,  false, false);
			platforms[16] = bodyFactory.makeBoxPolyBody(43f, 594f, 5f, 1f, BodyFactory.STEEL, BodyType.StaticBody, false, false);
			platforms[17] = bodyFactory.makeBoxPolyBody(43f, 591f, 5f, 1f, BodyFactory.STEEL, BodyType.StaticBody,  false, false);
			platforms[18] = bodyFactory.makeBoxPolyBody(43f, 588f, 5f, 1f, BodyFactory.STEEL, BodyType.StaticBody,  false, false);
			
			platforms[19] = bodyFactory.makeBoxPolyBody(52f, 594.5f, 2f, 1f, BodyFactory.STEEL, BodyType.StaticBody, false, false);
			platforms[20] = bodyFactory.makeBoxPolyBody(52f, 591.5f, 2f, 1f, BodyFactory.STEEL, BodyType.StaticBody,  false, false);
			platforms[21] = bodyFactory.makeBoxPolyBody(52f, 588.5f, 2f, 1f, BodyFactory.STEEL, BodyType.StaticBody,  false, false);
			platforms[22] = bodyFactory.makeBoxPolyBody(52f, 585.5f, 2f, 1f, BodyFactory.STEEL, BodyType.StaticBody,  false, false);
			platforms[23] = bodyFactory.makeBoxPolyBody(40f, 590.5f, 1f, 18f, BodyFactory.STEEL, BodyType.StaticBody,  false, false);
			
			Vector2 vertex11 = new Vector2(0, 0);
			Vector2 vertex12 = new Vector2(1, 0);
			Vector2 vertex13 = new Vector2(0.5f, 2);
			
			Vector2[] triangleVertices2 = {vertex11, vertex12, vertex13};
			
			spikes[0] = bodyFactory.makePolygonShapeBody(triangleVertices2, 45, 581.5f, BodyFactory.ICE, BodyType.StaticBody,  false, false);
			spikes[1] = bodyFactory.makePolygonShapeBody(triangleVertices2, 46, 581.5f, BodyFactory.ICE, BodyType.StaticBody,  false, false);
			spikes[2] = bodyFactory.makePolygonShapeBody(triangleVertices2, 47, 581.5f, BodyFactory.ICE, BodyType.StaticBody,  false, false);
			spikes[3] = bodyFactory.makePolygonShapeBody(triangleVertices2, 48, 581.5f, BodyFactory.ICE, BodyType.StaticBody,  false, false);
			spikes[4] = bodyFactory.makePolygonShapeBody(triangleVertices2, 49, 581.5f, BodyFactory.ICE, BodyType.StaticBody,  false, false);
			spikes[5] = bodyFactory.makePolygonShapeBody(triangleVertices2, 50, 581.5f, BodyFactory.ICE, BodyType.StaticBody, false, false);
			spikes[6] = bodyFactory.makePolygonShapeBody(triangleVertices2, 51, 581.5f, BodyFactory.ICE, BodyType.StaticBody,  false, false);
			
			papers[4] = bodyFactory.makeBoxPolyBody(55, 582, 0.25f, 0.5f, BodyFactory.STEEL, BodyType.StaticBody,  false, true);
			
			db.createDoor(60, 582.5f, 1.5f, 17, BodyFactory.WOOD, "endOfLevel7", LevelDestination.OVERWORLD);
			
	        			
			NPC npc = new NPC();
			String[] words = {"Heya Ice Cream! Tryna continue?", "Well you better watch out! There's enemies 'round these parts...", 
					"Find and collect the key cards and you'll unlock the next phase!", "Good Luck!"};
			npc.spawnNPC(words, -32, 582);
			String[] message = {"Watch out for the projectiles!"};
			npc.spawnNPC(message, -3, 593);
			
			Map.getInstance().getEntityHandler().spawnShopNPC();
			Map.getInstance().getEntityHandler().spawnLevelSeven();
			
			polygonSpriteBatch = new PolygonSpriteBatch();
			
			polySprites = new ArrayList<>();
			triangles = new ArrayList<>();
			bodies = new ArrayList<>();
			polygonShapes = new ArrayList<>();
			
			isCreated = true;
			
			Texture texture = new Texture(Gdx.files.internal("ground.txt"));
			textureRegion = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
			textureRegion.flip(false, true);
			texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge);
			texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);	
			
//			for (int i = 0; i < bodyFactory.getBoxBodies().size(); i++) {	
//				
//				Body body = bodyFactory.getBoxBodies().get(i);
//				Fixture fixture = body.getFixtureList().get(0);
//				PolygonShape shape = (PolygonShape) fixture.getShape();
//				
//				float[] vertices = calculateVertices(shape, body);		
//				short triangles[] = new EarClippingTriangulator().computeTriangles(vertices).toArray();
//				
//				bodies.add(body);
//				polygonShapes.add(shape);
//				this.triangles.add(triangles);
//		
//				//polySprites.add(newSprite);
//			}
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
}
