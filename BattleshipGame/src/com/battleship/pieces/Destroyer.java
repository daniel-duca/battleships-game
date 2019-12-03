package com.battleship.pieces;

public class Destroyer extends Ship {
	
	public Destroyer()
	{
	    super(6);
	}
	
	@Override
	public BattleType getType() {
		return BattleType.Destroyer;
	}
	
	
}
