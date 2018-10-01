package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import gameStructure.Board;
import gameStructure.Game;
import gameStructure.Player;

import org.junit.Before;
import org.junit.Test;

import cells.Cell;


public class GameTest {
	Board board;
	boolean isFinished = false;
	Player p, p2, currentPlayer;
	Board b, b1;
	Cell aCell;
	ArrayList<Player> ap;
	Cell[] tc;
	Game aGame, g;

	@Before
	public void initialize() {
		ap = new ArrayList<Player>();
		b1 = new Board();

		p = new Player("Teddy", aCell);
		p2 = new Player("Thomas", aCell);
		
		ap.add(p);
		ap.add(p2);
		b = new Board(ap, tc);
		aGame= new Game(b);
		g = new Game();
	}

	@Test
	public void testRandomDice(){
		int a;
		boolean flag = true;
		for(int i=0; i<10000; i++){
			a = aGame.throwDie();
			assertEquals(a>=1 && a<7, flag);
		}
	}
	
	@Test
	public void testNextPlayer() {
		p2=aGame.nextPlayer(ap.indexOf(p));
		boolean assertion = true;
		if(p.hashCode()!=p2.hashCode()){
			assertion= false;
		}
		assertFalse(assertion);
	}
}
