package com.mygdx.game;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {
		
		private static HashMap<String, Music> music;
		private static HashMap<String, Sound> sound;
		Music caveMusic, menuMusic, overworldMusic, shopMusic;
		Sound buttonPress, enemyDeath, playerDeath, swordJab;
		SqliteManager sm;
		float volume;
			
	/**
	 * Creates hashmap of music objects
	 */
	public AudioManager() {
		music = new HashMap<String,Music>();
		sound = new HashMap<String, Sound>();
		sm = new SqliteManager();
		
		caveMusic = Gdx.audio.newMusic(Gdx.files.internal("tracks/cave_track.wav"));
		shopMusic = Gdx.audio.newMusic(Gdx.files.internal("tracks/shop_track.wav"));
		menuMusic = Gdx.audio.newMusic(Gdx.files.internal("tracks/menu_track.wav"));
		overworldMusic = Gdx.audio.newMusic(Gdx.files.internal("tracks/overworld_track.wav"));
		
		music.put("cave", caveMusic);
		music.put("shop", shopMusic);
		music.put("menu", menuMusic);
		music.put("overworld", overworldMusic);
		
		buttonPress = Gdx.audio.newSound(Gdx.files.internal("sfx/button_click.mp3"));
		enemyDeath = Gdx.audio.newSound(Gdx.files.internal("sfx/enemy_death.mp3"));
		playerDeath = Gdx.audio.newSound(Gdx.files.internal("sfx/player_death.mp3"));
		swordJab = Gdx.audio.newSound(Gdx.files.internal("sfx/sword_jab.mp3"));
		
		sound.put("button", buttonPress);
		sound.put("enemy", enemyDeath);
		sound.put("player", playerDeath);
		sound.put("sword", swordJab);
		
		volume = sm.getVolume();
		
	}
		
	/**
	 * Get specific music object from hashmap
	 * @param key
	 * @return
	 */
	public Music getMusic(String key) {
		return music.get(key);
	}
	
	/**
	 * Stop all music
	 */
	public void stopAll() {
		for (java.util.Map.Entry<String, Music> set : music.entrySet()) {
			set.getValue().stop();
		}
	}
	
	/**
	 * Update all music
	 */
	public void updateAll() {
		for (java.util.Map.Entry<String, Music> set : music.entrySet()) {
			volume = sm.getVolume();
			set.getValue().setVolume(volume / 100);
		}
	}
	
	/**
	 * Play certain song
	 * @param song - Song played
	 */
	public void playSong(String song) {
		if (!music.get(song).isPlaying()) {
			music.get(song).setVolume(volume / 100);
			music.get(song).setLooping(true);
			music.get(song).play();
		}
	}
	
	/**
	 * Play a certain sound effect
	 * @param sfx - Sound played
	 */
	public void playSFX(String sfx) {
		sound.get(sfx).play(volume / 100);
	}
	
	/**
	 * Plays cave music
	 */
	public void playCave() {
		playSong("cave");
	}
	
	/**
	 * Plays menu music
	 */
	public void playMenu() {
		playSong("menu");
	}
	
	/**
	 * Plays shop music
	 */
	public void playShop() {
		playSong("shop");
	}
	
	/**
	 * Player overworld music
	 */
	public void playOverworld() {
		playSong("overworld");
	}
	
	
	/**
	 * Play button click
	 */
	public void playButton() {
		playSFX("button");
	}
	
	/**
	 * Play enemy death sound
	 */
	public void playEnemyDeath() {
		playSFX("enemy");
	}
	
	/**
	 * Play player death sound
	 */
	public void playPlayerDeath() {
		playSFX("player");
	}

	
	/**
	 * Play sword jab sound
	 */
	public void playSwordJab() {
		playSFX("sword");
	}
}
