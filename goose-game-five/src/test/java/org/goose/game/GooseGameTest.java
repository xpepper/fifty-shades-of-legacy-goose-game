package org.goose.game;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GooseGameTest {


    private GooseGame goso;

    @Before
    public void initialize(){
        goso = new GooseGame(new FixedDice());
    }

    @Test
    public void addNewPlayer(){

        assertEquals("players: Pippo", goso.add("Pippo") );
    }

    @Test
    public void addSecondPlayer(){
        goso.add("Pippo");
        String test = goso.add("Pluto");

        assertEquals( "players: Pippo, Pluto", test);
    }

    @Test
    public void duplicatedPlayer(){
        goso.add("Pippo");
        goso.add("Pluto");
        String test = goso.add("Pippo");

        assertEquals( "Pippo: already exisiting player", test);

    }

    @Test
    public void movePlayerPippo(){

        goso.add("Pippo");
        goso.add("Pluto");

        assertEquals("Pippo rools 4, 2. Pippo moves from Start to 6" ,  goso.userWrite("move Pippo 4, 2"));
    }

    @Test
    public void movePlayerPluto(){
        goso.add("Pippo");
        goso.add("Pluto");

        assertEquals("Pluto rools 2, 2. Pluto moves from Start to 4" ,  goso.userWrite("move Pluto 2, 2"));
    }

    @Test
    public void movePlayerPippoTo11(){
        goso.add("Pippo");
        goso.add("Pluto");
        goso.userWrite("move Pippo 4, 2");
        assertEquals("Pippo rools 2, 3. Pippo moves from 6 to 11" ,  goso.userWrite("move Pippo 2, 3"));
    }

    @Test
    public void playerPippoWin(){
        goso.add("Pippo");
        goso.userWrite("move Pippo 30, 30");

        assertEquals("Pippo rools 1, 2. Pippo moves from 60 to 63. Pippo Wins!!" ,goso.userWrite("move Pippo 1, 2")  );
    }

    @Test
    public void playerPippoWinExactShooting(){
        goso.add("Pippo");
        goso.userWrite("move Pippo 30, 30");

        assertEquals("Pippo rools 3, 2. Pippo moves from 60 to 63. Pippo bounces! Pippo returns to 61" ,goso.userWrite("move Pippo 3, 2")  );
    }

    @Test
    public void playerPippoDiceRoll() {
        goso.add("Pippo");
        goso.userWrite("move Pippo 2, 2");
        assertEquals("Pippo rools 1, 2. Pippo moves from 4 to 7" ,goso.userWrite("move Pippo")  );
    }

    private class FixedDice implements IDice {

        public String roll() {
            return getDice1() + ", " + getDice2();
        }

        public int getDice1() {
            return 1;
        }

        public int getDice2() {
            return 2;
        }
    }

}
