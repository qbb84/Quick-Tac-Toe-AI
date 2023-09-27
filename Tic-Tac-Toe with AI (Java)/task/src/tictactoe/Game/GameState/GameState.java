package tictactoe.Game.GameState;

import java.util.HashMap;

public enum GameState {

    GAME_NOT_FINISHED("Game not finished"),
    GAME_DRAW("Draw"),

    GAME_X_WINS("X wins"),

    GAME_O_WINS("O wins");

    private String name;

    GameState(String name){
        this.name = name;
    }



    public String getStateName() {
        return name;
    }

    public void setStateName(String name) {
        this.name = name;
    }



}
