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
	
	public Screens(Game game) {
		Screens.game = game;
	}
	
	public static void toMenu(MainMenu menu) {
		mMenu = menu;
		logger.info("Screen set to menu.");
		game.setScreen(menu);
	}
	
	public static void toSettings(Settings settings) {
		Screens.settings = settings;
		logger.info("Screen set to settings.");
		game.setScreen(settings);
	}
	
	public static void toCredits(Credits credits) {
		Screens.credits = credits;
		logger.info("Screen set to credits.");
		game.setScreen(credits);
	}
	
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
}
