package com.battleship.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import com.battleship.gui.GameUI;
import com.battleship.model.BattleShipModel;
import com.battleship.engine.BattleGame.Direction;
import com.battleship.engine.BattleGame.Rotation;
import com.battleship.pieces.BattleType;
import com.battleship.pieces.Cell;
import com.battleship.pieces.EmptyCell;
import com.battleship.pieces.Ship;
import com.battleship.player.HumanPlayer;
import com.battleship.player.Player.PlayerType;
import com.battleship.states.ComputerTurnState;
import com.battleship.states.InitilizeState;
import javax.swing.JTextPane;

public class SidePanel extends JPanel {
	
	/*
	 * This class responsible for all control bottoms on GUI and them appearance depends of picked ship location.   
	 */

	private GameUI gameUI;
	private static final Font LARGE_FONT = new Font("Rockwell", Font.BOLD, 20);
	private static final int STATISTICS_OFFSET = 220;
	private static final int SMALL_OFFSET = 30;
	private static final int LARGE_OFFSET = 50;
	private static final int MESSAGE_STRIDE = 30;
	private JTable table;
	private JLabel shipTable = new JLabel("");
	private JLabel shipTableAI = new JLabel("");
	private BattleShipModel model;
	private Font font;
	
	
	
	
	JButton btnMoveRight, btnMoveLeft, btnMoveUp, btnMoveDown , btnRotateLeft, btnRotateRight, btnFire, btnStartGame;
	private TableCellRenderer centerRenderer;
	private JTextPane txtpnFgdgf;
	

	
	public SidePanel(BattleShipModel model, GameUI view) {
		//this.game = game;
		this.model = model;
		this.gameUI = view;
		
		setLayout(null);
		
		
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("font\\Fonseca.ttf")).deriveFont(20f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("font\\Fonseca.ttf")));
		} catch (IOException|FontFormatException e) {
		     //Handle exception
		}
		
		loadMoveControls();
		
		   btnFire = new JButton("FIRE"); 
		   btnFire.setEnabled(false);
		    btnFire.setFont(new Font("Rockwell", Font.BOLD, 18));  
			//b.setBorder(new EmptyBorder(30,10,0,0));
		    btnFire.setBounds(109,140,80,30);
		    btnFire.setBackground(new Color(211, 211, 211));
		    btnFire.setForeground(new Color(0, 59, 111));
		    btnFire.setAlignmentX(Component.CENTER_ALIGNMENT);
			btnFire.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					gameUI.getGame().ShotTarget(gameUI.getGame().getPlayer());
					gameUI.getGame().ChangeState(ComputerTurnState.GetInstance());
				}
			});
		    
		    add(btnFire);
		
		btnStartGame = new JButton("Start Game");  
		//b.setBorder(new EmptyBorder(30,10,0,0));
		btnStartGame.setFont(new Font("Rockwell", Font.BOLD, 18)); 
		btnStartGame.setForeground(new Color(0, 59, 111));
		btnStartGame.setBackground(new Color(211, 211, 211));
		btnStartGame.setBounds(74, 442, 150, 27);
		btnStartGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnStartGame.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				gameUI.getGame().ChangeState(InitilizeState.GetInstance());
				btnFire.setEnabled(true);
				btnStartGame.setEnabled(false);
				shipTable.setIcon(new ImageIcon("images\\shipTable.png"));
				shipTable.setBounds(10, 290, 148, 141);
				shipTableAI.setIcon(new ImageIcon("images\\shipTable.png"));
				shipTableAI.setBounds(210, 290, 148, 141);
				add(shipTableAI);
				add(shipTable);
				table = new JTable();
				table.setFont(new Font("Rockwell", Font.PLAIN, 25));
				gameUI.getGame().getShipHealthByPlayerType(BattleType.Cruiser , PlayerType.Human);
				table.setModel(new DefaultTableModel(
						new Object[][] {
							{gameUI.getGame().getShipHealthByPlayerType(BattleType.Cruiser , PlayerType.Human), gameUI.getGame().getShipHealthByPlayerType(BattleType.Cruiser , PlayerType.Computer)},
							{gameUI.getGame().getShipHealthByPlayerType(BattleType.Submarine , PlayerType.Human), gameUI.getGame().getShipHealthByPlayerType(BattleType.Submarine , PlayerType.Computer)},
							{gameUI.getGame().getShipHealthByPlayerType(BattleType.Battleship , PlayerType.Human), gameUI.getGame().getShipHealthByPlayerType(BattleType.Battleship , PlayerType.Computer)},
							{gameUI.getGame().getShipHealthByPlayerType(BattleType.Carrier , PlayerType.Human), gameUI.getGame().getShipHealthByPlayerType(BattleType.Carrier , PlayerType.Computer)},
							{gameUI.getGame().getShipHealthByPlayerType(BattleType.Destroyer , PlayerType.Human), gameUI.getGame().getShipHealthByPlayerType(BattleType.Destroyer , PlayerType.Computer)},
						},
						new String[] {
							"New column", "New column"
						}
						
					));
				table.setBounds(103, 290, 100, 141);
				table.setRowHeight(28);
				add(table);
				DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
				centerRenderer.setHorizontalAlignment(JLabel.CENTER);
				table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
				table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
				table.getColumnModel().getColumn(0).setPreferredWidth(100);
				table.getColumnModel().getColumn(1).setPreferredWidth(100);
				
				
			}
		});
	    
	    add(btnStartGame);
	   	    
		setPreferredSize(new Dimension(300, BoardPanel.ROW_COUNT * BoardPanel.CELL_SIZE));
		setBackground(new Color(0, 59, 111));
		
	
		
		
		
		
	}
	
	private void loadMoveControls() {
		
		btnMoveRight = new JButton("");  
		btnMoveRight.setIcon(new ImageIcon("images\\right.png"));
		//b.setBorder(new EmptyBorder(30,10,0,0));
		btnMoveRight.setBounds(164,70,30,30);
		btnMoveRight.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnMoveRight.setEnabled(false);
		btnMoveRight.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				gameUI.getGame().getPlayer().moveRight(gameUI.getGame());
			}
		});
	    add(btnMoveRight);
	    
	    btnMoveLeft = new JButton("");  
		btnMoveLeft.setIcon(new ImageIcon("images\\left.png"));
		btnMoveLeft.setBounds(104,70,30,30);
		btnMoveLeft.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnMoveLeft.setEnabled(false);
		btnMoveLeft.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				gameUI.getGame().getPlayer().moveLeft(gameUI.getGame());
			}
		});
	    
	    add(btnMoveLeft);
	    
	    btnMoveUp = new JButton("");  
		btnMoveUp.setIcon(new ImageIcon("images\\up.png"));
		btnMoveUp.setBounds(134,40,30,30);
		btnMoveUp.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnMoveUp.setEnabled(false);
		btnMoveUp.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				gameUI.getGame().getPlayer().moveUp(gameUI.getGame());
			}
		});
	    
	    add(btnMoveUp);
	    
	    btnMoveDown = new JButton("");  
		btnMoveDown.setIcon(new ImageIcon("images\\down.png"));
		btnMoveDown.setBounds(134,100,30,30);
		btnMoveDown.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnMoveDown.setEnabled(false);
		btnMoveDown.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				gameUI.getGame().getPlayer().moveDown(gameUI.getGame());
			}
		});
	    
	    add(btnMoveDown);
	    
	    btnRotateLeft = new JButton("");  
		btnRotateLeft.setIcon(new ImageIcon("images\\rleft.png"));
		btnRotateLeft.setBounds(74,140,30,30);
		btnRotateLeft.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnRotateLeft.setEnabled(false);
		btnRotateLeft.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				gameUI.getGame().getPlayer().rotateLeft(gameUI.getGame());
			}
		});
		add(btnRotateLeft);
		
		btnRotateRight = new JButton("");  
		btnRotateRight.setIcon(new ImageIcon("images\\rright.png"));
		btnRotateRight.setBounds(194,140,30,30);
		btnRotateRight.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnRotateRight.setEnabled(false);
		btnRotateRight.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				gameUI.getGame().getPlayer().rotateRight(gameUI.getGame());
			}
		});
		add(btnRotateRight);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (gameUI.getGame().inPlayingMode() && model.isTimeLimit())
		{
			
			JTextPane textPane = new JTextPane();
			g.setFont(font);
			g.setColor(Color.WHITE);
			g.drawString(TimerLimit.GetInstance().getCount(), 185, 29);
			textPane.setBackground(new Color(0, 59, 111));
			textPane.setEnabled(false);
			textPane.setEditable(true);
			textPane.setBounds(60, 11, 125, 27);
			textPane.setFont(font);
			textPane.setText("Time Limit: ");
			//g.drawString(Countdown.GetInstance().getCountLimit(), 200, 30);
			add(textPane);
			
		
		}
		
		
		g.setColor(Color.WHITE);
		int drawY = STATISTICS_OFFSET;
		//g.drawString("Total Human Player Score: " + game.humanScore, LARGE_OFFSET, drawY += MESSAGE_STRIDE);
		//g.drawString("Total AI Player Score: " + game.aiScore, LARGE_OFFSET, drawY += MESSAGE_STRIDE);

		if (gameUI.getGame().inPlayingMode()) {
			enabledisableMoveButtons(gameUI.getGame().getPlayer().getPlayerType()!=PlayerType.Computer);
			g.setFont(font);
			g.drawString("Computer", 190, 280);
			g.drawString(gameUI.getGame().getModel().getName(), 20, 280);
			g.drawString(String.valueOf(gameUI.getGame().playerScoreCalculator(PlayerType.Human)), 45, 464);
			g.drawString(String.valueOf(gameUI.getGame().playerScoreCalculator(PlayerType.Computer)), 240, 464);
			
			if (gameUI.getGame().getPlayer().getPlayerType()!=PlayerType.Computer) {
				g.drawString(gameUI.getGame().getPlayer().getTitle(), 90, 200);
				btnFire.setEnabled(!((HumanPlayer)gameUI.getGame().getPlayer()).isShipSelected()&&((HumanPlayer)gameUI.getGame().getPlayer()).isClickOnRange()&&BoardPanel.isChoosed());
			}
			else {
				g.drawString(gameUI.getGame().getPlayer().getTitle(), 60, 200);
				btnFire.setEnabled(false);
			}
			g.drawString(Countdown.GetInstance().getCount(), 135, 230);
			table.setModel(new DefaultTableModel(
					new Object[][] {
						{gameUI.getGame().getShipHealthByPlayerType(BattleType.Cruiser , PlayerType.Human), gameUI.getGame().getShipHealthByPlayerType(BattleType.Cruiser , PlayerType.Computer)},
						{gameUI.getGame().getShipHealthByPlayerType(BattleType.Submarine , PlayerType.Human), gameUI.getGame().getShipHealthByPlayerType(BattleType.Submarine , PlayerType.Computer)},
						{gameUI.getGame().getShipHealthByPlayerType(BattleType.Battleship , PlayerType.Human), gameUI.getGame().getShipHealthByPlayerType(BattleType.Battleship , PlayerType.Computer)},
						{gameUI.getGame().getShipHealthByPlayerType(BattleType.Carrier , PlayerType.Human), gameUI.getGame().getShipHealthByPlayerType(BattleType.Carrier , PlayerType.Computer)},
						{gameUI.getGame().getShipHealthByPlayerType(BattleType.Destroyer , PlayerType.Human), gameUI.getGame().getShipHealthByPlayerType(BattleType.Destroyer , PlayerType.Computer)},
					},
					new String[] {
						"New column", "New column"
					}
					
				));
			
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(JLabel.CENTER);
			table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
			table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
			
		}else if(gameUI.getGame().inGameOver()) {
			System.out.println("Game over");
			
			
		}
		
	}

	private void enabledisableMoveButtons(boolean isEnabled) {
		Point p = gameUI.getGame().getPlayer().getSelectCellToHit();
		Ship ship = null;
		if (p != null) {
			Cell cell = model.getCurrentCell((int)p.getX(),(int)p.getY());
			if (!(cell instanceof EmptyCell)) {
				ship = (Ship)cell;
				
			}
		}
		
		btnMoveDown.setEnabled(gameUI.getGame().isFirstMove() && isEnabled && gameUI.getGame().isVerticalMovePossible(p, Direction.Down , ship));
		
		btnMoveUp.setEnabled(gameUI.getGame().isFirstMove() && isEnabled && gameUI.getGame().isVerticalMovePossible(p , Direction.Up , ship));
		
		btnMoveRight.setEnabled(gameUI.getGame().isFirstMove() && isEnabled && gameUI.getGame().isHorizontalMovePossible(p , Direction.Right , ship));
		
		btnMoveLeft.setEnabled(gameUI.getGame().isFirstMove() && isEnabled && gameUI.getGame().isHorizontalMovePossible(p , Direction.Left , ship));
		
		btnRotateLeft.setEnabled(gameUI.getGame().isFirstMove() && isEnabled && gameUI.getGame().isLeftRotationPossible(p , Rotation.rotateLeft , ship));
		
		btnRotateRight.setEnabled(gameUI.getGame().isFirstMove() && isEnabled && gameUI.getGame().isRightRotationPossible(p , Rotation.rotateRight , ship));
		
	}
}

