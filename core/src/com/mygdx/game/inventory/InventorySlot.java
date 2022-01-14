package com.mygdx.game.inventory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;
import com.mygdx.game.entities.EntityHandler;
import com.mygdx.game.item.InventoryItem;

public class InventorySlot extends Stack {
	
	static Logger logger = LogManager.getLogger(InventorySlot.class.getName());
	
	//Custom background in case needed
	private Image slotBackground;
	
	//Stack of actors on slot
	private Stack slotStack;
	
	private int numItems;
	private int filterItemType;
	
	private boolean forEquippables;
	
	/**
	 * Inventory slot that holds the item with default settings
	 */
	public InventorySlot() {
			
		filterItemType = 0;
		
		slotBackground = new Image();
		slotStack = new Stack();
		
		slotStack.add(slotBackground);
		slotStack.setName("stack");
		
		this.add(slotStack);
	}
	
	/**
	 * Inventory slot that filters certain items and may have a unique background
	 * @param filterItemType - items to filter
	 * @param slotBackground - background of slot
	 * @param forEquippables - is the slot used for equippable items
	 */
	public InventorySlot(int filterItemType, Image slotBackground, boolean forEquippables) {
		this();
		this.filterItemType = filterItemType;
		this.slotBackground = slotBackground;
		this.forEquippables = forEquippables;
		slotStack.add(slotBackground);
	}	
	
	@Override
    public void add(Actor actor) {
        super.add(actor);

        if( !actor.equals(slotStack)) {
            incrementItemCount();
        }
    }

	/**
	 * Add all the actors in the array to the inventory slot stack
	 * @param array - array of actors to add
	 */
	public void add(Array<Actor> array) {
		for (Actor actor : array) {
			super.add(actor);
			if (!actor.equals(slotStack)) {
				incrementItemCount();
			}
		}
	}
	
	/**
	 * Removes all the actors in the array from the inventory slot stack
	 * @param array - array of actors to remove
	 */
	public void remove(Actor actor) {
		super.removeActor(actor);
		
		if (!actor.equals(slotStack)) {
			decrementItemCount();
		}
	}
	
	/**
	 * If the inventory slots both accept eachother's item types, swaps them from their inventory slots
	 * @param inventorySlotSource - the slot that was dragged from first
	 * @param inventorySlotTarget - the slot that is dragged to
	 * @param dragActor - the actor that is being dragged
	 */
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
        logger.info("Items in inventory swapped.");
	}
	
	/**
	 * Decreases inventory slots number of "items" in the stack
	 */
	public void decrementItemCount() {
		numItems--;
		if (slotStack.getChildren().size == 1) {
			slotStack.add(slotBackground);
		}
	}
	
	/**
	 * Increases inventory slots number of "items" in the stack
	 */
	public void incrementItemCount() {
		numItems++;
		if (slotStack.getChildren().size == 1) {
			slotStack.add(slotBackground);
		}
	}
	
	/**
	 * Gets the number of items in the slot
	 * @return items size
	 */
	public int getNumItems(){
        if( hasChildren() ){
            SnapshotArray<Actor> items = this.getChildren();
            return items.size - 1;
        }
        return 0;
    }
	
	/**
	 * Gets whether the inventory slot has an item in it
	 * @return boolean of whether an item is present
	 */
	public boolean hasItem() {
		if (hasChildren()) {
			SnapshotArray<Actor> items = this.getChildren();
			if (items.size > 1) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if the slot accepts the item use type
	 * @param itemUseType - item use type of the item
	 * @return if item accepts type
	 */
	public boolean doesAcceptItemUseType(int itemUseType) {
		if (filterItemType == 0) {
			return true;
		} else {
			//bitwise function to decide whether item use type + filter == item use type
			return ((filterItemType & itemUseType) == itemUseType);
		}
	}
	
	/**
	 * Gets the inventory item from the slot
	 * @return inventory item
	 */
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
	
	/**
	 * Gets all the items in the slot
	 * @return array of actors in slot
	 */
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
	
	public boolean isForEquippables() {
		return forEquippables;
	}	
	
}
