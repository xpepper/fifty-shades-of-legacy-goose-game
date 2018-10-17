package com.goosegame.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameTest {

    @Mock
    private DiceRollerService diceService;
    private Game game;
    private Player firstPlayer;

    @Before
    public void setUpGame() throws Exception {
        game = new Game(diceService);

        firstPlayer = new Player("Piero", "gamesutra");
        game.add(firstPlayer);
        addThreeMorePlayers(game);
    }

    @Test
    public void move_player_from_the_starting_position() throws Exception {
        when(diceService.roll()).thenReturn(new Roll(1,2));
        MoveResult moveResult = game.move(firstPlayer);

        assertThat(moveResult.getNewPosition(), equalTo(1+2));
        assertThat(moveResult.getMessage(), equalTo("Piero moves from Start to 3."));
    }

    @Test
    public void move_player_to_bridge() throws Exception {
        when(diceService.roll()).thenReturn(new Roll(3,3));
        MoveResult moveResult = game.move(firstPlayer);

        assertThat(moveResult.getNewPosition(), equalTo(12));
        assertThat(moveResult.getMessage(), equalTo("Piero moves from Start to The Bridge. Piero jumps to 12"));
    }

    @Test
    public void move_player_to_victory() throws Exception {
        when(diceService.roll()).thenReturn(new Roll(1,2));
        firstPlayer.setPosition(60);
        MoveResult moveResult = game.move(firstPlayer);

        assertThat(moveResult.getNewPosition(), equalTo(63));
        assertThat(moveResult.getMessage(), equalTo("Piero moves from 60 to 63. Piero wins!"));
    }


    private void addThreeMorePlayers(Game game) {
        game.add(new Player(randomAlphabetic(6), randomAlphabetic(6)));
        game.add(new Player(randomAlphabetic(6), randomAlphabetic(6)));
        game.add(new Player(randomAlphabetic(6), randomAlphabetic(6)));
    }
}