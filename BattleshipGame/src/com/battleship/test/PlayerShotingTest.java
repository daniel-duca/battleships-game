package com.battleship.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.awt.Point;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.battleship.engine.BattleGame;
import com.battleship.gui.GameUI;
import com.battleship.model.BattleShipModel;
import com.battleship.pieces.Cruiser;
import com.battleship.pieces.Destroyer;
import com.battleship.pieces.Field;
import com.battleship.pieces.Ship;
import com.battleship.player.AIPlayer;
import com.battleship.player.HumanPlayer;
import com.battleship.player.Player;

public class PlayerShotingTest {

	BattleGame controller;
	BattleShipModel model;
	Player human = null;
	Player opponent = null;
	Point point = new Point(13,  14);
	Point point2 = new Point(14, 13);
	Ship cell;
	
	public PlayerShotingTest() {
		
	}
	
	@BeforeEach
	public void eachTest() {
		model = new BattleShipModel();
		GameUI view = new GameUI(model);

		controller = new BattleGame(view, model);
		
		view.setGameListener(controller);
		
		controller.fillEmptyBoard();
		Field field = new Field((int)point.getX(),(int)point.getY(),true);
		Field field2 = new Field((int)point2.getX(),(int)point2.getY(),true);
		controller.setShip(new Cruiser(), field, new HumanPlayer());
		controller.setShip(new Destroyer(), field2, new AIPlayer());
		cell = (Ship)model.getCurrentCell((int)point2.getX(), (int)point2.getY());
		opponent = cell.getPlayer();
		opponent.setSelectCellToHit(point2);
	}
	
	@Test
	@DisplayName("when player shot opponent")
	public void testShot() {
		controller.ShotTarget(opponent);
		assertEquals(5, cell.getShipParts());
	}
	
	@Test
	@DisplayName("when player win")
	public void testWin() {
		int len = cell.getShipParts();
		for (int i = 0; i < len; i++) {
			controller.ShotTarget(opponent);
		}
		
		assertEquals(0, cell.getShipParts());
	}
}
