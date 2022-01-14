package com.mygdx.game.ui;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.CrashWriter;
import com.mygdx.game.Map;
import com.mygdx.game.SqliteManager;
import com.mygdx.game.entities.EntityHandler;

public class Money extends Actor {

	static Logger logger = LogManager.getLogger(EntityHandler.class.getName());
	
	int money;
	private Texture moneyTex;
	private BitmapFont bitmapFont;
	private float x, y;
	private SqliteManager sm;
	
	public Money() {
		try {
			sm = new SqliteManager();
			moneyTex = new Texture(Gdx.files.internal("money.png"));
			bitmapFont = new BitmapFont();
			money = sm.getMoney();
			x = 225;
			y = 430;
			logger.info("Money instanced.");
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
		sm.updateMoney(money);
		this.money = sm.getMoney();
	}
	
}
