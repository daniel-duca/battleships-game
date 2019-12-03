package com.battleship.pieces;

public class Cruiser extends Ship {
	
	public Cruiser() {
		super(2);
	}
	
	@Override
	public BattleType getType() {
		return BattleType.Cruiser;
	}
	
}
