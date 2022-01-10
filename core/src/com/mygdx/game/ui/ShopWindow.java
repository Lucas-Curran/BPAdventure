package com.mygdx.game.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;


public class ShopWindow extends Window {
	
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
	private final int BORDER_WIDTH = 3;
	private Money money;

	//TODO: Scrollable table - https://stackoverflow.com/questions/23944088/libgdx-table-in-scrollpane
	
	public ShopWindow(HashMap<Label, ShopItem> buyList, HashMap<Label, ShopItem> sellList, Money money) {
		super("Shop", new WindowStyle(new BitmapFont(), Color.RED, windowBg));
		
		this.buyList = buyList;
		this.sellList = sellList;
		this.money = money;

		// sellList = current inventory items
				
		container = new Table();
		
		setTouchable(Touchable.disabled);
		
		borderTexture = new Texture(Gdx.files.internal("border.png"));
		borderTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		borderImage = new Image(borderTexture);

		getTitleTable().padBottom(15);
		
		stage = new Stage();	
		com.mygdx.game.Map.getInstance().getInputMultiplexer().addProcessor(stage);
		stage.setDebugAll(true);

		buyTable = new Table(new Skin(Gdx.files.internal("uiskin.json")));
		
		for (Map.Entry<Label, ShopItem> set : buyList.entrySet()) {	
			set.getKey().setWrap(true);
			final Group tempGroup = new Group();
			
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
					borderImage.remove();
					float width = tempGroup.getWidth();
					float height = tempGroup.getHeight();
					float groupX = tempGroup.getX(Align.left);
					float groupY = tempGroup.getY(Align.top);
					borderImage.setSize(width + BORDER_WIDTH * 2, height + BORDER_WIDTH * 2);
					borderImage.setPosition(groupX - BORDER_WIDTH + 18, groupY + 22 + height - BORDER_WIDTH);
					stage.addActor(borderImage);
					tempGroup.getParent().getParent().getParent().toFront();
					return true;
				}			
	  					
			});
			buyTable.row();
		}

		sellTable = new Table(new Skin(Gdx.files.internal("uiskin.json")));

		for (Map.Entry<Label, ShopItem> set : sellList.entrySet()) {
			set.getKey().setWrap(true);
			final Group tempGroup = new Group();
			
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
					borderImage.remove();
					float width = tempGroup.getWidth();
					float height = tempGroup.getHeight();
					float groupX = tempGroup.getX(Align.left);
					float groupY = tempGroup.getY(Align.top);
					borderImage.setSize(width + BORDER_WIDTH * 2, height + BORDER_WIDTH * 2);
					borderImage.setPosition(groupX - BORDER_WIDTH + 18, groupY + 22 + height - BORDER_WIDTH);
					stage.addActor(borderImage);
					tempGroup.getParent().getParent().getParent().toFront();
					return true;
				}			
	  					
			});
			sellTable.row();
		}
		
		TextButton confirmButton = new TextButton("Confirm", Utilities.buttonStyles("default-rect", "default-rect-down"));
		Utilities.buttonSettings(confirmButton);
		
		infoTable = new Table();
		
		infoTable.add(confirmButton);
		infoTable.row();
		
		shopTable = new Table();
		shopTable.add(buyTable);
		shopTable.add(sellTable);
		shopTable.add(infoTable);
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
		
		buyTable.invalidate();
		sellTable.invalidate();
		
		sellTable.padLeft(30);
		shopTable.left();
		
		toBack();
		setResizable(false);
		setWidth(500);
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
		if (isShopVisible()) {
			if (!money.hasParent()) {
				infoTable.add(money);
			} 
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
	
}
