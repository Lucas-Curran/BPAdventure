package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.systems.RenderingSystem;

public class Utilities {
	
	private static TextureAtlas atlas = new TextureAtlas("bpaatlas.txt");
	private static TextureAtlas uiAtlas = new TextureAtlas("uiskin.txt");
	
	public static Skin UISKIN = new Skin(atlas);
	public static Skin ACTUAL_UI_SKIN = new Skin(Gdx.files.internal("uiskin.json"));
	
	private static TextButtonStyle textButtonStyle;
	private static Skin buttonSkin;
	private static TextureAtlas textureAtlasTest;
	private static BitmapFont font;

	public static TextureRegion[] spriteSheetToFrames(TextureRegion region, int FRAME_COLS, int FRAME_ROWS){
		// split texture region
		TextureRegion[][] tmp = region.split(region.getRegionWidth() / FRAME_COLS,
				region.getRegionHeight() / FRAME_ROWS);

		// compact 2d array to 1d array
		TextureRegion[] frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				frames[index++] = tmp[i][j];
			}
		}

		return frames;
	}
	
	public static TextButton buttonSettings(TextButton button) {
		button.getLabel().setAlignment(Align.left);
		button.getLabelCell().padLeft(35);
		button.getLabel().setFontScale(2,2);
		return button;
	}
	
	public static TextButtonStyle buttonStyles(String upStyle, String overStyle) {
		font = new BitmapFont();
		buttonSkin = new Skin(Gdx.files.internal("uiskin.json"));
		textureAtlasTest = new TextureAtlas("test.txt");
		buttonSkin.addRegions(textureAtlasTest);
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = font;
		textButtonStyle.fontColor = Color.WHITE;
		textButtonStyle.overFontColor = Color.YELLOW;
		textButtonStyle.up = buttonSkin.getDrawable(upStyle);
		textButtonStyle.over = buttonSkin.getDrawable(overStyle);
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -1;
		return textButtonStyle;
	}
	
	public static void renderPolygonTextures(Texture texture) {
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
	
	public static float[] calculateVertices(PolygonShape shape, Body body) {
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
	
	
	
	
}
	
