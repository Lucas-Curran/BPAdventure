package com.mygdx.game;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class AudioManager {
		
		private HashMap<String, Music> music;
		Music caveMusic, menuMusic, overworldMusic, shopMusic;
		SqliteManager sm = new SqliteManager();
		float volume;
			
	/**
	 * Creates hashmap of music objects
	 */
	public AudioManager() {
		music = new HashMap<String,Music>();
		
		caveMusic = Gdx.audio.newMusic(Gdx.files.internal("tracks/cave_track.wav"));
		shopMusic = Gdx.audio.newMusic(Gdx.files.internal("tracks/shop_track.wav"));
		menuMusic = Gdx.audio.newMusic(Gdx.files.internal("tracks/menu_track.wav"));
		overworldMusic = Gdx.audio.newMusic(Gdx.files.internal("tracks/overworld_track.wav"));
		
		music.put("cave", caveMusic);
		music.put("shop", shopMusic);
		music.put("menu", menuMusic);
		music.put("overworld", overworldMusic);
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
			set.getValue().stop();
			System.out.println(volume);
		}

	}
	
	/**
	 * Play certain song
	 * @param song - Song played
	 */
	public void playSong(String song) {
		if (!music.get(song).isPlaying()) {
			music.get(song).setVolume(volume);
			music.get(song).setLooping(true);
			music.get(song).play();
		}
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

}
