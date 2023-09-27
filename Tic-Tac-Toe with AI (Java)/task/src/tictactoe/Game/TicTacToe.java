package tictactoe.Game;

import tictactoe.Game.Algorithms.Minimax;
import tictactoe.Game.Board.Board;
import tictactoe.Game.Board.Player;
import tictactoe.Game.Board.TraverseType;
import tictactoe.Game.GameState.GameState;
import tictactoe.Game.GameState.State;

import java.util.*;


public class TicTacToe extends AbstractGame {

    private String player1, player2;

    private static final int gameCounter;

    private static final HashMap<TicTacToe, GameState> activeState;

    private final HashMap<Player, Difficulty> gameDifficulty;

    private Scanner scanner;


    private int starter = 0;


    static {
        gameCounter = 0;
        activeState = new LinkedHashMap<>();
    }

    public TicTacToe(Player playerOne, Player playerTwo) {
        this(playerOne, playerTwo, new Board<Character>(' ', 3, 3));

    }

    public TicTacToe(Player playerOne, Player playerTwo, Board<?> board) {
        super(playerOne, playerTwo, board);

        scanner = new Scanner(System.in);
        gameDifficulty = new LinkedHashMap<>();

        super.assignPlayerCharacter(playerOne, 'X');
        super.assignPlayerCharacter(playerTwo, 'O');
    }

    public void run() throws InterruptedException {
        while (true) {
            checkGameState();
            startLevel(super.getPlayerOne());
            if (getGameState() != GameState.GAME_NOT_FINISHED) {
                System.out.println(getGameState().getStateName());
                return;
            }
            startLevel(super.getPlayerTwo());
        }
    }

    public void setupInitialState() {

        String input;
        String[] args;


        while (true) {
            System.out.print("Input command: ");
            input = getInput().nextLine();
            args = input.split(" ");

            if (args.length < 3 && !args[0].equalsIgnoreCase("exit")) {
                System.out.println("Bad parameters!");
            } else if (input.equalsIgnoreCase("exit")) {
                System.exit(0);
                break;
            } else break;


        }

        if (Objects.equals(args[0], "start")) {
            player1 = args[1];
            player2 = args[2];

            setDifficulty(Player.PLAYER_1, Difficulty.valueOf(player1.toUpperCase()));
            setDifficulty(Player.PLAYER_2, Difficulty.valueOf(player2.toUpperCase()));

            getBoard().displayBoard();
        }

        setState(GameState.GAME_NOT_FINISHED);


    }

    public static int getGameCounter() {
        return gameCounter;
    }

    private void startLevel(Player player) {

        var playerChar = player.equals(Player.PLAYER_1) ? 'X' : 'O';
        Difficulty difficulty = getDifficulty(player);

        System.out.println("Making move... Difficulty: " + difficulty.name());

        switch (difficulty) {
            case EASY -> easyLevel(playerChar);
            case MEDIUM -> mediumLevel(playerChar, player);
            case HARD -> hardLevel(player);
            default -> {
                if (input(player) == true) {
                    startLevel(player);
                } else return;
            }
        }


    }

    private boolean input(Player player) {

        checkGameState();
        scanner = new Scanner(System.in);
        System.out.println("Enter the coordinates: ");

        int firstInput;
        int secondInput;


        try {
            firstInput = getInput().nextInt();
            secondInput = getInput().nextInt();
        } catch (InputMismatchException ex) {
            System.out.println("You should enter numbers!");
            return true;
        }


        if (firstInput > 3 || secondInput > 3) {
            System.out.println("Coordinates should be from 1 to 3!");
            input(player);
            return true;
        }
        //TODO: isn't convenient if there's a new game / empty matrix, add all empty chars
        if (getBoard().get()[firstInput - 1][secondInput - 1] != ' ') {
            System.out.println("This cell is occupied! Choose another one!");
            return true;
        }


        updateGameState(super.getPlayerCharacter(player), firstInput - 1, secondInput - 1);

        if (player1.equalsIgnoreCase("user")) {
            super.getBoard().displayBoard();
        }
        checkGameState();

        return false;

    }


    public GameState getGameState() {
        return activeState.get(this);
    }


    public void setState(GameState gameState) {
        activeState.put(this, gameState);

    }

    public String AIOutput(Player player, boolean canWin) {
        Player opposingPlayer = player == Player.PLAYER_1 ? Player.PLAYER_2 : Player.PLAYER_1;

        String[] Player_1_TEXT = {
                "Hey " + opposingPlayer + " I think I'll block that",
                opposingPlayer + ", yo, that is the easiest block to see",
                "I like blocking!",
                "I will block, just like Minecraft " + opposingPlayer,
                "Come on " + opposingPlayer + " This is basic geometry!",
                "Pacman chooses better lanes than you " + opposingPlayer,

        };

        String[] Player_2_TEXT = {
                "Just like you " + opposingPlayer + " I can see what to block!",
                "Not so smart are you " + opposingPlayer + "?",
                "Do you think I'm blind " + opposingPlayer + "?",
                "Time to block it!",
                "I know where to put my pieces, I play tetris " + opposingPlayer + "...",
                "Although pacman is probably stuck in a game like you are " + opposingPlayer + " loser",
        };

        String[] win_TEXT = {
                "All that trash talk " + opposingPlayer + ", and I win this easily?",
                "gg " + opposingPlayer,
                "I think you need some lessons " + opposingPlayer + " maybe I can train you! GG",
                "You realise I can win in one move right? " + opposingPlayer,
                "I'm racking up these wins higher than your height " + opposingPlayer,
                "That was fun " + opposingPlayer,
                "Nice game! " + opposingPlayer,
        };

        String[] correctOutput = canWin ? win_TEXT : player.equals(Player.PLAYER_1) ? Player_1_TEXT : Player_2_TEXT;
        int rand = new Random().nextInt(correctOutput.length - 1);

        return correctOutput[rand];

    }


    public void hardLevel(Player player) {


        char currentPlayer = player == Player.PLAYER_1 ? 'X' : 'O';

        Minimax<TicTacToe> minimax = new Minimax<>('X', 'O', this);

        //int[] bestMinimaxMove = minimax2(getSavedState(), player);
        int[] bestMinimaxMove = minimax.run(player);

        updateGameState(currentPlayer, bestMinimaxMove[0], bestMinimaxMove[1]);
        checkGameState();

        int gamePositions = minimax.gamePositions;
        int amountOfGames = minimax.amountOfGames;
        int xWinsCount = minimax.xWinsCount;
        int oWinsCount = minimax.oWinsCount;
        int draws = minimax.draws;

        System.out.println("Searching " + gamePositions + " positions of " + amountOfGames + " games for the optimal move...");
        System.out.println("Found: " + xWinsCount + " Wins for 'X' " + oWinsCount + " Wins for 'O', and " + draws + " draw(s) in the current position!");

        getBoard().displayBoard();

    }


    public void mediumLevel(char input, Player player) {

        ArrayList<Pair<Pair<Integer, Integer>, Character>> squaresToBlock = traverseMatrix();
        Pair<Pair<Integer, Integer>, Character> squareToChoose = null;

        for (Pair<Pair<Integer, Integer>, Character> pairs : squaresToBlock) {
            if (pairs.Value() == input) {
                squareToChoose = pairs;
                break;
            }
            if (pairs.key() != null) squareToChoose = pairs;
        }


        if (squareToChoose != null && squareToChoose.key() != null) {
            String AiTalk = squareToChoose.Value() != input ? AIOutput(player, false) : AIOutput(player, true);
            System.out.println(AiTalk);
            char correctChar = squareToChoose.Value().equals(input) ? input : input == 'X' ? 'X' : 'O';
            updateGameState(correctChar, squareToChoose.key().key(), squareToChoose.key().Value());
            checkGameState();
        } else {
            easyLevel(input);
        }

    }

    public void easyLevel(char input) {
        List<Pair<Integer, Integer>> emptyIndexes = new ArrayList<>();
        Random random = new Random();
        char[][] board = super.getBoard().get();
        for (int i = 0; i < board.length; i++) {
            for (int k = 0; k < board[i].length; k++) {
                if (board[i][k] == '_') {
                    emptyIndexes.add(new Pair<>(i, k));
                }
            }
        }

        Pair<Integer, Integer> randomSquare = emptyIndexes.size() > 1 ? emptyIndexes.get(random.nextInt(emptyIndexes.size() - 1)) : emptyIndexes.get(0);

        updateGameState(input, randomSquare.key(), randomSquare.Value());
        checkGameState();
    }

    public void updateGameState(char input, int row, int col) {
        this.getBoard().insert(input, row, col);
    }


    public void checkGameState() {

        GameState checkState;

        try {
            checkState = State.checkGameState(getBoard().get());
        } catch (NullPointerException ex) {
            this.setState(GameState.GAME_NOT_FINISHED);
            return;
        }

        switch (checkState) {
            case GAME_X_WINS -> this.setState(GameState.GAME_X_WINS);
            case GAME_O_WINS -> this.setState(GameState.GAME_O_WINS);
            case GAME_DRAW -> this.setState(GameState.GAME_DRAW);
        }

    }

    private ArrayList<Pair<Integer, Integer>> getAllEmptySquares(char[][] matrix) {
        ArrayList<Pair<Integer, Integer>> emptySquareList = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == '_') {
                    emptySquareList.add(new Pair<>(i, j));
                }
            }
        }
        return emptySquareList;
    }

    public Pair<Integer, Integer> emptySquares(TraverseType traverseType, int row) {

        char[][] board = getBoard().get();

        switch (traverseType) {
            case HORIZONTAL -> {
                for (int i = 0; i < board.length; i++) {
                    if (board[row][i] == '_') {
                        return new Pair<>(row, i);
                    }

                }
            }
            case VERTICAL -> {
                for (int k = 0; k < board.length; k++) {
                    if (board[k][row] == '_') {
                        return new Pair<>(k, row);
                    }
                }
            }
            case LEFT_DIAGONAL -> {
                for (int i = 0; i < board.length; i++) {
                    if (board[i][i] == '_') {
                        return new Pair<>(i, i);
                    }
                }
            }
            case RIGHT_DIAGONAL -> {
                for (int i = 0; i < board.length; i++) {
                    if (board[i][board[i].length - 1 - i] == '_') {
                        return new Pair<>(i, board[i].length - 1 - i);
                    }
                }
            }
        }

        return null;
    }

    public ArrayList<Pair<Pair<Integer, Integer>, Character>> traverseMatrix() {

        char[][] matrix = super.getBoard().get();
        HashMap<String, Integer> stateMap = new HashMap<>();
        ArrayList<Pair<Pair<Integer, Integer>, Character>> blockableSquares = new ArrayList<>();

        //Vertical & Horizontal Traversal
        for (int i = 0; i < matrix.length; i++) {
            stateMap.put("X_HORIZONTAL", 0);
            stateMap.put("O_HORIZONTAL", 0);
            stateMap.put("X_VERTICAL", 0);
            stateMap.put("O_VERTICAL", 0);
            for (int k = 0; k < matrix[i].length; k++) {
                //Left -> Right
                switch (matrix[i][k]) {
                    case 'O' -> stateMap.put("O_HORIZONTAL", stateMap.get("O_HORIZONTAL") + 1);
                    case 'X' -> stateMap.put("X_HORIZONTAL", stateMap.get("X_HORIZONTAL") + 1);
                }
                if (stateMap.get("O_HORIZONTAL") == 2) {
                    if (emptySquares(TraverseType.HORIZONTAL, i) != null) {
                        blockableSquares.add(new Pair<>(emptySquares(TraverseType.HORIZONTAL, i), 'O'));
                    }
                }
                if (stateMap.get("X_HORIZONTAL") == 2) {
                    if (emptySquares(TraverseType.HORIZONTAL, i) != null) {
                        blockableSquares.add(new Pair<>(emptySquares(TraverseType.HORIZONTAL, i), 'X'));
                    }
                }

                //Up -> Down
                switch (matrix[k][i]) {
                    case 'O' -> stateMap.put("O_VERTICAL", stateMap.get("O_VERTICAL") + 1);
                    case 'X' -> stateMap.put("X_VERTICAL", stateMap.get("X_VERTICAL") + 1);
                }
                if (stateMap.get("O_VERTICAL") == 2) {
                    if (emptySquares(TraverseType.VERTICAL, i) != null) {
                        blockableSquares.add(new Pair<>(emptySquares(TraverseType.VERTICAL, i), 'O'));
                    }
                }
                if (stateMap.get("X_VERTICAL") == 2) {
                    if (emptySquares(TraverseType.VERTICAL, i) != null) {
                        blockableSquares.add(new Pair<>(emptySquares(TraverseType.VERTICAL, i), 'X'));
                    }
                }


            }
        }


        //Left Diagonal
        stateMap.put("X_LEFT_DIAGONAL", 0);
        stateMap.put("O_LEFT_DIAGONAL", 0);
        for (int i = 0; i < 1; i++) {
            for (int k = 0; k < matrix[i].length; k++) {

                if (stateMap.get("O_LEFT_DIAGONAL") >= 2) {
                    blockableSquares.add(new Pair<>(emptySquares(TraverseType.LEFT_DIAGONAL, 0), 'O'));
                }
                if (stateMap.get("X_LEFT_DIAGONAL") >= 2) {
                    blockableSquares.add(new Pair<>(emptySquares(TraverseType.LEFT_DIAGONAL, 0), 'X'));
                }
                switch (matrix[k][k]) {
                    case 'O' -> stateMap.put("O_LEFT_DIAGONAL", stateMap.get("O_LEFT_DIAGONAL") + 1);
                    case 'X' -> stateMap.put("X_LEFT_DIAGONAL", stateMap.get("X_LEFT_DIAGONAL") + 1);
                }
            }
        }

        //Right Diagonal
        stateMap.put("X_RIGHT_DIAGONAL", 0);
        stateMap.put("O_RIGHT_DIAGONAL", 0);
        for (int i = 0; i < matrix.length; i++) {

            switch (matrix[i][matrix[i].length - i - 1]) {
                case 'O' -> stateMap.put("O_RIGHT_DIAGONAL", stateMap.get("O_RIGHT_DIAGONAL") + 1);
                case 'X' -> stateMap.put("X_RIGHT_DIAGONAL", stateMap.get("X_RIGHT_DIAGONAL") + 1);
            }

            if (stateMap.get("O_RIGHT_DIAGONAL") == 2) {
                blockableSquares.add(new Pair<>(emptySquares(TraverseType.RIGHT_DIAGONAL, 0), 'O'));
            }
            if (stateMap.get("X_RIGHT_DIAGONAL") == 2) {
                blockableSquares.add(new Pair<>(emptySquares(TraverseType.RIGHT_DIAGONAL, 0), 'X'));
            }

        }

        return blockableSquares;
    }

    public Scanner getInput() {
        return this.scanner;
    }

    public Difficulty getDifficulty(Player player) {
        return this.gameDifficulty.get(player);
    }

    private boolean setDifficulty(Player player, Difficulty difficulty) {

        if (gameDifficulty.size() < 2 && !gameDifficulty.containsKey(player)) {
            gameDifficulty.put(player, difficulty);
            return true;
        }

        System.out.println("The difficulty has already been set.");
        return false;

    }
}
