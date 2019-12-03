package com.battleship.gui;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;
import com.battleship.engine.BattleGame;

import com.battleship.states.GameOverState;


public class TimerLimit {
	
	String timer;
	Timer t;
	TimerTask tt;
	JLabel label;
	int timedownSeconds;
	int timedownMinutes;
	final int COUNTERMINUTES = 10;
	final int COUNTERSECONDS = 59;
	
	private static TimerLimit _instance;

    private TimerLimit()
    {
        if(_instance != null)
        {
            return;
        }

        _instance = this;
    }
    
    public static TimerLimit GetInstance()
    {
        if(_instance == null)
        {
            new TimerLimit();
        }

        return _instance;
    }
	public void start(BattleGame game) {
		this.timedownSeconds = 0;
		this.timedownMinutes = COUNTERMINUTES;
		t = new Timer();
		tt = new TimerTask() {
		    @Override
		    public void run() {
		    	timedownSeconds--;
		    	game.drawScore();
		    	if (timedownSeconds < 0) {
		    		timedownMinutes--;
		    		timedownSeconds = COUNTERSECONDS;
		    	}
		    	if (timedownSeconds == 0 && timedownMinutes == 0) {
		    		stop();
		    		game.ChangeState(GameOverState.GetInstance());
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
		if (timedownSeconds>=10)
			timer = 0 + Integer.toString(timedownMinutes) + ":" + Integer.toString(timedownSeconds);
		if (timedownSeconds<10)
			timer = 0 + Integer.toString(timedownMinutes) + ":" + 0 + Integer.toString(timedownSeconds);
		if (timedownSeconds==0)
			timer = 0 + Integer.toString(timedownMinutes) + ":" + 0 + 0;
		if (timedownMinutes==10)
			timer = Integer.toString(timedownMinutes) + ":" + 0 + 0;
		return timer;
	}
}
