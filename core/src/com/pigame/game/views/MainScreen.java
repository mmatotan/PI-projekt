package com.pigame.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.pigame.game.*;

public class MainScreen implements Screen, InputProcessor{
	
	final int TILE_SIZE = 16;
	
	private Game parent;
	Texture img;
	OrthographicCamera cam;
	TiledMap tiledMap;
	TiledMapRenderer tiledMapRenderer;

	public MainScreen(Game game) {
		parent = game;
	}	
	
	@Override
	public void show() {
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, w/2, h/2);
		cam.update();
		tiledMap = new TmxMapLoader().load("maps/MapGame.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		Gdx.input.setInputProcessor(this);
		
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		cam.update();
		tiledMapRenderer.setView(cam);
		tiledMapRenderer.render();
				
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

	
	@Override
	public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.LEFT)
            cam.translate(-TILE_SIZE,0);
        if(keycode == Input.Keys.RIGHT)
            cam.translate(TILE_SIZE,0);
        if(keycode == Input.Keys.DOWN)
            cam.translate(0,-TILE_SIZE);
        if(keycode == Input.Keys.UP)
            cam.translate(0,TILE_SIZE);
		return false;
	}
	

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}
	

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}
	

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}
	

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}
	

	@Override
	public boolean scrolled(float amountX, float amountY) {
		// TODO Auto-generated method stub
		return false;
	}

	

}
