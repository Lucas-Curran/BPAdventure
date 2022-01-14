package com.mygdx.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.components.PlayerComponent;
import com.mygdx.game.entities.Player;

public class Weapon {
	
	static Logger logger = LogManager.getLogger(Weapon.class.getName());
	
	private BodyFactory bodyFactory;
	private Body sword;
	
	private float swing = 0;
	private boolean startSwing = true;
	private boolean swingFinished = false;
	
	private int direction;
	
	private final int LEFT = 1;
	private final int RIGHT = 2;
	private float x, y;
	
	public Weapon() {
		bodyFactory = BodyFactory.getInstance(new GameWorld().getInstance());
	}
	
	public void createSword(float x, float y) {
		this.x = x;
		this.y = y;
		Texture texture = new Texture(Gdx.files.internal("border.png"));
		sword = bodyFactory.makeBoxPolyBody(x, y, 1.3f, 0.3f, BodyFactory.STEEL, BodyType.DynamicBody, false, true, texture);
		sword.setGravityScale(0);
		sword.setUserData("Sword");		
		logger.info("Sword created.");
	}
	
	public void positionSword(float x, float y, int direction) {
		this.direction = direction;
		if (direction == LEFT) {
			sword.setTransform(x - 0.7f, y, 0);
		} else {
			sword.setTransform(x + 0.7f, y, 0);
		} 
	}
	
	public void swingSword() {	
		if (direction == LEFT) {
			if (startSwing) {
//				sword.applyAngularImpulse(0.4f, true);	
//				if (sword.getAngularVelocity() >= 47) {
//					startSwing =! startSwing;
//				}
				
				sword.applyLinearImpulse(-1.5f, 0, sword.getPosition().x, sword.getPosition().y, true);
				if (sword.getLinearVelocity().x <= -30) {
					startSwing =! startSwing;
				}
			} else if (!startSwing) {
//				sword.applyAngularImpulse(-0.4f, true);
//				if (sword.getAngularVelocity() < 0) {
//					sword.setAngularVelocity(0);
//					swingFinished = true;	
//					startSwing = true;
//				}
				
				sword.applyLinearImpulse(1.5f, 0, sword.getPosition().x, sword.getPosition().y, true);
				if (sword.getLinearVelocity().x > 0) {
					sword.setLinearVelocity(0, 0);
					swingFinished = true;	
					startSwing = true;
				}
			}
			
		} else {
			if (startSwing) {
//				sword.applyAngularImpulse(-0.4f, true);	
//				if (sword.getAngularVelocity() <= -47) {
//					startSwing =! startSwing;
//				}
				sword.applyLinearImpulse(1.5f, 0, sword.getPosition().x, sword.getPosition().y, true);
				if (sword.getLinearVelocity().x >= 30) {
					startSwing =! startSwing;
				}
			} else if (!startSwing) {
//				sword.applyAngularImpulse(0.4f, true);
//				if (sword.getAngularVelocity() > 0) {
//					sword.setAngularVelocity(0);
//					swingFinished = true;	
//					startSwing = true;
//				}
				sword.applyLinearImpulse(-1.5f, 0, sword.getPosition().x, sword.getPosition().y, true);
				if (sword.getLinearVelocity().x < 0) {
					sword.setLinearVelocity(0, 0);
					swingFinished = true;	
					startSwing = true;
				}
			}
			
		}
	}
	
	public boolean isSwingFinished() {
		return swingFinished;
	}	
	
	public void setSwingFinished(boolean swingFinished) {
		this.swingFinished = swingFinished;
	}
	
}
