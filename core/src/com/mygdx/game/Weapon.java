package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.components.PlayerComponent;
import com.mygdx.game.entities.Player;

public class Weapon {
	
	private BodyFactory bodyFactory;
	private Body sword;
	
	private float swing = 0;
	private boolean startSwing = true;
	private boolean swingFinished = false;
	
	private int direction;
	
	private final int LEFT = 1;
	private final int RIGHT = 2;
	
	public Weapon() {
		bodyFactory = BodyFactory.getInstance(new GameWorld().getInstance());
	}
	
	public void createSword(float x, float y) {
		Texture texture = new Texture(Gdx.files.internal("border.png"));
		sword = bodyFactory.makeBoxPolyBody(x, y, 0.3f, 1.3f, BodyFactory.STEEL, BodyType.DynamicBody, null, false, true, texture);
	}
	
	public void positionSword(float x, float y, int direction) {
		this.direction = direction;
		sword.setLinearVelocity(0, -10);
		if (direction == LEFT) {
			sword.setTransform(x - 0.5f, y + .4f, 0);
		} else {
			sword.setTransform(x + 0.5f, y + .4f, 0);
		} 
	}
	
	public void swingSword() {	
		System.out.println(sword.getAngularVelocity());
		if (direction == LEFT) {
			if (startSwing) {
				sword.applyAngularImpulse(0.4f, true);	
				if (sword.getAngularVelocity() >= 47) {
					startSwing =! startSwing;
				}
			} else if (!startSwing) {
				sword.applyAngularImpulse(-0.4f, true);
				if (sword.getAngularVelocity() < 0) {
					sword.setAngularVelocity(0);
					swingFinished = true;	
					startSwing = true;
				}
			}
		} else {
			if (startSwing) {
				sword.applyAngularImpulse(-0.4f, true);	
				if (sword.getAngularVelocity() <= -47) {
					startSwing =! startSwing;
				}
			} else if (!startSwing) {
				sword.applyAngularImpulse(0.4f, true);
				if (sword.getAngularVelocity() > 0) {
					sword.setAngularVelocity(0);
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
