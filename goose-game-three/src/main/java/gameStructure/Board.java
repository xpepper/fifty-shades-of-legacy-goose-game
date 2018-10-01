package gameStructure;

import java.util.ArrayList;

import cells.Cell;
import cells.GooseCell;
import cells.NormalCell;
import cells.StartingCell;
import cells.TeleportCell;
import cells.TrapCell;
import cells.WaitingCell;

/**
 * Class that defines the board of the Game, the board is defined by a list of
 * players and a table of cells.
 * 
 * @author ostrowst
 */

public class Board {

	protected ArrayList<Player> players;
	protected Cell[] cells;

	public Board() {

	}

	/**
	 * Constructor of a Board, it initializes a list of players and a table of
	 * cells.
	 * 
	 * @param p
	 *            ArrayList of players that will be initialized.
	 * @param c
	 *            Table of cells that will be initialized.
	 */
	public Board(ArrayList<Player> p, Cell[] c) {
		this.players = p;
		this.cells = c;
	}

	/**
	 * It swaps the position of two players.
	 * 
	 * @param p1
	 *            The first player that will be swapped.
	 * @param p2
	 *            The second player that will be swapped.
	 */
	public void swapPlayer(Player p1, Player p2) { // swaps two players
		Player tmp = new Player();
		tmp.setCurrentCell(p1.getCurrentCell());
		p1.setCurrentCell(p2.getCurrentCell());
		p2.setCurrentCell(tmp.getCurrentCell());
	}

	/**
	 * It normalizes a move, if a move send a player out of bounds, it sends
	 * back the player on the other way.
	 * 
	 * @param i
	 *            Index to normalize
	 * @return The correct new index of a player
	 */
	public int normalize(int i) {
		int bounce;

		if (i <= 63) {
			return i;
		}
		bounce = i - 63;
		return 63 - bounce;
	}

	/**
	 * Initializes the board to the size given in parameters with every specific
	 * cell at the right place.
	 * 
	 * @param size
	 *            Last cell of the board
	 */
	public void initializeBoard(int size) { // initialization of the board
		for (int i = 0; i <= size; i++) {
			switch (i) {
			case 0:
				this.cells[i] = new StartingCell(this.players);
				break;
			case 9:
			case 18:
			case 27:
			case 36:
			case 45:
			case 54:
				this.cells[i] = new GooseCell(i);
				break;
			case 31:
			case 52:
				this.cells[i] = new TrapCell(i);
				break;

			case 6:
				this.cells[i] = new TeleportCell(i, 12);
				break;

			case 42:
				this.cells[i] = new TeleportCell(i, 30);
				break;

			case 58:
				this.cells[i] = new TeleportCell(i, 1);
				break;

			case 19:
				this.cells[i] = new WaitingCell(i, 2, 2);
				break;

			default:
				this.cells[i] = new NormalCell(i);
				break;
			}
		}
	}
}
