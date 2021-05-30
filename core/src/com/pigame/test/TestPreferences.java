package com.pigame.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.pigame.game.Game;

public class TestPreferences {

	private static final String PREF_MUSIC_VOLUME = "volume";
	private static final String PREF_MUSIC_ENABLED = "music.enabled";
	private static final String PREF_SOUND_VOL = "sound";
	private static final String PREF_SOUND_ENABLED = "sound.enabled";
	private static final String PREFS_NAME = "prefs";
	
	private HeadlessApplicationConfiguration cfg;
	HeadlessApplication app;
	Game game;
	
	float n = 0.23f;
	boolean b = true;
	
	@Before
	public void init() {
		this.cfg = new HeadlessApplicationConfiguration();
		this.game = new Game();
		this.app = new HeadlessApplication(game, cfg);
		
		this.game.createPreferences();
	}
	
	@Test
	public void testSoundVolume() {
		this.game.getPreferences().setSoundVolume(n);
		Assert.assertEquals(n, Gdx.app.getPreferences(PREFS_NAME).getFloat(PREF_SOUND_VOL), 0.001);
	}
	
	@Test
	public void testSoundEnable() {
		this.game.getPreferences().setSoundEffectsEnabled(b);
		Assert.assertEquals(b, Gdx.app.getPreferences(PREFS_NAME).getBoolean(PREF_SOUND_ENABLED));
	}
	
	@Test
	public void testMusicVolume() {
		this.game.getPreferences().setMusicVolume(n);
		Assert.assertEquals(n, Gdx.app.getPreferences(PREFS_NAME).getFloat(PREF_MUSIC_VOLUME), 0.001);
	}
	
	@Test
	public void testMusicEnable() {
		this.game.getPreferences().setMusicEnabled(b);
		Assert.assertEquals(b, Gdx.app.getPreferences(PREFS_NAME).getBoolean(PREF_SOUND_ENABLED));
	}

}
