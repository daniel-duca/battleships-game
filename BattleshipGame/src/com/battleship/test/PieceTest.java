package com.battleship.test;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import com.battleship.pieces.BattleType;
import com.battleship.pieces.Battleship;
import com.battleship.pieces.Carrier;
import com.battleship.pieces.Cruiser;
import com.battleship.pieces.Destroyer;
import com.battleship.pieces.Ship;
import com.battleship.pieces.Submarine;

public class PieceTest {

	@Test
	@DisplayName("when cruiser type is valid")
	public void testCruiser() {
		Ship ship = new Cruiser();
		assertTrue("Cruiser not valid type", ship.getType() == BattleType.Cruiser);
	}
	
	@Test
	@DisplayName("when carrier type is valid")
	public void testCarrier() {
		Ship ship = new Carrier();
		assertTrue("Carrier not valid type", ship.getType() == BattleType.Carrier);
	}
	
	@Test
	@DisplayName("when destroyer type is valid")
	public void testDestroyer() {
		Ship ship = new Destroyer();
		assertTrue("Destroyer not valid type", ship.getType() == BattleType.Destroyer);
	}
	
	@Test
	@DisplayName("when submarine type is valid")
	public void testSubmarine() {
		Ship ship = new Submarine();
		assertTrue("Submarine not valid type", ship.getType() == BattleType.Submarine);
	}
	
	@Test
	@DisplayName("when battleship type is valid")
	public void testBattleship() {
		Ship ship = new Battleship();
		assertTrue("Battleship not valid type", ship.getType() == BattleType.Battleship);
	}
}
