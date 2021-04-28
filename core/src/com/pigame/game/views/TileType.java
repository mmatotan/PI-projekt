package com.pigame.game.views;

import java.util.HashMap;

public enum TileType {
	
	GRASS(1, false, "Grass"),
	PATH(2, false, "Path"),
	TREE(3, true, "Tree"),
	TREETOP(4, true, "TreeTop"),
	DOOR(5, true, "Door"),
	WALL(6, true, "Wall");
	
	public static final int TILE_SIZE = 16;//nes
	
	
	private int id;
	private boolean collidable;
	private String name;
	private float damage; //vjv maknut
	
	private TileType (int id, boolean collidable, String name) {
		this(id, collidable, name, 0);
	}
	
	private TileType (int id, boolean collidable, String name, float damage) {
		this.id = id;
		this.collidable = collidable;
		this.name = name;
		this.damage = damage;
	}

	
	public int getId() {
		return id;
	}

	public boolean isCollidable() {
		return collidable;
	}

	public String getName() {
		return name;
	}
    //maknut...
	public float getDamage() {
		return damage;
	}

	private static HashMap<Integer, TileType> tileMap;
	
	static {
		for (TileType tileType : TileType.values()) {
			tileMap.put(tileType.getId(), tileType);
		}
	}
	
	public static TileType getTileTypeById (int id) {
		return tileMap.get(id);
	}
	
}
