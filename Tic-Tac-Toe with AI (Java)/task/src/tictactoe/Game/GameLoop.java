package tictactoe.Game;

import java.util.Scanner;

public abstract class GameLoop {

    private final Scanner scanner;


    public GameLoop(Scanner scanner){
        this.scanner = scanner;
    }

    public Scanner getScanner() {
        return scanner;
    }
}
