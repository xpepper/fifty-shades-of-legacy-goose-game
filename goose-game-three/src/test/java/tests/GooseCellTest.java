package tests;

import static org.junit.Assert.*;
import gameStructure.Player;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import cells.Cell;
import cells.GooseCell;

public class GooseCellTest {

	Cell gc1, gc2;
	Player p;
	
	@Before
	public void initialize(){
		p = new Player("pierre", gc1);
		gc1 = new GooseCell(2);
		gc2 = new GooseCell(3);
	}

	@Test
	public void testHandleMove(){
		int diceThrow;
		Random r = new Random();
		diceThrow = r.nextInt(6)+1;
		assertEquals(2+diceThrow, gc1.handleMove(diceThrow));
		assertEquals(3+diceThrow, gc2.handleMove(diceThrow));
	}
}
