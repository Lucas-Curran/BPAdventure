package com.mygdx.game.entities;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.Utilities;
import com.mygdx.game.components.B2dBodyComponent;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.NPCComponent;
import com.mygdx.game.components.TextureComponent;
import com.mygdx.game.components.TransformComponent;
import com.mygdx.game.components.TypeComponent;
import com.mygdx.game.item.InventoryItem;
import com.mygdx.game.item.ShopItem;
import com.mygdx.game.item.InventoryItem.ItemAttribute;
import com.mygdx.game.item.InventoryItem.ItemTypeID;
import com.mygdx.game.item.InventoryItem.ItemUseType;

public class NPC extends EntityHandler {
	
	static Logger logger = LogManager.getLogger(NPC.class.getName());
	
	/**
	 * Spawns an NPC
	 * @param text - text that the NPC will write in the textbox
	 * @param posx - x position of NPC
	 * @param posy - y position of NPC
	 * @param npcTexture - texture of NPC
	 * @param hasOptions - decides whether the NPC will give options at end of dialog
	 * @return NPC entity
	 */
	public Entity spawnNPC(String[] text, int posx, int posy, TextureRegion npcTexture, boolean hasOptions) {
		
				Entity entity = pooledEngine.createEntity();
				B2dBodyComponent b2dbody = pooledEngine.createComponent(B2dBodyComponent.class);
				TransformComponent position = pooledEngine.createComponent(TransformComponent.class);
				TextureComponent texture = pooledEngine.createComponent(TextureComponent.class);
				CollisionComponent colComp = pooledEngine.createComponent(CollisionComponent.class);
				NPCComponent npcComp = pooledEngine.createComponent(NPCComponent.class);
				TypeComponent type = pooledEngine.createComponent(TypeComponent.class);

				// create the data for the components and add them to the components
				b2dbody.body = bodyFactory.makeCirclePolyBody(posx, posy, 1, BodyFactory.OTHER, BodyType.KinematicBody,true, true);
				// set object position (x,y,z) z used to define draw order 0 first drawn
				position.position.set(b2dbody.body.getPosition().x, b2dbody.body.getPosition().y, 0);
				texture.region = npcTexture;
				type.type = TypeComponent.NPC;
				npcComp.text = text;
				npcComp.wares = getShopWares();
				npcComp.hasOptions = hasOptions;
				
				b2dbody.body.setUserData(entity);
				
				// add the components to the entity
				entity.add(type);
				entity.add(colComp);
				entity.add(b2dbody);
				entity.add(position);
				entity.add(texture);
				entity.add(npcComp);
				
				logger.info("NPC created.");
				
				return entity;
	}
	
	/**
	 * Gets the hash map of shops items
	 * @return HashMap of shop items given labels and shop items
	 */
	public HashMap<Label, ShopItem> getShopWares() {
		
		HashMap<Label, ShopItem> shopItems = new HashMap<Label, ShopItem>();
		
		ShopItem helmet01 = new ShopItem(new InventoryItem(Utilities.itemsAtlas.findRegion("helmet01"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_HELMET.getValue(), ItemTypeID.HELMET01), 3);
		ShopItem helmet02 = new ShopItem(new InventoryItem(Utilities.itemsAtlas.findRegion("helmet02"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_HELMET.getValue(), ItemTypeID.HELMET02), 6);
		ShopItem helmet03 = new ShopItem(new InventoryItem(Utilities.itemsAtlas.findRegion("helmet03"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_HELMET.getValue(), ItemTypeID.HELMET03), 9);
		ShopItem armorchest01 = new ShopItem(new InventoryItem(Utilities.itemsAtlas.findRegion("armorchest01"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_CHEST.getValue(), ItemTypeID.BODYARMOR01), 3);
		ShopItem armorchest02 = new ShopItem(new InventoryItem(Utilities.itemsAtlas.findRegion("chestarmor02"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_CHEST.getValue(), ItemTypeID.BODYARMOR02), 6);
		ShopItem armorchest03 = new ShopItem(new InventoryItem(Utilities.itemsAtlas.findRegion("armorchest03"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_CHEST.getValue(), ItemTypeID.BODYARMOR03), 9);
		ShopItem legs01 = new ShopItem(new InventoryItem(Utilities.itemsAtlas.findRegion("legs01"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_LEGS.getValue(), ItemTypeID.LEGS01), 3);
		ShopItem legs02 = new ShopItem(new InventoryItem(Utilities.itemsAtlas.findRegion("legs02"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_LEGS.getValue(), ItemTypeID.LEGS02), 6);
		ShopItem legs03 = new ShopItem(new InventoryItem(Utilities.itemsAtlas.findRegion("legs03"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_LEGS.getValue(), ItemTypeID.LEGS03), 9);
		ShopItem boots01 =  new ShopItem(new InventoryItem(Utilities.itemsAtlas.findRegion("boots01"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_FEET.getValue(), ItemTypeID.BOOTS01), 3);
		ShopItem boots02 =  new ShopItem(new InventoryItem(Utilities.itemsAtlas.findRegion("boots02"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_FEET.getValue(), ItemTypeID.BOOTS02), 6);
		ShopItem boots03 =  new ShopItem(new InventoryItem(Utilities.itemsAtlas.findRegion("boots03"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_FEET.getValue(), ItemTypeID.BOOTS03), 9);
		ShopItem shield01 =  new ShopItem(new InventoryItem(Utilities.itemsAtlas.findRegion("shield01"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_SHIELD.getValue(), ItemTypeID.SHIELD01), 3);
		ShopItem shield02 =  new ShopItem(new InventoryItem(Utilities.itemsAtlas.findRegion("shield02"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_SHIELD.getValue(), ItemTypeID.SHIELD02), 6);
		ShopItem shield03 =  new ShopItem(new InventoryItem(Utilities.itemsAtlas.findRegion("shield03"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_SHIELD.getValue(), ItemTypeID.SHIELD03), 9);
		ShopItem weapon01 =  new ShopItem(new InventoryItem(Utilities.itemsAtlas.findRegion("sword01"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.WEAPON_ONEHAND.getValue(), ItemTypeID.WEAPON01), 3);
		ShopItem weapon02 =  new ShopItem(new InventoryItem(Utilities.itemsAtlas.findRegion("sword02"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.WEAPON_ONEHAND.getValue(), ItemTypeID.WEAPON02), 6);		
		ShopItem weapon03 =  new ShopItem(new InventoryItem(Utilities.itemsAtlas.findRegion("sword03"), ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.WEAPON_ONEHAND.getValue(), ItemTypeID.WEAPON03), 9);
		ShopItem apple = new ShopItem(new InventoryItem(Utilities.foodAtlas.findRegion("apple"), ItemAttribute.CONSUMABLE.getValue(), ItemUseType.ITEM_RESTORE_HEALTH.getValue(), ItemTypeID.APPLE), 1);
		ShopItem donut = new ShopItem(new InventoryItem(Utilities.foodAtlas.findRegion("donut"), ItemAttribute.CONSUMABLE.getValue(), ItemUseType.ITEM_RESTORE_HEALTH.getValue(), ItemTypeID.DONUT), 2);
		ShopItem cake = new ShopItem(new InventoryItem(Utilities.foodAtlas.findRegion("cake"), ItemAttribute.CONSUMABLE.getValue(), ItemUseType.ITEM_RESTORE_HEALTH.getValue(), ItemTypeID.CAKE), 3);
		ShopItem burger = new ShopItem(new InventoryItem(Utilities.foodAtlas.findRegion("burger"), ItemAttribute.CONSUMABLE.getValue(), ItemUseType.ITEM_RESTORE_HEALTH.getValue(), ItemTypeID.BURGER), 4);
		
		Label label1 = new Label("Beginner Helmet", Utilities.ACTUAL_UI_SKIN);
		Label label2 = new Label("Helmet of the Forgotten Adventurer", Utilities.ACTUAL_UI_SKIN);
		Label label3 = new Label("Orc Warlord's Great Helm", Utilities.ACTUAL_UI_SKIN);
		Label label4 = new Label("Beginner Chestplate", Utilities.ACTUAL_UI_SKIN);	
		Label label5 = new Label("The Green Man's Chestplate", Utilities.ACTUAL_UI_SKIN);
		Label label6 = new Label("Shining Chestplate", Utilities.ACTUAL_UI_SKIN);
		Label label7 = new Label("Beginner Legs", Utilities.ACTUAL_UI_SKIN);
		Label label8 = new Label("Sailer's Legs", Utilities.ACTUAL_UI_SKIN);
		Label label9 = new Label("Carnivorous Legs", Utilities.ACTUAL_UI_SKIN);
		Label label10 = new Label("Beginner Boots", Utilities.ACTUAL_UI_SKIN);
		Label label11 = new Label("Runner's Boots", Utilities.ACTUAL_UI_SKIN);
		Label label12 = new Label("Herme's Great Boots", Utilities.ACTUAL_UI_SKIN);
		Label label13 = new Label("Beginner Shield", Utilities.ACTUAL_UI_SKIN);
		Label label14 = new Label("Iron Shield", Utilities.ACTUAL_UI_SKIN);
		Label label15 = new Label("Unpenetrable Shield", Utilities.ACTUAL_UI_SKIN);
		Label label16 = new Label("Beginner Sword", Utilities.ACTUAL_UI_SKIN);
		Label label17 = new Label("Great Sword", Utilities.ACTUAL_UI_SKIN);
		Label label18 = new Label("Legend's Sword", Utilities.ACTUAL_UI_SKIN);
		Label label19 = new Label("Apple", Utilities.ACTUAL_UI_SKIN);
		Label label20 = new Label("Donut", Utilities.ACTUAL_UI_SKIN);
		Label label21 = new Label("Cake", Utilities.ACTUAL_UI_SKIN);
		Label label22 = new Label("Burger", Utilities.ACTUAL_UI_SKIN);
		
		shopItems.put(label1, helmet01);
		shopItems.put(label2, helmet02);
		shopItems.put(label3, helmet03);
		shopItems.put(label4, armorchest01);
		shopItems.put(label5, armorchest02);
		shopItems.put(label6, armorchest03);
		shopItems.put(label7, legs01);
		shopItems.put(label8, legs02);
		shopItems.put(label9, legs03);
		shopItems.put(label10, boots01);
		shopItems.put(label11, boots02);
		shopItems.put(label12, boots03);
		shopItems.put(label13, shield01);
		shopItems.put(label14, shield02);
		shopItems.put(label15, shield03);	
		shopItems.put(label16, weapon01);	
		shopItems.put(label17, weapon02);	
		shopItems.put(label18, weapon03);	
		shopItems.put(label19, apple);
		shopItems.put(label20, donut);
		shopItems.put(label21, cake);
		shopItems.put(label22, burger);
		
		logger.info("Shop items created.");
		
		return shopItems;
	}
	
}
