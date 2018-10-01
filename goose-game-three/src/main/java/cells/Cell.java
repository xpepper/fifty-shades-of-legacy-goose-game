package cells;
import gameStructure.Player;

/**
 * Interface for the cells of the Goose Game. Note that that there can be only one
 * player by cell, the stating cell (index 0) excepted. 
 *
 * @author ostrowst
 */ 
 
public interface Cell {
	
/**
 * Indicates if a player occupying this cell can, at this turn, leave the cell.
 * 
 * @return true if and only if the player in the cell can freely leaves the cell
 * at this turn.
 */

	public boolean canBeLeftNow();
	
/**
 * Indicates if a cell holds a player until another player reaches the same cell.
 * 
 * @return true if and only if the only way for a player to get out of this cell
 * is for another player to replace him.
 */
	
	public boolean isRetaining();
	
/**
 * Indicates the position in the board of this cell.
 * 
 * @return a number greater than or equal to 0 (for the starting cell only). 
 */
	
	public int getIndex();
	
/**
 * Returns the index of the cell really reached by a player when he reaches this cell.
 * For normal cells, the returned value equals <code> getIndex() </code> and is thus 
 * independent from <code> diceThrow() </code>.
 * 
 * @param diceThrow
 * the result of the dice when the player reaches this cell
 * @return the index of the cell effectively reached when a player reaches this cell after
 * the given throw of dice
 */
 
	public int handleMove(int diceThrow);
	
/**
 * Indicates if a player currently occupies this cell.
 * 
 * @return true if and only if a player is in this cell.
 */
	
	public boolean isBusy();
	
/**
 * Returns the player currently occupying this cell, <code> null </code> if none.
 * 
 * @return the player currently in this cell, <code> null </code> if none.
 */
	
	public Player getPlayer();
	
/**
 * Remembers <code> player </code> to be in this cell.
 * 
 * @param player
 * The new player in this cell.
 */
	
	public void welcome(Player player);
}

	

