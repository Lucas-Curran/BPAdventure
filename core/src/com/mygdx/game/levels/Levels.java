package com.mygdx.game.levels;

/**
 * Any textures not credited are either public domain or custom made by the BPAdventure Team. 
 * All textures used are free to use for any purpose including commercially 
 */

import com.badlogic.gdx.physics.box2d.World;

/**
 * Holds the references to levels
 * 
 */
public class Levels {

	private Overworld overworld;
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
	private VictoryScreen victoryLevel;
	LevelFactory[] levels;
	World world;
	
	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
/**
 * initializes all the worlds and puts them in an array
 * @param world - sends the GameWorld
 */
	public void createAllLevels(World world) {

		overworld = new Overworld();
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
		victoryLevel = new VictoryScreen(world);
		
		levels = new LevelFactory[] {overworld, levelTwo, levelThree, levelFour, levelFive, levelSix, levelSeven, levelEight, levelNine, levelTen};
			
	}
	
/**
 * dictates the different locations doors can go to 
 * also holds the PNGs for the backgrounds
 * @author 00011598
 *
 */
	public static enum LevelDestination {
		 
		OVERWORLD 	("overworld_bg.png"),
		LVL_2  	  ("caveBackground.png"),
		LVL_3  		 ("fireCave_bg.png"),
		LVL_4  			 ("snow_bg.png"),
		LVL_5  		   ("desert_bg.png"),
		LVL_6  		   ("jungle_bg.png"),
		LVL_7  	   ("purpleCave_bg.png"),
		LVL_8	   ("nearTheEnd_bg.png"),
		LVL_9	     ("goldCave_bg.png"),
		LVL_10			  ("sky_bg.png"),
		INTERNAL					("");
		
		private final String value;
		
		LevelDestination(String value) {
			this.value = value;
		}
		
		public String getValue() { return value; }
		// could use value to do chests, if value = cave give this item
		
	}
	
	/**
	 * Attributions: 
	 * Snow Background by greggman licensed CC-BY 3.0: https://opengameart.org/content/backgrounds-for-2d-platformers
	 * Jungle Background by greggman licensed CC-BY 3.0: https://opengameart.org/content/backgrounds-for-2d-platformers
	 * Sky Background by TAD licensed CC-BY 3.0: https://opengameart.org/content/sky-backgrounds
	 * Fire Cave Background by CreativeGameAssets.com licensed CC-BY-SA 4.0 https://opengameart.org/content/2d-backgrounds-for-platformer-game-dungeons-and-cave-parallax-vector-illustration
	 * Purple Cave Background by CreativeGameAssets.com licensed CC-BY-SA 4.0 https://opengameart.org/content/2d-backgrounds-for-platformer-game-dungeons-and-cave-parallax-vector-illustration
	 * Orange and Yellow Cave Background by CreativeGameAssets.com licensed CC-BY-SA 4.0 https://opengameart.org/content/2d-backgrounds-for-platformer-game-dungeons-and-cave-parallax-vector-illustration
	 * Black and Gold Cave Background by CreativeGameAssets.com licensed CC-BY-SA 4.0 https://opengameart.org/content/2d-backgrounds-for-platformer-game-dungeons-and-cave-parallax-vector-illustration
	 */
	
	public Overworld getOverworld() {
		return overworld;
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
	
	public VictoryScreen getVictoryLevel() {
		return victoryLevel;
	}
	/**
	 * disposes the given level
	 * @param level - LevelDestination enum corresponds to a number according to the switch
	 */
	public void dispose(LevelDestination level) {
		int levelNumber = 0;
		switch(level) {
		case OVERWORLD:
			levelNumber = 0;
			break;
		case LVL_2:
			levelNumber = 1;
			break;
		case LVL_3:
			levelNumber = 2;
			break;
		case LVL_4:
			levelNumber = 3;
			break;
		case LVL_5:
			levelNumber = 4;
			break;
		case LVL_6:
			levelNumber = 5;
			break;
		case LVL_7:
			levelNumber = 6;
			break;
		case LVL_8:
			levelNumber = 7;
			break;
		case LVL_9:
			levelNumber = 8;
			break;
		case LVL_10:
			levelNumber = 9;
			break;

		}

	}
	/**
	 * disposes all levels
	 */
	public void disposeAll() {
		overworld.dispose();
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
		victoryLevel.dispose();
	}
	
}
