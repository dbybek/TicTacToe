package com.dbybek.tictactoe.strategies.WinningStrategies;

import com.dbybek.tictactoe.models.Board;
import com.dbybek.tictactoe.models.Move;
import com.dbybek.tictactoe.models.Symbol;

import java.util.HashMap;

public class RowWinningStrategy implements WinningStrategy {
    private HashMap<Integer,HashMap<Symbol,Integer>> rowMaps =  new HashMap<>();

    @Override
    public boolean checkWinner(Board board, Move move) {
        int row =  move.getCell().getRow();
        Symbol symbol = move.getPlayer().getSymbol();

        if(!rowMaps.containsKey(row)) {
            rowMaps.put(row, new HashMap<>());
        }

        HashMap<Symbol,Integer> rowMap = rowMaps.get(row);
        if(!rowMap.containsKey(symbol)) {
            rowMap.put(symbol, 0);
        }
        rowMap.put(symbol, rowMap.get(symbol) + 1);

        return rowMap.get(symbol) == board.getDimension();
    }

    @Override
    public void handleUndo(Board board, Move move) {
        int row =  move.getCell().getRow();
        Symbol symbol = move.getPlayer().getSymbol();
        HashMap<Symbol,Integer> rowMap = rowMaps.get(row);
        rowMap.put(symbol, rowMap.get(symbol) - 1);
        rowMaps.put(row, rowMap);
    }
}
