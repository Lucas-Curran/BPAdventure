package com.mygdx.game.inventory;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;

public class InventorySlotSource extends Source {

	private DragAndDrop dragAndDrop;
	private InventorySlot sourceSlot;
	
	/**
	 * Dragging source attached to inventory slot
	 * @param sourceSlot - inventory slot to attach drag function to
	 * @param dragAndDrop - instance of drag and drop
	 */
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
		
		//decrements item from source slot when dragged
		sourceSlot.decrementItemCount();

		payload.setDragActor(getActor());
		//Drag actor position is the position of the actor when being dragged, this function is meant to subtract the offsets from the actors parents
		//this is a workaround from some of scene2d's actor parents functions in positioning them 
		dragAndDrop.setDragActorPosition(getActor().getWidth()/2 
				- getActor().getParent().getParent().getX() - 
				getActor().getParent().getX(), 
				-getActor().getHeight()/2 - 
				getActor().getParent().getParent().getY() - 
				getActor().getParent().getY());
		
		//sets the drag actor to the front of the z-index so it is not blocked by slots or windows
		payload.getDragActor().getParent().getParent().toFront();
		
		return payload;
	}
	
	@Override
	public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, Target target) {
		super.dragStop(event, x, y, pointer, payload, target);
		if (target == null) {
			//if drag is stopped and the target is null, remove the actor from the slot and re-add it 
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
