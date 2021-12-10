package com.mygdx.game.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.utils.Align;

public class InventorySlotSource extends Source {

	private DragAndDrop dragAndDrop;
	private InventorySlot sourceSlot;
	
	public InventorySlotSource(InventorySlot sourceSlot, DragAndDrop dragAndDrop) {
		super(sourceSlot.getTopInventoryItem());
		this.sourceSlot = sourceSlot;
		this.dragAndDrop = dragAndDrop;
	}

	@Override
	public Payload dragStart(InputEvent event, float x, float y, int pointer) {
		Payload payload = new Payload();
		
		Actor actor = getActor();
		if (actor == null) {
			return null;
		}
		
		InventorySlot source = (InventorySlot)actor.getParent();
		if (source == null) {
			return null;
		} else {
			sourceSlot = source;
		};
		
		sourceSlot.decrementItemCount();

		payload.setDragActor(getActor());
		dragAndDrop.setDragActorPosition(getActor().getWidth()/2 - event.getStageX(), -getActor().getHeight()/2 - event.getStageY());
		
		return payload;
	}
	
	@Override
	public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, Target target) {
		super.dragStop(event, x, y, pointer, payload, target);
		if (target == null) {
			sourceSlot.remove(getActor());
			sourceSlot.add(getActor());
		} 
	}

	@Override
	public void drag(InputEvent event, float x, float y, int pointer) {
		super.drag(event, x, y, pointer);
	}

	public InventorySlot getSourceSlot() {
		return sourceSlot;
	}
	
}
