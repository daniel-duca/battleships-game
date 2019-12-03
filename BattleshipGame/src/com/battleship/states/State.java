package com.battleship.states;

import com.battleship.engine.BattleGame;

public abstract class State {
	public enum StateType{
		Idle,
		Initilize,
		PlayerTurn,
		ComputerTurn,
		GameOver
	}
	public abstract StateType getType();
	public abstract void OnEnterState(BattleGame game);
	public abstract void OnExitState(BattleGame game);
	public abstract void OnFireShip(BattleGame game, boolean isHit);
	public abstract void OnTimeExpired(BattleGame game);
	
}
