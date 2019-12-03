package com.battleship.pieces;

import com.battleship.player.Player;

public abstract class Ship extends Cell {
	abstract public BattleType getType();
	private Field field;
	private int length = 1;
	private int shipParts;
	
	  private boolean sunken = false;
	 
	  public Ship(int length, Field field, Player player)
	  {
		super(player);
	    this.setField(field);
	    this.length = length;
	    this.shipParts = length;
	  }
	  
	  public Ship(int length) {
		  this.length = length;
		  this.shipParts = length;
	  }
	  
	  public int getLength()
	  {
	    return length;
	  }
	  public int getShipParts()
	  {
		  return shipParts;
	  }
	  
	  
	  public boolean sunked()
	  {
		  System.out.println("Ship shipParts: "+ shipParts);
	    if (shipParts <= 0) {
	      if (!sunken){
	    	  System.out.println("Ship sunken");
	          effects.sinking();
	      }
	      sunken = true;
	      return true;
	    }
	    return false;
	  }

	  public void shot()
	  {
	    shipParts--;
	    // play sound
	    effects.strike();
	  }

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}
	
	public Ship setHorizontalFromShipClass(boolean status) {
		this.field.setHorizontal(status);
		return this;
	}
	  
	@Override
	public final Cell placeShip(Field field, Player player) {
		this.setField(field);
		this.setPlayer(player);
		
		return this;
	}
	
	 
}
