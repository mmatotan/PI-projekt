package com.pigame.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
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
import com.pigame.game.renders.OrthogonalTiledMapRendererWithSprites;

public class MainScreen implements Screen, InputProcessor{
	
	public final int TILE_SIZE = 16;
	public final int MAP_SIZE_X = 40;
	public final int MAP_SIZE_Y = 60;
	public final int CAM_WIDTH_TILES = 21;
	public final int CAM_HEIGHT_TILES = 13;
	
	private Game parent;
	private Stage stage;
	
	Texture img;
	OrthographicCamera cam;
	TiledMap tiledMap;
	OrthogonalTiledMapRendererWithSprites tiledMapRenderer;
	
	Sprite sprite;
	Texture texture;
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
		Gdx.input.setInputProcessor(this);
				
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		tiledMap = new TmxMapLoader().load("maps/MapGame.tmx"); //Loading of the .tmx file
		int mapWidth = tiledMap.getProperties().get("width", Integer.class);
		int mapHeight = tiledMap.getProperties().get("height", Integer.class);
		
		cam = new OrthographicCamera();
		//Setting the camera to be zoomed in, too zoomed out otherwise
		cam.setToOrtho(false, CAM_WIDTH_TILES * TILE_SIZE, CAM_HEIGHT_TILES * TILE_SIZE);
		// Looks odd but the camera now displays 20 tiles in width, and 12 in height
		cam.translate(mapWidth/4 * TILE_SIZE, 0); //Sets the camera to the middle of map(x axis)
		cam.update();
		
		player = new Player(mapWidth/2 * TILE_SIZE, 6 * TILE_SIZE); //Init of player class in middle of screen
		texture = new Texture(Gdx.files.internal("dungeon_assets/frames/wizzard_m_idle_anim_f0.png"));
		sprite = new Sprite(texture);
		sprite.setPosition(player.getX(), player.getY());
		
		tiledMapRenderer = new OrthogonalTiledMapRendererWithSprites(tiledMap); //Loading the map as orthogonal(bird view)
		tiledMapRenderer.addSprite(sprite);
		tiledMapRenderer.render();
		
		addBars(w, h);
	}
	
	private void addBars(float w, float h) {
		//Classic stage implementation for HUD(HP and Mana display)
		Table table = new Table();
		table.setFillParent(true);
		table.setPosition(w/2 - 5 * TILE_SIZE, h/2 - 2 * TILE_SIZE, TILE_SIZE);
		stage.addActor(table);
    

  	//Define the skin being used
		Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		createPauseWindow(skin);
    //Create progress bars starting from zero all the way to the maximum defined number with a step of 1
		HPBar = new ProgressBar(0, player.getMaxHP(), 1, false, skin);
		ManaBar = new ProgressBar(0, player.getMaxMana(), 1, false, skin);
		ManaBar.setColor(0, 0, 1, 1); //Blue
		
		//Add the bars to the table
		table.add(HPBar);
		table.row().pad(10, 0, 10, 0);
		table.add(ManaBar);
	}
  
  public void createPauseWindow(Skin skin){
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
	
		Gdx.gl.glClearColor(1f, 0f, 0f, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		cam.update();
		tiledMapRenderer.setView(cam);
		tiledMapRenderer.render();
		
		//Change color to red if HP lower than 50
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
        if(keycode == Input.Keys.LEFT) {
        	moveLeft(player, cam);
        }
        if(keycode == Input.Keys.RIGHT) {
        	moveRight(player, cam);
        }
        if(keycode == Input.Keys.UP) {
        	moveUp(player, cam);
        }
        if(keycode == Input.Keys.DOWN) {
        	moveDown(player, cam);
        }
        
        sprite.setPosition(player.getX(), player.getY());
		return false;
	}
	
	public void moveLeft(Player player, OrthographicCamera cam) {
    	if(player.getX() > 0) {
    		if(!sprite.isFlipX()) {
        		sprite.flip(true, false);
    		}
    		player.setX(player.getX()-TILE_SIZE);
    		cam.translate(-TILE_SIZE,0);
    	}
	}
	
	public void moveRight(Player player, OrthographicCamera cam) {
    	if(player.getX() < (MAP_SIZE_X - 1)*TILE_SIZE) {
    		if(sprite.isFlipX()) {
        		sprite.flip(true, false);
    		}
    		cam.translate(TILE_SIZE,0);
    		player.setX(player.getX()+TILE_SIZE);
    	}
	}
	
	public void moveUp(Player player, OrthographicCamera cam) {
    	if(player.getY() < (MAP_SIZE_Y - 1)*TILE_SIZE) {
    		cam.translate(0,TILE_SIZE);
    		player.setY(player.getY()+TILE_SIZE);
    	}
	}
	
	public void moveDown(Player player, OrthographicCamera cam) {
    	if(player.getY() > 0) {
    		cam.translate(0,-TILE_SIZE);
    		player.setY(player.getY()-TILE_SIZE);
    	}
	}
	

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}
	

	@Override
	public boolean keyTyped(char character) {
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
