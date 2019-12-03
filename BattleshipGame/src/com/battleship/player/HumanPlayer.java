package com.battleship.player;

import java.awt.Point;
import java.util.Random;

import com.battleship.engine.BattleGame;
import com.battleship.gui.BoardPanel;
import com.battleship.pieces.Cell;
import com.battleship.pieces.EmptyCell;
import com.battleship.pieces.Field;
import com.battleship.pieces.Ship;

public class HumanPlayer extends Player {
	private Point selectCellToHit = null;
	private boolean isShipSelected = false;
	private boolean clickOnRange = false;
	
	public HumanPlayer() {
		super(PlayerType.Human, BoardPanel.ROW_COUNT);
	}

	@Override
	public Field placeShip(Ship ship) {
		int x, y;
        boolean horizontal;
        Random random = new Random();
        
        horizontal = random.nextBoolean();
		x = random.nextInt((BoardPanel.ROW_COUNT - BoardPanel.ROW_COUNT/2-1) + 1) + BoardPanel.ROW_COUNT/2;
		y = random.nextInt(BoardPanel.ROW_COUNT-1);
		
		Field field = new Field(x,y,horizontal);
		
		return field;
	}

	public Point getSelectCellToHit() {
		return selectCellToHit;
	}


	public void setSelectCellToHit(Point p) {
		selectCellToHit = p;
	}

	@Override
	public Point getTarget() {
		return selectCellToHit;
	}

	@Override
	public String getTitle() {
		return "Me Playing";
	}
	
	
	public void setScore(BattleGame game) {
		score = game.playerScoreCalculator(this.getPlayerType());
	}
	
	public int getScore() {
		
		return score;
	}
	
	
	/*
	*      Motion functions 
	*/
	
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
		game.setFirstMove(false);
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
		game.setFirstMove(false);
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
		game.setFirstMove(false);
		
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
		game.setFirstMove(false);
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
		game.setFirstMove(false);

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
		game.setFirstMove(false);
	}

	public boolean isShipSelected() {
		return isShipSelected;
	}

	public void setShipSelected(boolean isShipSelected) {
		this.isShipSelected = isShipSelected;
	}

	public boolean isClickOnRange() {
		return clickOnRange;
	}

	public void setClickOnRange(boolean clickOnRange) {
		this.clickOnRange = clickOnRange;
	}
}
