package com.dbybek.tictactoe.controllers;

import com.dbybek.tictactoe.exceptions.CellNotEmpty;
import com.dbybek.tictactoe.exceptions.NoMoveFoundForUndo;
import com.dbybek.tictactoe.models.Game;
import com.dbybek.tictactoe.models.Player;
import com.dbybek.tictactoe.strategies.WinningStrategies.WinningStrategy;

import java.util.List;

public class GameController {

    private static Game game;

    public Game startGame (int dimension,
                           List<Player> players,
                           int nextPlayerIndex,
                           List<WinningStrategy> winningStrategies) {
        try {
            game = Game.getBuilder()
                    .setDimension(dimension)
                    .setPlayers(players)
                    .setNextPlayerIndex(nextPlayerIndex)
                    .setWinningStrategies(winningStrategies)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong. Could not start the game.");
        }
        return game;
    }

    public void printGame(Game game) {
        game.getBoard().printBoard();
    }

    public Player getWinner() {
        return game.getWinner();
    }

    public void makeMove(Game game) throws CellNotEmpty {
        game.makeMove();
    }

    public void undo(Game game) throws NoMoveFoundForUndo {
        game.undo();
    }
}
