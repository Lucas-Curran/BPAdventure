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
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.Camera;
import com.mygdx.game.GameWorld;
import com.mygdx.game.Map;
import com.mygdx.game.entities.Player;
import com.mygdx.game.levels.Levels.LevelDestination;

public class LevelThree extends LevelFactory implements ApplicationListener{
	//roomFactory.makeRectangleRoom(15, 9, 1, 100, 10);
	boolean isCreated;
	private PolygonSpriteBatch polygonSpriteBatch;
	private ArrayList<PolygonSprite> polySprites;
	private Camera camera;
	
	private ArrayList<short[]> triangles;
	private ArrayList<Body> bodies;
	private ArrayList<PolygonShape> polygonShapes;
	
	private TextureRegion textureRegion;
	
	GameWorld gameWorld;
	World world;
	Box2DDebugRenderer renderer;
	
	float[] vertices;
	
	Body[] pillars = new Body[20];
	Body[] safeSpaces = new Body[5];
	Body sawCenter, rotator, blade1, blade2, slide, slideJump, level3EndDoor, lavaFloor, lavaCeiling, lavaCeiling2;
	
	public LevelThree(World world) {
		this.world = world;
	}
	
	@Override
	public void create() {
				
		super.createLevel(15, 200, 1, 100, 15);
		camera = new Camera();
		
		DoorBuilder db = DoorBuilder.getInstance();
		
		pillars[1] = bodyFactory.makeBoxPolyBody(-33, 187f, 1, 1, BodyFactory.STEEL, BodyType.StaticBody, false, false);
		pillars[2] = bodyFactory.makeBoxPolyBody(-30, 188f, 1, 3, BodyFactory.STEEL, BodyType.StaticBody, false, false);
		pillars[3] = bodyFactory.makeBoxPolyBody(-25, 187f, 1, 3, BodyFactory.STEEL, BodyType.StaticBody, false, false);
		pillars[4] = bodyFactory.makeBoxPolyBody(-14, 188f, 1, 3, BodyFactory.STEEL, BodyType.StaticBody, false, false);
		pillars[5] = bodyFactory.makeBoxPolyBody(-8, 187f, 1, 1, BodyFactory.STEEL, BodyType.StaticBody, false, false);
		pillars[5].setUserData("gravityPillar");
		pillars[6] = bodyFactory.makeBoxPolyBody(-5, 199f, 1, 3, BodyFactory.STEEL, BodyType.StaticBody, false, false);
		pillars[7] = bodyFactory.makeBoxPolyBody(0, 198f, 1, 4, BodyFactory.STEEL, BodyType.StaticBody, false, false);
		pillars[8] = bodyFactory.makeBoxPolyBody(5, 197f, 1, 5, BodyFactory.STEEL, BodyType.StaticBody, false, false);
		pillars[9] = bodyFactory.makeBoxPolyBody(5, 195f, 4, 1, BodyFactory.STEEL, BodyType.DynamicBody, false, false);
		pillars[10] = bodyFactory.makeBoxPolyBody(10, 198f, 1, 4, BodyFactory.STEEL, BodyType.StaticBody, false, false);
		pillars[11] = bodyFactory.makeBoxPolyBody(14, 197f, 1, 5, BodyFactory.STEEL, BodyType.StaticBody, false, false);
		pillars[12] = bodyFactory.makeBoxPolyBody(18, 196f, 1, 7, BodyFactory.STEEL, BodyType.StaticBody, false, false);
		pillars[13] = bodyFactory.makeBoxPolyBody(40, 196f, 1, 1, BodyFactory.STEEL, BodyType.StaticBody, false, false);
		pillars[13].setUserData("gravityPillar2");
		db.createDoor(45, 187f, 0, 0, BodyFactory.STEEL, "lvl3EndDoor", LevelDestination.LVL_4);
		lavaFloor = bodyFactory.makeBoxPolyBody(5, 186.1f, 77, 1, BodyFactory.STEEL, BodyType.StaticBody, false, false);
		lavaFloor.setUserData("lavaFloor");
		lavaCeiling = bodyFactory.makeBoxPolyBody(-5, 199.9f, 60, 1, BodyFactory.STEEL, BodyType.StaticBody, false, false);
		lavaCeiling.setUserData("lavaCeiling");
		lavaCeiling2 = bodyFactory.makeBoxPolyBody(50, 199.9f, 30, 1, BodyFactory.STEEL, BodyType.StaticBody, false, false);
		lavaCeiling2.setUserData("lavaCeiling2");
		
//		rotator = bodyFactory.makeBoxPolyBody(-22, 104f, 1, 1, BodyFactory.STEEL, BodyType.DynamicBody, false, false);
		
		
		sawCenter = bodyFactory.makeCirclePolyBody(-20, 190f, 2, BodyFactory.RUBBER, BodyType.StaticBody, false, false);
		
		Vector2 vertex1 = new Vector2(1, 3);
		Vector2 vertex2 = new Vector2(3, 4);
		Vector2 vertex3 = new Vector2(1, 5);
		
		Vector2 vertex11 = new Vector2(1, 3);
		Vector2 vertex12 = new Vector2(-1, 4);
		Vector2 vertex13 = new Vector2(1, 5);
		
		Vector2 vertex21 = new Vector2(1, -1);
		Vector2 vertex22 = new Vector2(5, 5);
		Vector2 vertex23 = new Vector2(1, 5);
		
		Vector2 vertex31 = new Vector2(1, -1);
		Vector2 vertex32 = new Vector2(-5, 5);
		Vector2 vertex33 = new Vector2(1, 5);
		
		Vector2[] triangleVertices = {vertex1, vertex2, vertex3};
		Vector2[] triangleVertices2 = {vertex11, vertex12, vertex13};
		Vector2[] triangleVertices3 = {vertex21, vertex22, vertex23};
		Vector2[] triangleVertices4 = {vertex31, vertex32, vertex33};
		
		blade1 = bodyFactory.makePolygonShapeBody(triangleVertices, -20, 186, BodyFactory.STEEL, BodyType.DynamicBody, false, false);
		blade2 = bodyFactory.makePolygonShapeBody(triangleVertices2, -22, 186, BodyFactory.STEEL, BodyType.DynamicBody, false, false);
		slide = bodyFactory.makePolygonShapeBody(triangleVertices3, 21, 195, BodyFactory.ICE, BodyType.StaticBody, false, false);
		slideJump = bodyFactory.makePolygonShapeBody(triangleVertices4, 35, 195, BodyFactory.ICE, BodyType.StaticBody, false, false);
		slide.setGravityScale(0);
		blade1.setGravityScale(0);
		blade2.setGravityScale(0);
		
		RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.initialize(sawCenter, blade1, sawCenter.getWorldCenter());
        revoluteJointDef.enableMotor = true;
        revoluteJointDef.motorSpeed = -3;
        revoluteJointDef.maxMotorTorque = 3000;
        
        RevoluteJointDef revoluteJointDef2 = new RevoluteJointDef();
        revoluteJointDef2.initialize(sawCenter, blade2, sawCenter.getWorldCenter());
        revoluteJointDef2.enableMotor = true;
        revoluteJointDef2.motorSpeed = -3;
        revoluteJointDef2.maxMotorTorque = 3000;
        
        RevoluteJointDef revoluteJointDef3 = new RevoluteJointDef();
        revoluteJointDef3.initialize(pillars[8], pillars[9], pillars[9].getWorldCenter());
//        revoluteJointDef2.enableMotor = false;
//        revoluteJointDef2.motorSpeed = -3;
//        revoluteJointDef2.maxMotorTorque = 3000;
        
//        WeldJointDef weldJointDef = new WeldJointDef();
//        weldJointDef.initialize(sawCenter, blade1, sawCenter.getWorldCenter());
//        WeldJointDef weldJointDef2 = new WeldJointDef();
//        weldJointDef2.initialize(sawCenter, blade2, sawCenter.getWorldCenter());

        world.createJoint(revoluteJointDef);
        world.createJoint(revoluteJointDef2);
        world.createJoint(revoluteJointDef3);
//        world.createJoint(weldJointDef);
//        world.createJoint(weldJointDef2);

		
		
//		polygonSpriteBatch = new PolygonSpriteBatch();
//		
//		polySprites = new ArrayList<>();
//		triangles = new ArrayList<>();
//		bodies = new ArrayList<>();
//		polygonShapes = new ArrayList<>();
		
		isCreated = true;
		
		Texture texture = new Texture(Gdx.files.internal("ground.txt"));
		textureRegion = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
		textureRegion.flip(false, true);
		texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge);
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);	
		
//		for (int i = 0; i < bodyFactory.getBoxBodies().size(); i++) {	
//			
//			Body body = bodyFactory.getBoxBodies().get(i);
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
////			polySprites.add(newSprite);
//		}
	}
	

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void render() {	
		camera.getCamera().update();
		polygonSpriteBatch.setProjectionMatrix(camera.getCombined());
        
//		polygonSpriteBatch.begin();
//		for (int i = 0; i < triangles.size(); i++) {
//			vertices = calculateVertices(polygonShapes.get(i), bodies.get(i));
//			PolygonRegion newRegion = new PolygonRegion(textureRegion, vertices, triangles.get(i));
//			PolygonSprite newSprite = new PolygonSprite(newRegion);
//			newSprite.draw(polygonSpriteBatch);
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
	
	public ArrayList<PolygonSprite> getPolySprites() {
		return polySprites;
	}
	
	public PolygonSpriteBatch getPolygonSpriteBatch() {
		return polygonSpriteBatch;
	}
	
//	public float[] calculateVertices(PolygonShape shape, Body body) {
//		Vector2 mTmp = new Vector2();
//		int vertexCount = shape.getVertexCount();
//		float[] vertices = new float[vertexCount * 2];
//		for (int k = 0; k < vertexCount; k++) {
//			shape.getVertex(k, mTmp);
//			mTmp.rotateDeg(body.getAngle()*MathUtils.radiansToDegrees);
//			mTmp.add(body.getPosition());
//			vertices[k*2] = mTmp.x;
//			vertices[k*2+1] = mTmp.y;
//		}
//		return vertices;
//	}
	
	public void setCameraPosition(Vector3 position) {
		camera.getCamera().position.set(position);
	}
	
}
