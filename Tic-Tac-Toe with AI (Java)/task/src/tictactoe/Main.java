package tictactoe;

import tictactoe.Game.Board.Player;
import tictactoe.Game.TicTacToe;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        TicTacToe game = new TicTacToe(Player.PLAYER_1, Player.PLAYER_2);
        game.setupInitialState();
        game.run();


        }

}

