package com.pigame.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.pigame.game.*;
import com.pigame.game.player.Player;
import com.pigame.game.renders.OrthogonalTiledMapRendererWithSprites;

public class MainScreen implements Screen, InputProcessor{
	
	final int TILE_SIZE = 16;
	final int MAP_SIZE_X = 40;
	final int MAP_SIZE_Y = 60;
	final int CAM_WIDTH_TILES = 21;
	final int CAM_HEIGHT_TILES = 13;
	
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
		
		//Classic stage implementation for HUD(HP and Mana display)
		Table table = new Table();
		table.setFillParent(true);
		table.setPosition(w/2 - 5 * TILE_SIZE, h/2 - 2 * TILE_SIZE, TILE_SIZE);
		stage.addActor(table);
		
		Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		
		HPBar = new ProgressBar(0, player.getMaxHP(), 1, false, skin);
		ManaBar = new ProgressBar(0, player.getMaxMana(), 1, false, skin);
		ManaBar.setColor(0, 0, 1, 1); //Blue
		
		table.add(HPBar);
		table.row().pad(10, 0, 10, 0);
		table.add(ManaBar);
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1f, 0f, 0f, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		cam.update();
		tiledMapRenderer.setView(cam);
		tiledMapRenderer.render();
		
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
		stage.dispose();
	}

	
	@Override
	public boolean keyDown(int keycode) {
		//Movement, temporarily moves the map around, should move the sprite of the main character
        if(keycode == Input.Keys.LEFT) {
        	if(player.getX() > 0) {
        		if(!sprite.isFlipX()) {
            		sprite.flip(true, false);
        		}
        		player.setX(player.getX()-TILE_SIZE);
        		cam.translate(-TILE_SIZE,0);
        	}
        }
        if(keycode == Input.Keys.RIGHT) {
        	if(player.getX() < (MAP_SIZE_X - 1)*TILE_SIZE) {
        		if(sprite.isFlipX()) {
            		sprite.flip(true, false);
        		}
        		cam.translate(TILE_SIZE,0);
        		player.setX(player.getX()+TILE_SIZE);
        	}
        }
        if(keycode == Input.Keys.DOWN) {
        	if(player.getY() > 0) {
        		cam.translate(0,-TILE_SIZE);
        		player.setY(player.getY()-TILE_SIZE);
        	}
        }
        if(keycode == Input.Keys.UP) {
        	if(player.getY() < (MAP_SIZE_Y - 1)*TILE_SIZE) {
        		cam.translate(0,TILE_SIZE);
        		player.setY(player.getY()+TILE_SIZE);
        	}
        }
        sprite.setPosition(player.getX(), player.getY());
		return false;
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
