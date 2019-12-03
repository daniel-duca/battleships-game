package com.battleship.gui;
import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.battleship.engine.BattleGame;
import com.battleship.model.BattleShipModel;
import com.battleship.pieces.BattleType;
import com.battleship.player.Player.PlayerType;

public class GameUI extends JFrame {

	private BattleGame game;
	private BoardPanel board;
	private BoardPanel humanBoard;
	private SidePanel side;
	
	public void close () {
		this.dispose();
	}
	
	public GameUI(BattleShipModel model) {
		super("Battleship Game");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage("images\\Logo.png"));
		setResizable(false);
		
		//this.game = game;
		this.board = new BoardPanel(model, this);
		this.side = new SidePanel(model, this);
		
		add(this.board, BorderLayout.WEST);
		add(this.side, BorderLayout.EAST);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	
	public SidePanel getSide()
	{
		return side;
	}
	
	public void repaintBoard() {
		board.repaint();
		side.repaint();
	}

	public void setGameListener(BattleGame controller) {
		this.game = controller;
	}

	public BattleGame getGame() {
		return game;
	}	
	
	public BoardPanel getBoard() {
		return board;
	}	

}
