package com.mygdx.game.inventory;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;
import com.mygdx.game.item.InventoryItem;

public class InventorySlot extends Stack {
	
	//Custom background in case needed
	private Image slotBackground;
	
	//Stack of actors on slot
	private Stack slotStack;
	
	private int numItems;
	private int filterItemType;
	
	public InventorySlot() {
			
		filterItemType = 0;
		
		slotBackground = new Image();
		slotStack = new Stack();
		
		slotStack.add(slotBackground);
		slotStack.setName("stack");
		
		this.add(slotStack);
		
	}
	
	public InventorySlot(int filterItemType, Image slotBackground) {
		this();
		this.filterItemType = filterItemType;
		this.slotBackground = slotBackground;
		slotStack.add(slotBackground);
	}
	
	
	
	@Override
    public void add(Actor actor) {
        super.add(actor);

        if( !actor.equals(slotStack)) {
            incrementItemCount();
        }
    }

	
	public void add(Array<Actor> array) {
		for (Actor actor : array) {
			super.add(actor);
			if (!actor.equals(slotStack)) {
				incrementItemCount();
			}
		}
	}
	
	public void remove(Actor actor) {
		super.removeActor(actor);
		
		if (!actor.equals(slotStack)) {
			decrementItemCount();
		}
	}
	
	static public void swapSlots(InventorySlot inventorySlotSource, InventorySlot inventorySlotTarget, InventoryItem dragActor) {
		//do not swap
		if (!inventorySlotTarget.doesAcceptItemUseType(dragActor.getItemUseType()) ||
			!inventorySlotSource.doesAcceptItemUseType(inventorySlotTarget.getTopInventoryItem().getItemUseType())) {
			inventorySlotSource.add(dragActor);
			return;
		}
		
		//swap
		Array<Actor> tempArray = inventorySlotSource.getAllInventoryItems();
		tempArray.add(dragActor);
		inventorySlotSource.add(inventorySlotTarget.getAllInventoryItems());
        inventorySlotTarget.add(tempArray);

	}
	
	public void decrementItemCount() {
		numItems--;
		if (slotStack.getChildren().size == 1) {
			slotStack.add(slotBackground);
		}
	}
	
	public void incrementItemCount() {
		numItems++;
		if (slotStack.getChildren().size == 1) {
			slotStack.add(slotBackground);
		}
	}
	
	public int getNumItems(){
        if( hasChildren() ){
            SnapshotArray<Actor> items = this.getChildren();
            return items.size - 1;
        }
        return 0;
    }
	
	public boolean hasItem() {
		if (hasChildren()) {
			SnapshotArray<Actor> items = this.getChildren();
			if (items.size > 1) {
				return true;
			}
		}
		return false;
	}
	
	public boolean doesAcceptItemUseType(int itemUseType) {
		if (filterItemType == 0) {
			return true;
		} else {
			return ((filterItemType & itemUseType) == itemUseType);
		}
	}
	
	public InventoryItem getTopInventoryItem() {
		InventoryItem actor = null;
		if (hasChildren()) {
			SnapshotArray<Actor> items = this.getChildren();
			if (items.size > 1) {
				actor = (InventoryItem) items.peek();
			}
		}
		return actor;
	}
	
	public Array<Actor> getAllInventoryItems() {
		Array<Actor> items = new Array<Actor>();
		if (hasItem()) {
			SnapshotArray<Actor> arrayChildren = this.getChildren();
			int numInventoryItems = arrayChildren.size - 1;
			for (int i = 0; i < numInventoryItems; i++) {
				decrementItemCount();
				items.add(arrayChildren.pop());
			}
		}
		return items;
	}
	
	
	
}
