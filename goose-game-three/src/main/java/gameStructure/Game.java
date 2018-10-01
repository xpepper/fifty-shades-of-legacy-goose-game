package gameStructure;

import java.util.ArrayList;
import java.util.Random;

import cells.Cell;
import cells.GooseCell;
import cells.TeleportCell;
import cells.TrapCell;
import cells.WaitingCell;

/**
 * Main Class of the Game, it initializes the board, the players and launch the
 * game
 * 
 * @author ostrowst
 */

public class Game {

	protected Board board;
	protected boolean isFinished = false;
	protected Player p1, p2, p3, currentPlayer;

	public Game() {

	}

	/**
	 * Constructor of a Game, it initializes the board that will be used for
	 * this game.
	 * 
	 * @param b
	 *            Board that will be initialized.
	 */
	public Game(Board b) {
		this.board = b;
	}

	/**
	 * Main method of the game, it launches the game until a player wins.
	 */
	public void play() {

		int dice = 0;
		Cell realCell, targetCell;
		int idPlayer;
		currentPlayer = p1;
		Cell previousCell = null;

		while (!this.isFinished) {

			previousCell = currentPlayer.getCurrentCell();

			if (currentPlayer.getCurrentCell().canBeLeftNow()) {
				dice = this.throwDie() + this.throwDie();

				targetCell = this.board.cells[board.normalize((currentPlayer
						.getCurrentCell().getIndex() + dice))];
				realCell = computeTargetCell(targetCell, dice);

				movePlayer(realCell);

				displayTrace(previousCell, targetCell, realCell, dice);

				if (currentPlayer.getCurrentCell().getIndex() == 63) {
					this.isFinished = true;
					System.out.println(currentPlayer.getName() + " has won.");
				}

				previousCell.welcome(null);

			} else if (previousCell instanceof WaitingCell
					|| previousCell instanceof TrapCell) {
				String trace = currentPlayer.getName() + " is in cell "
						+ previousCell.getIndex() + ", "
						+ currentPlayer.getName() + " cannot play";
				System.out.println(trace);
			}

			// gives the next player or gives the first player if the current
			// player is the last of the list
			idPlayer = this.board.players.indexOf(this.currentPlayer);
			this.currentPlayer = nextPlayer(idPlayer);
		}

	}

	/**
	 * Random throw of a die, returns a number between one and six
	 * 
	 * @return the value of the throw (between 1 and 6)
	 */
	public int throwDie() {
		Random r = new Random();
		return r.nextInt(6) + 1;
	}

	/**
	 * Returns the next player of the list of players, if he is the last player,
	 * it goes back to the first one in the list.
	 * 
	 * @return the next player
	 */

	public Player nextPlayer(int idCurrentPlayer) {
		if (idCurrentPlayer + 1 == 3) {
			return this.board.players.get(0);
		}
		return this.board.players.get(idCurrentPlayer + 1);
	}

	/**
	 * Initializes the players and adds them to the player list.
	 */

	public void initializePlayers() {
		p1 = new Player("Pierre", board.cells[0]);
		p2 = new Player("Paul", board.cells[0]);
		p3 = new Player("Jacques", board.cells[0]);

		this.board.players.add(p1);
		this.board.players.add(p2);
		this.board.players.add(p3);
	}

	/**
	 * Returns the cell really reached by the move.
	 * 
	 * @param targetCell
	 *            The cell normally reached by the player
	 * @param dice
	 *            Value of the throw of the dice
	 * @return The cell truly reached by the player
	 */
	public Cell computeTargetCell(Cell targetCell, int dice) {
		return this.board.cells[board.normalize(targetCell.handleMove(dice))];
	}

	/**
	 * Displays the trace for the GooseCells, TeleportCells and NormalCells.
	 * 
	 * @param previousCell
	 *            Previous cell of the current player
	 * @param targetCell
	 *            Cell normally reached by the player after the throws
	 * @param realCell
	 *            Cell really reached by the player
	 * @param dice
	 *            Value of the throws
	 */

	public void displayTrace(Cell previousCell, Cell targetCell, Cell realCell,
			int dice) {
		String trace;
		if (targetCell instanceof GooseCell) {
			trace = currentPlayer.getName() + " is in cell "
					+ previousCell.getIndex() + ", " + currentPlayer.getName()
					+ " throws " + dice + ", reaches goose "
					+ targetCell.getIndex() + " and jumps to "
					+ (realCell.getIndex());
			System.out.println(trace);
		} else if (targetCell instanceof TeleportCell) {
			trace = currentPlayer.getName() + " is in cell "
					+ previousCell.getIndex() + ", " + currentPlayer.getName()
					+ " throws " + dice + ", reaches cell "
					+ targetCell.getIndex() + " and jumps to "
					+ realCell.getIndex();
			System.out.println(trace);
		} else {
			trace = currentPlayer.getName() + " is in cell "
					+ previousCell.getIndex() + ", " + currentPlayer.getName()
					+ " throws " + dice + ", reaches cell "
					+ (realCell.getIndex());
			System.out.println(trace);
		}
	}

	/**
	 * It moves a player in the cell he has to be, if there is another player in
	 * this cell, it swaps the two players
	 * 
	 * @param realCell
	 *            The cell really reached by the player
	 */

	public void movePlayer(Cell realCell) {
		if (realCell.isBusy()) {
			this.board.swapPlayer(this.currentPlayer, realCell.getPlayer());
			currentPlayer.setCurrentCell(realCell);
			realCell.welcome(this.currentPlayer);
		} else {
			realCell.welcome(currentPlayer);
			currentPlayer.setCurrentCell(realCell);
		}
	}

	public static void main(String[] args) {
		ArrayList<Player> playerList = new ArrayList<Player>(3);
		Cell[] cellTable = new Cell[64];
		Board b = new Board(playerList, cellTable);
		Game g = new Game(b);
		b.initializeBoard(63);
		g.initializePlayers();
		g.play();
	}
}
