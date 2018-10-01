package tests;

import static org.junit.Assert.*;
import gameStructure.Player;

import org.junit.Before;
import org.junit.Test;

import cells.TeleportCell;

public class TeleportCellTest {

	TeleportCell tc1, tc2;
	Player p;
	
	@Before
	public void initialize(){
		p = new Player("pierre", tc1);
		tc1 = new TeleportCell(2, 15);
		tc2 = new TeleportCell(3, 18);
	}

	@Test
	public void testHandleMove(){
		int diceThrow = 0;
		assertEquals(15, tc1.handleMove(diceThrow));
		assertEquals(18, tc2.handleMove(diceThrow));
	}
}
