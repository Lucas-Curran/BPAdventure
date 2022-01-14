package com.mygdx.game;

/**
 * Any textures not credited are either public domain or custom made by the BPAdventure Team. 
 * All textures used are free to use for any purpose including commercially 
 */

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Align;

public class Utilities {
	
	private static TextureAtlas atlas = new TextureAtlas("bpaatlas.txt");
	private static TextureAtlas uiAtlas = new TextureAtlas("uiskin.txt");
	
	public static TextureAtlas itemsAtlas = new TextureAtlas("items.txt");
	public static TextureAtlas textureAtlas = new TextureAtlas("textures.txt");
	
	public static TextureRegion tex = new TextureRegion(textureAtlas.findRegion("IceCharacter"));
	
	public static TextureAtlas lvl89Atlas = new TextureAtlas("lvl8and9.txt");
	public static TextureAtlas rightTextures = new TextureAtlas("moreExtraSprites.txt");
	public static TextureAtlas levelSevenAtlas = new TextureAtlas("moreSprites.txt");
	public static TextureAtlas levelTwoAtlas = new TextureAtlas("atlas_leveltwo.txt");
	public static TextureAtlas oneItemAtlas = new TextureAtlas("mostUselessAtlasOfAllTime.txt");
	
	public static TextureRegion rockMob = Utilities.levelTwoAtlas.findRegion("RockMobEnemy");
	public static TextureRegion spikyRockMob = Utilities.levelTwoAtlas.findRegion("SpikyRockEnemy");
	public static TextureAtlas otherTexturesAtlas = new TextureAtlas("otherTextures.txt");
	public static TextureRegion eyeBoss = otherTexturesAtlas.findRegion("eyeBoss");
	public static TextureRegion flyingEye = otherTexturesAtlas.findRegion("flyingEye");
	public static TextureRegion iceBird = otherTexturesAtlas.findRegion("iceBird");
	public static TextureRegion iceMonster = otherTexturesAtlas.findRegion("iceMonster");
	public static TextureRegion jungleDragon = otherTexturesAtlas.findRegion("jungleDragon");
	public static TextureRegion mummyEnemy = otherTexturesAtlas.findRegion("mummyEnemy");
	public static TextureRegion slimeKing = otherTexturesAtlas.findRegion("slimeKing");
	public static TextureRegion soldierKnight = otherTexturesAtlas.findRegion("soldierKnight");
	
	public static TextureRegion spikySlime = levelSevenAtlas.findRegion("spikySlime");
	public static TextureRegion slimyMob = levelSevenAtlas.findRegion("slimyMob");
	public static TextureRegion boss_8 = lvl89Atlas.findRegion("7_boss");
	public static TextureRegion rocketMob = lvl89Atlas.findRegion("rocketMob");
	public static TextureRegion spiderMob = lvl89Atlas.findRegion("spider");
	public static TextureRegion jumpingMob = lvl89Atlas.findRegion("vertical");
	
	public static TextureRegion boss_9 = lvl89Atlas.findRegion("boss_enemy");
	public static TextureRegion vertical_9 = lvl89Atlas.findRegion("jumpingMob");
	public static TextureRegion patrolMob = lvl89Atlas.findRegion("patrol_mob");
	public static TextureRegion spikeBouncer = lvl89Atlas.findRegion("spikeBouncer");
	public static TextureRegion steeringMob = lvl89Atlas.findRegion("steeringMob");
	
	public static TextureRegion keyCard = oneItemAtlas.findRegion("keyCard");
	
	/**
	 * Attributions:
	 * Ice Bird by Bevouliin licensed CC-BY 4.0: https://opengameart.org/content/flappy-bird-sprite-icy-flying-character
	 * Mummy Enemy by Svetlana Kushnariova licensed CC-BY 4.0: https://opengameart.org/content/mummies 
	 * Slime King by khairul169 licensed CC-BY 3.0: https://opengameart.org/content/slime-character
	 * Soldier Knight by Segel licensed CC-BY 3.0: https://opengameart.org/content/2d-knight-chibi
	 * Rocket Mob by Bevouliin.com licensed CC0: https://opengameart.org/content/missile-enemy-game-character
	 * Spider Mob by Bevouliin.com licensed CC0: https://opengameart.org/content/enemy-game-character-cute-spider
	 * Slime Mob by leeor_net licensed CC0: https://opengameart.org/content/green-tooth-beast
	 * Spiky Bouncer by Bevouliin.com licensed CC0: https://opengameart.org/content/bevouliin-free-ingame-items-spike-monsters
	 * Patrol Mob by BlackSwordo licensed CC-BY 4.0: https://opengameart.org/content/firecreature
	 * Spiky Slime by Bevouliin.com licensed CC0: https://opengameart.org/content/free-game-obstacles-spiky-monster-bevouliin-game-asset
	 * Level 9 Bosses by AntumDeluge licensed CC-BY 4.0: https://opengameart.org/content/minotaur-2
	 * Level 9 Vertical Enemy by Sean Noonan licensed CC0: https://opengameart.org/content/top-down-tentacle-creature
	 * Level 8 Boss by Redshrike licensed CC-BY 3.0: https://opengameart.org/content/3-form-rpg-boss-harlequin-epicycle
	 * Level 9 Alternate Vertical enemy by Blackswordo licensed CC-BY 4.0: https://opengameart.org/content/monstah
	 * Steering Mob by GoblinGameWerx licensed CC0: https://opengameart.org/content/water-dwelling-race-head
	 * Key Card by benlamouche license CC-BY 4.0: https://opengameart.org/content/key-cards
	 */
	
	public static Texture giftBlock = new Texture(Gdx.files.internal("purpleBlock.png"));
	
	public static Skin UISKIN = new Skin(atlas);
	public static Skin ACTUAL_UI_SKIN = new Skin(Gdx.files.internal("uiskin.json"));
	
	private static TextButtonStyle textButtonStyle;
	private static SliderStyle sliderStyle;
	private static Skin buttonSkin, sliderSkin;
	private static TextureAtlas textureAtlasTest, textureAtlasUI;
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
		button.getLabelCell().padLeft(25);
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
	
	public static SliderStyle sliderStyles() {
		sliderSkin = new Skin(Gdx.files.internal("uiskin.json"));
		textureAtlasUI = new TextureAtlas("uiskin.txt");
		sliderSkin.addRegions(textureAtlasUI);
		sliderStyle = new SliderStyle();
		sliderStyle.knob = sliderSkin.getDrawable("default-slider-knob");
		sliderStyle.background = sliderSkin.getDrawable("default-slider");
		return sliderStyle;
	}
	
	public static Object[] addPolygonTexture(Texture texture, Body body) {

		Fixture fixture = body.getFixtureList().get(0);

		PolygonShape shape = (PolygonShape) fixture.getShape();
			
		float[] vertices = calculateVertices(shape, body);		
		short triangles[] = new EarClippingTriangulator().computeTriangles(vertices).toArray();
	
		texture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		
		TextureRegion textureRegion = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());

		Object[] values = new Object[4];
		
		values[0] = body;
		values[1] = shape;
		values[2] = triangles;
		values[3] = textureRegion;
		
		return values;
	}
	
	public static void renderAllTextures(Camera camera, PolygonSpriteBatch polygonSpriteBatch, ArrayList<Object[]> bodies) {
		camera.getCamera().update();
		polygonSpriteBatch.setProjectionMatrix(camera.getCombined());
		polygonSpriteBatch.begin();
		for (int i = 0; i < bodies.size(); i++) {
			float[] vertices = calculateVertices((PolygonShape) bodies.get(i)[1], (Body) bodies.get(i)[0]);
			PolygonRegion newRegion = new PolygonRegion((TextureRegion) bodies.get(i)[3], vertices, (short[]) bodies.get(i)[2]);
			PolygonSprite newSprite = new PolygonSprite(newRegion);
			newSprite.draw(polygonSpriteBatch);
			//polygonSpriteBatch.draw(newRegion, 0, 0, 0, 0, 1, 1, 1/32f, 1/32f, 0);
		}
		polygonSpriteBatch.end();
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
			//vertices[k*2] = mTmp.x * 32;
			//vertices[k*2+1] = mTmp.y * 32;
		}		

		return vertices;
	}
	
	
	
	
	public static float vectorToAngle(Vector2 vector) {
		return (float)Math.atan2(-vector.x, vector.y);
	}
	
	public static Vector2 angleToVector (Vector2 outVector, float angle) {
		outVector.x = -(float)Math.sin(angle);
		outVector.y = (float)Math.cos(angle);
		return outVector;
	}
	
	public static Vector2 aimTo(Vector2 shooter, Vector2 target) {
		Vector2 aim = new Vector2();
		float velx = target.x - shooter.x; // get distance from shooter to target on x plain
		float vely = target.y - shooter.y; // get distance from shooter to target on y plain
		float length = (float) Math.sqrt(velx * velx + vely * vely); // get distance to target direct
		if (length != 0) {
			aim.x = velx / length;  // get required x velocity to aim at target
			aim.y = vely / length;  // get required y velocity to aim at target
		}
		return aim;
	}
}
	
