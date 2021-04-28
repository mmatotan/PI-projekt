package com.pigame.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
//
import com.badlogic.gdx.graphics.OrthographicCamera;
//

import com.pigame.game.*;

public class MainScreen implements Screen{
	
	private Game parent;

	public MainScreen(Game game) {
		parent = game;
	}
	
	//NEWW
	OrthographicCamera cam;
	
	GameMap gameMap;
	//
	
	
	@Override
	public void show() {
		//
		cam = new OrthographicCamera();
		cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.update();
		
		gameMap = new TiledGameMap();
		//
	}

	@Override
	public void render(float delta) {
		//
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (Gdx.input.isTouched()) {
			cam.translate(-Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
			cam.update();
		}
		
		gameMap.render(cam);
		//
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
