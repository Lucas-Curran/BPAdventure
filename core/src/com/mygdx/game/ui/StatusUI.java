package com.mygdx.game.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.mygdx.game.Map;
import com.mygdx.game.Utilities;
import com.mygdx.game.entities.EntityHandler;

public class StatusUI extends Window {

	static Logger logger = LogManager.getLogger(StatusUI.class.getName());
	
	private Money money;
	private HealthBar hpBar;
	
	public StatusUI(Money money) {
		super("Status", new WindowStyle(new BitmapFont(), Color.RED, null));
		this.money = money;
		hpBar = new HealthBar();
		this.money.setName("money");
		this.setFillParent(true);
		this.padTop(20);
		this.add(hpBar).padLeft(20);
		this.add(this.money);
		this.top().left();
		this.pack();
		logger.info("StatusUI instanced.");
	}
	
	public Money getMoney() {
		return money;
	}
	
	public HealthBar getHealthBar() {
		return hpBar;
	}
	
}
