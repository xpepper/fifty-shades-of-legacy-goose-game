package com.goosegame.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Game {
    private static Logger logger = LoggerFactory.getLogger(Game.class);

    private static final int VICTORY_POSITION = 63;

    private final List<Player> players;

    private DiceRollerService diceRollerService;
    private Player nextPlayer = null;

    public Game(DiceRollerService diceService) {
        players = new ArrayList<>();
        diceRollerService = diceService;
    }

    public boolean add(Player candidate) {
        return players.add(candidate);
    }

    public boolean hasAlready(Player player) {
        return players.stream().anyMatch(p -> p.hasNickname(player.getNickname()));
    }

    public boolean isFull() {
        return players.size() == 4;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public boolean isWaitingForMorePlayers() {
        return !isFull();
    }

    public boolean isOver() {
        return players.stream().anyMatch(p -> p.getPosition() == VICTORY_POSITION);
    }

    public Optional<Player> playerFrom(String playerId) {
        return players.stream().filter(p -> p.hasUUID(playerId)).findAny();
    }

    public MoveResult move(Player currentPlayer) throws IllegalTurnException {
        if (!isPlayerInTurn(currentPlayer)) {
            throw new IllegalTurnException(currentPlayer);
        }
        Roll roll = diceRollerService.roll();

        int startPosition = 0, newPosition = 0;
        startPosition = currentPlayer.getPosition();
        newPosition = currentPlayer.getPosition() + roll.result();
        currentPlayer.setPosition(newPosition);
        String message = String.format("%s moves from %s to %s. ", currentPlayer.getName(), cellName(startPosition), cellName(newPosition));

        Optional<Player> playerInPosition = players.stream().filter(p -> p.getPosition() == currentPlayer.getPosition() && !p.getUuid().equals(currentPlayer.getUuid())).findFirst();
        if (playerInPosition.isPresent()) {
            playerInPosition.get().setPosition(startPosition);
            message += String.format("On %s there was %s, who is moved back to %s. ", newPosition, playerInPosition.get().getName(), startPosition);
        }

        if (isGoose(currentPlayer.getPosition())) {
            startPosition = currentPlayer.getPosition();
            newPosition = currentPlayer.getPosition() + roll.result();
            currentPlayer.setPosition(newPosition);
            message = message.substring(0, message.length() - 2);
            message += String.format(", goose. %s moves from %s to %s. ", currentPlayer.getName(), cellName(startPosition), cellName(newPosition));
        }
        if (currentPlayer.getPosition() > 63) {
            currentPlayer.setPosition(63 - (currentPlayer.getPosition() - 63));
            message += String.format("%s bounced! %s goes back to %s", currentPlayer.getName(), currentPlayer.getName(), currentPlayer.getPosition());
        } else if (currentPlayer.getPosition() == 6) {
            currentPlayer.setPosition(currentPlayer.getPosition() + 6);
            message += String.format("%s jumps to %s", currentPlayer.getName(), currentPlayer.getPosition());
        }
        if (currentPlayer.getPosition() == 63) {
            message += String.format("%s wins!", currentPlayer.getName());
        }

        setNextPlayerAfter(currentPlayer);

        MoveResult moveResult = new MoveResult(roll, message, currentPlayer.getPosition());

        logger.info("{} moved", currentPlayer);
        logger.info("next player is {}", nextPlayer);
        return moveResult;
    }

    private void setNextPlayerAfter(Player player) {
        int nextPlayerIndex = (players.indexOf(player) + 1) % players.size();
        nextPlayer = players.get(nextPlayerIndex);
    }

    private boolean isGoose(int position) {
        return Arrays.asList(5, 14, 23, 9, 18, 27).contains(position);
    }

    private String cellName(int position) {
        if (position == 0)
            return "Start";
        if (position == 6)
            return "The Bridge";
        return String.valueOf(position);
    }

    private boolean isPlayerInTurn(Player currentPlayer) {
        if (nextPlayer == null)
            nextPlayer = players.get(0);

        return nextPlayer.equals(currentPlayer);
    }
}
