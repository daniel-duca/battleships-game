package com.battleship.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.battleship.engine.BattleGame;
import com.battleship.gui.GameUI;
import com.battleship.model.BattleShipModel;
import com.battleship.states.ComputerTurnState;
import com.battleship.states.GameOverState;
import com.battleship.states.InitilizeState;
import com.battleship.states.PlayerTurnState;
import com.battleship.states.State.StateType;

@TestInstance(Lifecycle.PER_CLASS)
public class StateMachineTest {
	
	BattleGame controller;
	BattleShipModel model;
	
	public StateMachineTest() {
		model = new BattleShipModel();
		GameUI view = new GameUI(model);

		controller = new BattleGame(view, model);
		
		view.setGameListener(controller);
		
		controller.fillEmptyBoard();
		controller.generateShips();
		controller.updateAllShipsLocationAndHealth();
	}

	
	@Test
	@DisplayName("when computer turn state")
	public void testComputerState() {
		ComputerTurnState state = ComputerTurnState.GetInstance();
		controller.ChangeState(state);
		assertEquals(state.getType(), StateType.ComputerTurn);
	}
	
	@Test
	@DisplayName("when human turn state")
	public void testHumanState() {
		PlayerTurnState state = PlayerTurnState.GetInstance();
		controller.ChangeState(state);
		assertEquals(state.getType(), StateType.PlayerTurn);
	}
	
	@Test
	@DisplayName("when initilze state")
	public void testInitilizeState() {
		InitilizeState state = InitilizeState.GetInstance();
		controller.ChangeState(state);
		assertEquals(state.getType(), StateType.Initilize);
	}
}
