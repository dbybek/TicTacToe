package com.dbybek.tictactoe.models;

import com.dbybek.tictactoe.factory.BotPlayingStrategyFactory;
import com.dbybek.tictactoe.strategies.BotPlayingStrategies.BotPlayingStrategy;

public class Bot extends Player {
    private BotDifficultyLevel botLevel;
    private BotPlayingStrategy botPlayingStrategy;

    public Bot(String name, int id, Symbol symbol, PlayerType playerType, BotDifficultyLevel botLevel) {
        super(name, id, symbol, playerType);
        this.botLevel = botLevel;
        this.botPlayingStrategy = BotPlayingStrategyFactory.getBotPlayingStrategy(botLevel);
    }

    public BotDifficultyLevel getBotLevel() {
        return botLevel;
    }

    public void setBotLevel(BotDifficultyLevel botLevel) {
        this.botLevel = botLevel;
    }

    @Override
    public Move makeMove(Board board) {
        Move move = botPlayingStrategy.makeMove(board);
        // because in the strategy this field was null
        move.setPlayer(this);
        return move;
    }
}
