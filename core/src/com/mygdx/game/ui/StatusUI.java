package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.mygdx.game.Map;
import com.mygdx.game.Utilities;

public class StatusUI extends Window {

	private Money money;
	
	public StatusUI(Money money) {
		super("Status", new WindowStyle(new BitmapFont(), Color.RED, null));
		this.money = money;
		HealthBar hpBar = new HealthBar();
		this.money.setName("money");
		this.setFillParent(true);
		this.padTop(20);
		this.add(hpBar).padLeft(20);
		this.add(this.money);
		this.top().left();
		this.pack();
	}
	
	public Money getMoney() {
		return money;
	}
	
}
