package com.pigame.game;

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
		createPreferences();
		
		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);
	}
	
	public void createPreferences() {
		preferences = new AppPreferences();
	}

	//Fetch preferences for this game
	public AppPreferences getPreferences() {
		return this.preferences;	
	}
}
