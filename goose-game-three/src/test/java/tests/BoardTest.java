package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import gameStructure.Board;
import gameStructure.Player;

import org.junit.Before;
import org.junit.Test;

import cells.Cell;
import cells.NormalCell;

public class BoardTest {

	Board b, b1;
	Player p1, p2;
	Cell c1, c2;
	ArrayList<Player> ap;
	Cell [] tc;
	
	@Before
	public void initialize(){
		ap = new ArrayList<Player>();
		b1 = new Board();
		b = new Board(ap, tc);
		p1 = new Player("pierre", c1);
		p2 = new Player("paul", c2);
		c1 = new NormalCell(2);
		c2 = new NormalCell(3);
	}

	@Test
	public void testSwapPlayers(){
		Player p3 = new Player();
		p3 = p1;
		b.swapPlayer(p1, p2);
		assertEquals(p2.getCurrentCell(), p3.getCurrentCell());
		assertEquals(p1.getCurrentCell(), p2.getCurrentCell());
	}
	
	@Test
	public void testNormalize(){
		int n1 = 65;
		int n2 = 58;
		assertEquals(61, b.normalize(n1));
		assertEquals(58, b.normalize(n2));
	}
}
