package com.pigame.game.player;

public class Player {
	
	//Main stats, X&Y are coordinates on a map
	private int HP;
	private int maxHP;
	private int mana;
	private int maxMana;
	private int X;
	private int Y;
	
	public Player(int x, int y){
		this.maxHP = 100;
		this.HP = 100;
		this.maxMana = 100;
		this.mana = 0;
		// Set player to the middle of the screen
		this.X = x;
		this.Y = y;
	}
	
	public int getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}
	
	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public int getMaxMana() {
		return maxMana;
	}

	public void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
	}

	
	void reduce_hp(int damage) {
		this.HP -= damage;
	}
	
	void increase_hp(int increase) {
		this.HP += increase;
		if(this.HP > 100) this.HP = 100;
	}

	public int getHP() {
		return HP;
	}

	public void setHP(int hP) {
		HP = hP;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}
}
