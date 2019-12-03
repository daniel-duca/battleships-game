package com.battleship.states;

import com.battleship.engine.BattleGame;

public class InitilizeState extends State {

	private static InitilizeState _instance;

    private InitilizeState()
    {
        if(_instance != null)
        {
            return;
        }

        _instance = this;
    }
    
    public static InitilizeState GetInstance()
    {
        if(_instance == null)
        {
            _instance = new InitilizeState();
        }

        return _instance;
    }
    

	public void OnEnterState(BattleGame game) {
    	//System.out.println("OnEnterState InitilizeState");
    	game.generateShips();
    	game.updateAllShipsLocationAndHealth();
    	game.ChangeState(PlayerTurnState.GetInstance());
	}
    
    @Override()
	public void OnExitState(BattleGame game) {
		System.out.println("OnExitState InitilizeState");
	}
	
    @Override()
	public void OnFireShip(BattleGame game, boolean isHit) {
		
	}
	
    @Override()
	public void OnTimeExpired(BattleGame game) {
		
	}
	
    @Override
	public StateType getType() {
		return StateType.Initilize;
	}
}
