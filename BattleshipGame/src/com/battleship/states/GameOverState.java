package com.battleship.states;

import java.io.FileNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.battleship.engine.BattleGame;
import com.battleship.gui.GameUI;
import com.battleship.player.Player.PlayerType;

public class GameOverState extends State {

	private static GameOverState _instance;

    private GameOverState()
    {
        if(_instance != null)
        {
            return;
        }

        _instance = this;
    }
    
    public static GameOverState GetInstance()
    {
        if(_instance == null)
        {
            _instance = new GameOverState();
        }

        return _instance;
    }
    
	@Override()
	public void OnEnterState(BattleGame game) {
		System.out.println("OnEnterState GameOverState");
		if (game.getModel().getScoresArray().get(game.getModel().getNamesArray().indexOf(game.getModel().getName()))<game.playerScoreCalculator(PlayerType.Human)) {
			game.getModel().getScoresArray().set(game.getModel().getNamesArray().indexOf(game.getModel().getName()),game.playerScoreCalculator(PlayerType.Human));
			game.getModel().getCSV().deleteCSV();
			try {
				game.getModel().getCSV().sort(game.getModel().getNamesArray(), game.getModel().getScoresArray(), game.getModel().getName());
				game.getModel().getCSV().copyFromArray(game.getModel().getNamesArray(), game.getModel().getScoresArray());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
		}
			
			

			//JOptionPane.showMessageDialog(game.getGame().getBoard(), "The winner is "+ game.showWinner());
			
		
		
		}
		int input = 10;
		if (!game.showWinner().equals("Draw"))
			input = JOptionPane.showOptionDialog(game.getGame().getBoard(), "The winner is " + game.showWinner(), "The winner", JOptionPane.DEFAULT_OPTION  , JOptionPane.QUESTION_MESSAGE, new ImageIcon("images\\crown.png")  , null, "OK");
		else
			input = JOptionPane.showOptionDialog(game.getGame().getBoard(), "It's a Draw, no winners in the game", "Draw", JOptionPane.DEFAULT_OPTION  , JOptionPane.QUESTION_MESSAGE, new ImageIcon("images\\crown.png")  , null, "OK");
		if(input == 0)
		{
		    System.exit(0);
		}
		//game.drawScore();
		//game.ChangeState(IdleState.GetInstance());
	}
	
	@Override()
	public void OnExitState(BattleGame game) {
		System.out.println("OnExitState GameOverState");
	}
	
	@Override()
	public void OnFireShip(BattleGame game, boolean isHit) {
		
	}
	
	@Override()
	public void OnTimeExpired(BattleGame game) {
		
	}
	
	@Override
	public StateType getType() {
		return StateType.GameOver;
	}
	
}
