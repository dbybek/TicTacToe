package com.dbybek.tictactoe.models;

import com.dbybek.tictactoe.exceptions.CellNotEmpty;

import java.util.List;
import java.util.Scanner;

public class Player {
    private String name;
    private int id;
    private Symbol symbol;
    private PlayerType playerType;
    private Scanner sc = new Scanner(System.in);

    public Player(String name, int id, Symbol symbol, PlayerType playerType) {
        this.name = name;
        this.id = id;
        this.symbol = symbol;
        this.playerType = playerType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

    public Move makeMove(Board board) throws CellNotEmpty {
        System.out.println(this.name + " please enter the row for the move: ");
        int row = sc.nextInt();
        System.out.println(this.name + " please enter the col for the move: ");
        int col = sc.nextInt();
        List<List<Cell>> brd = board.getBoard();
        if(!brd.get(row).get(col).getCellState().equals(CellState.EMPTY)){
            throw new CellNotEmpty("Cell not empty.");
        }
        return new Move(this, new Cell(row, col, CellState.EMPTY));
    }
}
