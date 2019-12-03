package com.battleship.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Point;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.battleship.engine.BattleGame;
import com.battleship.gui.GameUI;
import com.battleship.model.BattleShipModel;
import com.battleship.pieces.Cruiser;
import com.battleship.pieces.Field;
import com.battleship.pieces.Ship;
import com.battleship.player.HumanPlayer;
import com.battleship.player.Player;


public class PlayerMoveTest {

	BattleGame controller;
	BattleShipModel model;
	Player player = null;
	Point point = new Point(10,  14);
	Ship cell;
	
	public PlayerMoveTest() {
		
	}
	
	@BeforeEach
	public void eachTest() {
		model = new BattleShipModel();
		GameUI view = new GameUI(model);

		controller = new BattleGame(view, model);
		
		view.setGameListener(controller);
		
		controller.fillEmptyBoard();
		Field field = new Field((int)point.getX(),(int)point.getY(),true);
		controller.setShip(new Cruiser(), field, new HumanPlayer());
		cell = (Ship)model.getCurrentCell((int)point.getX(), (int)point.getY());
		player = cell.getPlayer();
		player.setSelectCellToHit(point);
	}
	
	@Test
	@DisplayName("when player move left")
	public void testMoveLeft() {
		cell.getPlayer().moveLeft(controller);
		int expected = (int)point.getX()-1;
		assertTrue(cell.getField().getX() == expected);
	}
	
	@Test
	@DisplayName("when player move right")
	public void testMoveRight() {
		cell.getPlayer().moveRight(controller);
		int expected = (int)point.getX()+cell.getShipParts();
		assertTrue(cell.getField().getX() == expected);
	}
	
	@Test
	@DisplayName("when player rotate right")
	public void testRotateRight() {
		cell.getPlayer().rotateRight(controller);
		assertTrue(cell.getField().getX() == (int)point.getX()+1);
		assertTrue(cell.getField().getY() == (int)point.getY()-1);
	}
	
	@Test
	@DisplayName("when player rotate left")
	public void testRotateLeft() {
		cell.getPlayer().rotateLeft(controller);
		assertTrue(cell.getField().getX() == (int)point.getX()-1);
		assertTrue(cell.getField().getY() == (int)point.getY());
	}
	
}
