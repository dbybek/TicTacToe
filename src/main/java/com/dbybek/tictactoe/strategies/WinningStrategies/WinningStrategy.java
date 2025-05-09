package com.dbybek.tictactoe.strategies.WinningStrategies;

import com.dbybek.tictactoe.models.Board;
import com.dbybek.tictactoe.models.Move;

public interface WinningStrategy {
    boolean checkWinner(Board board, Move move);

    void handleUndo(Board board, Move move);
}
