
# TicTacToe AI

TicTacToe AI is a Java-based implementation of the classic TicTacToe game with built-in artificial intelligence using the Minimax algorithm. The project features easy, medium, and hard difficulty bots, each employing the Minimax algorithm to determine optimal moves.

## Features

- **Minimax Algorithm:** The AI players use the Minimax algorithm to evaluate every possible move and select the one that maximizes their chances of winning or minimizing the opponent's chances.

- **Difficulty Levels:**
  - **Easy:** The easy bot makes random moves, suitable for beginners.
  - **Medium:** The medium bot employs blocking potential wins.
  - **Hard:** The hard bot uses the Minimax algorithm with a higher depth, offering a more challenging opponent.

## Getting Started

Follow these steps to set up and run the TicTacToe AI project:

1. Clone the repository:

   ```bash
   git clone https://github.com/rewindbytes/TicTacToeAI.git
   cd TicTacToeAI
   ```

2. Open the project in your preferred Java development environment.

3. Build and run the project.

## Usage

1. Choose the difficulty level when prompted.
2. Make your moves by entering the row and column numbers.
3. Play against the AI and enjoy the challenge!

## Structure

The project structure is organized as follows:

- `TicTacToe.java`: Main class handling the game logic.
- `Board.java`: Generic Class representing the game board.
- `Player.java`: Enum defining player behaviour.
- `Minimax.java`: Generic Class implementing an AI player using the Minimax algorithm.
- `AbstractGame.java`: Abstract Class representing any game.

## Example

```bash
user/easy/medium/hard

Input Command: start hard hard

Game Start!

   1   2   3
1    |   |   
  ---|---|---
2    |   |   
  ---|---|---
3    |   |   

Making move... Difficulty: USER
Enter the coordinates : 2 2

   1   2   3
1    |   |   
  ---|---|---
2    | X |   
  ---|---|---
3    |   |   

...

Player X wins!
```

## License

This project is licensed under the [MIT License](LICENSE).
