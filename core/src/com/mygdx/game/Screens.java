package com.mygdx.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Game;

public class Screens {
	
	static Logger logger = LogManager.getLogger(Screens.class.getName());
	
	private static Game game;
	private static Screens instance;
	private static Credits credits;
	private static MainMenu mMenu;
	private static Settings settings;
	private static ControlsMenu controls;
	
	/**
	 * Used for containing instances of screens, and allowing for switching between them statically
	 * @param game instance of game
	 */
	public Screens(Game game) {
		Screens.game = game;
	}
	
	/**
	 * Switches the screen to menu
	 * @param menu instance of menu
	 */
	public static void toMenu(MainMenu menu) {
		mMenu = menu;
		logger.info("Screen set to menu.");
		game.setScreen(menu);
	}
	
	/**
	 * Switches screen to settings
	 * @param settings instance of settings
	 */
	public static void toSettings(Settings settings) {
		Screens.settings = settings;
		logger.info("Screen set to settings.");
		game.setScreen(settings);
	}
	
	/**
	 * Switches screen to credits
	 * @param credits instance of credits
	 */
	public static void toCredits(Credits credits) {
		Screens.credits = credits;
		logger.info("Screen set to credits.");
		game.setScreen(credits);
	}
	
	public static void toControls(ControlsMenu controls) {
		Screens.controls = controls;
		logger.info("Screen set to controls.");
		game.setScreen(controls);
	}
	
	/**
	 * Switches screen to map
	 */
	public static void toMap() {
		Map.getInstance().getAudioManager().playOverworld();
		logger.info("Screen set to map.");
		game.setScreen(Map.getInstance());
	}
	
	public static Game getGame() {
		return game;
	}
	
	public static MainMenu getMenu() {
		return mMenu;
	}
	
	public static Settings getSettings() {
		return settings;
	}
	
	public static Credits getCredits() {
		return credits;
	}
	
	public static ControlsMenu getControlMenu() {
		return controls;
	}
}
