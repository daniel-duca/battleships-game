package com.battleship.player;

import java.awt.Point;
import java.util.Random;

import com.battleship.gui.BoardPanel;
import com.battleship.engine.*;
import com.battleship.pieces.*;
import com.battleship.player.Player.PlayerType;

public class AIPlayer extends Player{
	private Point selectCellToHit = null;
	
	public AIPlayer() {
		super(PlayerType.Computer, BoardPanel.ROW_COUNT/2-1);
	}

	@Override
	public Field placeShip(Ship ship) {
		int x, y;
        boolean horizontal;
        Random random = new Random();
        
        horizontal = random.nextBoolean();
		x = random.nextInt(BoardPanel.ROW_COUNT/2);
		y = random.nextInt(BoardPanel.ROW_COUNT);
		
		Field field = new Field(x,y,horizontal);
		
		return field;
	}

	@Override
	public Point getTarget() {
		return selectCellToHit;
	}

	@Override
	public void setSelectCellToHit(Point p) {
		selectCellToHit = p;		
	}

	@Override
	public Point getSelectCellToHit() {
		return selectCellToHit;
	}

	@Override
	public String getTitle() {
		return "Computer Player";
	}
	
	/*
	*      Motion functions 
	*/
	
	
	public void setScore(BattleGame game) {
		score = game.playerScoreCalculator(this.getPlayerType());
	}
	
	public int getScore() {
		
		return score;
	}
	

	
	public void moveLeft(BattleGame game) {
		int x;
		Point p = getSelectCellToHit();
		x = (int)p.getX();
		Cell cell = game.getModel().getCurrentCell((int)p.getX(),(int)p.getY());
		Ship ship = (Ship)cell;
		while(!(cell instanceof EmptyCell)) {
			cell = game.getModel().getCurrentCell(--x, (int)p.getY());
		}
		game.setField(x, (int)p.getY(), ship);
		System.out.println(ship + " is moving to " + x + "-" + (int)p.getY() );
		ship.setField(new Field(x, (int)p.getY(), true));
		game.setField(x + ship.getLength(), (int)p.getY(),new EmptyCell());
	}
	public void moveRight(BattleGame game) {
		int x;
		Point p = getSelectCellToHit();
		x = (int)p.getX();
		Cell cell = game.getModel().getCurrentCell((int)p.getX(),(int)p.getY());
		Ship ship = (Ship)cell;
		while(!(cell instanceof EmptyCell)) {
			cell = game.getModel().getCurrentCell(++x, (int)p.getY());
		}
		game.setField(x, (int)p.getY(), ship);
		System.out.println(ship + " is moving to " + x + "-" + (int)p.getY() );
		ship.setField(new Field(x, (int)p.getY(), true));
		game.setField(x - ship.getLength(), (int)p.getY(),new EmptyCell());
	}
	public void moveUp(BattleGame game) {
		int y;
		Point p = getSelectCellToHit();
		y = (int)p.getY();
		Cell cell = game.getModel().getCurrentCell((int)p.getX(),(int)p.getY());
		Ship ship = (Ship)cell;
		while(!(cell instanceof EmptyCell)) {
			cell = game.getModel().getCurrentCell((int)p.getX(), --y);
		}
		game.setField((int)p.getX(), y, ship);
		System.out.println(ship + " is moving to " + (int)p.getX() + "-" + y );
		ship.setField(new Field((int)p.getX(), y, false));
		game.setField((int)p.getX() , y + ship.getLength(), new EmptyCell());
		
	}
	public void moveDown(BattleGame game) {
		int y;
		Point p = getSelectCellToHit();
		y = (int)p.getY();
		Cell cell = game.getModel().getCurrentCell((int)p.getX(),(int)p.getY());
		Ship ship = (Ship)cell;
		while(!(cell instanceof EmptyCell)) {
			cell = game.getModel().getCurrentCell((int)p.getX(), ++y);
		}
		game.setField((int)p.getX(), y, ship);
		System.out.println(ship + " is moving to " + (int)p.getX() + "-" + y );
		ship.setField(new Field((int)p.getX(), y, false));
		game.setField((int)p.getX() , y - ship.getLength(), new EmptyCell());
	}

	public void rotateLeft(BattleGame game) {
		int y,x,i = 1;
		Point p = getSelectCellToHit();
		y = (int)p.getY();
		Cell cell = game.getModel().getCurrentCell((int)p.getX(),(int)p.getY());
		Ship ship = (Ship)cell;
		while(!(cell instanceof EmptyCell)) {
			cell = game.getModel().getCurrentCell((int)p.getX(), ++y);
		}
		for(x = (int)p.getX() ; x > (int)p.getX() - ship.getLength() ; i++,x-- ) {
			game.setField(x, y-1, ship.setHorizontalFromShipClass(true));
			ship.setField(new Field(x, y-1, true));
			game.setField((int)p.getX() , y - (i+1), new EmptyCell());
		}

	}
	public void rotateRight(BattleGame game) {
		int y,x,i = 1;
		Point p = getSelectCellToHit();
		x = (int)p.getX();
		Cell cell = game.getModel().getCurrentCell((int)p.getX(),(int)p.getY());
		Ship ship = (Ship)cell;
		while(!(cell instanceof EmptyCell)) {
			cell = game.getModel().getCurrentCell(++x, (int)p.getY());
		}
		for(y = (int)p.getY() ; y > (int)p.getY() - ship.getLength() ; i++,y-- ) {
			game.setField(x-1, y, ship.setHorizontalFromShipClass(false));
			ship.setField(new Field(x-1, y, false));
			game.setField(x - (i+1) , (int)p.getY(), new EmptyCell());
		}
	}
	

}
