package com.battleship.pieces;

import com.battleship.player.Player;

public class Submarine extends Ship {

	public Submarine(Field field, Player player)
	{
	    super(3, field, player);
	}
	
	public Submarine() {
		super(3);
	}
	
	@Override
	public BattleType getType() {
		return BattleType.Submarine;
	}

}
