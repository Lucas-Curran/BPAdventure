package com.mygdx.game.systems;


import com.mygdx.game.components.*;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;

public class PlayerControlSystem extends IteratingSystem {
	 
	ComponentMapper<PlayerComponent> pm;
	ComponentMapper<B2dBodyComponent> bodm;
	ComponentMapper<StateComponent> sm;
	boolean left, right, up, down;
	
	public PlayerControlSystem() {
		super(Family.all(PlayerComponent.class).get());
		pm = ComponentMapper.getFor(PlayerComponent.class);
		bodm = ComponentMapper.getFor(B2dBodyComponent.class);
		sm = ComponentMapper.getFor(StateComponent.class);
		
		left = Gdx.input.isKeyPressed(Input.Keys.A);
		right = Gdx.input.isKeyPressed(Input.Keys.D);
		up = Gdx.input.isKeyPressed(Input.Keys.W);
		down = Gdx.input.isKeyPressed(Input.Keys.S);
	}
	
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		B2dBodyComponent b2body = bodm.get(entity);
		StateComponent state = sm.get(entity);
		
		if(b2body.body.getLinearVelocity().y > 0){
			state.set(StateComponent.STATE_FALLING);
		}
		
		if(b2body.body.getLinearVelocity().y == 0){
			if(state.get() == StateComponent.STATE_FALLING){
				state.set(StateComponent.STATE_NORMAL);
			}
			if(b2body.body.getLinearVelocity().x != 0){
				state.set(StateComponent.STATE_MOVING);
			}
		}
		
		if(left){
			b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, -5f, 0.2f),b2body.body.getLinearVelocity().y);
		}
		if(right){
			b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, 5f, 0.2f),b2body.body.getLinearVelocity().y);
		}
		
		if(!left && !right){
			b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, 0, 0.1f),b2body.body.getLinearVelocity().y);
		}
		
		if(up && 
				(state.get() == StateComponent.STATE_NORMAL || state.get() == StateComponent.STATE_MOVING)){
			//b2body.body.applyForceToCenter(0, 3000,true);
			b2body.body.applyLinearImpulse(0, 75f, b2body.body.getWorldCenter().x,b2body.body.getWorldCenter().y, true);
			state.set(StateComponent.STATE_JUMPING);
		}
	}
}