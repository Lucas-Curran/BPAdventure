package com.mygdx.game.systems;

import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.Flee;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.ai.steer.behaviors.Wander;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.SteeringComponent;

public class SteeringPresets {
	
	//Code adapted from GameDevelopment.blog
	
	/**
	 * Code run when player is not close
	 * @param scom - SteerinComponent of entity
	 * @return - Vector for wandering
	 */
	public static Wander<Vector2> getWander(SteeringComponent scom){
		Wander<Vector2> wander = new Wander<Vector2>(scom)
				.setFaceEnabled(false) // let wander behaviour manage facing
				.setWanderOffset(0) // distance away from entity to set target
				.setWanderOrientation(0f) // the initial orientation
				.setWanderRadius(10f) // size of target 
				.setWanderRate(MathUtils.PI2 * 4); // higher values = more spinning
		return wander;
	}
	
	/**
	 * Seeks a target
	 * @param seeker - Entity that follows 
	 * @param target - Target entity that the seeker follows
	 * @return - Vector for seeking
	 */
	public static Seek<Vector2> getSeek(SteeringComponent seeker, SteeringComponent target){
		Seek<Vector2> seek = new Seek<Vector2>(seeker,target);
		return seek;
	}
	
	/**
	 * Flee code that runs away from an entity
	 * @param runner - Entity that flees
	 * @param fleeingFrom - Target entity that the runner flees from
	 * @return - Vector for fleeing
	 */
	public static Flee<Vector2> getFlee(SteeringComponent runner, SteeringComponent fleeingFrom){
		Flee<Vector2> seek = new Flee<Vector2>(runner,fleeingFrom);
		return seek;
	}
	
	/**
	 * Seeks out a target and slows down
	 * @param runner - Entity that follows
	 * @param target - Target entity that the runner follows
	 * @return - Vector for fleeing
	 */
	public static Arrive<Vector2> getArrive(SteeringComponent runner, SteeringComponent target){
		Arrive<Vector2> arrive = new Arrive<Vector2>(runner, target)
				.setTimeToTarget(0.01f) // default 0.1f
				.setArrivalTolerance(2f) //
				.setDecelerationRadius(10f);
				
		return arrive;
	}
}
