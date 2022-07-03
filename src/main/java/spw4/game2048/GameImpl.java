package spw4.game2048;

import java.util.*;

public class GameImpl implements Game {

  public static final int GAME_SIZE = 4;
  private int[][] board;
  private int score;
  private boolean won;
  private int moves;
  public static RandomGenerator randomGenerator = new RandomGenerator();
  private boolean hasChanges;

  public GameImpl() {
    board = new int[GAME_SIZE][GAME_SIZE];
  }

  public GameImpl(int[][] board) {
    this();
    for (int i = 0; i < GAME_SIZE; i++) {
      this.board[i] = Arrays.copyOf(board[i], GAME_SIZE);
    }
  }

    public int getMoves() {
        // to do ...
        return 0;
    }

    public int getScore() {
        // to do ...
        return 0;
    }

  public int getValueAt(int x, int y) {
    return board[x][y];
  }


    public boolean isOver() {
        // to do ...
        return false;
    }

    public boolean isWon() {
        // to do ...
        return false;
    }

    @Override
    public String toString() {
        // to do ...
        return "";
    }

  public void initialize() {
    board = new int[GAME_SIZE][GAME_SIZE];
    generateTiles(2);
  }

  private void generateTiles(int amount) {
    if (isFull())
      return;

    for (int i = 0; i < amount; i++) {
      int x, y;
      do {
        x = randomGenerator.getRandomIndex();
        y = randomGenerator.getRandomIndex();
      } while (board[x][y] != 0);
      board[x][y] = randomGenerator.getTileValue();
    }
  }

  public void move(Direction direction) {
    hasChanges = false;

    if (direction == Direction.right) {
      // rotate 180
      boardRotate();
      boardRotate();
    } else if (direction == Direction.up) {
      // rotate 90
      boardRotate();
    } else if (direction == Direction.down) {
      // rotate 270
      boardRotate();
      boardRotate();
      boardRotate();
    }

    for (int i = 0; i < GAME_SIZE; i++) {
      moveEmptySpacesLeft(i);
      mergeColumnsLeft(i);
      moveEmptySpacesLeft(i);
    }

    if (direction == Direction.right) {
      // rotate 180
      boardRotate();
      boardRotate();
    } else if (direction == Direction.up) {
      // rotate 270
      boardRotate();
      boardRotate();
      boardRotate();
    } else if (direction == Direction.down) {
      // rotate 90
      boardRotate();
    }
    if (hasChanges) {
      moves++;
      generateTiles(1);
    }
  }

  void boardRotate() {
    boardTranspose();
    boardReverse();
  }

  void boardTranspose() {
    for (int i = 0; i < GAME_SIZE; i++) {
      for (int j = i + 1; j < GAME_SIZE; j++) {
        int temp = board[i][j];
        board[i][j] = board[j][i];
        board[j][i] = temp;
      }
    }
  }

  void boardReverse() {
    for (int i = 0; i < GAME_SIZE; i++) {
      for (int j = 0, k = GAME_SIZE - 1; j < k; j++, k--) {
        int temp = board[j][i];
        board[j][i] = board[k][i];
        board[k][i] = temp;
      }
    }
  }

  private void moveEmptySpacesLeft(int row) {
    for (int j = 0; j < GAME_SIZE; j++) {
      if (board[row][j] != 0) {
        continue;
      }
      swapToCorrectPositionLeft(row, j);
    }
  }

  private void swapToCorrectPositionLeft(int row, int col) {
    int currPos = col;
    while (col < GAME_SIZE - 1) {
      if (board[row][col + 1] != 0) {
        board[row][currPos] = board[row][col + 1];
        board[row][col + 1] = 0;
        currPos = col + 1;
        hasChanges = true;
      }
      col++;
    }
  }

  private void mergeColumnsLeft(int row) {
    for (int j = 1; j < GAME_SIZE; j++) {
      if (board[row][j] == 0) {
        continue;
      }
      if (board[row][j - 1] == board[row][j]) {
        board[row][j] = 0;
        board[row][j - 1] *= 2;
        score += board[row][j - 1];
        if (board[row][j - 1] == 2048) {
          won = true;
        }
        hasChanges = true;
      }
    }
  }
}