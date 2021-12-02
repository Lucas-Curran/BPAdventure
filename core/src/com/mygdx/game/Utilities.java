package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
	
	public Utilities() {
		
	}

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
	
	
//	public void addToDragAndDrop(final DragAndDrop dragAndDrop, final Image image, final Table sourceTable, final Table targetTable, final boolean isSource, final boolean isTarget) {
//		
//		if (isSource) {
//			dragAndDrop.addSource(new Source(image) {
//
//			float payloadX;
//			float payloadY;
//
//			@Override
//			public Payload dragStart(InputEvent event, float x, float y, int pointer) {				
//				Payload payload = new Payload();
//				payload.setDragActor(getActor());
//				dragAndDrop.setDragActorPosition(image.getWidth()/2, -image.getHeight()/2);
//				payloadX = payload.getDragActor().getX(Align.bottomLeft);
//				payloadY = payload.getDragActor().getY(Align.bottomLeft);
//				return payload;
//			}
//
//			@Override
//			public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, Target target) {
//				if (target != null) {
//					payload.getDragActor().setPosition(target.getActor().getX(Align.bottomLeft), target.getActor().getY(Align.bottomLeft));
//					target.getActor().setPosition(payloadX, payloadY);
//					
//					Actor targetActor = target.getActor();
//					Actor payloadActor = payload.getDragActor();	
//					
//					System.out.println("Target: " + targetTable);
//					System.out.println("Source: " + sourceTable);
//					
//					Cell<Actor> payloadCell = sourceTable.getCell(payloadActor);
//					Cell<Actor> targetCell = targetTable.getCell(targetActor);
//
//					payloadCell.clearActor();
//					targetCell.clearActor();
//					
//					payloadCell.setActor(targetActor);
//					targetCell.setActor(payloadActor);
//					
//				} else {
//					payload.getDragActor().setPosition(payloadX, payloadY);
//				}
//				super.dragStop(event, x, y, pointer, payload, target);
//			}
//			});
//		}
//
//		if (isTarget) {
//			dragAndDrop.addTarget(new Target(image) {
//				
//				@Override
//				public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
//					getActor().setColor(Color.GREEN);
//					return true;
//				}
//
//				@Override
//				public void drop(Source source, Payload payload, float x, float y, int pointer) {
//					System.out.println("On target");
//				}
//
//				@Override
//				public void reset(Source source, Payload payload) {
//					getActor().setColor(Color.WHITE);
//					super.reset(source, payload);
//				}
//
//			});
//		}
//	}
}
	
