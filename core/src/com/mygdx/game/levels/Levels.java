package com.mygdx.game.levels;

/**
 * Holds the references to levels
 * 
 */
public class Levels {

	private LevelOne levelOne;
	//private LevelTwo levelTwo;
	
	public Levels() {
		levelOne = new LevelOne();
		//levelTwo = new LevelTwo();
	}
	
	public LevelOne getLevelOne() {
		return levelOne;
	}
	
//	public LevelTwo getLevelTwo() {
//		return levelTwo;
//	}
	
	public void dispose() {
		levelOne.dispose();
		//levelTwo.dispose();
	}
	
}
