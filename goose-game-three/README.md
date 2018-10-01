## GooseGame
Taken from https://github.com/ostroluge/GooseGame

30/09/2014

Team (number 4):
Teddy LEQUETTE - Thomas OSTROWSKI

GooseGame:
A list of three players is pre-initialized, before the beginning of a game.
Each round, the current player throws two dice, so he can move from 2 to 12
cells.
If he reaches a specific cell (GooseCell, TeleportCell, TrapCell, WaitingCell)
his move is modified.
When a player reaches cell 63, he won the game.

Significant method:

play() : It is the main loop of the Game, it runs until
a player wins the game.

public void play() {

		while (!this.isFinished) {

			previousCell = currentPlayer.getCurrentCell();

			if (currentPlayer.getCurrentCell().canBeLeftNow()) {
				dice = this.throwDie() + this.throwDie();

				targetCell = this.board.cells[board.normalize((currentPlayer
						.getCurrentCell().getIndex() + dice))];
				realCell = computeTargetCell(targetCell, dice);

				movePlayer(realCell);

				displayTrace(previousCell, targetCell, realCell, dice);

				if (currentPlayer.getCurrentCell().getIndex() == 63) {
					this.isFinished = true;
					System.out.println(currentPlayer.getName() + " has won.");
				}

				previousCell.welcome(null);

			} else if (previousCell instanceof WaitingCell
					|| previousCell instanceof TrapCell) {
				String trace = currentPlayer.getName() + " is in cell "
						+ previousCell.getIndex() + ", "
						+ currentPlayer.getName() + " cannot play";
				System.out.println(trace);
			}

			// gives the next player or gives the first player if the current
			// player is the last of the list
			idPlayer = this.board.players.indexOf(this.currentPlayer);
			this.currentPlayer = nextPlayer(idPlayer);
		}

	}
