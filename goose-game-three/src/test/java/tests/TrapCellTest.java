package tests;

import static org.junit.Assert.*;
import gameStructure.Player;

import org.junit.Before;
import org.junit.Test;

import cells.Cell;
import cells.TrapCell;

public class TrapCellTest {

	Cell trc1, trc2;
	Player p;
	
	@Before
	public void initialize(){
		p = new Player("pierre", trc1);
		trc1 = new TrapCell(2);
		trc2 = new TrapCell(3);
	}
	
	@Test
	public void testCanBeLeftNow(){
		assertEquals(false, trc1.canBeLeftNow());
		assertEquals(false, trc2.canBeLeftNow());
	}
	
	@Test
	public void testIsRetaining(){
		assertEquals(true, trc1.isRetaining());
		assertEquals(true, trc2.isRetaining());
	}
}
