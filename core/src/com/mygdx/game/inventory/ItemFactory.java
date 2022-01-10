package com.mygdx.game.inventory;

import java.util.ArrayList;
import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Scaling;
import com.mygdx.game.item.InventoryItem;
import com.mygdx.game.item.InventoryItem.ItemTypeID;

public class ItemFactory {
	
	private Json _json = new Json();
    private final String INVENTORY_ITEM = "inventory_items.json";
    private static ItemFactory instance = null;
    private Hashtable<ItemTypeID,InventoryItem> inventoryItemList;

    public static ItemFactory getInstance() {
        if (instance == null) {
            instance = new ItemFactory();
        }

        return instance;
    }

    private ItemFactory(){
        ArrayList<JsonValue> list = _json.fromJson(ArrayList.class, Gdx.files.internal(INVENTORY_ITEM));
        inventoryItemList = new Hashtable<ItemTypeID, InventoryItem>();

        for (JsonValue jsonVal : list) {
            InventoryItem inventoryItem = _json.readValue(InventoryItem.class, jsonVal);
            inventoryItemList.put(inventoryItem.getItemTypeID(), inventoryItem);
        }
    }

    public InventoryItem getInventoryItem(ItemTypeID inventoryItemType){
        InventoryItem item = new InventoryItem(inventoryItemList.get(inventoryItemType));
        item.setDrawable(new TextureRegionDrawable(new TextureAtlas("bpaatlas.txt").findRegion(item.getItemTypeID().toString())));
        item.setScaling(Scaling.none);
        return item;
    }
    
}
