package tictactoe.Game.Algorithms;

import tictactoe.Game.*;
import tictactoe.Game.Board.Board;
import tictactoe.Game.Board.Player;
import tictactoe.Game.GameState.GameState;
import tictactoe.Game.GameState.State;
import tictactoe.Game.Utils.Utility;

public class Minimax<T extends AbstractGame> implements IAlgorithm {

    private final char PLAYER_ONE;
    private final char PLAYER_TWO;

    private T gameType;

    public int gamePositions, oWinsCount, xWinsCount, draws, amountOfGames;


    public Minimax(final char firstPlayer, final char secondPlayer, final T gameType){
        this.PLAYER_ONE = firstPlayer;
        this.PLAYER_TWO = secondPlayer;
        this.gameType = gameType;

    }

    @Override
    public int[] run(Player player) {
        return algorithm(getGameType().getBoard(), player);
    }

    @Override
    public int[] algorithm(Board board, Player player) {
        gamePositions = 0;
        xWinsCount = 0;
        oWinsCount = 0;
        draws = 0;
        amountOfGames = 0;

        Board newBoard = Utility.createBoardCopy(board);
        char[][] boardCopy = newBoard.get();

        int[] bestMove = {};
        var playerWho = player.equals(Player.PLAYER_1) ? 'X' : 'O';
        var bestScore = playerWho == 'X' ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        var isMaximizing = playerWho == 'O';


        for (int i = 0; i < boardCopy.length; i++) {
            for (int k = 0; k < boardCopy[i].length; k++) {

                if (boardCopy[i][k] == ' ' || boardCopy[i][k] == 0 || boardCopy[i][k] == '_') {
                    boardCopy[i][k] = playerWho;

                    int score = minimaxSearch(boardCopy, 0, isMaximizing);
                    boardCopy[i][k] = ' ';

                    if (playerWho == 'X' && score > bestScore) {
                        bestScore = score;
                        bestMove = new int[]{i, k};
                    } else if (playerWho == 'O' && score < bestScore) {
                        bestScore = score;
                        bestMove = new int[]{i, k};
                    }


                }
            }

        }
        return bestMove;
    }


    private int minimaxSearch(char[][] board, int depth, boolean isMaximizing) {
        gamePositions += 1;

        GameState getState = State.checkGameState(board);

        switch (getState) {
            case GAME_X_WINS -> {
                amountOfGames++;
                xWinsCount++;
                return 1;
            }
            case GAME_O_WINS -> {
                amountOfGames++;
                oWinsCount++;
                return -1;
            }
            case GAME_DRAW -> {
                amountOfGames++;
                draws++;
                return 0;
            }
        }

        int bestScore;
        if (isMaximizing) {
            bestScore = (int) -1e9;

            for (int i = 0; i < board.length; i++) {
                for (int k = 0; k < board[i].length; k++) {
                    if (board[i][k] == ' ' || board[i][k] == 0 || board[i][k] == '_') {
                        board[i][k] = 'X';
                        var score = minimaxSearch(board, depth + 1, false);
                        board[i][k] = ' ';

                        bestScore = Math.max(score, bestScore);
                    }

                }

            }

        } else {
            bestScore = (int) 1e9;

            for (int i = 0; i < board.length; i++) {
                for (int k = 0; k < board[i].length; k++) {
                    if (board[i][k] == ' ' || board[i][k] == 0 || board[i][k] == '_') {
                        board[i][k] = 'O';
                        var score = minimaxSearch(board, depth + 1, true);
                        board[i][k] = ' ';

                        bestScore = Math.min(score, bestScore);
                    }

                }

            }
        }
        return bestScore;
    }

    public char getPLAYER_ONE() {
        return this.PLAYER_ONE;
    }

    public char getPLAYER_TWO() {
        return this.PLAYER_TWO;
    }

    public T getGameType() {
        return gameType;
    }

    @Override
    public String algorithmName() {
        return "Minimax";
    }
}
