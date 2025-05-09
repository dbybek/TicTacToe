package com.dbybek.tictactoe.models;

import com.dbybek.tictactoe.exceptions.*;
import com.dbybek.tictactoe.strategies.WinningStrategies.WinningStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Game {

    private List<Player> players;
    private Board board;
    private Player winner;
    private List<Move> moves;
    private GameState gameState;
    private int nextPlayerIndex;
    private List<WinningStrategy> winningStrategies;

    private Game(Builder builder) {
        this.players = builder.players;
        this.board = new Board(builder.dimension);
        this.moves = new ArrayList<>();
        this.winner = null;
        this.gameState = GameState.IN_PROGRESS;
        this.winningStrategies = builder.winningStrategies;
        this.nextPlayerIndex = builder.nextPlayerIndex;
    }

    public void makeMove() throws CellNotEmpty {
        Player currentPlayer = players.get(nextPlayerIndex);
        System.out.println("Current player name is "+ currentPlayer.getName());
        Move move = currentPlayer.makeMove(board);

        //Change the state and add details to the position where the player wants to add their symbol.
        int row = move.getCell().getRow();
        int column = move.getCell().getColumn();

        Cell cellToChange = board.getBoard().get(row).get(column);
        cellToChange.setPlayer(currentPlayer);
        cellToChange.setCellState(CellState.FILLED);

        //Maintain the list of moves
        Move finalMoveObject = new Move(currentPlayer, cellToChange);

        moves.add(finalMoveObject);

        //update next player
        nextPlayerIndex += 1;
        nextPlayerIndex %= players.size();

        if(checkWinner(finalMoveObject)) {
            // whether a player is winning or not
            winner = currentPlayer;
            gameState = GameState.ENDED;
        }
        else if(moves.size() == (board.getDimension() * board.getDimension())) {
            // no valid moves left
            gameState = GameState.DRAW;
        }
    }

    public boolean checkWinner(Move move) {
        for(WinningStrategy winningStrategy: winningStrategies) {
            if(winningStrategy.checkWinner(board, move)) {
                return true;
            }
        }
        return false;
    }

    public void undo() throws NoMoveFoundForUndo {
        //Steps for Undo:
        // 1. Find the last move.
        // 2. Remove it.
        // 3. Cell state should be changed back to EMPTY.
        // 4. Clear all the hashmaps in the winning strategies.
        // 5. If no move has been made till now but undo request has came then throw exception.

        if(moves.isEmpty()) {
            throw new NoMoveFoundForUndo("No moves found. Please play the first move.");
        }

        Move lastMove = moves.get(moves.size()-1);
        moves.remove(lastMove);

        Cell cell = lastMove.getCell();
        cell.setCellState(CellState.EMPTY);
        cell.setPlayer(null);

        //Changing the values in the hashmap
        for(WinningStrategy winningStrategy: winningStrategies) {
            winningStrategy.handleUndo(board, lastMove);
        }

        //Revert the next Player index as well
        nextPlayerIndex -= 1;
        nextPlayerIndex = (nextPlayerIndex + players.size()) % players.size();
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getNextPlayerIndex() {
        return nextPlayerIndex;
    }

    public void setNextPlayerIndex(int nextPlayerIndex) {
        this.nextPlayerIndex = nextPlayerIndex;
    }

    public List<WinningStrategy> getWinningStrategies() {
        return winningStrategies;
    }

    public void setWinningStrategies(List<WinningStrategy> winningStrategies) {
        this.winningStrategies = winningStrategies;
    }

    public static class Builder {
        private int dimension;
        private List<Player> players;
        private int nextPlayerIndex;
        private List<WinningStrategy> winningStrategies;

        public Builder setDimension(int dimension) {
            this.dimension = dimension;
            return this;
        }

        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public Builder setNextPlayerIndex(int nextPlayerIndex) {
            this.nextPlayerIndex = nextPlayerIndex;
            return this;
        }

        public Builder setWinningStrategies(List<WinningStrategy> winningStrategies) {
            this.winningStrategies = winningStrategies;
            return this;
        }

        private void validateBotCount() throws InvalidBotCountException {
            int botCount = 0;
            for (Player player : players) {
                if(player.getPlayerType().equals(PlayerType.BOT)){
                    botCount++;
                }
            }
            if(botCount > 1){
                throw new InvalidBotCountException("Bot count exceed 1");
            }
        }

        private void validateBoardDimension() throws InvalidBoardDimensionException {
            if(this.dimension < 3 ||  this.dimension > 10){
                throw new InvalidBoardDimensionException("Dimension of the board should be between 3 to 10.");
            }
        }

        private void  validatePlayersCount() throws InvalidPlayersCountException {
            if(players.size() != dimension-1){
                throw new InvalidPlayersCountException("Player count should be 1 less than dimension of the board.");
            }
        }

        private void validateUniqueSymbolOfAllPlayer() throws DuplicateSymbolException{
            HashSet<Character> hs = new HashSet<>();
            for (Player player : players) {
                hs.add(player.getSymbol().getSymbol());
            }
            if(hs.size() != players.size()){
                throw new DuplicateSymbolException("Each player must have an unique symbol.");
            }
        }

        private void validate() throws InvalidBotCountException, InvalidBoardDimensionException, InvalidPlayersCountException, DuplicateSymbolException{
            validateBotCount();
            validateBoardDimension();
            validatePlayersCount();
            validateUniqueSymbolOfAllPlayer();
        }

        public Game build() throws  InvalidBotCountException, InvalidBoardDimensionException, InvalidPlayersCountException, DuplicateSymbolException {
            this.validate();
            return new Game(this);
        }
    }
}
