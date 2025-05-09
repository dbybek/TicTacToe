package com.dbybek.tictactoe.strategies.BotPlayingStrategies;

import com.dbybek.tictactoe.models.Board;
import com.dbybek.tictactoe.models.Cell;
import com.dbybek.tictactoe.models.CellState;
import com.dbybek.tictactoe.models.Move;

import java.util.List;

public class EasyBotPlayingStrategy implements BotPlayingStrategy{
    @Override
    public Move makeMove(Board board) {
        for (List<Cell> row : board.getBoard()) {
            for (Cell cell : row) {
                if(cell.getCellState().equals(CellState.EMPTY)){
                    return new Move(null, cell);
                }
            }
        }
        return null;
    }
}
