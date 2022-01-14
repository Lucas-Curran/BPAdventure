package com.mygdx.game.systems;


import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Camera;
import com.mygdx.game.Map;
import com.mygdx.game.components.B2dBodyComponent;
import com.mygdx.game.components.PlayerComponent;
import com.mygdx.game.components.StateComponent;
import com.mygdx.game.components.TransformComponent;

public class PlayerControlSystem extends IteratingSystem {

	ComponentMapper<PlayerComponent> pm;
	ComponentMapper<B2dBodyComponent> bodm;
	ComponentMapper<StateComponent> sm;
	Camera cam;

	/**
	 * Instaniates system for controling the players movements
	 */
	public PlayerControlSystem() {
		super(Family.all(PlayerComponent.class).get());
		pm = ComponentMapper.getFor(PlayerComponent.class);
		bodm = ComponentMapper.getFor(B2dBodyComponent.class);
		sm = ComponentMapper.getFor(StateComponent.class);
		cam = new Camera();
	}
	
	public float jumpScale = 40;

	/**
	 * Sets how high or low the player jumps
	 * @param jumpScale - height of jump
	 */
	public void setJumpScale(float jumpScale) {
		this.jumpScale = jumpScale;
	}

	
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		B2dBodyComponent b2body = bodm.get(entity);
		StateComponent state = sm.get(entity);
		PlayerComponent player = pm.get(entity);

		//checks whether entity is a player before moving
	if (entity.getComponent(PlayerComponent.class).player == true) {
		float horizontalVel = 3.5f;

		//if body has positive or negative y velocity, set state to falling
		if(b2body.body.getLinearVelocity().y > 0 || b2body.body.getLinearVelocity().y < 0 ) {
			state.set(StateComponent.STATE_FALLING);
		}

		//set state back to normal if state was falling and y velocity is 0, or set to moving if x velocity is greater than 0
		if(b2body.body.getLinearVelocity().y == 0){
			if(state.get() == StateComponent.STATE_FALLING){
				state.set(StateComponent.STATE_NORMAL);
			}
			if(b2body.body.getLinearVelocity().x != 0){
				state.set(StateComponent.STATE_MOVING);
			}
		}

		//runs movement code if map isnt in action
		if (!Map.getInstance().inAction()) {
			//if A is pressed, move left
			if(Gdx.input.isKeyPressed(Input.Keys.A)){
				player.direction = player.LEFT;
				
				b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, -horizontalVel, 0.2f),b2body.body.getLinearVelocity().y);
			}
			//if D is pressed, move right
			if(Gdx.input.isKeyPressed(Input.Keys.D)){
				player.direction = player.RIGHT;
				
				b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, horizontalVel, 0.2f),b2body.body.getLinearVelocity().y);
			}

//			if(!Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D)) {
//				b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, 0, 0.1f),b2body.body.getLinearVelocity().y);
//				
//			}

			//If space is pressed and state is normal/moving, but not falling, then make the player jump and set to jumping
			if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && 
					(state.get() == StateComponent.STATE_NORMAL || state.get() == StateComponent.STATE_MOVING)){
				
				b2body.body.applyForceToCenter(0, 0f,true);
				b2body.body.applyLinearImpulse(0f, jumpScale, b2body.body.getWorldCenter().x,b2body.body.getWorldCenter().y, true);
				state.set(StateComponent.STATE_JUMPING);
			}
		}
	} 
	}
}