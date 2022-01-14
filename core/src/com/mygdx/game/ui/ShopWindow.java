package com.mygdx.game.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.CrashWriter;
import com.mygdx.game.Utilities;
import com.mygdx.game.entities.EntityHandler;
import com.mygdx.game.item.InventoryItem;
import com.mygdx.game.item.ShopItem;


public class ShopWindow extends Window implements InputProcessor  {
	
	static Logger logger = LogManager.getLogger(ShopWindow.class.getName());
	
	private Table buyTable;
	private Table sellTable;
	private Table shopTable;
	private Table infoTable;
	private Stage stage;
	private HashMap<Label, ShopItem> buyList;
	private HashMap<Label, ShopItem> sellList;
	private Texture borderTexture;
	private Image borderImage;
	private static SpriteDrawable windowBg = new SpriteDrawable(new Sprite(createTexture(Gdx.files.internal("menu_bg.png"))));
	private Table container;
	private ScrollPane scroll;
	private TextButton confirmButton, leaveButton;
	private ShopItem selectedBuyItem;
	private ShopItem selectedSellItem;
	private Group itemToRemove;
	private ArrayList<Label> labels;
	private Label infoLabel;

	/**
	 * Instaniates shop window with given buy list and sell list
	 * @param buyList - items to buy
	 * @param sellList - items to sell, gotten from inventory
	 * @param money
	 */
	public ShopWindow(HashMap<Label, ShopItem> buyList, HashMap<Label, ShopItem> sellList, Money money) {
		super("Shop", new WindowStyle(new BitmapFont(), Color.RED, windowBg));
		
		this.buyList = buyList;
		this.sellList = sellList;
		try {
			
			// sellList = current inventory items

			labels = new ArrayList<Label>();

			container = new Table();

			borderTexture = new Texture(Gdx.files.internal("border.png"));
			borderTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
			borderImage = new Image(borderTexture);

			getTitleTable().padBottom(15);

			stage = new Stage();	
			//stage.setDebugAll(true);

			buyTable = new Table(new Skin(Gdx.files.internal("uiskin.json")));

			//Buy list is looped through and added to the buy table
			for (Map.Entry<Label, ShopItem> set : buyList.entrySet()) {	
				set.getKey().setWrap(true);
				final Group tempGroup = new Group();

				labels.add(set.getKey());

				set.getKey().setWidth(150);
				set.getKey().setHeight(32);

				set.getValue().setWidth(32);
				set.getValue().setHeight(32);

				set.getValue().setBounds(set.getValue().getX() + set.getKey().getWidth(), set.getValue().getY(), 32, 32);

				tempGroup.addActor(set.getKey());
				tempGroup.addActor(set.getValue());

				buyTable.add(tempGroup).width(182).height(32).padTop(10);

				tempGroup.addListener(new ClickListener() {

					@Override
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
						
						for (int i = 0; i < labels.size(); i++) {
							//reset all labels back to white color
							labels.get(i).setColor(Color.WHITE);
						}

						selectedBuyItem = null;
						selectedSellItem = null;
						selectedBuyItem = (ShopItem) tempGroup.getChild(1);
						Label tempLabel = (Label) tempGroup.getChild(0);
						selectedBuyItem.setName(tempLabel.getText().toString());
						itemToRemove = tempGroup;
						tempLabel.setColor(Color.YELLOW);
						//set touched label to yellow color and set selected buy item
						return true;
					}			

				});
				buyTable.row();
			}

			sellTable = new Table(new Skin(Gdx.files.internal("uiskin.json")));

			//Loop through sell list to add items to sell table
			for (Map.Entry<Label, ShopItem> set : sellList.entrySet()) {
				set.getKey().setWrap(true);
				final Group tempGroup = new Group();

				labels.add(set.getKey());

				set.getKey().setWidth(150);
				set.getKey().setHeight(32);

				set.getValue().setWidth(32);
				set.getValue().setHeight(32);

				set.getValue().setBounds(set.getValue().getX() + set.getKey().getWidth(), set.getValue().getY(), 32, 32);

				tempGroup.addActor(set.getKey());
				tempGroup.addActor(set.getValue());

				sellTable.add(tempGroup).width(182).height(32).padTop(10);

				tempGroup.addListener(new ClickListener() {

					@Override
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

						for (int i = 0; i < labels.size(); i++) {
							labels.get(i).setColor(Color.WHITE);
						}

						selectedBuyItem = null;
						selectedSellItem = null;
						selectedSellItem = (ShopItem) tempGroup.getChild(1);
						Label tempLabel = (Label) tempGroup.getChild(0);
						itemToRemove = tempGroup;
						tempGroup.addActor(borderImage);
						tempLabel.setColor(Color.YELLOW);
						//Same stuff as buy loop
						return true;
					}			

				});
				sellTable.row();
			}


			confirmButton = new TextButton("Confirm", Utilities.buttonStyles("default-rect", "default-rect-down"));
			leaveButton = new TextButton("Leave", Utilities.buttonStyles("default-rect", "default-rect-down"));

			infoTable = new Table();

			infoTable.add(leaveButton).width(91);
			infoTable.add(confirmButton).width(91);


			Label buyLabel = new Label("Buy", Utilities.ACTUAL_UI_SKIN);
			Label sellLabel = new Label("Sell", Utilities.ACTUAL_UI_SKIN);
			infoLabel = new Label("", Utilities.ACTUAL_UI_SKIN);
			infoLabel.setScale(0.7f);

			buyLabel.setAlignment(Align.center, Align.center);
			sellLabel.setAlignment(Align.center, Align.center);

			shopTable = new Table();
			shopTable.add(buyLabel).width(182);
			shopTable.add(sellLabel).width(182);
			shopTable.row();
			shopTable.add(buyTable);
			shopTable.add(sellTable);
			shopTable.row();
			shopTable.add(infoLabel).padTop(20);
			shopTable.add(infoTable).padTop(20);
			shopTable.pad(30);

			ScrollPaneStyle paneStyle = new ScrollPaneStyle();
			paneStyle.background = Utilities.ACTUAL_UI_SKIN.getDrawable("default-pane");
			paneStyle.vScrollKnob = Utilities.ACTUAL_UI_SKIN.getDrawable("default-scroll");
			
			scroll = new ScrollPane(shopTable, paneStyle);

			container.add(scroll).width(600).height(400);
			
			//container adds scroll which contains shop table and is then added to the stage

			container.padLeft(650);
			container.padBottom(500);

			stage.addActor(container);
			stage.addActor(this);

			pack();

			toBack();
			setResizable(true);
			setVisible(false);
			setShopVisible(false);

			stage.setScrollFocus(scroll);

			logger.info("Shop Window instanced.");
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			try {
				CrashWriter cw = new CrashWriter(e);
				cw.writeCrash();
			} catch (IOException e1) {
				logger.error(e1.getMessage());
			}
		}
	}
	
	public void setShopVisible(boolean isVisible) {
		container.setVisible(isVisible);	
	}
	
	public boolean isShopVisible() {
		return container.isVisible();
	}
	
	public void render(float delta) {
		
		//Leave button clicked, set all the buttons and labels back to default state, set visibility to false and play overworld music
		if (leaveButton.isPressed()) {
			setShopVisible(false);
			Group tempParent = confirmButton.getParent();
			Group tempParent2 = leaveButton.getParent();
			confirmButton.remove();
			leaveButton.remove();
			tempParent.addActor(confirmButton);
			tempParent2.addActor(leaveButton);
			
			for (int i = 0; i < labels.size(); i++) {
				labels.get(i).setColor(Color.WHITE);
			}	
			
			infoLabel.setText("");
			com.mygdx.game.Map.getInstance().getAudioManager().playButton();
			com.mygdx.game.Map.getInstance().getAudioManager().stopAll();
			com.mygdx.game.Map.getInstance().getAudioManager().playOverworld();
			
			logger.info("Shop window closed.");		
		}
		
		//Confirm button pressed, check whether item is being sold or bought
		if (confirmButton.isPressed()) {
			
			com.mygdx.game.Map.getInstance().getAudioManager().playButton();
			
			Group tempParent = confirmButton.getParent();
			confirmButton.remove();
			tempParent.addActor(confirmButton);
			
			Money money = com.mygdx.game.Map.getInstance().getPlayerHUD().getStatusUI().getMoney();
		
			//if buy item is selected, check if they have enough money, if there is enough, buy the item, add it to inventory, and remove it from the buy list
			if (selectedBuyItem != null) {		
				if (money.getMoney() >= selectedBuyItem.getCost()) {
					InventoryItem tempItem = new InventoryItem(selectedBuyItem);
					com.mygdx.game.Map.getInstance().getPlayerHUD().getInventory().addItemToInventory(tempItem, selectedBuyItem.getName());
					com.mygdx.game.Map.getInstance().getPlayerHUD().getInventory().equipEquippableItems();		
					money.setMoney(money.getMoney() - selectedBuyItem.getCost());
					logger.info("Bought " + selectedBuyItem.getName() + " from shop.");
					if (!tempItem.isConsumable()) {
						buyList.remove(itemToRemove.getChild(0));
					}
				} else {			
					infoLabel.setText("Not enough money!");
				}
			}
			//if sell item isn't null, give the user half the money that the item costs and remove it from their inventory
			if (selectedSellItem != null) {
				money.setMoney(money.getMoney() + (selectedSellItem.getCost() / 2));
				InventoryItem tempItem = new InventoryItem(selectedSellItem);
				tempItem.setName(selectedSellItem.getName());
				logger.info("Sold " + tempItem.getName() + " to shop.");
				com.mygdx.game.Map.getInstance().getPlayerHUD().getInventory().removeItemFromInventory(tempItem);
			}
			
			//clear all the items in both sell and buy table
			
				sellTable.clearChildren();
				buyTable.clearChildren();
				labels.clear();
				
				//loop back through buy list to update what can be bought
				for (Map.Entry<Label, ShopItem> set : buyList.entrySet()) {	
					set.getKey().setWrap(true);
					final Group tempGroup = new Group();
					tempGroup.addActor(set.getKey());
					tempGroup.addActor(set.getValue());
					
					labels.add(set.getKey());
					
					buyTable.add(tempGroup).width(182).height(32).padTop(10);
					
					tempGroup.addListener(new ClickListener() {

						@Override
						public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
							
							for (int i = 0; i < labels.size(); i++) {
								labels.get(i).setColor(Color.WHITE);
							}
							
							selectedBuyItem = null;
							selectedSellItem = null;
							selectedBuyItem = (ShopItem) tempGroup.getChild(1);
							Label tempLabel = (Label) tempGroup.getChild(0);
							selectedBuyItem.setName(tempLabel.getText().toString());
							itemToRemove = tempGroup;
							tempLabel.setColor(Color.YELLOW);
							return true;
						}			
			  					
					});
					buyTable.row();
				}
				
				//Loop back through sell list to recheck the players inventory for items to sell
				for (Map.Entry<Label, ShopItem> set : com.mygdx.game.Map.getInstance().getPlayerHUD().getInventory().getItemsToSell().entrySet()) {
					set.getKey().setWrap(true);
					final Group tempGroup = new Group();
					
					labels.add(set.getKey());
					
					set.getKey().setWidth(150);
					set.getKey().setHeight(32);
					
					set.getValue().setWidth(32);
					set.getValue().setHeight(32);
					
					set.getValue().setBounds(set.getValue().getX() + set.getKey().getWidth(), set.getValue().getY(), 32, 32);
					
					tempGroup.addActor(set.getKey());
					tempGroup.addActor(set.getValue());
					
					sellTable.add(tempGroup).width(182).height(32).padTop(10);
					
					tempGroup.addListener(new ClickListener() {

						@Override
						public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
							
							for (int i = 0; i < labels.size(); i++) {
								labels.get(i).setColor(Color.WHITE);
							}
							
							selectedBuyItem = null;
							selectedSellItem = null;
							selectedSellItem = (ShopItem) tempGroup.getChild(1);
							Label tempLabel = (Label) tempGroup.getChild(0);
							selectedSellItem.setName(tempLabel.getText().toString());
							itemToRemove = tempGroup;
							tempLabel.setColor(Color.YELLOW);
							return true;
						}			
			  					
					});
					sellTable.row();
				}
				
				selectedBuyItem = null;
				selectedSellItem = null;	
		}
		
		stage.act(delta);
		stage.draw();	
	}

	public void resize(int width, int height) {
		stage.getViewport().update(width, height, false);
	}
	
	/**
	 * Resizes texture to be used as background
	 * @param file - internal file texture
	 * @return resized texture
	 */
	public static Texture createTexture(FileHandle file) {
        Pixmap pixmap = new Pixmap(file);
        Pixmap scalePixmap = new Pixmap(400, 300, pixmap.getFormat());
        scalePixmap.drawPixmap(pixmap,
                0, 0, pixmap.getWidth(), pixmap.getHeight(),
                0, 0, scalePixmap.getWidth(), scalePixmap.getHeight()
        );
        Texture pixmaptexture = new Texture(scalePixmap); 
        pixmap.dispose();
        scalePixmap.dispose();
        return pixmaptexture;
    }

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Stage getStage() {
		return stage;
	}
	
}
