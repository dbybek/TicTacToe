package com.dbybek.tictactoe.factory;

import com.dbybek.tictactoe.models.BotDifficultyLevel;
import com.dbybek.tictactoe.strategies.BotPlayingStrategies.BotPlayingStrategy;
import com.dbybek.tictactoe.strategies.BotPlayingStrategies.EasyBotPlayingStrategy;
import com.dbybek.tictactoe.strategies.BotPlayingStrategies.HardBotPlayingStrategy;
import com.dbybek.tictactoe.strategies.BotPlayingStrategies.MediumBotPlayingStrategy;

public class BotPlayingStrategyFactory {
    public static BotPlayingStrategy getBotPlayingStrategy(BotDifficultyLevel botLevel){
        return switch (botLevel){
            case EASY-> new EasyBotPlayingStrategy();
            case MEDIUM-> new MediumBotPlayingStrategy();
            case HARD-> new HardBotPlayingStrategy();
        };
    }
}
