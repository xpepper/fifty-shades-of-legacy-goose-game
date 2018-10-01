package cells;

/**
 * Class which initializes a GooseCell, when a player reaches a GooseCell, 
 * he doubles his dice throw.
 * 
 * @author ostrowst
 */


public class GooseCell extends NormalCell {

/**
 * Constructor of the GooseCell class, it creates a GooseCell and initializes
 * the GooseCell at the index given in parameters.
 * @param idx
 * Index of the GooseCell that will be created.
 */
	
	public GooseCell(int idx){
		super(idx); 
	}
	
/**
 * Returns the index of the cell really reached by a player when he reaches this cell.
 * For a GooseCell, the returned value is equals to <code> getIndex() + diceThrow <code>
 * 
 *  @param diceThrow
 *  The result of the dice when the player reaches this cell.
 *  @return the index of the cell effectively reached when a player reaches this cell after
 * the given throw of dice
 */
	
	public int handleMove(int diceThrow){
		return super.index+diceThrow; // It doubles the value of the throw.
	}
}
