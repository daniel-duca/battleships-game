package com.battleship.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Point;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.battleship.engine.BattleGame;
import com.battleship.gui.BoardPanel;
import com.battleship.gui.GameUI;
import com.battleship.model.BattleShipModel;
import com.battleship.pieces.EmptyCell;


@TestInstance(Lifecycle.PER_CLASS)
public class BattleGameTest {

	BattleGame controller;
	BattleShipModel model;
	
	public BattleGameTest() {
		System.out.println("Before instance");
		model = new BattleShipModel();
		GameUI view = new GameUI(model);

		controller = new BattleGame(view, model);
		
		view.setGameListener(controller);
	}
	
	@BeforeEach
	public void init() {
		System.out.println("Before init");
	}
	
	@Test
	@DisplayName("when fill empty cells in board")
	public void testEmptyBoard() {
		controller.fillEmptyBoard();
		controller.generateShips();
		controller.updateAllShipsLocationAndHealth();

		assertAll("Should return empty cells",
				() -> Assert.assertTrue(controller.getModel().getCurrentCell(0, 0) instanceof EmptyCell),
				() -> Assert.assertTrue(controller.getModel().getCurrentCell(BoardPanel.COL_COUNT, BoardPanel.ROW_COUNT) instanceof EmptyCell)
		);
	}
	
	@Test
	@DisplayName("when human part ship missing")
	public void testHumanPartShips() {
		assertEquals(20, controller.humanPlayerShipsLocations.size());
	}
	
	@Test
	@DisplayName("when computer part ship missing")
	public void testComputerPartShips() {
		assertEquals(20, controller.aiPlayerShipsLocations.size());
	}
	
	@Test
	@DisplayName("when game not over")
	public void testGameNotOver() {
		assertFalse(controller.inGameOver());
	}
	
	@Test
	@DisplayName("when check bounderies")
	public void testBounderies() {
		assertTrue(controller.checkBounderies(new Point(5, 6)));
	}
	
	
}
