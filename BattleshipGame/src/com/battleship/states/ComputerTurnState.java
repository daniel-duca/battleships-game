package com.battleship.states;

import java.awt.Point;
import java.lang.Math;
import java.util.Random;
import com.battleship.pieces.*;
import com.battleship.player.Player;
import com.battleship.player.Player.PlayerType;
import com.battleship.engine.BattleGame;
import com.battleship.engine.BattleGame.Direction;
import com.battleship.engine.BattleGame.Rotation;
import com.battleship.gui.Countdown;
import com.battleship.gui.TimerLimit;


public class ComputerTurnState extends State {
	
	
	private int isTargetFound;
	private Point NearestShipToTarget;
	private Point targetPoint;
	private static ComputerTurnState _instance;
	private boolean firstRound;
	

    private ComputerTurnState()
    {
        if(_instance != null)
        {
            return;
        }

        _instance = this;
    }
    
    public static ComputerTurnState GetInstance()
    {
        if(_instance == null)
        {
            new ComputerTurnState();
        }

        return _instance;
    }



	
	public void OnEnterState(BattleGame game) {
		if (game.checkGameState())
			game.ChangeState(GameOverState.GetInstance());
		
		/*
		 *                      AI Hunting Algorithm 
		 *  this algorithm generates the targets on the range(by using two array lists with all ships location (the 
		 *  function of this in BattleGame class)) and gives order to shot, if there are not targets on the range, the 
		 *  the algorithm trying to calculate the location of the enemy ship one step from shooting range and make move to and shot, other the 
		 *  algorithm gives order for motion to one of ships and after this checks again for enemy ships in range and 
		 *  shot them, if no targets on the range it shots  for switch the turn to human player. 
		 */
		
		
		Random random = new Random();
		System.out.println("OnEnterState ComputerTurnState");
		Countdown.GetInstance().start(game);
		firstRound = true;
		targetPoint = targetGenerator(game , firstRound);
		firstRound = false;
		game.getGame().setEnabled(false);

		if (isTargetFound==1) {
			System.out.println("The exect target found and it is :  " + targetPoint + "\n");
			targetCharging (game , targetPoint);
		}
		else if (isTargetFound ==2) {
			moveToTargetAndShot(game , targetPoint);

		}
		else if (isTargetFound==0)  {
			// IF no target near to you, Move yourself random and calculate target again 
			game.updateAllShipsLocationAndHealth();
			for ( int i = 0; i <game.aiPlayerShipsLocations.size(); i++) {
				int randomIndex = random.nextInt(game.aiPlayerShipsLocations.size());
				Point aiShipPoint = game.aiPlayerShipsLocations.get(randomIndex);
				Ship ship = (Ship)game.getModel().getCurrentCell((int)aiShipPoint.getX(), (int)aiShipPoint.getY());
				if (moveAndShot(game, aiShipPoint, ship ,Direction.Right , Direction.Left)) {
					return;
				}
				else {
					targetPoint = targetGenerator(game , firstRound);
					targetCharging (game , targetPoint);
					return;
				}
			}
			
		}
	}
	
	public void moveToTargetAndShot(BattleGame game , Point targetPoint) {
		System.out.println("moveToTargetAndShot");
		int xOfAIShip  = (int)NearestShipToTarget.getX();
		int yOfAIShip = (int)NearestShipToTarget.getY();
		int xOfTargetPoint  = (int)targetPoint.getX();
		int yOfTargetPoint = (int)targetPoint.getY();
		Ship ship = (Ship)game.getModel().getCurrentCell(xOfAIShip, yOfAIShip);
		game.getPlayer().setSelectCellToHit(NearestShipToTarget);
		if(xOfAIShip <= xOfTargetPoint) {
			if (moveAndShot(game, NearestShipToTarget, ship , Direction.Right , Direction.Left)) {
				return;
			}
			else {
				targetPoint = targetGenerator(game, firstRound);
				targetCharging (game , targetPoint);
				return;
			}		
		}
		else if (xOfAIShip > xOfTargetPoint) {
			if (moveAndShot(game, NearestShipToTarget, ship, Direction.Left , Direction.Right)) {
				return;
			}
			else {
				targetPoint = targetGenerator(game, firstRound);
				targetCharging (game , targetPoint);
				return;
			}		
		}
		
	}
	
	public boolean moveAndShot(BattleGame game , Point aiShipPoint , Ship ship , Direction firstDirection , Direction secondDirection) {

		
		if (game.isHorizontalMovePossible(aiShipPoint, firstDirection, ship)) {
			game.getPlayer().setSelectCellToHit(aiShipPoint);
			if (firstDirection == Direction.Right) {
				game.getPlayer().moveRight(game);
			}
			else {
				game.getPlayer().moveLeft(game);
				
			}
			targetCharging(game, targetGenerator(game , firstRound));
			return true;
		}
		else if (game.isLeftRotationPossible(aiShipPoint, Rotation.rotateLeft, ship)) {
			game.getPlayer().setSelectCellToHit(aiShipPoint);
			game.getPlayer().rotateLeft(game);
			targetCharging(game, targetGenerator(game, firstRound));
			return true;
		}

		else if(game.isVerticalMovePossible(aiShipPoint, Direction.Up, ship)) {
			game.getPlayer().setSelectCellToHit(aiShipPoint);
			game.getPlayer().moveUp(game);
			targetCharging(game, targetGenerator(game, firstRound));
			return true;
		}
		else if (game.isHorizontalMovePossible(aiShipPoint, secondDirection, ship)) {
			game.getPlayer().setSelectCellToHit(aiShipPoint);
			if (firstDirection == Direction.Right) {
				game.getPlayer().moveRight(game);
			}
			else {
				game.getPlayer().moveLeft(game);
				
			}
			targetCharging(game, targetGenerator(game, firstRound));
			return true;
		}
		else if (game.isVerticalMovePossible(aiShipPoint, Direction.Down, ship)) {
			game.getPlayer().setSelectCellToHit(aiShipPoint);
			game.getPlayer().moveDown(game);
			targetCharging(game, targetGenerator(game , firstRound));
			return true;
		}
		else if (game.isRightRotationPossible(aiShipPoint, Rotation.rotateRight, ship)) {
			game.getPlayer().setSelectCellToHit(aiShipPoint);
			game.getPlayer().rotateRight(game);
			targetCharging(game, targetGenerator(game,firstRound));
			return true;
		}
		
		return false;

	}
	
	public Point targetGenerator(BattleGame game , boolean firstRound) {
		isTargetFound = 0;
		game.updateAllShipsLocationAndHealth();
		Random random = new Random();
		int AIShipX, AIShipY, HumanShipX,HumanShipY,ShipLen;
		for ( Point aiShipPoint : game.aiPlayerShipsLocations) {
			for ( Point humanShipPoint : game.humanPlayerShipsLocations) {
				
				Ship ship  = (Ship)game.getModel().getCurrentCell((int)aiShipPoint.getX(), (int)aiShipPoint.getY());
				
				if ((Math.abs((int)(aiShipPoint.getX() - humanShipPoint.getX())) <= ship.getLength())
						&& (Math.abs((int)(aiShipPoint.getY() - humanShipPoint.getY())) <= ship.getLength()))
				{	
					isTargetFound = 1;
					return (new Point((int)humanShipPoint.getX(),(int)humanShipPoint.getY()));
				}
			}
		}
		if (firstRound) {
			for ( Point aiShipPoint : game.aiPlayerShipsLocations) {
				for ( Point humanShipPoint : game.humanPlayerShipsLocations) {
					AIShipX = (int)aiShipPoint.getX();
					AIShipY = (int)aiShipPoint.getY();
					HumanShipX = (int)humanShipPoint.getX();
					HumanShipY = (int)humanShipPoint.getY();
					Ship ship  = (Ship)game.getModel().getCurrentCell(AIShipX, AIShipY);

					ShipLen = ship.getLength();
	
					if 	((Math.abs(AIShipX - HumanShipX) == ShipLen+1 && Math.abs(AIShipY - HumanShipY) <= ShipLen+1) || (Math.abs(AIShipX - HumanShipX) <= ShipLen+1 && Math.abs(AIShipY - HumanShipY) == ShipLen+1))
					{
						isTargetFound = 2;
						//System.out.println("ShipLen: " + ShipLen);
						//System.out.println("(Math.abs(AIShipX - HumanShipX) = " + Math.abs(AIShipX - HumanShipX));
						//System.out.println("Math.abs(AIShipY - HumanShipY) = " + Math.abs(AIShipY - HumanShipY));
						//System.out.println("Math.abs(AIShipX - HumanShipX) = " + Math.abs(AIShipX - HumanShipX));
						//System.out.println(" Math.abs(AIShipY - HumanShipY) = " + Math.abs(AIShipY - HumanShipY));
						//System.out.println("isTargetFound = " + isTargetFound);
						NearestShipToTarget = new Point(AIShipX,AIShipY);
						//System.out.println("NearestShipToTarget: " + NearestShipToTarget);
						//System.out.println("target from target generator: " + new Point(HumanShipX,HumanShipY));
						firstRound = false;
						return (new Point(HumanShipX,HumanShipY));
					}
					
				}
			}
		}
		int randomIndex = random.nextInt(game.aiPlayerShipsLocations.size());
		Point aiShipPoint = game.aiPlayerShipsLocations.get(randomIndex);
		int x =(int)aiShipPoint.getX();
		int y = (int)aiShipPoint.getY();
		return (new Point(x+1 , y+1));

	}
	
	public void targetCharging (BattleGame game , Point p) {
		Random random = new Random();
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	game.getPlayer().setSelectCellToHit(new Point((int)p.getX() , (int)p.getY()));
		            	game.ShotTarget(game.getPlayer());
		            	game.ChangeState(PlayerTurnState.GetInstance());
	
		            }
		        }, 
		        random.nextInt(4)*1000 +1
		);
	}
	
	@Override()
	public void OnExitState(BattleGame game) {
		System.out.println("OnExitState ComputerTurnState");
		Countdown.GetInstance().stop();
		game.getGame().setEnabled(true);

		//int a = game.getShipHealthByPlayerType(BattleType.Destroyer, PlayerType.Human);
		//int b = game.getShipHealthByPlayerType(BattleType.Destroyer,PlayerType.Computer);
		//System.out.println("a = " + a + "\n");
		//System.out.println("b = " + b + "\n");
		//int humanScore = game.playerScoreCalculator(PlayerType.Human);
		//System.out.println("humanScore!!!!!!!!!!! = " + humanScore);
		
	}
	
	@Override()
	public void OnFireShip(BattleGame game, boolean isHit) { 
		
	}
	
	@Override()
	public void OnTimeExpired(BattleGame game) {
		
	}

	@Override
	public StateType getType() {
		return StateType.ComputerTurn;
	}
	
	
}





