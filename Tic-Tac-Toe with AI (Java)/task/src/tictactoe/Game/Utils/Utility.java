package tictactoe.Game.Utils;

import tictactoe.Game.Board.Board;

public class Utility {

    public static Board createBoardCopy(Board board){
        int size = board.getSize();
        Board copy = new Board(board.getType(), board.getBoardRowSize(), board.getBoardColumnSize());

        for(int i = 0; i < board.get().length; i++){
            for(int k = 0; k < board.get()[i].length; k++){
                copy.insert(board.get()[i][k], i, k);
            }
        }
        return copy;
    }

    public static void printBoard(char[][] board, int score){
        for (char[] chars : board) {
            for (char aChar : chars) {
                System.out.print(aChar + "  ");
            }
            System.out.println(); // Print a newline after each row
        }
        System.out.println("score: " + score); // Print the score after the matrix
    }
}
