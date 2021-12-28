package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Money extends Actor {

	int money;
	private Texture moneyTex;
	private BitmapFont bitmapFont;
	
	public Money() {
		moneyTex = new Texture(Gdx.files.internal("money.png"));
		bitmapFont = new BitmapFont();
		money = 0;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(moneyTex, 225, 430, 32, 32);
		bitmapFont.draw(batch, ": " + money, 260, 450);
	}
	
	public int getMoney() {
		return money;
	}
	
	public void setMoney(int money) {
		this.money = money;
	}
	
}
