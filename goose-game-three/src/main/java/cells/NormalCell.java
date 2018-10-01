package cells;

import cells.Cell;
import gameStructure.*;

/**
 * Class which defines the "normal" cells of the Game, a NormalCell has a
 * classic behavior.
 * 
 * @author ostrowst
 */

public class NormalCell implements Cell {

	public int index;
	public Player p;

	public NormalCell() {

	}

	/**
	 * Constructor of the NormalCell that creates a NormalCell and initializes
	 * the NormalCell at the index given in parameters.
	 * 
	 * @param i
	 *            Index of the NormalCell that will be created.
	 */

	public NormalCell(int i) {
		this.index = i;
		this.p = null;
	}

	/**
	 * Constructor of the NormalCell class that creates a NormalCell and
	 * initializes it at the index given in parameters and set a player in this
	 * cell.
	 * 
	 * @param i
	 *            Index of the NormalCell that will be created.
	 * @param p
	 *            Player that will be in this cell.
	 */
	public NormalCell(int i, Player p) {
		this.index = i;
		this.p = p;
	}

	/**
	 * Indicates if a player occupying this cell can, at this turn, leave the
	 * cell.
	 * 
	 * @return true if and only if the player in the cell can freely leaves the
	 *         cell at this turn.
	 */

	public boolean canBeLeftNow() {
		return true;
	}

	/**
	 * Indicates if a cell holds a player until another player reaches the same
	 * cell.
	 * 
	 * @return true if and only if the only way for a player to get out of this
	 *         cell is for another player to replace him.
	 */

	public boolean isRetaining() {
		return false;
	}

	/**
	 * Gives the value of the index of this NormalCell.
	 * 
	 * @return the index of the cell.
	 */

	public int getIndex() {
		return this.index;
	}

	/**
	 * Returns the index of the cell really reached by a player when he reaches
	 * this cell. For a NormalCell, the returned value is equals to
	 * <code> getIndex()<code>
	 * 
	 * @param diceThrow
	 *            The result of the dice when the player reaches this cell.
	 * @return the index of the cell effectively reached when a player reaches
	 *         this cell after the given throw of dice
	 */

	public int handleMove(int diceThrow) {
		return this.index;
	}

	/**
	 * Indicates if a player currently occupies this cell.
	 * 
	 * @return true if and only if a player is in this cell.
	 */

	public boolean isBusy() {
		if (this.p != null) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the player currently occupying this cell, <code> null </code> if
	 * none.
	 * 
	 * @return the player currently in this cell, <code> null </code> if none.
	 */

	public Player getPlayer() {
		return this.p;
	}

	/**
	 * Remembers <code> player </code> to be in this cell.
	 * 
	 * @param player
	 *            The new player in this cell.
	 */

	public void welcome(Player player) {
		this.p = player;
	}
}
