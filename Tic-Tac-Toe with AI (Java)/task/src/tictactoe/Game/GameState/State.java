package tictactoe.Game.GameState;

import tictactoe.Game.GameState.GameState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class State {


    public static GameState checkGameState(char[][] board) {
      return traverseMatrix(board);
    }
    

    private static GameState traverseMatrix(char[][] matrix) {
        HashMap<String, Integer> stateMap = new HashMap<>();
        
        final var _xWin = GameState.GAME_X_WINS;
        final var _oWin = GameState.GAME_O_WINS;
        final var _Draw = GameState.GAME_DRAW;

        int xHorizontalCount = 0;
        int oHorizontalCount = 0;
        int xVerticalCount = 0;
        int oVerticalCount = 0;

        //Vertical & Horizontal Traversal
        for (int i = 0; i < matrix.length; i++) {
            for (int k = 0; k < matrix[i].length; k++) {
                //Left -> Right
                switch (matrix[i][k]) {
                    case 'O' -> oHorizontalCount++;
                    case 'X' -> xHorizontalCount++;
                }
                //Up -> Down
                switch (matrix[k][i]) {
                    case 'O' -> oVerticalCount++;
                    case 'X' -> xVerticalCount++;
                }

            }
            if (xHorizontalCount == matrix.length || xVerticalCount == matrix.length) {
                return GameState.GAME_X_WINS;
            } else if (oHorizontalCount == matrix.length || oVerticalCount == matrix.length) {
                return GameState.GAME_O_WINS;
            }

            // Reset counts for the next row
            xHorizontalCount = 0;
            oHorizontalCount = 0;
            xVerticalCount = 0;
            oVerticalCount = 0;
        }


        //Left Diagonal
        stateMap.put("X_LEFT_DIAGONAL", 0);
        stateMap.put("O_LEFT_DIAGONAL", 0);
        for (int k = 0; k < matrix.length; k++) {
            switch (matrix[k][k]) {
                case 'O' -> stateMap.put("O_LEFT_DIAGONAL", stateMap.get("O_LEFT_DIAGONAL") + 1);
                case 'X' -> stateMap.put("X_LEFT_DIAGONAL", stateMap.get("X_LEFT_DIAGONAL") + 1);
            }
        }


        //Right Diagonal
        stateMap.put("X_RIGHT_DIAGONAL", 0);
        stateMap.put("O_RIGHT_DIAGONAL", 0);
        for (int i = 0; i < matrix.length; i++) {
            switch (matrix[i][matrix[i].length - 1 - i]) {
                case 'O' -> stateMap.put("O_RIGHT_DIAGONAL", stateMap.get("O_RIGHT_DIAGONAL") + 1);
                case 'X' -> stateMap.put("X_RIGHT_DIAGONAL", stateMap.get("X_RIGHT_DIAGONAL") + 1);
            }
        }
        //Check if there's a diagonal winner
        for (Map.Entry<String, Integer> se : stateMap.entrySet()) {
            if (se.getValue().equals(matrix.length)) {
                return se.getKey().charAt(0) == 'O' ? _oWin : _xWin;
            }
        }
        //Check if there's a draw
        ArrayList<Character> list = new ArrayList<>();

        for (char[] chars : matrix) {
            for (char aChar : chars) {
                if (aChar == 'X' || aChar == 'O') list.add(aChar);
            }
        }
        if (list.size() == matrix.length * matrix.length) {
            return _Draw;
        }
        
        return GameState.GAME_NOT_FINISHED;
    }
}
