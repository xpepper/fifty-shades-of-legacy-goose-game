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
    private Player player;

    @Before
    public void setUpGame() throws Exception {
        game = new Game(diceService);

        player = addPlayer("Piero", "gamesutra");
        addMorePlayers(game, 3);
    }

    @Test
    public void move_player_from_the_starting_position() throws Exception {
        MoveResult moveResult = letPlayerRoll(player, new Roll(1, 2));

        assertThat(moveResult.getNewPosition(), equalTo(1 + 2));
        assertThat(moveResult.getMessage(), equalTo("Piero moves from Start to 3."));
    }

    @Test
    public void move_player_to_bridge() throws Exception {
        MoveResult moveResult = letPlayerRoll(player, new Roll(3, 3));

        assertThat(moveResult.getNewPosition(), equalTo(12));
        assertThat(moveResult.getMessage(), equalTo("Piero moves from Start to The Bridge. Piero jumps to 12"));
    }

    @Test
    public void swap_players_when_reaching_the_same_position() throws Exception {
        game = new Game(diceService);

        Player firstPlayer = addPlayer("Piero", "gamesutra");
        Player secondPlayer = addPlayer("Paolo", "gooser");
        addMorePlayers(game, 2);

        letPlayerRoll(firstPlayer, new Roll(3, 4));
        assertThat(firstPlayer.getPosition(), equalTo(3 + 4));

        MoveResult moveResult = letPlayerRoll(secondPlayer, new Roll(3, 4));

        assertThat(moveResult.getNewPosition(), equalTo(3 + 4));
        assertThat(moveResult.getMessage(), equalTo("Paolo moves from Start to 7. On 7 there was Piero, who is moved back to 0."));

        assertThat(firstPlayer.getPosition(), equalTo(0));
    }

    @Test
    public void move_player_to_victory() throws Exception {
        when(diceService.roll()).thenReturn(new Roll(1, 2));
        player.setPosition(60);
        MoveResult moveResult = game.move(player);

        assertThat(moveResult.getNewPosition(), equalTo(63));
        assertThat(moveResult.getMessage(), equalTo("Piero moves from 60 to 63. Piero wins!"));
    }

    private Player addPlayer(String name, String nickname) {
        Player player = new Player(name, nickname);
        game.add(player);
        return player;
    }

    private MoveResult letPlayerRoll(Player firstPlayer, Roll roll) throws IllegalTurnException {
        when(diceService.roll()).thenReturn(roll);
        return game.move(firstPlayer);
    }


    private void addMorePlayers(Game game, int howMany) {
        for (int i = 0; i < howMany; i++) {
            game.add(new Player(randomAlphabetic(6), randomAlphabetic(6)));
        }
    }
}