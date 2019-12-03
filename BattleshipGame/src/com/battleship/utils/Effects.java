package com.battleship.utils;

public class Effects {
	private final String SOUND_PATH = "/";

	  public void splash()
	  {
	    play("water_splash.mp3");
	  }

	  public void strike()
	  {
	    play("strike.mp3");
	  }

	  public void sinking()
	  {
	    play("sinking.mp3");
	  }

	  private void play(String filename)
	  {
	    Sound sound = new Sound(SOUND_PATH + filename);
	    sound.playSound();
	  }
}
