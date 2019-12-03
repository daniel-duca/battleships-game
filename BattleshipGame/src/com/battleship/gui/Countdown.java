package com.battleship.gui;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;
import com.battleship.engine.BattleGame;
import com.battleship.states.State;

public class Countdown {
	
	/*
	 *                     This is timer to human player turn and other
	 */
	Timer t;
	TimerTask tt;
	JLabel label;
	int countdown;
	final int COUNTER = 30;
	
	private static Countdown _instance;

    private Countdown()
    {
        if(_instance != null)
        {
            return;
        }

        _instance = this;
    }
    
    public static Countdown GetInstance()
    {
        if(_instance == null)
        {
            new Countdown();
        }

        return _instance;
    }
	public void start(BattleGame game) {
		this.countdown = COUNTER;
		t = new Timer();
		tt = new TimerTask() {
		    @Override
		    public void run() {
		    	countdown--;
		    	game.drawScore();
		    	if (countdown == 0) {
		    		stop();
		    		State state =  game.getOpponentState();
		    		game.ChangeState(state);
		    	}
		    };
		};
		t.schedule(tt,1000,1000);
	}
	
	public void stop() {
		tt.cancel();
		t.cancel();
		System.out.println("Stopping");
	}
	
	public String getCount() {
		return Integer.toString(countdown);
	}
}
