package com.dbybek.tictactoe;

import com.dbybek.tictactoe.controllers.GameController;
import com.dbybek.tictactoe.exceptions.CellNotEmpty;
import com.dbybek.tictactoe.exceptions.NoMoveFoundForUndo;
import com.dbybek.tictactoe.models.*;
import com.dbybek.tictactoe.strategies.WinningStrategies.ColWinningStrategy;
import com.dbybek.tictactoe.strategies.WinningStrategies.DiagonalWinningStrategy;
import com.dbybek.tictactoe.strategies.WinningStrategies.RowWinningStrategy;
import com.dbybek.tictactoe.strategies.WinningStrategies.WinningStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TicTacToe {

    public static void main(String[] args) throws CellNotEmpty, NoMoveFoundForUndo {
        Scanner sc = new Scanner(System.in);

        /* Size of the board taken by the user*/
        System.out.println("Please enter the dimension of the game");
        int dimension = sc.nextInt();

        /* Ask user if we have a Bot in this game or not*/
        System.out.println("Will there be any Bot in the game ? Y/N");
        char isBotPresent = sc.next().charAt(0);

        /* Player list to be added by the user.*/
        List<Player> players = new ArrayList<>();
        int playersListSize = isBotPresent=='Y'?dimension-2:dimension-1;
        for(int i=0;i<playersListSize;i++){
            System.out.println("What is the name of the player number : " + (i+1));
            String name = sc.next();
            System.out.println("What is the symbol of the player number : " + (i+1));
            char symbol = sc.next().charAt(0);
            players.add(new Player(name,i+1,new Symbol(symbol),PlayerType.HUMAN));
        }

        /* if Bot present add it in the player list*/
        if(isBotPresent == 'Y'){
            System.out.println("What is the name of the Bot : ");
            String botName = sc.next();
            System.out.println("What is the character symbol of the Bot : ");
            char botSymbol = sc.next().charAt(0);
            System.out.println("Please Enter the Level of the Bot as easy,medium or hard --> EASY/MEDIUM/HARD : ");
            String botLevel = sc.next();
            players.add(new Bot(botName,dimension-1,new Symbol(botSymbol),PlayerType.BOT,BotDifficultyLevel.valueOf(botLevel)));
        }

        List<WinningStrategy> winningStrategies = new ArrayList<>();
        winningStrategies.add(new RowWinningStrategy());
        winningStrategies.add(new ColWinningStrategy());
        winningStrategies.add(new DiagonalWinningStrategy());

        /* Next Player Index will be checked using the below value.*/
        int nextPlayerIndex = 0;

        GameController gameController = new GameController();
        Game game = gameController.startGame(dimension,players,nextPlayerIndex,winningStrategies);

        /* Game progress with every player moves. */
        while(game.getGameState().equals(GameState.IN_PROGRESS)){
            /* Print the board. */
            gameController.printGame(game);

            /* Make a move. */
            gameController.makeMove(game);

            /* Undo if required. */
            System.out.println("Do you want to undo? Enter: y/n");
            String undo = sc.next();

            if(undo.equalsIgnoreCase("y")){
                gameController.undo(game);
            }

            continue;
        }

        /* Print the board. */
        gameController.printGame(game);

        /* Print the winner. */
        System.out.println(game.getGameState().equals(GameState.ENDED)?game.getWinner().getName():"Game ended in a draw.");
    }
}
