package com.battleship.pieces;
public class Field
/*
 * Field represents the virtual location on the board from geometrical point of view , (x,y) and vertical or horizontal.
 */
{
  private boolean horizontal = false;
  private int x;
  private int y;


  public Field(int x, int y, boolean horizontal)
  {
    // set ship options
    this.horizontal = horizontal;
    this.x = x;
    this.y = y;
  }
  
  public int getX()
  {
    return x;
  }

  public int getY()
  {
    return y;
  }

  public boolean isHorizontal()
  {
    return horizontal;
  }
  public void setHorizontal(boolean status) {
	  this.horizontal = status;
  }

}