package tests;

import static org.junit.Assert.*;
import gameStructure.Player;

import org.junit.Before;
import org.junit.Test;

import cells.Cell;
import cells.WaitingCell;

public class WaitingCellTest {

	Cell wc1, wc2, wc3;
	Player p;
	
	@Before
	public void initialize(){
		p = new Player("pierre", wc1);
		wc1 = new WaitingCell(2, 5, 5);
		wc2 = new WaitingCell(3, 5, 5);
		wc3 = new WaitingCell(4, -1, 3);
	}

	@Test
	public void testCanBeLeftNow(){
		int waitingCpt = 5;
		
		for(int i=1; i<=waitingCpt; i++){
			assertEquals(false, wc1.canBeLeftNow());
			assertEquals(false, wc2.canBeLeftNow());
		}
		assertEquals(true, wc1.canBeLeftNow());
		assertEquals(true, wc2.canBeLeftNow());
	
		assertEquals(false, wc3.canBeLeftNow());
	}

	@Test
	public void testWelcome(){
		wc2.welcome(p);
		assertEquals(p, wc2.getPlayer());
	}
}
