package com.mygdx.game.levels;

import com.badlogic.gdx.physics.box2d.World;

/**
 * Holds the references to levels
 * 
 */
public class Levels {

	private LevelOne levelOne;
	private LevelTwo levelTwo;
	private LevelThree levelThree;
	
	public Levels(World world) {
		levelOne = new LevelOne();
		levelTwo = new LevelTwo();
		levelThree = new LevelThree(world);
	}
	
	public LevelOne getLevelOne() {
		return levelOne;
	}
	
	public LevelTwo getLevelTwo() {
		return levelTwo;
	}
	
	public LevelThree getLevelThree() {
		return levelThree;
	}
	
	public void dispose() {
		levelOne.dispose();
		levelTwo.dispose();
		levelThree.dispose();
	}
	
}
