package cells;
import gameStructure.*;

/**
 * Class which defines the WaitingCell of the Game, when a
 * player reaches a WaitingCell, he has to wait for a define number
 * of rounds, or he can leave the cell if another player replaces him.
 *
 * @author ostrowst
 */

public class WaitingCell extends NormalCell {

	int waitingCpt;
	int waitingTime;
	
/**
 * Constructor of a WaitingCell that creates a WaitingCell at
 * the index given in parameters and set a waiting counter and 
 * a waiting time. 
 *
 * @param i
 * Index of the WaitingCell that will be created.
 * @param cpt
 * Waiting counter of the WaintingCell.
 * @param time
 * Waiting time of the WaitingCell
 */
	
	public WaitingCell(int i, int cpt, int time){
		super(i);
		this.waitingCpt = cpt;
		this.waitingTime = time;
	}
	
/**
 * Indicates if a player occupying this cell can, at this turn, leave the cell.
 * 
 * @return true if and only if the player in the cell can freely leaves the cell
 * at this turn.
 */

	public boolean canBeLeftNow(){
		if(waitingCpt > 0){
			this.waitingCpt --;
			return false;
		}
		else if(waitingCpt == 0){
			waitingCpt = waitingTime;
			return true;
		}
		else{
			return false;
		}
	}

/**
 * Remembers <code> player </code> to be in this cell and reset 
 * the waiting counter to the waiting time.
 *
 */
	
	public void welcome(Player p){
		waitingCpt = waitingTime;
		super.p = p;
	}
}
