package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.systems.RenderingSystem;

public class Utilities {
	
	private static TextureAtlas atlas = new TextureAtlas("bpaatlas.txt");
	public static Skin UISKIN = new Skin(atlas);

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
	
