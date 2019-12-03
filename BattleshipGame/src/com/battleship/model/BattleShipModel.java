package com.battleship.model;

import java.util.ArrayList;

import com.battleship.gui.BoardPanel;
import com.battleship.gui.Menu;
import com.battleship.pieces.Cell;
import com.battleship.pieces.EmptyCell;

public class BattleShipModel {
	

	private boolean tLimitMode = false;
	private ArrayList<String> names = new ArrayList<String>();
	private ArrayList<Integer> scores = new ArrayList<Integer>();
	private String name;
	private CSV csv = new CSV();
	private Menu menu;

	private Cell[][] cells;

	public Cell[][] getCells() {
		return cells;
	}

	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}
	
	public Cell getCurrentCell(int x, int y) {
		if (x >= 0 && y >= 0 && x < BoardPanel.ROW_COUNT && y < BoardPanel.ROW_COUNT) {
			return getCells()[y][x];
		}

		return new EmptyCell();
	}
	
	public void setTLimitMode() {
		tLimitMode = true;
	}
	

	public ArrayList<String> getNamesArray() {
		return names;
	}

	public ArrayList<Integer> getScoresArray() {
		return scores;
	}

	public void setScore(int i, Integer score) {
		scores.set(i, score);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Menu getMenu() {
		return menu;
	}
	
	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public CSV getCSV() {
		return csv;
	}
	
	public boolean isTimeLimit() {
		if (tLimitMode)
			return true;
		return false;
	}


}
