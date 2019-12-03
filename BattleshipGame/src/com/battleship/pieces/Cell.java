package com.battleship.pieces;

import com.battleship.player.Player;
import com.battleship.utils.Effects;

public abstract class Cell implements Cloneable{
	/*
	 * Cell represents the info about the ownership and the status of the virtual location on board
	 * for example: who is the ship that located there belongs to and if it sunken or not
	 */
	Player player;
	protected final Effects effects = new Effects();
	
	public Cell(Player player) {
		this.player = player;
	}
	
	public Cell() {
		
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public Ship clone()throws CloneNotSupportedException{  
		return (Ship) super.clone();  
	} 
	
	abstract public Cell placeShip(Field field, Player player);
	
	abstract public boolean sunked();

	abstract public void shot();
}
