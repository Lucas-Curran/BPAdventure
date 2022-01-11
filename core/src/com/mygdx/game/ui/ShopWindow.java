package com.mygdx.game.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Utilities;
import com.mygdx.game.item.InventoryItem;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;


public class ShopWindow extends Window implements InputProcessor  {
	
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

	public ShopWindow(HashMap<Label, ShopItem> buyList, HashMap<Label, ShopItem> sellList, Money money) {
		super("Shop", new WindowStyle(new BitmapFont(), Color.RED, windowBg));
		
		this.buyList = buyList;
		this.sellList = sellList;

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

		sellTable = new Table(new Skin(Gdx.files.internal("uiskin.json")));

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
	}
	
	public void setShopVisible(boolean isVisible) {
		container.setVisible(isVisible);	
	}
	
	public boolean isShopVisible() {
		return container.isVisible();
	}
	
	public void render(float delta) {
		
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
			
		}
		
		if (confirmButton.isPressed()) {
			
			Group tempParent = confirmButton.getParent();
			confirmButton.remove();
			tempParent.addActor(confirmButton);
			
			Money money = com.mygdx.game.Map.getInstance().getPlayerHUD().getStatusUI().getMoney();
		
			if (selectedBuyItem != null) {		
				if (money.getMoney() >= selectedBuyItem.getCost()) {
					InventoryItem tempItem = new InventoryItem(selectedBuyItem);
					System.out.println("Bought item " + selectedBuyItem.getName());
					com.mygdx.game.Map.getInstance().getPlayerHUD().getInventory().addItemToInventory(tempItem, selectedBuyItem.getName());
					money.setMoney(money.getMoney() - selectedBuyItem.getCost());
					buyList.remove(itemToRemove.getChild(0));	
				} else {			
					infoLabel.setText("Not enough money!");
				}
			}
			if (selectedSellItem != null) {
				money.setMoney(money.getMoney() + (selectedSellItem.getCost() / 2));
				InventoryItem tempItem = new InventoryItem(selectedSellItem);
				tempItem.setName(selectedSellItem.getName());
				com.mygdx.game.Map.getInstance().getPlayerHUD().getInventory().removeItemFromInventory(tempItem);
			}
			
				sellTable.clearChildren();
				buyTable.clearChildren();
				labels.clear();
				
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
				
				for (Map.Entry<Label, ShopItem> set : com.mygdx.game.Map.getInstance().getPlayerHUD().getInventory().getAllItems().entrySet()) {
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
