package com.battleship.pieces;
import com.battleship.player.Player;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.params.converter.ArgumentConversionException;

public class ShipFactory {

	static Map<BattleType, Cell> ships = new HashMap<BattleType, Cell>(){{
	    put(BattleType.Battleship, new Battleship());
	    put(BattleType.Carrier, new Carrier());
	    put(BattleType.Cruiser, new Cruiser());
	    put(BattleType.Destroyer, new Destroyer());
	    put(BattleType.Submarine, new Submarine());
	}};
	
	public static Cell createShip(BattleType type, Field field, Player player) {
		try {
			return ships.get(type).placeShip(field, player).clone();
		} catch (CloneNotSupportedException e) {
			throw new ArgumentConversionException("");
		}
	}
}
