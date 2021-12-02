package com.mygdx.game;

import com.badlogic.gdx.Game;

public class Screens {
	
	private static Game game;
	private static Screens instance;
	
	public Screens(Game game) {
		Screens.game = game;
	}
	
	public static void toMap(Map map) {
		game.setScreen(map);
	}
	
	public static void toHUD(PlayerHUD playerHUD) {
		game.setScreen(playerHUD);
	}
	
	public Game getGame() {
		return game;
	}
	
}
