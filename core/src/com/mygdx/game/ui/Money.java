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
	private float x, y;
	
	public Money() {
		moneyTex = new Texture(Gdx.files.internal("money.png"));
		bitmapFont = new BitmapFont();
		money = 100;
		x = 225;
		y = 430;
	}
	
	public Money(Money money) {
		this.money = money.money;
		this.moneyTex = money.moneyTex;
		this.bitmapFont = money.bitmapFont;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(moneyTex, x, y, 32, 32);
		bitmapFont.draw(batch, ": " + money, x + 35, y + 20);
	}
	
	@Override 
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public int getMoney() {
		return money;
	}
	
	public void setMoney(int money) {
		this.money = money;
	}
	
}
