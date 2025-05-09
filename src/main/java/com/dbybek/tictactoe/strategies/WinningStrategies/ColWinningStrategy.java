package com.dbybek.tictactoe.strategies.WinningStrategies;

import com.dbybek.tictactoe.models.Board;
import com.dbybek.tictactoe.models.Move;
import com.dbybek.tictactoe.models.Symbol;

import java.util.HashMap;

public class ColWinningStrategy implements WinningStrategy {
    private HashMap<Integer, HashMap<Symbol,Integer>> colMaps =  new HashMap<>();

    @Override
    public boolean checkWinner(Board board, Move move) {
        int col = move.getCell().getColumn();
        Symbol symbol = move.getPlayer().getSymbol();

        if(!colMaps.containsKey(col)){
            colMaps.put(col, new HashMap<>());
        }

        HashMap<Symbol,Integer> colMap = colMaps.get(col);
        if(!colMap.containsKey(symbol)){
            colMap.put(symbol,0);
        }
        colMap.put(symbol,colMap.get(symbol)+1);

        return colMap.get(symbol) == board.getDimension();
    }

    @Override
    public void handleUndo(Board board, Move move) {
        int col = move.getCell().getColumn();
        Symbol symbol = move.getPlayer().getSymbol();
        HashMap<Symbol,Integer> colMap = colMaps.get(col);
        colMap.put(symbol,colMap.get(symbol)-1);
        colMaps.put(col, colMap);
    }
}
