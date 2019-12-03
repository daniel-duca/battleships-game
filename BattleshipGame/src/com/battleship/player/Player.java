package com.battleship.player;

import java.awt.Point;

import com.battleship.engine.BattleGame;
import com.battleship.pieces.Field;
import com.battleship.pieces.Ship;

public abstract class Player {
	public final int limitX;
	protected int score;
	public enum PlayerType{
		Human,
		Computer
	}
	private PlayerType playerType;
	
	
	Player(PlayerType pt, int limitX){
		this.playerType = pt;
		this.limitX = limitX;
	}
	public abstract Field placeShip(Ship ship);
	public abstract Point getTarget();
	public abstract void setSelectCellToHit(Point p);
	public abstract Point getSelectCellToHit();
	public abstract String getTitle();
	public abstract void rotateLeft(BattleGame game);
	public abstract void rotateRight(BattleGame game);
	public abstract void moveLeft(BattleGame game);
	public abstract void moveRight(BattleGame game);
	public abstract void moveUp(BattleGame game);
	public abstract void moveDown(BattleGame game);
	//public abstract int scoreCounter(BattleGame game);
	
	public PlayerType getPlayerType() {
		return playerType;
	}
	
}
