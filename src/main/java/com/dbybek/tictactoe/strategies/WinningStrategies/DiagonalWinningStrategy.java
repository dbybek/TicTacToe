package com.dbybek.tictactoe.strategies.WinningStrategies;

import com.dbybek.tictactoe.models.Board;
import com.dbybek.tictactoe.models.Move;
import com.dbybek.tictactoe.models.Symbol;

import java.util.HashMap;

public class DiagonalWinningStrategy implements WinningStrategy {
    private HashMap<Symbol,Integer> leftDiagonal =  new HashMap<>();
    private HashMap<Symbol,Integer> rightDiagonal = new HashMap<>();

    @Override
    public boolean checkWinner(Board board, Move move) {
        int row = move.getCell().getRow();
        int column = move.getCell().getColumn();
        Symbol symbol = move.getPlayer().getSymbol();

        //We should only verify if the cell is in the diagonal else return false.
        if(row == column){//left diagonal
            if(!leftDiagonal.containsKey(symbol)){
                leftDiagonal.put(symbol,0);
            }
            leftDiagonal.put(symbol,leftDiagonal.get(symbol)+1);

            //Check winner
            if(leftDiagonal.get(symbol) == board.getDimension()){
                return true;
            }
        }

        if(row+column == board.getDimension()-1){//right diagonal
            if(!rightDiagonal.containsKey(symbol)){
                rightDiagonal.put(symbol,0);
            }
            rightDiagonal.put(symbol,rightDiagonal.get(symbol)+1);

            //Check winner
            return rightDiagonal.get(symbol) == board.getDimension();
        }

        return false;
    }

    @Override
    public void handleUndo(Board board, Move move) {
        int row = move.getCell().getRow();
        int column = move.getCell().getColumn();
        Symbol symbol = move.getPlayer().getSymbol();

        if(row == column){//left diagonal
            leftDiagonal.put(symbol, leftDiagonal.get(symbol)-1);
        }

        if(row+column == board.getDimension()-1){//Right diagonal
            rightDiagonal.put(symbol, rightDiagonal.get(symbol)-1);
        }
    }
}
