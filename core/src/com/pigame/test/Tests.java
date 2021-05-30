package com.pigame.test;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.pigame.game.Game;
import com.pigame.game.player.Player;
import com.pigame.game.views.MainScreen;

public class Tests {
	
	private HeadlessApplicationConfiguration cfg;
	HeadlessApplication app;
	Game game;
	
	@Before
	public void init() {
		this.cfg = new HeadlessApplicationConfiguration();
		this.game = new Game();
		this.app = new HeadlessApplication(game, cfg);
		
		this.game.createPreferences();
	}
	
	@Test
	public void leftBorderTest() {
		//MainScreen mainScreen = mock(MainScreen.class);
		//OrthographicCamera cam = mock(OrthographicCamera.class);
		//Player player = new Player(0, 0);
		
		//mainScreen.moveLeft(player, cam);
		//System.out.println(player.getX());
	}
	
}
