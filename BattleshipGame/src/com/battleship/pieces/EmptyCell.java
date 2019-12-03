package com.battleship.pieces;

import com.battleship.player.Player;

public class EmptyCell extends Cell {
	Field field;
	
	public EmptyCell() {
		field = new Field(0,0,true);
	}

	@Override
	public boolean sunked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void shot() {
		effects.splash();		
	}
	
	@Override
	public final Cell placeShip(Field field, Player player) {
		return this;
	}

}
