package com.pigame.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TiledGameMap extends GameMap {
	
	TiledMap tiledMap;
	OrthogonalTiledMapRenderer tiledMapRenderer; 
	
	public TiledGameMap () {
		tiledMap = new TmxMapLoader().load("MapGame.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
	}
	
	@Override
	public void render(OrthographicCamera camera) {
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
	}
	
	
	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
		tiledMap.dispose();
	}


	@Override
	public TileType getTileTypeByCoordinate(int layer, int col, int row) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int getLayers() {
		// TODO Auto-generated method stub
		return 0;
	}

}

