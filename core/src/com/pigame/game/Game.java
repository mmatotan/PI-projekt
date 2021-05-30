package com.pigame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.pigame.game.views.*;

public class Game extends com.badlogic.gdx.Game {
	
	private LoadingScreen loadingScreen;
	private PreferencesScreen preferencesScreen;
	private MenuScreen menuScreen;
	private MainScreen mainScreen;
	private EndScreen endScreen;
	
	private AppPreferences preferences;
	
	public final static int MENU = 0;
	public final static int PREFERENCES = 1;
	public final static int APPLICATION = 2;
	public final static int ENDGAME = 3;
	
	private Music music;
	private Sound sound;
	
	//Method for easily changing screens
	public void changeScreen(int screen) {
		switch(screen) {
			case MENU:
				if(menuScreen == null) menuScreen = new MenuScreen(this);
				this.setScreen(menuScreen);
				break;
			case PREFERENCES:
				if(preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
				this.setScreen(preferencesScreen);
				break;
			case APPLICATION:
				if(mainScreen == null) mainScreen = new MainScreen(this);
				this.setScreen(mainScreen);
				break;
			case ENDGAME:
				if(endScreen == null) endScreen = new EndScreen(this);
				this.setScreen(endScreen);
				break;
		}
	}

	//Creation leads to a loading screen which forwards itself to the main menu screen
	@Override
	public void create() {
		preferences = new AppPreferences();
		
		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);
		
		music = Gdx.audio.newMusic(Gdx.files.internal("ElevatorMusic.mp3"));
		music.setLooping(true);
		if(getPreferences().isMusicEnabled()) {
			music.setVolume(getPreferences().getMusicVolume());
			music.play();
		}
		
		sound = Gdx.audio.newSound(Gdx.files.internal("soundEffect.ogg"));
		if(getPreferences().isSoundEffectsEnabled()) {
			if(Gdx.input.isKeyPressed(Keys.ANY_KEY)) {
				long soundId = sound.play(getPreferences().getSoundVolume());
				sound.setLooping(soundId, false);
				sound.setPitch(soundId, 2);
			}
		}
	}
	
	
	//Fetch preferences for this game
	public AppPreferences getPreferences() {
		return this.preferences;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		music.dispose();
		sound.dispose();
	}
}
