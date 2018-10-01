package cells;

import gameStructure.Player;

import java.util.ArrayList;

/**
 * Class which defines the starting cell of a game,
 * the starting cell has 0 for index, and it is where all
 * the players start the game.
 *  
 * @author ostrowst
 *
 */

public class StartingCell implements Cell {

	protected ArrayList<Player> players;
	protected final int index = 0;
	
	/**
	 * Constructor of the StartingCell that creates a StartingCell and initializes
	 * the cell with a list of players.
	 * 
	 * @param ap
	 *            List of players that will be playing during this game.
	 */
	
	public StartingCell(ArrayList<Player> ap){
		this.players = ap;
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
	 * Gives the value of the index of the StartingCell.
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
	
	@Override
	public int handleMove(int diceThrow) {
		return this.index;
	}

	/**
	 * Indicates if a player currently occupies this cell.
	 * 
	 * @return true if and only if a player is in this cell.
	 */
	
	@Override
	public boolean isBusy() {
		return false;
	}

	@Override
	public Player getPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void welcome(Player player) {
		// TODO Auto-generated method stub
		
	}
}
