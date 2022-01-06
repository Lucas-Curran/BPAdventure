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
	private LevelSeven levelSeven;
	
	public Levels(World world) {
		levelOne = new LevelOne();
		levelTwo = new LevelTwo();
		levelThree = new LevelThree(world);
		levelSeven = new LevelSeven(world);
	}
	
	public static enum LevelDestination {
		 
		OVERWORLD 	("overworld_bg.png"),
		LVL_2  	  ("caveBackground.png"),
		LVL_3  		 ("fireCave_bg.png"),
		LVL_4  						(""),
		LVL_5  						(""),
		LVL_6  						(""),
		LVL_7  	   ("purpleCave_bg.png");
		
		private final String value;
		
		LevelDestination(String value) {
			this.value = value;
		}
		
		public String getValue() { return value; }
		// could use value to do chests, if value = cave give this item
		
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
	
	public LevelSeven getLevelSeven() {
		return levelSeven;
	}
	
	public void dispose() {
		levelOne.dispose();
		levelTwo.dispose();
		levelThree.dispose();
	}
	
}
