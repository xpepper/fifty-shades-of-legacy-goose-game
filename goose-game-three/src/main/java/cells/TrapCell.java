package cells;

/**
 * Class which defines the TrapCell of the game, when a
 * player is in a TrapCell, he is stuck in it until another
 * player reaches this cell.
 *
 * @author ostrowst
 */

public class TrapCell extends NormalCell {

/**
 * Constructor of a TrapCell, that creates a TrapCell at the 
 * index given in parameters.	
 * @param idx
 * Index of the TrapCell that will be created.
 */

	public TrapCell(int idx){
		super(idx);
	}
	
/**
 * Indicates if a player occupying this cell can, at this turn, leave the cell.
 * 
 * @return true if and only if the player in the cell can freely leaves the cell
 * at this turn.
 */

	public boolean canBeLeftNow(){
		return false;
	}

/**
 * Indicates if a cell holds a player until another player reaches the same cell.
 * 
 * @return true if and only if the only way for a player to get out of this cell
 * is for another player to replace him.
 */		

	public boolean isRetaining(){
		return true;
	}
}
