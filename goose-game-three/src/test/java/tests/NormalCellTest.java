package tests;

import static org.junit.Assert.*;
import gameStructure.Player;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import cells.Cell;
import cells.NormalCell;

public class NormalCellTest {

	Cell nc1, nc2;
	Player p;
	
	@Before
	public void initialize(){
		p = new Player("pierre", nc1);
		nc1 = new NormalCell(2, p);
		nc2 = new NormalCell(3);
	}

	@Test
	public void testIsBusy(){
		assertEquals(true, nc1.isBusy());
		assertEquals(false, nc2.isBusy());
	}
	
	@Test
	public void testCanBeLeftNow(){
		assertEquals(true, nc1.canBeLeftNow());
		assertEquals(true, nc2.canBeLeftNow());
	}
	
	@Test
	public void testIsRetaining(){
		assertEquals(false, nc1.isRetaining());
		assertEquals(false, nc2.isRetaining());
	}
	
	@Test
	public void testGetIndex(){
		assertEquals(2, nc1.getIndex());
		assertEquals(3, nc2.getIndex());
	}

	@Test
	public void testHandleMove(){
		int diceThrow;
		Random r = new Random();
		diceThrow = r.nextInt(6)+1;
		assertEquals(2, nc1.handleMove(diceThrow));
		assertEquals(3, nc2.handleMove(diceThrow));
	}
	
	@Test
	public void testGetPlayer(){
		assertEquals(this.p, nc1.getPlayer());
		assertEquals(null, nc2.getPlayer());
	}
	
	@Test
	public void testWelcome(){
		nc2.welcome(p);
		assertEquals(this.p, nc2.getPlayer());
	}
}
