package tictactoe.Game.Algorithms;

import tictactoe.Game.Board.Board;
import tictactoe.Game.Board.Player;

public interface IAlgorithm {

     default int[] algorithm(Board board, Player player) {
        return new int[0];
    }

    int[] run(Player player);


    String algorithmName();
}
