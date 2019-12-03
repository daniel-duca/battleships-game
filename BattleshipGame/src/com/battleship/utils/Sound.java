package com.battleship.utils;

import java.io.*;
import javazoom.jl.decoder.*;
import javazoom.jl.player.*;

public class Sound extends Thread
{

	  private BufferedInputStream file = null;
	  private Player player = null;

	  public Sound(String filepath)
	  {
	    try {
	      InputStream input = getClass().getResourceAsStream(filepath);
	      file = new BufferedInputStream(input);
	      player = new Player(file);
	    } catch (Exception e) {
	      System.out.println(e);
	    }
	  }

	  public void playSound()
	  {   
	    start();
	  }

	  @Override
	  public void run() 
	  {
	    try {
	      player.play();
	    } catch (JavaLayerException e) {
	      System.out.println("Soundmanager error: " + e.getMessage());
	    }
	  }
}