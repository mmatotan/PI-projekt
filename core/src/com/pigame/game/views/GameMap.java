package com.pigame.game.views;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class GameMap {

	public abstract void render (OrthographicCamera camera);
	public abstract void update (float delta);
	public abstract void dispose ();
	
	
	public TileType getTileTypeByLocation(int layer, float x, float y) {
		return this.getTileTypeByCoordinate(layer, (int) (x/TileType.TILE_SIZE), (int) (y/TileType.TILE_SIZE));
	}
	
	public abstract TileType getTileTypeByCoordinate(int layer, int col, int row);
	
	public abstract int getWidth();
	public abstract int getHeight();
	public abstract int getLayers();

}
