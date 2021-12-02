package com.mygdx.game.systems;


import com.mygdx.game.Camera;
import com.mygdx.game.Map;
import com.mygdx.game.TextBox;
import com.mygdx.game.components.*;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class PlayerControlSystem extends IteratingSystem {

	ComponentMapper<PlayerComponent> pm;
	ComponentMapper<B2dBodyComponent> bodm;
	ComponentMapper<StateComponent> sm;
	Camera cam;

	public PlayerControlSystem() {
		super(Family.all(PlayerComponent.class).get());
		pm = ComponentMapper.getFor(PlayerComponent.class);
		bodm = ComponentMapper.getFor(B2dBodyComponent.class);
		sm = ComponentMapper.getFor(StateComponent.class);
		cam = new Camera();
	}
	
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		B2dBodyComponent b2body = bodm.get(entity);
		StateComponent state = sm.get(entity);
		PlayerComponent player = pm.get(entity);

	if (entity.getComponent(PlayerComponent.class).player == true) {
		float horizontalVel = 3.5f;

		if(b2body.body.getLinearVelocity().y > 0 || b2body.body.getLinearVelocity().y < 0 ) {
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

		if (!Map.getInstance().inAction()) {
			if(Gdx.input.isKeyPressed(Input.Keys.A)){
				b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, -horizontalVel, 0.2f),b2body.body.getLinearVelocity().y);
			}
			if(Gdx.input.isKeyPressed(Input.Keys.D)){
				b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, horizontalVel, 0.2f),b2body.body.getLinearVelocity().y);
			}

			if(!Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D)) {
				b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, 0, 0.1f),b2body.body.getLinearVelocity().y);
			}

			if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && 
					(state.get() == StateComponent.STATE_NORMAL || state.get() == StateComponent.STATE_MOVING)){
				System.out.println("Jump");
				b2body.body.applyForceToCenter(0, 300f,true);
				b2body.body.applyLinearImpulse(0f, 50f, b2body.body.getWorldCenter().x,b2body.body.getWorldCenter().y, true);
				state.set(StateComponent.STATE_JUMPING);
			}
		}
		entity.getComponent(TransformComponent.class).position.set(new Vector3(cam.getCamera().position.x, cam.getCamera().position.y, 0));
	} 
	}
}