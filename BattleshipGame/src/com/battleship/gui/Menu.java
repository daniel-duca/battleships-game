package com.battleship.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.battleship.engine.BattleGame;
import com.battleship.model.BattleShipModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Menu extends JFrame implements Verify{

	private static final long serialVersionUID = 1L;
	private JButton newPlayer, loadPlayer, highScores, backHs, backToIntro, okHs, dMMode, tLMode, backL, okL;
	private JPanel introPanel = new JPanel(), modesPanel = new JPanel(), highScoresPanel = new JPanel(), newPlayerPanel = new JPanel(),	loadPlayerPanel = new JPanel(), contentPanel = new JPanel();
	private CardLayout cardLayout = new CardLayout();
	private JTable highScoresTable, loadPlayerTable;
	private JTextField textField;
	private JLabel lblkeySensativeNew;
	private BattleShipModel model = new BattleShipModel();
	private ScrollPane loadPlayerTableScroll, highScoresTableScroll;
	private JPanel modesButtonsPanel, introButtonsPanel;
	private Font font, font1, font2;
	
	public void hideMenu() {
		this.setVisible(false);
	}
	
	public void showMenu() {
		this.setVisible(true);
	}
	
	public BattleShipModel getModel() {
		return model;
	}

	public Menu() {
		super("Battleship");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(800, 600);
		setResizable(false);
		showMenu();
		setIconImage(Toolkit.getDefaultToolkit().getImage("images\\Logo.png"));
		actionListener al = new actionListener();
		contentPanel.setLayout(cardLayout);
		this.setContentPane(contentPanel);
		cardLayout.show(contentPanel, "introPanel");
		setLocationRelativeTo(null);
		
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("font\\SearchingForSignal-Italic.ttf")).deriveFont(180f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("font\\SearchingForSignal-Italic.ttf")));
		} catch (IOException|FontFormatException e) {
		     //Handle exception
		}
		
		try {
			font1 = Font.createFont(Font.TRUETYPE_FONT, new File("font\\soloist1.ttf")).deriveFont(35f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("font\\soloist1.ttf")));
		} catch (IOException|FontFormatException e) {
		     //Handle exception
		}
		
		try {
			font2 = Font.createFont(Font.TRUETYPE_FONT, new File("font\\SearchingForSignal-Italic.ttf")).deriveFont(100f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("font\\SearchingForSignal-Italic.ttf")));
		} catch (IOException|FontFormatException e) {
		     //Handle exception
		}
		
		
		
		
		if(model.getCSV().getFile().exists()) 
			model.getCSV().copyToArray(model.getNamesArray(), model.getScoresArray());
		
		
		
//----------------------------------------------------------------INTRO PANEL--------------------------------------------------------------	
//definitions
		contentPanel.add(introPanel, "introPanel");
		introPanel.setLayout(null);
		
//background color
		introPanel.setBackground(new Color(0, 59, 111));
		JLabel lblBattleship1 = new JLabel("Battleship");
		lblBattleship1.setForeground(new Color(0, 59, 111));
		lblBattleship1.setFont(font);
		lblBattleship1.setBounds(159, 40, 591, 154);
		introPanel.add(lblBattleship1);
				
//buttons		
		newPlayer = new JButton("Create Player");
		newPlayer.setBackground(new Color(211, 211, 211));
		newPlayer.setForeground(new Color(0, 59, 111));
		newPlayer.setFont(new Font("Rockwell", Font.BOLD, 18));
		newPlayer.setBounds(314, 242, 171, 33);
		introPanel.add(newPlayer);
		newPlayer.addActionListener(al);

		loadPlayer = new JButton("Load Player");
		loadPlayer.setBackground(new Color(211, 211, 211));
		loadPlayer.setForeground(new Color(0, 59, 111));
		loadPlayer.setFont(new Font("Rockwell", Font.BOLD, 18));
		loadPlayer.setBounds(314, 277, 171, 33);
		introPanel.add(loadPlayer);
		loadPlayer.addActionListener(al);
		
		highScores = new JButton("High Scores");
		highScores.setBackground(new Color(211, 211, 211));
		highScores.setForeground(new Color(0, 59, 111));
		highScores.setFont(new Font("Rockwell", Font.BOLD, 18));
		highScores.setBounds(314, 312, 171, 33);
		introPanel.add(highScores);
		highScores.addActionListener(al);
								
		introButtonsPanel = new JPanel();
		introButtonsPanel.setBackground(new Color(0, 59, 111));
		introButtonsPanel.setBounds(311, 237, 178, 112);
		introPanel.add(introButtonsPanel);
		introButtonsPanel.setLayout(null);
		
//picture		
		JLabel introPic1 = new JLabel("");
		introPic1.setIcon(new ImageIcon("images\\Wallpaper.jpg"));
		introPic1.setBounds(0, 0, 794, 571);
		introPanel.add(introPic1);

//----------------------------------------------------------------MODES PANEL--------------------------------------------------------------	
//definitions
		contentPanel.add(modesPanel, "modesPanel");
		modesPanel.setLayout(null);

//background color
		modesPanel.setBackground(new Color(0, 59, 111));

//buttons		
		dMMode = new JButton("Death Match Mode");
		dMMode.setBackground(new Color(211, 211, 211));
		dMMode.setForeground(new Color(0, 59, 111));
		dMMode.setFont(new Font("Rockwell", Font.BOLD, 14));
		dMMode.setBounds(314, 242, 171, 33);
		modesPanel.add(dMMode);
		dMMode.addActionListener(al);

		tLMode = new JButton("Time Limit Mode");
		tLMode.setBackground(new Color(211, 211, 211));
		tLMode.setForeground(new Color(0, 59, 111));
		tLMode.setFont(new Font("Rockwell", Font.BOLD, 14));
		tLMode.setBounds(314, 277, 171, 33);
		modesPanel.add(tLMode);
		tLMode.addActionListener(al);
		
		modesButtonsPanel = new JPanel();
		modesButtonsPanel.setBackground(new Color(0, 59, 111));
		modesButtonsPanel.setBounds(311, 237, 178, 78);
		modesPanel.add(modesButtonsPanel);
		


		JLabel lblBattleship = new JLabel("Battleship");
		lblBattleship.setForeground(new Color(0, 59, 111));
		lblBattleship.setFont(font);
		lblBattleship.setBounds(159, 40, 591, 154);
		modesPanel.add(lblBattleship);
		
//picture		
		JLabel introPic = new JLabel("");
		introPic.setIcon(new ImageIcon("images\\Wallpaper.jpg"));
		introPic.setBounds(0, 0, 794, 571);
		modesPanel.add(introPic);
		
		

//--------------------------------------------------------------HIGH SCORES PANEL--------------------------------------------------------------	
//definitions
		contentPanel.add(highScoresPanel, "highScoresPanel");
		highScoresPanel.setLayout(null);

//background color		
		highScoresPanel.setBackground(new Color(0, 59, 111));

//buttons	
		backHs = new JButton("Back");
		backHs.setBackground(new Color(211, 211, 211));
		backHs.setForeground(new Color(0, 59, 111));
		backHs.setFont(new Font("Rockwell", Font.BOLD, 18));
		backHs.setBounds(324, 468, 171, 33);
		highScoresPanel.add(backHs);
		backHs.addActionListener(al);

//inscriptions		
		JLabel highScoresLabel = new JLabel("HIGH SCORES");
		highScoresLabel.setForeground(new Color(211, 211, 211));
		highScoresLabel.setFont(font2);
		highScoresLabel.setBounds(247, 24, 334, 84);
		highScoresPanel.add(highScoresLabel);

		JPanel line = new JPanel();
		line.setBackground(new Color(211, 211, 211));
		line.setBounds(189, 105, 428, 3);
		highScoresPanel.add(line);

		JLabel nameLabel = new JLabel("Name");
		nameLabel.setForeground(new Color(211, 211, 211));
		nameLabel.setFont(font1);
		nameLabel.setBounds(225, 136, 126, 45);
		highScoresPanel.add(nameLabel);

		JLabel scoreLabel = new JLabel("Score");
		scoreLabel.setForeground(new Color(211, 211, 211));
		scoreLabel.setFont(font1);
		scoreLabel.setBounds(450, 136, 149, 45);
		highScoresPanel.add(scoreLabel);

		highScoresTable = new JTable();
		highScoresTable.setEnabled(false);
		highScoresTable.setColumnSelectionAllowed(true);
		highScoresTable.setCellSelectionEnabled(true);
		highScoresTable.setLocation(0, 0);
		highScoresTableScroll = new ScrollPane();
		highScoresTableScroll.setBounds(155, 187, 507, 210);
		highScoresTableScroll.add(highScoresTable);
		
		highScoresPanel.add(highScoresTableScroll);

		DefaultTableModel dtm1 = new DefaultTableModel(0,2);
		for(int i = 0; i<model.getNamesArray().size(); i++) 
	    	dtm1.addRow(new Object[] {model.getNamesArray().get(i),model.getScoresArray().get(i) });
	    
		highScoresTable.setFont(new Font("Rockwell", Font.PLAIN, 30));
		highScoresTable.setForeground(new Color(0, 59, 111));
		highScoresTable.setBackground(new Color(211, 211, 211));
		highScoresTable.setRowSelectionAllowed(true);

		highScoresTable.setModel(dtm1);
		
		highScoresTable.getColumnModel().getColumn(0).setPreferredWidth(150);
		highScoresTable.getColumnModel().getColumn(0).setMinWidth(150);
		highScoresTable.getColumnModel().getColumn(1).setPreferredWidth(150);
		highScoresTable.getColumnModel().getColumn(1).setMinWidth(150);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		highScoresTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		highScoresTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		highScoresTable.setRowHeight(50);
		highScoresTable.setBounds(155, 167, 507, 250);
		 

//----------------------------------------------------------------NEW PLAYER PANEL--------------------------------------------------------------	
//definitions		
		contentPanel.add(newPlayerPanel, "newPlayerPanel");
		newPlayerPanel.setLayout(null);
//background color
		newPlayerPanel.setBackground(new Color(0, 59, 111));
//buttons		
		backToIntro = new JButton("Back");
		backToIntro.setBackground(new Color(211, 211, 211));
		backToIntro.setForeground(new Color(0, 59, 111));
		backToIntro.setFont(new Font("Rockwell", Font.BOLD, 18));
		backToIntro.setBounds(495, 345, 171, 33);
		newPlayerPanel.add(backToIntro);
		backToIntro.addActionListener(al);

		okHs = new JButton("OK");
		okHs.setBackground(new Color(211, 211, 211));
		okHs.setForeground(new Color(0, 59, 111));
		okHs.setFont(new Font("Rockwell", Font.BOLD, 18));
		okHs.setBounds(175, 345, 171, 33);
		newPlayerPanel.add(okHs);

		textField = new JTextField();
		textField.setBounds(175, 175, 491, 56);
		textField.setFont(new Font("Rockwell", Font.BOLD, 30));
		textField.setBackground(new Color(211, 211, 211));
		textField.setForeground(new Color(0, 59, 111));
		newPlayerPanel.add(textField);
		textField.setColumns(10);

		lblkeySensativeNew = new JLabel("*Only letters or numbers\r\n");
		lblkeySensativeNew.setBounds(175, 242, 491, 24);
		lblkeySensativeNew.setForeground(new Color(211, 211, 211));
		lblkeySensativeNew.setFont(new Font("Rockwell", Font.BOLD, 17));
		newPlayerPanel.add(lblkeySensativeNew);
		
		JLabel lblthereIsA = new JLabel("*There is a difference between a capital and a small letter \r\n");
		lblthereIsA.setForeground(new Color(211, 211, 211));
		lblthereIsA.setFont(new Font("Rockwell", Font.BOLD, 17));
		lblthereIsA.setBounds(175, 268, 591, 24);
		newPlayerPanel.add(lblthereIsA);

		okHs.addActionListener(al);
//inscriptions			
		JLabel createLabel = new JLabel("New Player");
		createLabel.setForeground(new Color(211, 211, 211));
		createLabel.setFont(font2);
		createLabel.setBounds(247, 24, 334, 84);
		newPlayerPanel.add(createLabel);

		JPanel line1 = new JPanel();
		line1.setBackground(new Color(211, 211, 211));
		line1.setBounds(189, 105, 428, 3);
		newPlayerPanel.add(line1);
		
		

//--------------------------------------------------------------LOAD PLAYER PANEL--------------------------------------------------------------	
//definitions
		contentPanel.add(loadPlayerPanel, "loadPlayerPanel");
		loadPlayerPanel.setLayout(null);

//background color		
		loadPlayerPanel.setBackground(new Color(0, 59, 111));

//buttons	
		backL = new JButton("Back");
		backL.setBackground(new Color(211, 211, 211));
		backL.setForeground(new Color(0, 59, 111));
		backL.setFont(new Font("Rockwell", Font.BOLD, 18));
		backL.setBounds(491, 445, 171, 33);
		loadPlayerPanel.add(backL);
		backL.addActionListener(al);

		okL = new JButton("OK");
		okL.setBackground(new Color(211, 211, 211));
		okL.setForeground(new Color(0, 59, 111));
		okL.setFont(new Font("Rockwell", Font.BOLD, 18));
		okL.setBounds(155, 445, 171, 33);
		loadPlayerPanel.add(okL);
		okL.addActionListener(al);

//inscriptions		
		JLabel loadPlayerLabel = new JLabel("LOAD PLAYER");
		loadPlayerLabel.setForeground(new Color(211, 211, 211));
		loadPlayerLabel.setFont(font2);
		loadPlayerLabel.setBounds(247, 24, 334, 84);
		loadPlayerPanel.add(loadPlayerLabel);

		JPanel line2 = new JPanel();
		line2.setBackground(new Color(211, 211, 211));
		line2.setBounds(189, 105, 428, 3);
		loadPlayerPanel.add(line2);

		JLabel nameLabel1 = new JLabel("Name");
		nameLabel1.setForeground(new Color(211, 211, 211));
		nameLabel1.setFont(font1);
		nameLabel1.setBounds(331, 136, 155, 45);
		loadPlayerPanel.add(nameLabel1);

// table
		
		loadPlayerTable = new JTable();
		loadPlayerTable.setLocation(0, 0);
		loadPlayerTableScroll = new ScrollPane();
		loadPlayerTableScroll.setBounds(155, 187, 507, 210);
		loadPlayerTableScroll.add(loadPlayerTable);
		loadPlayerPanel.add(loadPlayerTableScroll);

		DefaultTableModel dtm = new DefaultTableModel(0,1);
		for(int i = 0; i<model.getNamesArray().size(); i++) 
	    	dtm.addRow(new Object[] {model.getNamesArray().get(i)});
	    
		loadPlayerTable.setFont(new Font("Rockwell", Font.PLAIN, 30));
		loadPlayerTable.setForeground(new Color(0, 59, 111));
		loadPlayerTable.setBackground(new Color(211, 211, 211));
		loadPlayerTable.setRowSelectionAllowed(true);

		loadPlayerTable.setModel(dtm);
		
		loadPlayerTable.getColumnModel().getColumn(0).setPreferredWidth(150);
		loadPlayerTable.getColumnModel().getColumn(0).setMinWidth(150);

		DefaultTableCellRenderer centerRenderer1 = new DefaultTableCellRenderer();
		centerRenderer1.setHorizontalAlignment(JLabel.CENTER);
		loadPlayerTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		loadPlayerTable.setRowHeight(50);
		loadPlayerTable.setBounds(155, 167, 507, 250);
		loadPlayerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	

	}	

	

//-------------------------------------------------------------actionListener----------------------------------------------------------------
	public class actionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			JButton src = (JButton) event.getSource();
			if (src.equals(newPlayer)) {
				textField.setText("");
				cardLayout.show(contentPanel, "newPlayerPanel");
			}
			if (src.equals(highScores)) {
				cardLayout.show(contentPanel, "highScoresPanel");
			}
			if (src.equals(backHs))
				cardLayout.show(contentPanel, "introPanel");
			if (src.equals(backToIntro) || src.equals(backL))
				cardLayout.show(contentPanel, "introPanel");
			if (src.equals(loadPlayer)) {
				
				loadPlayerTable.getSelectionModel().clearSelection();
				cardLayout.show(contentPanel, "loadPlayerPanel");
			}
			if (src.equals(okL)) {
				if (loadPlayerTable.getSelectionModel().isSelectionEmpty())
					JOptionPane.showMessageDialog(newPlayerPanel, "Name is not selected", "Error", JOptionPane.CANCEL_OPTION);
				else {
					model.setName((String) loadPlayerTable.getValueAt(loadPlayerTable.getSelectedRow(), loadPlayerTable.getSelectedColumn()));
					cardLayout.show(contentPanel, "modesPanel");
				}
			}
			if (src.equals(okHs)) {
				
				if (textField.getText().equals(""))
					JOptionPane.showMessageDialog(newPlayerPanel, "Name is not entered", "Error", JOptionPane.CANCEL_OPTION);
				else if (model.getNamesArray().contains(textField.getText()))
					JOptionPane.showMessageDialog(newPlayerPanel, "Name is already taken", "Error", JOptionPane.CANCEL_OPTION);
				else {
					if(!(isUsername(textField.getText()))) 
						JOptionPane.showMessageDialog(newPlayerPanel, "Invalid Name", "Error", JOptionPane.CANCEL_OPTION);
					else
					{
						model.setName(textField.getText());
						cardLayout.show(contentPanel, "modesPanel");
					try {
						model.getCSV().writeDataLine(model.getName(), 0);
						model.getNamesArray().add(model.getName());
						model.getScoresArray().add(0);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
				}
			}
			if (src.equals(dMMode)||src.equals(tLMode)) {
				if (src.equals(tLMode))
					model.setTLimitMode();
				GameUI view = new GameUI(model);
				BattleGame controller = new BattleGame(view, model);
				view.setGameListener(controller);
				hideMenu();
			}
		
			
		}
	}
}

