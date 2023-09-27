package tictactoe.Game;

import tictactoe.Game.Board.Board;
import tictactoe.Game.Board.Player;

import java.util.HashMap;
import java.util.LinkedHashMap;

public abstract class AbstractGame {

    private Player playerOne;
    private Player playerTwo;

    private HashMap playerCharacter;

    private Board<?> board;




    public AbstractGame(Player playerOne, Player playerTwo, Board<?> board){
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.board = board;

        this.playerCharacter = new LinkedHashMap<Player, Character>();
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public Board<?> getBoard() {
        return board;
    }

    public void assignPlayerCharacter(Player player, char character){
        this.playerCharacter.put(player, character);
    }

    public char getPlayerCharacter(Player player){
        return (char) this.playerCharacter.get(player);
    }
}
