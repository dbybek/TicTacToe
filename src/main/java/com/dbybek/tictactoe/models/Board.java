package com.dbybek.tictactoe.models;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int dimension;
    private List<List<Cell>> board;

    public Board(int dimension) {
        this.dimension = dimension;
        this.board = new ArrayList<>();

        for (int i = 0; i < this.dimension; i++) {
            this.board.add(new ArrayList<>());

            for (int j = 0; j < this.dimension; j++) {
                this.board.get(i).add(new Cell(i,j,CellState.EMPTY));
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < dimension; i++) {
            List<Cell> row = board.get(i);
            for (int j = 0; j < dimension; j++) {
                row.get(j).printCell();
            }
            System.out.println();
        }
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public List<List<Cell>> getBoard() {
        return board;
    }

    public void setBoard(List<List<Cell>> board) {
        this.board = board;
    }
}
