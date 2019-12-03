package com.battleship.engine;

import javax.swing.SwingUtilities;

import com.battleship.gui.Menu;

public class BattleGameMain {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				runApp();
			}
			
		});
	}

	public static void runApp() {
		
		Menu menu = new Menu();
		menu.getModel().setMenu(menu);
		
	}

}
