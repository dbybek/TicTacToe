package com.dbybek.tictactoe.strategies.BotPlayingStrategies;

import com.dbybek.tictactoe.models.Board;
import com.dbybek.tictactoe.models.Move;

public interface BotPlayingStrategy {
    Move makeMove(Board board);
}
