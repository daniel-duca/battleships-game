package com.battleship.pieces;

public class Battleship extends Ship{
	
	public Battleship() {
		super(4);
	}
		
	@Override
	public BattleType getType() {
		return BattleType.Battleship;
	}

}
