package com.mygdx.game.inventory;

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.mygdx.game.Map;
import com.mygdx.game.item.InventoryItem;

public class InventorySlotTarget extends Target {
	
	InventorySlot targetSlot;
	
	public InventorySlotTarget(InventorySlot actor) {
		super(actor);
		targetSlot = actor;
	}

	@Override
	public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
		return true;
	}

	@Override
	public void drop(Source source, Payload payload, float x, float y, int pointer) {
		InventoryItem sourceActor = (InventoryItem) payload.getDragActor();
		InventoryItem targetActor = targetSlot.getTopInventoryItem();
		InventorySlot sourceSlot = ((InventorySlotSource) source).getSourceSlot();
		
		if (sourceActor == null) {
			return;
		}
		
		if (!targetSlot.doesAcceptItemUseType(sourceActor.getItemUseType())) {
			sourceSlot.remove(sourceActor);
			sourceSlot.add(sourceActor);
			return;
		}
		
		if (sourceSlot == targetSlot) {
			sourceSlot.remove(sourceActor);
			sourceSlot.add(sourceActor);
			return;
		}
		
		if (!targetSlot.hasItem()) {
			targetSlot.add(sourceActor);
			if (targetSlot.isForEquippables()) {
				if (sourceActor.isInventoryItemDefensive()) {
					Map.getInstance().getEntityHandler().getPlayer().setDefense(Map.getInstance().getEntityHandler().getPlayer().getDefense() + sourceActor.getDefense());
				} else if (sourceActor.isInventoryItemWeapon()) {
					Map.getInstance().getEntityHandler().getPlayer().setDamage(sourceActor.getDamage());
				}
			}
			
			if (sourceSlot.isForEquippables()) {
				if (sourceActor.isInventoryItemDefensive()) {
					Map.getInstance().getEntityHandler().getPlayer().setDefense(Map.getInstance().getEntityHandler().getPlayer().getDefense() - sourceActor.getDefense());
				} else if (sourceActor.isInventoryItemWeapon()) {
					Map.getInstance().getEntityHandler().getPlayer().setDamage(1);
				}
			}				
		} else {
			if (sourceActor.isSameItemType(targetActor) && sourceActor.isStackable()) {
				targetSlot.add(sourceActor);
			} else {
				InventorySlot.swapSlots(sourceSlot, targetSlot, sourceActor);
			}
		}
		
	}

	@Override
	public void reset(Source source, Payload payload) {
		// TODO Auto-generated method stub
		super.reset(source, payload);
	}
	
	

}
