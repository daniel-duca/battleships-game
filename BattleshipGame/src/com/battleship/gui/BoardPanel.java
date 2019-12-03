package com.battleship.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;
import com.battleship.model.BattleShipModel;
import com.battleship.pieces.BorderLimit;
import com.battleship.pieces.Cell;
import com.battleship.pieces.EmptyCell;
import com.battleship.pieces.Ship;
import com.battleship.player.HumanPlayer;
import com.battleship.player.Player.PlayerType;


public class BoardPanel extends JPanel implements MouseMotionListener{
	
	/*
	 * This class responsible to draw everything on the greed 
	 */

	private static final long serialVersionUID = 1L;
	public static final int COL_COUNT = 24;
	public static final int ROW_COUNT = 24;
	public static final int CELL_SIZE = 20;
	private static final int MIDDLE = ROW_COUNT / 2;
	private static boolean isMouseInBoard = false;
	private static Point lastPoint = null;
	private static boolean clickMyShip = false;
	private static boolean shipChoosed = false;
	private BorderLimit borderLimit = new BorderLimit();
	private BattleShipModel model;
	private GameUI gameUI;
	
	public BoardPanel(BattleShipModel model, GameUI view) {
		this.model = model;
		this.gameUI = view;
		
		setPreferredSize(new Dimension(COL_COUNT * CELL_SIZE, ROW_COUNT * CELL_SIZE));
		setBackground(new Color(0, 59, 111));
		addMouseMotionListener(this);
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				if (isMouseInBoard) {
					isMouseInBoard = false;
					repaint();
				}
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				Point p = new Point(e.getX()/CELL_SIZE,e.getY()/CELL_SIZE);
				gameUI.getGame().getPlayer().setSelectCellToHit(p);
				Cell cell = model.getCurrentCell(e.getX()/CELL_SIZE,e.getY()/CELL_SIZE);
				if (!(cell instanceof EmptyCell)) {
					Ship ship = (Ship)cell;
					clickMyShip = ship.getPlayer().getPlayerType() == PlayerType.Human;
					if (ship.getPlayer().getPlayerType() == PlayerType.Human)
						shipChoosed = true;
				}else {
					clickMyShip = false;
				}
				((HumanPlayer)gameUI.getGame().getPlayer()).setShipSelected(clickMyShip);
				if (!clickMyShip) {
					int lastX = (int) e.getX()/CELL_SIZE;
					int lastY = (int) e.getY()/CELL_SIZE;
					if (lastX >= borderLimit.left && lastX < borderLimit.right 
							&& lastY >= borderLimit.top && lastY < borderLimit.bottom)
						((HumanPlayer)gameUI.getGame().getPlayer()).setClickOnRange(true);
					else
						((HumanPlayer)gameUI.getGame().getPlayer()).setClickOnRange(false);
				}
			}
		});
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
				
		drawPlayers(g);
		drawSelectedPlayers(g);
		drawMovementPlayers(g);
		drawBoardLines(g);
	}

	private void drawMovementPlayers(Graphics g) {
		int lastX = 0, lastY = 0;
		boolean flag = (clickMyShip && gameUI.getGame().getPlayer().getPlayerType() == PlayerType.Human);
		
		if (!flag)
			return;

		if (isMouseInBoard) {
			lastX = (int) lastPoint.getX()/CELL_SIZE;
			lastY = (int) lastPoint.getY()/CELL_SIZE;
			//System.out.println("X,Y=" + lastX+"-"+lastY);
		}
		
		for(int x = 0; x < COL_COUNT; x++) {
			for(int y = 0; y < ROW_COUNT; y++) {
				//Cell cell = this.game.getField(x, y);
				
				if (lastX == x && lastY == y && clickMyShip 
							&& x >= borderLimit.left && x < borderLimit.right 
							&& y >= borderLimit.top && y < borderLimit.bottom) {
					if (lastX == 0 && lastY == 0 && !isMouseInBoard) continue;
						if (!isMouseInBoard)
							g.setColor(new Color(32, 156, 185));
						else {
							g.setColor(Color.YELLOW);
							//System.out.println("X,Y=" + lastX+"-"+lastY+ "-"+ isMouseInBoard);
						}
						g.fillRect(x*CELL_SIZE, y*CELL_SIZE, CELL_SIZE, CELL_SIZE);
					//drawSelected(g, x, y, cell);
				}
			}
		}
		
	}

	private void drawSelectedPlayers(Graphics g) {
		Point p = gameUI.getGame().getPlayer().getSelectCellToHit();
		
		for(int x = 0; x < COL_COUNT; x++) {
			for(int y = 0; y < ROW_COUNT; y++) {
				if (p != null && p.equals(new Point(x,y))) {
					
					if (clickMyShip)
						drawSelected(g, x, y, model.getCurrentCell(x, y));
					else if (gameUI.getGame().getPlayer().getPlayerType() == PlayerType.Human && !((HumanPlayer)gameUI.getGame().getPlayer()).isShipSelected()&&((HumanPlayer)gameUI.getGame().getPlayer()).isClickOnRange()&&shipChoosed) {
						g.setColor(Color.PINK);
						g.fillRect(x*CELL_SIZE, y*CELL_SIZE, CELL_SIZE, CELL_SIZE);
					}
				}
			}
		}
	}
	
	private void drawSelected(Graphics g, int x, int y, Cell cell) {
		
		if (!(cell instanceof EmptyCell) && isMouseInBoard) {
			Ship ship = (Ship)cell;
			if (ship.getPlayer().getPlayerType() == PlayerType.Human) {
				((HumanPlayer)gameUI.getGame().getPlayer()).setShipSelected(true);
				int cellXIndex = x, cellYIndex = y;
				Cell siblingCell;
				if (ship.getField().isHorizontal()) {
					siblingCell = model.getCurrentCell(--cellXIndex, cellYIndex);
					while(!(siblingCell instanceof EmptyCell)) {
						siblingCell = model.getCurrentCell(--cellXIndex, cellYIndex);
					}
					int len = ship.getLength()+cellXIndex;
					
					g.setColor(Color.ORANGE);
					while(++cellXIndex<=len) {
						g.fillRect(cellXIndex*CELL_SIZE, cellYIndex*CELL_SIZE, CELL_SIZE, CELL_SIZE);
					}
					
					cellXIndex -= (ship.getLength()*2);
					cellYIndex -= (ship.getLength());
					borderLimit.left = cellXIndex;
					borderLimit.top = cellYIndex;
					if (cellXIndex < 0)
						cellXIndex = 0;
					if (cellYIndex < 0)
						cellYIndex = 0;
					//System.out.println(cellXIndex+"-"+cellYIndex);
					borderLimit.right = cellXIndex+(ship.getLength()*3);
					borderLimit.bottom = cellYIndex+(ship.getLength()*2+1);
					for (int i = cellXIndex; i < cellXIndex+(ship.getLength()*3); i++) {
						for (int j = cellYIndex; j < cellYIndex+(ship.getLength()*2+1); j++) {
							siblingCell = model.getCurrentCell(i, j);
							if (j <= BoardPanel.COL_COUNT && j <= BoardPanel.ROW_COUNT && siblingCell instanceof EmptyCell) {
								g.setColor(Color.WHITE);
								g.fillRect(i*CELL_SIZE, j*CELL_SIZE, CELL_SIZE, CELL_SIZE);
							}
						}
					}
					
				}else {
					siblingCell = model.getCurrentCell(cellXIndex, --cellYIndex);
					while(!(siblingCell instanceof EmptyCell)) {
						siblingCell = model.getCurrentCell(cellXIndex, --cellYIndex);
					}
					int len = ship.getLength()+cellYIndex;
					
					g.setColor(Color.ORANGE);
					while(++cellYIndex<=len) {
						g.fillRect(cellXIndex*CELL_SIZE, cellYIndex*CELL_SIZE, CELL_SIZE, CELL_SIZE);
					}
					
					cellXIndex -= (ship.getLength());
					cellYIndex -= (ship.getLength()*2);
					borderLimit.left = cellXIndex;
					borderLimit.top = cellYIndex;
					if (cellXIndex < 0)
						cellXIndex = 0;
					if (cellYIndex < 0)
						cellYIndex = 0;
					//System.out.println(cellXIndex+"-"+cellYIndex);
					borderLimit.right = cellXIndex+(ship.getLength()*2+1);
					borderLimit.bottom = cellYIndex+(ship.getLength()*3);
					for (int i = cellXIndex; i < cellXIndex+(ship.getLength()*2+1); i++) {
						for (int j = cellYIndex; j < cellYIndex+(ship.getLength()*3); j++) {
							siblingCell = model.getCurrentCell(i, j);
							if (j <= BoardPanel.COL_COUNT && j <= BoardPanel.ROW_COUNT && siblingCell instanceof EmptyCell) {
								g.setColor(Color.WHITE);
								g.fillRect(i*CELL_SIZE, j*CELL_SIZE, CELL_SIZE, CELL_SIZE);
							}
						}
					}
				}
			}
		}
	}

	private void drawPlayers(Graphics g) {
		
		//if (clickMyShip && game.getPlayer().getPlayerType() == PlayerType.Human)
		//	return;
		
		for(int x = 0; x < COL_COUNT; x++) {
			for(int y = 0; y < ROW_COUNT; y++) {
				Cell cell = model.getCurrentCell(x, y);
				if (!(cell instanceof EmptyCell)) {
					Color playerColor = cell.getPlayer().getPlayerType() == PlayerType.Human ? Color.GREEN : Color.RED;
					g.setColor(playerColor);
					g.fillRect(x*CELL_SIZE, y*CELL_SIZE, CELL_SIZE, CELL_SIZE);
				}
			}
			
		}
		gameUI.getGame().isFirstGameInit = false;
	}

	private void drawBoardLines(Graphics g) {
		g.setColor(new Color(32, 156, 185));
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		for(int x = 0; x < COL_COUNT; x++) {
			for(int y = 0; y < ROW_COUNT; y++) {
				if (x == MIDDLE) {
					g.setColor(new Color(255, 255, 85));
				}else {
					g.setColor(new Color(32, 156, 185));
				}
				g.drawLine(x * CELL_SIZE, 0, x * CELL_SIZE, getHeight());
				g.drawLine(0, y * CELL_SIZE, getWidth(), y * CELL_SIZE);
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseMoved(MouseEvent e) {
		isMouseInBoard = true;
		//Cell cell = this.game.getField(e.getY() * ROW_COUNT, e.getX());
		//System.out.println("X,Y=" + e.getX() / CELL_SIZE+"-"+e.getY() / CELL_SIZE);
		lastPoint = new Point();
		lastPoint.setLocation(e.getX(), e.getY());
		this.repaint();
	}
	
	public static void falseShipChoosed() {
		shipChoosed = false;
	}
	
	public static boolean isChoosed() {
		return shipChoosed;
	}

}
