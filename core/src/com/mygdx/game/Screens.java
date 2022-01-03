package com.mygdx.game;

import com.badlogic.gdx.Game;

public class Screens {
	
	private static Game game;
	private static Screens instance;
	private static MainMenu mMenu;
	
	public Screens(Game game) {
		Screens.game = game;
	}
	
	public static void toMenu(MainMenu menu) {
		mMenu = menu;
		game.setScreen(menu);
	}
	
	public static void toMap() {
		game.setScreen(Map.getInstance());
	}
	
//	public static void toHUD(PlayerHUD playerHUD) {
//		game.setScreen(playerHUD);
//	}
//	
	public static Game getGame() {
		return game;
	}
	
	public static MainMenu getMenu() {
		return mMenu;
	}
	
}
