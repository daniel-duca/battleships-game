package com.battleship.states;
import com.battleship.engine.BattleGame;
import com.battleship.gui.BoardPanel;
import com.battleship.gui.Countdown;
import com.battleship.gui.TimerLimit;
import com.battleship.player.HumanPlayer;


public class PlayerTurnState extends State {

	private static PlayerTurnState _instance;
	private boolean limitStarted = false;

    private PlayerTurnState()
    {
        if(_instance != null)
        {
            return;
        }

        _instance = this;
    }
    
    public static PlayerTurnState GetInstance()
    {
        if(_instance == null)
        {
            new PlayerTurnState();
        }

        return _instance;
    }

	@Override()
	public void OnEnterState(BattleGame game) {
		System.out.println("OnEnterState PlayerTurnState");
		game.setFirstMove(true);
	    ((HumanPlayer)game.getPlayer()).setShipSelected(false);
	    ((HumanPlayer)game.getPlayer()).setClickOnRange(false);
		BoardPanel.falseShipChoosed();
		
		
		if (game.checkGameState())
			game.ChangeState(GameOverState.GetInstance());
		if (!getLimit()) {
			TimerLimit.GetInstance().start(game);
			setLimit();
		}
		Countdown.GetInstance().start(game);
		
	}
	
	@Override()
	public void OnExitState(BattleGame game) {
		System.out.println("OnExitState PlayerTurnState");
		Countdown.GetInstance().stop();

		
	}
	
	@Override()
	public void OnFireShip(BattleGame game, boolean isHit) {
		
	}
	
	@Override()
	public void OnTimeExpired(BattleGame game) {
		
	}
	
	@Override
	public StateType getType() {
		return StateType.PlayerTurn;
	}
	
	public void setLimit() {
		this.limitStarted = true;
	}
	
	public boolean getLimit() {
		return this.limitStarted;
	}
}

