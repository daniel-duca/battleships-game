package com.battleship.states;

import com.battleship.engine.BattleGame;

public class IdleState extends State {

	private static IdleState _instance;

    private IdleState()
    {
        if(_instance != null)
        {
            return;
        }

        _instance = this;
    }
    
    public static IdleState GetInstance()
    {
        if(_instance == null)
        {
            new IdleState();
        }

        return _instance;
    }

	@Override()
	public void OnEnterState(BattleGame game) {
		//System.out.println("OnEnterState IdleState");
		game.fillEmptyBoard();
	}
	
	@Override()
	public void OnExitState(BattleGame game) {
		//System.out.println("OnExitState IdleState");
	}
	
	@Override()
	public void OnFireShip(BattleGame game, boolean isHit) {
		
	}
	
	@Override()
	public void OnTimeExpired(BattleGame game) {
		
	}
	
	@Override
	public StateType getType() {
		return StateType.Idle;
	}
	
}
