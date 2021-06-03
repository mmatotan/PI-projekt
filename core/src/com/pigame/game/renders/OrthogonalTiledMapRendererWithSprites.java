package com.pigame.game.renders;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class OrthogonalTiledMapRendererWithSprites extends OrthogonalTiledMapRenderer {
	
	private Sprite sprite;
	private List<Sprite> sprites;
	private int drawSpritesAfterLayer = 3;
	
	public OrthogonalTiledMapRendererWithSprites(TiledMap map) {
		super(map);
		sprites = new ArrayList<Sprite>();
	}
	
	public void addSprite(Sprite sprite) {
		sprites.add(sprite);
	}
	
    @Override
    public void render() {
    	//Start rendering
        beginRender();
        //For counting layers
        int currentLayer = 0;
        for (MapLayer layer : map.getLayers()) {
        	//We can decide to hide a certain layer
            if (layer.isVisible()) {
            	//Render the map layer and if the next layer is a sprite, render the sprite afterwards
                if (layer instanceof TiledMapTileLayer) {
                    renderTileLayer((TiledMapTileLayer)layer);
                    currentLayer++;
                    if(currentLayer == drawSpritesAfterLayer){
                        for(Sprite sprite : sprites)
                            sprite.draw(this.getBatch());
                    }
                } else {
                    for (MapObject object : layer.getObjects()) {
                        renderObject(object);
                    }
                }
            }
        }
        endRender();
    }
	
}
