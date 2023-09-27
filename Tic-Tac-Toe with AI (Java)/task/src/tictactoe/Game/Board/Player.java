package tictactoe.Game.Board;

public enum Player {

    PLAYER_1("Player 1"),
    PLAYER_2("Player 2");


    private String s;
    Player(String s){
        this.s = s;
    }

    public String getS(){
        return this.s;
    }

    public void setS(String s) {
        this.s = s;
    }
}
