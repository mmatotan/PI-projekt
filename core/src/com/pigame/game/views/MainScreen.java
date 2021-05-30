package com.pigame.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.pigame.game.*;
import com.pigame.game.player.Player;

public class MainScreen implements Screen, InputProcessor{
	
	final int TILE_SIZE = 16;
	
	private Game parent;
	private Stage stage;
	
	Texture img;
	OrthographicCamera cam;
	TiledMap tiledMap;
	TiledMapRenderer tiledMapRenderer;
	
	Player player;
	ProgressBar HPBar;
	ProgressBar ManaBar;
	Window pauseWindow;
	
	
	boolean isPaused = false;
	
	public MainScreen(Game game) {
		parent = game;
		
		stage = new Stage(new ScreenViewport());
	}	
	
	
	@Override
	public void show() {
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, w/2, h/2); //Setting the camera to be zoomed in, too zoomed out otherwise
		cam.update();
		tiledMap = new TmxMapLoader().load("maps/MapGame.tmx"); //Loading of the .tmx file
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap); //Loading the map as orthogonal(bird view)
		Gdx.input.setInputProcessor(this);
		
		player = new Player(); //Init of player class
		
		//Classic stage implementation for HUD(HP and Mana display)
		Table table = new Table();
		table.setFillParent(true);
		table.setPosition(w/2 - 5 * TILE_SIZE, h/2 - 2 * TILE_SIZE, TILE_SIZE);
		stage.addActor(table);
		
		Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		
		// Pause window
		pauseWindow = new Window("PAUSE", skin);
		TextButton continueButton = new TextButton("Continue", skin);
		continueButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				resume();
			}
		});
		pauseWindow.padTop(64);
		pauseWindow.add(continueButton);
		pauseWindow.setSize(stage.getWidth() / 1.5f, stage.getHeight() / 1.5f);
		pauseWindow.setPosition(stage.getWidth() / 2 - pauseWindow.getWidth() / 2, stage.getHeight() / 2 - pauseWindow.getHeight() / 2);
		
		HPBar = new ProgressBar(0, player.getMaxHP(), 1, false, skin);
		ManaBar = new ProgressBar(0, player.getMaxMana(), 1, false, skin);
		ManaBar.setColor(0, 0, 1, 1); //Blue
		
		table.add(HPBar);
		table.row().pad(10, 0, 10, 0);
		table.add(ManaBar);
		
	}

	@Override
	public void render(float delta) {
		if(!isPaused) {
			Gdx.gl.glClearColor(0f, 0f, 0f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			cam.update();
			tiledMapRenderer.setView(cam);
			tiledMapRenderer.render();
		} else {
			//don't update, wait for resume
			stage.addActor(pauseWindow);
			if(Gdx.input.isKeyPressed(Keys.SPACE)) {
				if(isPaused) {
					resume();
				}
			}
		}
			
		if(Gdx.input.isKeyPressed(Keys.SPACE)) {
			if(!isPaused) {
				pause();
			}
		}
		//Change color to red if HP lower than 20
		if(player.getHP() > 50) {
			HPBar.setColor(0, 1, 0, 1);
		} else {
			HPBar.setColor(1, 0, 0, 1);
		}
			
		//Update values in the progress bars
		HPBar.setValue(player.getHP());
		ManaBar.setValue(player.getMana());
		
		stage.act();
		stage.draw();
				
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		isPaused = true;
	}

	@Override
	public void resume() {
		isPaused = false;
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	
	@Override
	public boolean keyDown(int keycode) {
		//Movement, temporarily moves the map around, should move the sprite of the main character
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
