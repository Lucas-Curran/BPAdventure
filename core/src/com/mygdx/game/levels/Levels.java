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
	private LevelFour levelFour;
	private LevelFive levelFive;
	private LevelSix levelSix;
	private LevelSeven levelSeven;
	private LevelEight levelEight;
	private LevelNine levelNine;
	private LevelTen levelTen;
	private IceDungeon iceDungeon;
	
	public Levels(World world) {

		levelOne = new LevelOne();
		levelTwo = new LevelTwo();
		levelThree = new LevelThree(world);
		levelFour = new LevelFour(world);
		levelFive = new LevelFive(world);
		levelSix = new LevelSix(world);
		levelSeven = new LevelSeven(world);
		levelEight = new LevelEight(world);
		levelNine = new LevelNine(world);
		levelTen = new LevelTen(world);
		iceDungeon = new IceDungeon(world);
			
	}
	
	public static enum LevelDestination {
		 
		OVERWORLD 	("overworld_bg.png"),
		LVL_2  	  ("caveBackground.png"),
		LVL_3  		 ("fireCave_bg.png"),
		LVL_4  			 ("snow_bg.png"),
		LVL_5  		 ("goldCave_bg.png"),
		LVL_6  		   ("desert_bg.png"),
		LVL_7  	   ("purpleCave_bg.png"),
		LVL_8		   ("jungle_bg.png"),
		LVL_9	   ("nearTheEnd_bg.png"),
		LVL_10			  ("sky_bg.png");
		
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
	
	public LevelFour getLevelFour() {
		return levelFour;
	}
	
	public LevelFive getLevelFive() {
		return levelFive;
	}
	
	public LevelSix getLevelSix() {
		return levelSix;
	}
	
	public LevelSeven getLevelSeven() {
		return levelSeven;
	}
	
	public LevelEight getLevelEight() {
		return levelEight;
	}
	
	public LevelNine getLevelNine() {
		return levelNine;
	}
	
	public LevelTen getLevelTen() {
		return levelTen;
	}
	
	public IceDungeon getIceDungeon() {
		return iceDungeon;
	}
	
	public void dispose() {
		levelOne.dispose();
		levelTwo.dispose();
		levelThree.dispose();
		levelFour.dispose();
		levelFive.dispose();
		levelSix.dispose();
		levelSeven.dispose();
		levelEight.dispose();
		levelNine.dispose();
		levelTen.dispose();
		iceDungeon.dispose();
	}
	
}
