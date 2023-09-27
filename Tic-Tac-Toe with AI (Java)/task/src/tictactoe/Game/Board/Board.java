package tictactoe.Game.Board;

import tictactoe.Game.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Board<E> {

    private char[][] board;

    private int boardRowSize, boardColumnSize;

    private boolean isMatrix;

    private E type;

    public Board(E type, int boardRowSize, int boardColumnSize) {
        this.type = type;
        this.boardRowSize = boardRowSize;
        this.boardColumnSize = boardColumnSize;

        createEmptyBoard();
    }

    public char[][] get() {
        return board;
    }

    public void insert(char insertionValue, int index1, int index2) {
        get()[index1][index2] = insertionValue;
    }

    public void remove(int index1, int index2) {
        get()[index1][index2] = ' ';
    }

    public Set<Pair<Integer, Integer>> find(char type){
        Set<Pair<Integer, Integer>> indexes = new HashSet<>();

        for(int i = 0; i < getBoardRowSize(); i++){
            for(int k = 0; k < getBoardColumnSize(); k++){
                if(get()[i][k] == type){
                    indexes.add(new Pair<>(i, k));
                }
            }
        }

       return indexes;
    }

    public void remove(char removeValue, boolean firstInstanceOfValue) {

        Pair<Character, Pair<Integer, Integer>> boardValues = searchBoard();

        if (firstInstanceOfValue) {
            if (boardValues.key().equals(removeValue))
                get()[boardValues.Value().key()][boardValues.Value().key()] = ' ';
            return;

        } else if (boardValues.key().equals(removeValue))
            get()[boardValues.Value().key()][boardValues.Value().key()] = ' ';

    }

    private Pair searchBoard() throws NullPointerException {

        for (int i = 0; i < get().length; i++) {
            for (int k = 0; k < get()[i].length; k++) {
                return new Pair(get()[i][k], new Pair<>(i, k));
            }
        }
        return null;
    }

    public void displayBoard() {
        System.out.print("---------");
        for (int i = 0; i < get().length; i++) {
            System.out.println(" ");
            System.out.print("| ");
            for (int j = 0; j < get()[i].length; j++) {
                if (get()[i][j] == '_' || get()[i][j] == 0) {
                    System.out.print("  ");
                } else {
                    System.out.print(get()[i][j] + " ");
                }

                if (j == get()[j].length - 1) {
                    System.out.print("|");
                }
            }
        }
        System.out.println("\n" + "---------");
    }


    private void createEmptyBoard() {
        this.board = new char[boardRowSize][boardColumnSize];

        for (int i = 0; i < getBoardRowSize(); i++) {
            for (int k = 0; k < getBoardColumnSize(); k++) {
                get()[i][k] = (char) getType();
            }
        }
    }


    public boolean isMatrix() {
        return this.boardColumnSize == boardRowSize;
    }

    public E getType() {
        return type;
    }

    public int getBoardRowSize() {
        return boardRowSize;
    }

    public int getBoardColumnSize() {
        return boardColumnSize;
    }

    public int getSize() {
        return this.boardRowSize * boardColumnSize;
    }



}
