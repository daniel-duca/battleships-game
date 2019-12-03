package com.battleship.engine;

import java.awt.Point;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;
import com.battleship.gui.BoardPanel;
import com.battleship.gui.GameUI;
import com.battleship.model.BattleShipModel;
import com.battleship.model.CSV;
import com.battleship.pieces.BattleType;
import com.battleship.pieces.Battleship;
import com.battleship.pieces.Carrier;
import com.battleship.pieces.Cell;
import com.battleship.pieces.Cruiser;
import com.battleship.pieces.Destroyer;
import com.battleship.pieces.EmptyCell;
import com.battleship.pieces.Field;
import com.battleship.pieces.Ship;
import com.battleship.pieces.ShipFactory;
import com.battleship.pieces.Submarine;
import com.battleship.player.AIPlayer;
import com.battleship.player.HumanPlayer;
import com.battleship.player.Player;
import com.battleship.player.Player.PlayerType;
import com.battleship.states.ComputerTurnState;
import com.battleship.states.IdleState;
import com.battleship.states.PlayerTurnState;
import com.battleship.states.State;
import com.battleship.states.State.StateType;

/*
 *       This class contains the main and almost all game logic
 */
public class BattleGame implements GameListener {
	
	public ArrayList<Point> humanPlayerShipsLocations = new ArrayList<Point>();
	public ArrayList<Point> aiPlayerShipsLocations = new ArrayList<Point>();

	private State currentState = IdleState.GetInstance();
	private final GameUI gameUI;
	public final int MAXIMUM_SCORE = 20;
	private boolean firstMove;
	CSV csv = new CSV();
	
	BattleShipModel model;

	public BattleShipModel getModel() {
		return model;
	}
	
	public GameUI getGame() {
		return gameUI;
	}

	public int humanScore = 10;
	public int aiScore = 10;
	//private Cell[][] gameField;
	public boolean isFirstGameInit = false;
	Player humanPlayer = new HumanPlayer();
	Player aiPlayer = new AIPlayer();
	public enum Direction{
		Left,
		Right,
		Up,
		Down
	}
	public enum Rotation{
		rotateLeft,
		rotateRight,
		rotateLeftForAI,
		rotateRightForAI
		
	}
/*
	public BattleGame() {
		currentState.OnEnterState(this);
	}
*/
	public BattleGame(GameUI view, BattleShipModel model) {
		this.gameUI = view;
		this.model = model;
		currentState.OnEnterState(this);
	}

	public void fillEmptyBoard() {
		Cell[][] cells = new Cell[BoardPanel.ROW_COUNT][BoardPanel.ROW_COUNT];

		for (Cell[] row : cells) {
			Arrays.fill(row, new EmptyCell());
		}
		
		model.setCells(cells);
	}

	public void generateShips() {
		Ship[] ships = new Ship[5];
		ships[0] = new Carrier();
		ships[1] = new Battleship();
		ships[2] = new Cruiser();
		ships[3] = new Submarine();
		ships[4] = new Destroyer();

		Player[] players = new Player[2];
		players[0] = humanPlayer;
		players[1] = aiPlayer;
		Field location;

		for (Player player : players) {
			for (int i = 0; i < ships.length; i++) {
				do {
					location = player.placeShip(ships[i]);
				} while (!isOccupied(player, ships[i], location));
				setShip(ships[i], location, player);
			}
		}
		this.isFirstGameInit = true;
		gameUI.repaintBoard();
		
	}

	private boolean isOccupied(Player player, Ship ship, Field location) {
		if ((model.getCurrentCell(location.getX(), location.getY()) instanceof EmptyCell)
				&& checkFieldLength(ship.getLength(), player.limitX, location)
				&& checkFieldArea(ship.getLength(), location))
			return true;

		return false;
	}

	private boolean checkFieldLength(int length, int limitX, Field location) {
		if (location.getX() < limitX && location.getY() < BoardPanel.ROW_COUNT) {
			// check ship length from this position
			if (location.isHorizontal()) {
				if ((location.getX() + length - 1) < limitX) {
					return true;
				}
			} else {
				if ((location.getY() + length - 1) < BoardPanel.ROW_COUNT) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkFieldArea(int length, Field location) {
		if (location.isHorizontal()) {
			// check left head and tail horizontally
			if (!(model.getCurrentCell(location.getX() + length, location.getY()) instanceof EmptyCell)
					|| !(model.getCurrentCell(location.getX() - 1, location.getY()) instanceof EmptyCell)) {
				return false;
			}
		} else {
			// check left head and tail vertically
			if (!(model.getCurrentCell(location.getX(), location.getY() + length) instanceof EmptyCell)
					|| !(model.getCurrentCell(location.getX(), location.getY() - 1) instanceof EmptyCell)) {
				return false;
			}
		}

		// check above and beyond
		for (int i = 0; i < length; i++) {
			if (location.isHorizontal()) {
				// check left and right side horizontally
				if (!(model.getCurrentCell(location.getX() + i, location.getY() - 1) instanceof EmptyCell)
						|| !(model.getCurrentCell(location.getX() + i, location.getY() + 1) instanceof EmptyCell)) {
					return false;
				}
			} else {
				// check left and right side vertically
				if (!(model.getCurrentCell(location.getX() - 1, location.getY() + i) instanceof EmptyCell)
						|| !(model.getCurrentCell(location.getX() + 1, location.getY() + i) instanceof EmptyCell)) {
					return false;
				}
			}
		}

		return true;
	}

	

	public void setShip(Ship ship, Field location, Player player) {
		Cell cell = null;
		for (int i = 0; i < ship.getLength(); i++) {
			if (location.isHorizontal()) {
				if (i == 0)
					cell = ShipFactory.createShip(ship.getType(), new Field(location.getX() + i, location.getY(), location.isHorizontal()), player);
				setField(location.getX() + i, location.getY(), cell);
			} else {
				if (i == 0)
					cell = ShipFactory.createShip(ship.getType(), new Field(location.getX() + i, location.getY(), location.isHorizontal()), player);
				setField(location.getX(), location.getY() + i, cell);
			}
		}
	}

	public void setField(int x, int y, Cell ship) {
		if (x >= 0 && y >= 0 && x < BoardPanel.ROW_COUNT && y < BoardPanel.ROW_COUNT) {
			model.getCells()[y][x] = ship;
		}
	}

	public boolean isFirstMove() {
		return firstMove;
	}

	public void setFirstMove(boolean firstMove) {
		this.firstMove = firstMove;
	}

	public void ChangeState(State newstate) {
		if (currentState != null)
			currentState.OnExitState(this);
		currentState = newstate;
		currentState.OnEnterState(this);

	}

	public void FireShip() {
		Random random = new Random();
		int index = random.nextInt(3);

		currentState.OnFireShip(this, index % 3 == 0);

	}

	public void ShotTarget(Player player) {
		Point point = player.getTarget();
		System.out.println("The shot destination is: " + (int)point.getX()+"-"+(int)point.getY());
		Cell target = model.getCurrentCell((int) point.getX(), (int) point.getY());

		if (!(target instanceof EmptyCell)) {
			Ship ship = (Ship) target;
			ship.shot();
			System.out.println("ship " + ship + " size: "+ ship.getLength() + " demaged");
			if (ship.sunked()) {
				System.out.println("ship " + ship + " size: "+ ship.getLength() +" sunked");
				removeShipFromBoard(ship);
			}
		} else {
			EmptyCell emptyCell = (EmptyCell) target;
			emptyCell.shot();
		}
		updateAllShipsLocationAndHealth();
		drawScore();
	}

	public void drawScore() {
		gameUI.repaintBoard();
	}

	public Player getPlayer() {
		return currentState.getType()==StateType.ComputerTurn ? (AIPlayer) this.aiPlayer : (HumanPlayer)this.humanPlayer;
	}
	
	public State getOpponentState() {
		return currentState.getType()==StateType.ComputerTurn ? PlayerTurnState.GetInstance() : ComputerTurnState.GetInstance();
	}
	
	public boolean inGameOver() {
		return currentState.getType() == StateType.GameOver;
	}
	
	public boolean checkGameState() 
	{
		if (playerScoreCalculator(PlayerType.Human) == MAXIMUM_SCORE || playerScoreCalculator(PlayerType.Computer) == MAXIMUM_SCORE)
			return true;
		return false;

	}
	
	public boolean inPlayingMode() {
		return currentState.getType()== StateType.ComputerTurn || currentState.getType() == StateType.PlayerTurn;
	}
	
	public boolean inFirstInitilzeGame() {
		return currentState.getType() == StateType.Initilize;
	}
	
	private void removeShipFromBoard(Ship ship) {
		Point leftEdgePoint;
		Point upperEdgePoint;
		int x = ship.getField().getX();
		int y = ship.getField().getY();
		int lastShipCellLocation;

		if (ship.getField().isHorizontal()) {
			leftEdgePoint = getFrontCell(Direction.Left, x, y);
			lastShipCellLocation = ship.getLength()+x;
			x = (int)leftEdgePoint.getX();
			y = (int)leftEdgePoint.getY();
			while(++x<lastShipCellLocation) {
				System.out.println("removing of: " + x + "-" + y);
				setField(x, y, new EmptyCell());
			}
		} else
		{
			upperEdgePoint = getFrontCell(Direction.Up, x, y);
			x = (int)upperEdgePoint.getX();
			y = (int)upperEdgePoint.getY();
			lastShipCellLocation = ship.getLength()+y;
			while(++y<=lastShipCellLocation) {
				System.out.println("removing of: " + x + "-" + y);
				setField(x, y, new EmptyCell());
			}
		}
	}

	
	/*
	 * Ship health controller
	 */
	
	public int getShipHealthByPlayerType(BattleType battleShipType , PlayerType playerType){
		
		//System.out.println("BattleShipType = " + battleShipType);
		//System.out.println("playerType = " + playerType);
		Cell cell = null;
		if (playerType == PlayerType.Human)
		{
			for ( Point ShipPoint : humanPlayerShipsLocations) 
			{
				cell = getModel().getCurrentCell((int)ShipPoint.getX(), (int)ShipPoint.getY());
				if (cell instanceof EmptyCell) continue;
				Ship ship  = (Ship)cell;
				//System.out.println("ship.getType() = " + ship.getType());
				if (ship.getType() == battleShipType )
					return ship.getShipParts();
			}
		}
		else
		{
			for ( Point ShipPoint : aiPlayerShipsLocations) {
				cell = getModel().getCurrentCell((int)ShipPoint.getX(), (int)ShipPoint.getY());
				if (cell instanceof EmptyCell) continue;
				Ship ship  = (Ship)cell;
				//System.out.println("ship.getType() = " + ship.getType());
				if (ship.getType() == battleShipType )
					return ship.getShipParts();
				}
		}
		return 0;
	}
	
	public int playerScoreCalculator(PlayerType playerType) 
	{
		if (playerType.toString() == "Human")
		{
			 return (MAXIMUM_SCORE - (getShipHealthByPlayerType(BattleType.Cruiser , PlayerType.Computer)+
					getShipHealthByPlayerType(BattleType.Submarine , PlayerType.Computer)+
					getShipHealthByPlayerType(BattleType.Battleship , PlayerType.Computer)+
					getShipHealthByPlayerType(BattleType.Carrier , PlayerType.Computer)+
					getShipHealthByPlayerType(BattleType.Destroyer , PlayerType.Computer)));

		}
		else
		{
			 return (MAXIMUM_SCORE - (getShipHealthByPlayerType(BattleType.Cruiser , PlayerType.Human)+
						getShipHealthByPlayerType(BattleType.Submarine , PlayerType.Human)+
						getShipHealthByPlayerType(BattleType.Battleship , PlayerType.Human)+
						getShipHealthByPlayerType(BattleType.Carrier , PlayerType.Human)+
						getShipHealthByPlayerType(BattleType.Destroyer , PlayerType.Human)));
		}

	}
	
	public String showWinner() {
		String winner = null;
		if(gameUI.getGame().playerScoreCalculator(PlayerType.Human)>gameUI.getGame().playerScoreCalculator(PlayerType.Computer))
			winner = model.getName();
		else if (gameUI.getGame().playerScoreCalculator(PlayerType.Human)==gameUI.getGame().playerScoreCalculator(PlayerType.Computer))
			winner = "Draw";
		else 
			winner = "Computer";
		
		return winner;
	}
		
	
	/*
	 *            Helper functions   (motion ability validators)
	 */
	
	public Point getFrontCell(Direction direction, int x, int y) {
		Cell cell = null;
		Ship ship = (Ship)this.model.getCurrentCell(x, y);
		Ship otherShip = null;
				
		switch (direction) {
		case Down:
			while(!(cell instanceof EmptyCell)) {
				cell = this.model.getCurrentCell(x, ++y);
				if (!(cell instanceof EmptyCell)) {
					otherShip = (Ship)cell;
					if (ship.getType() != otherShip.getType())
						break;
				}
			}
			return new Point(x, y);

		case Up:
			while(!(cell instanceof EmptyCell)) {
				cell = this.model.getCurrentCell(x, --y);
				if (!(cell instanceof EmptyCell)) {
					otherShip = (Ship)cell;
					if (ship.getType() != otherShip.getType())
						break;
				}
			}
			return new Point(x, y);
			
		case Left:
			while(!(cell instanceof EmptyCell)) {
				cell = this.model.getCurrentCell(--x, y);
				if (!(cell instanceof EmptyCell)) {
					otherShip = (Ship)cell;
					if (ship.getType() != otherShip.getType())
						break;
				}
			}
			return new Point(x, y);
			
		case Right:
			while(!(cell instanceof EmptyCell)) {
				cell = this.model.getCurrentCell(++x, y);
				if (!(cell instanceof EmptyCell)) {
					otherShip = (Ship)cell;
					if (ship.getType() != otherShip.getType())
						break;
				}
			}
			return new Point(x, y);

		default:
			break;
		}
		
		return null;
	}
	public boolean checkNextCell(Direction direction, Point p ) {
		Cell nextCell;

	
		switch (direction) {
		case Down:
			nextCell = this.model.getCurrentCell((int)p.getX(), (int)p.getY()+1);
			if (nextCell instanceof EmptyCell) {
				return true;
			}
			break;
		case Up:
			nextCell = this.model.getCurrentCell((int)p.getX()-1, (int)p.getY()-1);
			if (nextCell instanceof EmptyCell) {
				return true;
			}
			break;
		case Left:
			nextCell = this.model.getCurrentCell((int)p.getX()-1, (int)p.getY());
			if (nextCell instanceof EmptyCell) {
				return true;
			}
			break;
		case Right:
			nextCell = this.model.getCurrentCell((int)p.getX()+1, (int)p.getY());
			if (nextCell instanceof EmptyCell) {
				return true;
			}
			break;

		default:
			break;
		}
		return false;
	}
	public boolean isEnoughFreeSpaceForRotation(Point tailPoint, Rotation rotationDirection , Ship ship ) {
		int x = (int)tailPoint.getX(), y = (int)tailPoint.getY()-1;
		Cell nextCell = null;
		int i;

		switch (rotationDirection) {
		case rotateLeft:
			x = (int)tailPoint.getX();
			y = (int)tailPoint.getY()-1;
			i = 1;
			for(x = x-i ;x > (int)tailPoint.getX()  - ship.getLength() ; i++ ) {
				
				Cell cell = this.model.getCurrentCell(x, y);
				Cell upperCell = this.model.getCurrentCell(x, y - 1);
				Cell lowerCell = this.model.getCurrentCell(x, y + 1);
				
				if ((cell instanceof Ship || !checkBounderies(new Point(x,y))) || upperCell instanceof Ship  || lowerCell instanceof Ship ) {
					return false;
				}
				x--;
			}
			nextCell = this.model.getCurrentCell(x, y);
			if (nextCell instanceof Ship ) {
				return false;
			}
			
			break;
			
		case rotateRight:
			x = (int)tailPoint.getX()-1;
			y = (int)tailPoint.getY();
			i = 1;
			for(y = y-i ;y > (int)tailPoint.getY()  - ship.getLength() ; i++ ) {
				
				Cell cell = this.model.getCurrentCell(x, y);
				Cell leftCell = this.model.getCurrentCell(x-1, y);
				Cell rightCell = this.model.getCurrentCell(x+1, y);
				if ((cell instanceof Ship || !checkBounderies(new Point(x,y))) || leftCell instanceof Ship  || rightCell instanceof Ship ) {
					return false;
				}
				y--;
			}
			nextCell = this.model.getCurrentCell(x, y);
			if (nextCell instanceof Ship ) {
				return false;
			}
		case rotateRightForAI:
			x = (int)tailPoint.getX();
			y = (int)tailPoint.getY()-1;
			i = 1;
			for(x = x+i ;x < (int)tailPoint.getX()  + ship.getLength() ; i++ ) {
				
				Cell cell = this.model.getCurrentCell(x, y);
				Cell upperCell = this.model.getCurrentCell(x, y - 1);
				Cell lowerCell = this.model.getCurrentCell(x, y + 1);
				
				if ((cell instanceof Ship || !checkBounderies(new Point(x,y))) || upperCell instanceof Ship  || lowerCell instanceof Ship ) {
					return false;
				}
				x++;
			}
			nextCell = this.model.getCurrentCell(x, y);
			if (nextCell instanceof Ship ) {
				return false;
			}
		default:
			break;
		}	
		
		return true;
	}

	public boolean checkBounderies(Point p) {
		int x = (int)p.getX(), y = (int)p.getY();
		if ((x<0 || x>=BoardPanel.ROW_COUNT)||(y<0 || y>=BoardPanel.COL_COUNT)) {
			return false;
		}
		Cell cell = this.model.getCurrentCell(x, y);
		if (cell instanceof EmptyCell ) {
			return true;
		}
		return false;
	}
	
	public boolean isVerticalMovePossible(Point p , Direction direction , Ship ship) {
		return (ship != null && !ship.getField().isHorizontal()
				&& checkBounderies(getFrontCell(direction, (int)p.getX() ,(int)p.getY()))
				&& checkNextCell(direction,(getFrontCell(direction, (int)p.getX() ,(int)p.getY()))));
	}
	
	public boolean isHorizontalMovePossible(Point p , Direction direction , Ship ship) {
		return (ship != null && ship.getField().isHorizontal() 
				&& checkBounderies(getFrontCell(direction, (int)p.getX() ,(int)p.getY()))
				&& checkNextCell(direction,(getFrontCell(direction, (int)p.getX() ,(int)p.getY()))));
	}
	
	public boolean isLeftRotationPossible(Point p , Rotation RotationDirection , Ship ship) {
		return (ship != null && !ship.getField().isHorizontal()
				&& isEnoughFreeSpaceForRotation(getFrontCell(Direction.Down, (int)p.getX() ,(int)p.getY()), RotationDirection , ship));
	}
	
	public boolean isRightRotationPossible(Point p , Rotation RotationDirection , Ship ship) {
		return (ship != null && ship.getField().isHorizontal()
				&& isEnoughFreeSpaceForRotation(getFrontCell(Direction.Right, (int)p.getX() ,(int)p.getY()), RotationDirection , ship));
	}
	public boolean isRightRotationPossibleForAI(Point p , Rotation RotationDirection , Ship ship) {
		return (ship != null && !ship.getField().isHorizontal()
				&& isEnoughFreeSpaceForRotation(getFrontCell(Direction.Down, (int)p.getX() ,(int)p.getY()), RotationDirection , ship));
	}

	/* 
	 * Function fills the location of all points of AI Ships to aiPlayerShipsLocations array list and
	 * all points of human player to aiPlayerShipsLocations array list for further using of AI shooting algorithm
	 */
	public void updateAllShipsLocationAndHealth() {
		int x,y;
		humanPlayerShipsLocations.clear();
		aiPlayerShipsLocations.clear();
		Cell cell = null;
		Ship ship = null;
		for (x=0 ; x<BoardPanel.COL_COUNT ; x++) {
			for (y=0 ; y<BoardPanel.ROW_COUNT; y++) {
				cell = this.model.getCurrentCell(x, y);
				if (cell instanceof Ship) {
					ship = (Ship)cell;
					if (ship.getPlayer().getPlayerType() == PlayerType.Human) {
						humanPlayerShipsLocations.add(new Point(x,y));
					}
					else if(ship.getPlayer().getPlayerType() == PlayerType.Computer)  {
						aiPlayerShipsLocations.add(new Point(x,y));
					}
				}
			}
		}		
	}

}
