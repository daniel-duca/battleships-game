package com.battleship.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.battleship.pieces.BattleType;
import com.battleship.pieces.Cell;
import com.battleship.pieces.Field;
import com.battleship.pieces.Ship;
import com.battleship.pieces.ShipFactory;
import com.battleship.player.HumanPlayer;

public class ShipFactoryTest {

	@Test
	@DisplayName("when create ship from factory")
	public void testShot() {
		Cell cell = ShipFactory.createShip(BattleType.Battleship.Carrier, new Field(4,5,true), new HumanPlayer());
		assertTrue(cell instanceof Ship);
	}
}
