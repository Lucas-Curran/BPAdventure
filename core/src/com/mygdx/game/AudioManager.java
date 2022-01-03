package com.mygdx.game;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class AudioManager {
		
		private HashMap<String, Music> music;
		Music caveMusic, menuMusic, overworldMusic, shopMusic;
			
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
	}
		
	/**
	 * Get specific music object from hashmap
	 * @param key
	 * @return
	 */
	public Music getMusic(String key) {
		return music.get(key);
	}
	
	public void stopAll() {
		for (java.util.Map.Entry<String, Music> set : music.entrySet()) {
			set.getValue().stop();
		}
	}
	
	public void playSong(String song) {
		if (!music.get(song).isPlaying()) {
			music.get(song).setVolume(0.1f);
			music.get(song).setLooping(true);
			music.get(song).play();
		}
	}
	
	public void playCave() {
		playSong("cave");
	}
	
	public void playMenu() {
		playSong("menu");
	}
	
	public void playShop() {
		playSong("shop");
	}
	
	public void playOverworld() {
		playSong("overworld");
	}

}
