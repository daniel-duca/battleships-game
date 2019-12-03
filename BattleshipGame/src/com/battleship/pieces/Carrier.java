package com.battleship.pieces;

public class Carrier extends Ship {

	public Carrier()
	{
	    super(5);
	}
	
	@Override
	public BattleType getType() {
		return BattleType.Carrier;
	}
	
}
