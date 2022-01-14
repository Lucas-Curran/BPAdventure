package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;


public class Weapon {
	
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
	
	/**
	 * Creates sword
	 * @param x - x position of sword
	 * @param y - y position of sword
	 */
	public void createSword(float x, float y) {
		this.x = x;
		this.y = y;
		Texture texture = new Texture(Gdx.files.internal("border.png"));
		sword = bodyFactory.makeBoxPolyBody(x, y, 1.3f, 0.3f, BodyFactory.STEEL, BodyType.DynamicBody, false, true, texture);
		sword.setGravityScale(0);
		sword.setUserData("Sword");		
	}
	
	/**
	 * Adjusts sword position
	 * @param x - x position
	 * @param y - y position 
	 * @param direction - direction of player
	 */
	public void positionSword(float x, float y, int direction) {
		this.direction = direction;
		if (direction == LEFT) {
			sword.setTransform(x - 0.7f, y, 0);
		} else {
			sword.setTransform(x + 0.7f, y, 0);
		} 
	}
	
	/**
	 * Swings the sword (Stabbing motion)
	 */
	public void swingSword() {	
		if (direction == LEFT) {
			if (startSwing) {
				sword.applyLinearImpulse(-1.5f, 0, sword.getPosition().x, sword.getPosition().y, true);
				//retracts sword after reaching certain velocity
				if (sword.getLinearVelocity().x <= -30) {
					startSwing =! startSwing;
				}
			} else if (!startSwing) {
				sword.applyLinearImpulse(1.5f, 0, sword.getPosition().x, sword.getPosition().y, true);
				//stops sword and ends swing
				if (sword.getLinearVelocity().x > 0) {
					sword.setLinearVelocity(0, 0);
					swingFinished = true;	
					startSwing = true;
				}
			}
			
		} else {
			if (startSwing) {
				sword.applyLinearImpulse(1.5f, 0, sword.getPosition().x, sword.getPosition().y, true);
				//retracts sword after reaching certain velocity
				if (sword.getLinearVelocity().x >= 30) {
					startSwing =! startSwing;
				}
			} else if (!startSwing) {
				sword.applyLinearImpulse(-1.5f, 0, sword.getPosition().x, sword.getPosition().y, true);
				//stops sword and ends swing
				if (sword.getLinearVelocity().x < 0) {
					sword.setLinearVelocity(0, 0);
					swingFinished = true;	
					startSwing = true;
				}
			}
			
		}
	}
	
	/**
	 * Checks if swing is finished
	 * @return - boolean if swing is finished
	 */
	public boolean isSwingFinished() {
		return swingFinished;
	}	
	
	/**
	 * Sets if swing is finished
	 * @param swingFinished - boolean for swing finish
	 */
	public void setSwingFinished(boolean swingFinished) {
		this.swingFinished = swingFinished;
	}
	
}
