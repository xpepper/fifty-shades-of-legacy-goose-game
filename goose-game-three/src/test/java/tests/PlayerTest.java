package tests;

import static org.junit.Assert.*;
import gameStructure.Player;

import org.junit.Before;
import org.junit.Test;

import cells.Cell;
import cells.NormalCell;

public class PlayerTest {

	Player p, p2;
	Cell c1, c2;
	
	@Before
	public void initialize(){
		p = new Player("pierre", c1);
		p2 = new Player();
		c1 = new NormalCell(2);
		c2 = new NormalCell(3);
	}

	@Test
	public void testSetCell(){
		p.setCurrentCell(c2);
		assertEquals(c2, p.getCurrentCell());
	}
	
}
