package spw4.game2048;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.util.*;
import java.util.stream.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

  Game game;

  @BeforeEach
  void setup() {
    game = new GameImpl();
    GameImpl.randomGenerator = new RandomGeneratorStub(
      List.of(3, 3, 1, 2, 2, 3, 2, 2));
  }

  @Test
  void ctorWhenNewGameReturnsEmptyGame() {
    int sum = getGameBoardSum();
    assertEquals(0, sum);
  }

  @Nested
  class Initialize {
    @Test
    void initializeWhenGameIsEmptyTwoTilesAreCreated() {
      game.initialize();

      int sum = getGameBoardSum();

      assertEquals(4, sum);
    }

    @Test
    void initializeWhenGameIsNotEmptyTwoTilesAreCreated() {
      int[][] board = new int[][]{
        {0, 2, 4, 2},
        {0, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
      };
      game = new GameImpl(board);
      game.initialize();

      int sum = getGameBoardSum();

      assertEquals(4, sum);
    }

  }


  @Nested
  class MoveLeft {
    @Test
    void moveLeftNoTilesMergedPositionsAreUpdated() {
      int[][] board = new int[][]{
        {0, 0, 2, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 2}
      };
      game = new GameImpl(board);
      game.move(Direction.left);

      assertAll(
        () -> assertEquals(2, game.getValueAt(0, 0)),
        () -> assertEquals(0, game.getValueAt(0, 1)),
        () -> assertEquals(0, game.getValueAt(0, 2)),
        () -> assertEquals(0, game.getValueAt(0, 3)),
        () -> assertEquals(2, game.getValueAt(3, 0))
               );
    }

    @Test
    void moveLeftWhenTilesDifferNoTilesMerged() {
      int[][] board = new int[][]{
        {0, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 2, 4, 2},
        {0, 0, 0, 0}
      };
      game = new GameImpl(board);
      game.move(Direction.left);

      assertAll(
        () -> assertEquals(2, game.getValueAt(2, 0)),
        () -> assertEquals(4, game.getValueAt(2, 1)),
        () -> assertEquals(2, game.getValueAt(2, 2)),
        () -> assertEquals(0, game.getValueAt(2, 3))
               );
    }

    @ParameterizedTest(name = "board: {0}")
    @MethodSource
    void moveLeftTilesAreMergedPositionsAreUpdated(int[][] board) {
      game = new GameImpl(board);
      game.move(Direction.left);

      assertAll(
        () -> assertEquals(4, game.getValueAt(0, 0)),
        () -> assertEquals(0, game.getValueAt(0, 1))
               );
    }

    static Stream<Arguments> moveLeftTilesAreMergedPositionsAreUpdated() {
      return Stream.of(
        Arguments.of((Object) new int[][]{
          {0, 2, 0, 2},
          {0, 0, 0, 0},
          {0, 0, 0, 0},
          {0, 0, 0, 0}}),
        Arguments.of((Object) new int[][]{
          {0, 2, 2, 0},
          {0, 0, 0, 0},
          {0, 0, 0, 0},
          {0, 0, 0, 0}}),
        Arguments.of((Object) new int[][]{
          {0, 0, 2, 2},
          {0, 0, 0, 0},
          {0, 0, 0, 0},
          {0, 0, 0, 0}}),
        Arguments.of((Object) new int[][]{
          {2, 2, 0, 0},
          {0, 0, 0, 0},
          {0, 0, 0, 0},
          {0, 0, 0, 0}}),
        Arguments.of((Object) new int[][]{
          {2, 0, 0, 2},
          {0, 0, 0, 0},
          {0, 0, 0, 0},
          {0, 0, 0, 0}}));
    }

    @Test
    void moveLeftWhenMultipleMergesTilesMerge() {
      int[][] board = new int[][]{
        {0, 0, 0, 0},
        {2, 2, 2, 2},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
      };
      game = new GameImpl(board);
      game.move(Direction.left);

      assertAll(
        () -> assertEquals(4, game.getValueAt(1, 0)),
        () -> assertEquals(4, game.getValueAt(1, 1)),
        () -> assertEquals(0, game.getValueAt(1, 2)),
        () -> assertEquals(0, game.getValueAt(1, 3))
               );
    }

    @Test
    void moveLeftWhenMultipleMergesNotAllTilesAreMerged() {
      int[][] board = new int[][]{
        {0, 0, 0, 0},
        {4, 2, 2, 2},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
      };
      game = new GameImpl(board);
      game.move(Direction.left);

      assertAll(
        () -> assertEquals(4, game.getValueAt(1, 0)),
        () -> assertEquals(4, game.getValueAt(1, 1)),
        () -> assertEquals(2, game.getValueAt(1, 2)),
        () -> assertEquals(0, game.getValueAt(1, 3))
               );
    }
  }

  @Nested
  class MoveRight {
    @Test
    void moveRightNoTilesMergedPositionsAreUpdated() {
      int[][] board = new int[][]{
        {0, 0, 0, 0},
        {2, 0, 0, 0},
        {0, 2, 0, 0},
        {0, 0, 0, 0}
      };
      game = new GameImpl(board);
      game.move(Direction.right);

      assertAll(
        () -> assertEquals(2, game.getValueAt(1, 3)),
        () -> assertEquals(2, game.getValueAt(2, 3))
               );
    }

    @Test
    void moveRightWhenTilesDifferNoTilesMerged() {
      int[][] board = new int[][]{
        {0, 0, 0, 0},
        {0, 0, 0, 0},
        {2, 4, 2, 0},
        {0, 0, 0, 0}
      };
      game = new GameImpl(board);
      game.move(Direction.right);

      assertAll(
        () -> assertEquals(0, game.getValueAt(2, 0)),
        () -> assertEquals(2, game.getValueAt(2, 1)),
        () -> assertEquals(4, game.getValueAt(2, 2)),
        () -> assertEquals(2, game.getValueAt(2, 3))
               );
    }

    @ParameterizedTest(name = "board: {0}")
    @MethodSource
    void moveRightTilesAreMergedPositionsAreUpdated(int[][] board) {
      game = new GameImpl(board);
      game.move(Direction.right);

      assertAll(
        () -> assertEquals(4, game.getValueAt(0, 3)),
        () -> assertEquals(0, game.getValueAt(0, 2))
               );
    }

    static Stream<Arguments> moveRightTilesAreMergedPositionsAreUpdated() {
      return Stream.of(
        Arguments.of((Object) new int[][]{
          {0, 2, 0, 2},
          {0, 0, 0, 0},
          {0, 0, 0, 0},
          {0, 0, 0, 0}}),
        Arguments.of((Object) new int[][]{
          {0, 2, 2, 0},
          {0, 0, 0, 0},
          {0, 0, 0, 0},
          {0, 0, 0, 0}}),
        Arguments.of((Object) new int[][]{
          {0, 0, 2, 2},
          {0, 0, 0, 0},
          {0, 0, 0, 0},
          {0, 0, 0, 0}}),
        Arguments.of((Object) new int[][]{
          {2, 2, 0, 0},
          {0, 0, 0, 0},
          {0, 0, 0, 0},
          {0, 0, 0, 0}}),
        Arguments.of((Object) new int[][]{
          {2, 0, 0, 2},
          {0, 0, 0, 0},
          {0, 0, 0, 0},
          {0, 0, 0, 0}})
                      );
    }

    @Test
    void moveRightWhenMultipleMergesTilesMerge() {
      int[][] board = new int[][]{
        {0, 0, 0, 0},
        {2, 2, 2, 2},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
      };
      game = new GameImpl(board);
      game.move(Direction.right);

      assertAll(
        () -> assertEquals(0, game.getValueAt(1, 0)),
        () -> assertEquals(0, game.getValueAt(1, 1)),
        () -> assertEquals(4, game.getValueAt(1, 2)),
        () -> assertEquals(4, game.getValueAt(1, 3))
               );
    }

    @Test
    void moveRightWhenMultipleMergesNotAllTilesAreMerged() {
      int[][] board = new int[][]{
        {0, 0, 0, 0},
        {2, 4, 2, 2},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
      };
      game = new GameImpl(board);
      game.move(Direction.right);

      assertAll(
        () -> assertEquals(0, game.getValueAt(1, 0)),
        () -> assertEquals(2, game.getValueAt(1, 1)),
        () -> assertEquals(4, game.getValueAt(1, 2)),
        () -> assertEquals(4, game.getValueAt(1, 3))
               );
    }
  }

  @Nested
  class UpMoves {
    @Test
    void moveUpNoTilesMergedPositionsAreUpdated() {
      int[][] board = new int[][]{
        {0, 0, 0, 0},
        {2, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 2, 0, 0}
      };
      game = new GameImpl(board);
      game.move(Direction.up);

      assertAll(
        () -> assertEquals(2, game.getValueAt(0, 0)),
        () -> assertEquals(2, game.getValueAt(0, 1))
               );
    }

    @Test
    void moveUpWhenTilesDifferNoTilesMerged() {
      int[][] board = new int[][]{
        {0, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0},
        {2, 4, 2, 0}
      };
      game = new GameImpl(board);
      game.move(Direction.up);

      assertAll(
        () -> assertEquals(2, game.getValueAt(0, 0)),
        () -> assertEquals(4, game.getValueAt(0, 1)),
        () -> assertEquals(2, game.getValueAt(0, 2)),
        () -> assertEquals(0, game.getValueAt(0, 3))
               );
    }

    @ParameterizedTest(name = "board: {0}")
    @MethodSource
    void moveUpTilesAreMergedPositionsAreUpdated(int[][] board) {
      game = new GameImpl(board);
      game.move(Direction.up);

      assertAll(
        () -> assertEquals(4, game.getValueAt(0, 3)),
        () -> assertEquals(0, game.getValueAt(1, 3))
               );
    }

    static Stream<Arguments> moveUpTilesAreMergedPositionsAreUpdated() {
      return Stream.of(
        Arguments.of((Object) new int[][]{
          {0, 0, 0, 2},
          {0, 0, 0, 2},
          {0, 0, 0, 0},
          {0, 0, 0, 0}}),
        Arguments.of((Object) new int[][]{
          {0, 0, 0, 2},
          {0, 0, 0, 0},
          {0, 0, 0, 0},
          {0, 0, 0, 2}}),
        Arguments.of((Object) new int[][]{
          {0, 0, 0, 0},
          {0, 0, 0, 0},
          {0, 0, 0, 2},
          {0, 0, 0, 2}}),
        Arguments.of((Object) new int[][]{
          {0, 0, 0, 0},
          {0, 0, 0, 2},
          {0, 0, 0, 0},
          {0, 0, 0, 2}}));
    }

    @Test
    void moveUpWhenMultipleMergesTilesMerge() {
      int[][] board = new int[][]{
        {0, 2, 0, 0},
        {0, 2, 0, 0},
        {0, 2, 0, 0},
        {0, 2, 0, 0}
      };
      game = new GameImpl(board);
      game.move(Direction.up);

      assertAll(
        () -> assertEquals(4, game.getValueAt(0, 1)),
        () -> assertEquals(4, game.getValueAt(1, 1)),
        () -> assertEquals(0, game.getValueAt(2, 1)),
        () -> assertEquals(0, game.getValueAt(3, 1))
               );
    }

    @Test
    void moveUpWhenMultipleMergesNotAllTilesAreMerged() {
      int[][] board = new int[][]{
        {0, 2, 0, 0},
        {0, 4, 0, 0},
        {0, 2, 0, 0},
        {0, 2, 0, 0}
      };
      game = new GameImpl(board);
      game.move(Direction.up);

      assertAll(
        () -> assertEquals(2, game.getValueAt(0, 1)),
        () -> assertEquals(4, game.getValueAt(1, 1)),
        () -> assertEquals(4, game.getValueAt(2, 1)),
        () -> assertEquals(0, game.getValueAt(3, 1))
               );
    }
  }

  @Nested
  class DownMoves {
    @Test
    void moveDownNoTilesMergedPositionsAreUpdated() {
      int[][] board = new int[][]{
        {0, 0, 0, 0},
        {2, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 2, 0, 0}
      };
      game = new GameImpl(board);
      game.move(Direction.down);

      assertAll(
        () -> assertEquals(2, game.getValueAt(3, 0)),
        () -> assertEquals(2, game.getValueAt(3, 1))
               );
    }

    @Test
    void moveDownWhenTilesDifferNoTilesMerged() {
      int[][] board = new int[][]{
        {2, 0, 0, 0},
        {4, 0, 0, 0},
        {2, 0, 0, 0},
        {0, 0, 0, 0}
      };
      game = new GameImpl(board);
      game.move(Direction.down);

      assertAll(
        () -> assertEquals(0, game.getValueAt(0, 0)),
        () -> assertEquals(2, game.getValueAt(1, 0)),
        () -> assertEquals(4, game.getValueAt(2, 0)),
        () -> assertEquals(2, game.getValueAt(3, 0))
               );
    }

    @ParameterizedTest(name = "board: {0}")
    @MethodSource
    void moveDownTilesAreMergedPositionsAreUpdated(int[][] board) {
      game = new GameImpl(board);
      game.move(Direction.down);

      assertEquals(4, game.getValueAt(3, 2));
    }

    static Stream<Arguments> moveDownTilesAreMergedPositionsAreUpdated() {
      return Stream.of(
        Arguments.of((Object) new int[][]{
          {0, 0, 2, 0},
          {0, 0, 2, 0},
          {0, 0, 0, 0},
          {0, 0, 0, 0}}),
        Arguments.of((Object) new int[][]{
          {0, 0, 2, 0},
          {0, 0, 0, 0},
          {0, 0, 0, 0},
          {0, 0, 2, 0}}),
        Arguments.of((Object) new int[][]{
          {0, 0, 0, 0},
          {0, 0, 0, 0},
          {0, 0, 2, 0},
          {0, 0, 2, 0}}),
        Arguments.of((Object) new int[][]{
          {0, 0, 0, 0},
          {0, 0, 2, 0},
          {0, 0, 0, 0},
          {0, 0, 2, 0}}));
    }

    @Test
    void moveDownWhenMultipleMergesTilesMerge() {
      int[][] board = new int[][]{
        {0, 2, 0, 0},
        {0, 2, 0, 0},
        {0, 2, 0, 0},
        {0, 2, 0, 0}
      };
      game = new GameImpl(board);
      game.move(Direction.down);

      assertAll(
        () -> assertEquals(0, game.getValueAt(0, 1)),
        () -> assertEquals(0, game.getValueAt(1, 1)),
        () -> assertEquals(4, game.getValueAt(2, 1)),
        () -> assertEquals(4, game.getValueAt(3, 1))
               );
    }

    @Test
    void moveDownWhenMultipleMergesNotAllTilesAreMerged() {
      int[][] board = new int[][]{
        {0, 2, 0, 0},
        {0, 2, 0, 0},
        {0, 4, 0, 0},
        {0, 2, 0, 0}
      };
      game = new GameImpl(board);
      game.move(Direction.down);

      assertAll(
        () -> assertEquals(0, game.getValueAt(0, 1)),
        () -> assertEquals(4, game.getValueAt(1, 1)),
        () -> assertEquals(4, game.getValueAt(2, 1)),
        () -> assertEquals(2, game.getValueAt(3, 1))
               );
    }
  }

  @Nested
  class GeneralMove {
    @Test
    void moveWhenMoveTileIsCreated() {
      game.initialize();
      game.move(Direction.up);

      int count = 0;
      count = getCountGameBoardValues(count);

      assertEquals(3, count);
    }

    @Test
    void moveWhenNoChangeNoTileIsCreated() {
      int[][] board = new int[][]{
        {0, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 2, 0, 0}
      };
      game = new GameImpl(board);

      game.move(Direction.down);

      int count = 0;
      count = getCountGameBoardValues(count);
      assertEquals(1, count);
    }

    @Test
    void moveWhenTileIsPresentNewTileIsGenerated() {
      int[][] board = new int[][]{
        {0, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 2, 0},
        {0, 0, 0, 2}
      };
      game = new GameImpl(board);

      game.move(Direction.down);

      int count = 0;
      count = getCountGameBoardValues(count);
      assertEquals(3, count);
    }
  }

  private int getCountGameBoardValues(int count) {
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        if (game.getValueAt(i, j) != 0) count++;
      }
    }
    return count;
  }

  @Nested
  class Score {
    @Test
    void scoreWhenGameIsStartedScoreIsZero() {
      game.initialize();

      int score = game.getScore();

      assertEquals(0, score);
    }

    @Test
    void scoreWhenNoTilesAreMergedScoreDoesNotChange() {
      int[][] board = new int[][]{
        {0, 2, 0, 0},
        {0, 2, 0, 0},
        {0, 4, 0, 0},
        {0, 2, 0, 0}
      };
      game = new GameImpl(board);
      game.move(Direction.left);

      int score = game.getScore();

      assertEquals(0, score);
    }

    @Test
    void scoreWhenTilesAreMergedScoreChanges() {
      int[][] board = new int[][]{
        {0, 2, 0, 0},
        {0, 2, 0, 0},
        {0, 4, 0, 0},
        {0, 2, 0, 0}
      };
      game = new GameImpl(board);
      game.move(Direction.down);

      int score = game.getScore();

      assertEquals(4, score);
    }
  }

  @Nested
  class IsOver {
    @Test
    void isOverWhenNewBoardReturnsFalse() {
      assertFalse(game.isOver());
    }

    @Test
    void isOverWhenFullAndMoreMovesPossibleReturnsTrue() {
      int[][] board = new int[][]{
        {2, 4, 2, 4},
        {4, 2, 4, 2},
        {2, 4, 2, 4},
        {4, 2, 4, 4}
      };
      game = new GameImpl(board);
      assertFalse(game.isOver());
    }

    @Test
    void isOverWhenNoMoreMovesPossibleReturnsTrue() {
      int[][] board = new int[][]{
        {2, 4, 2, 4},
        {4, 2, 4, 2},
        {2, 4, 2, 4},
        {4, 2, 4, 2}
      };
      game = new GameImpl(board);
      assertTrue(game.isOver());
    }
  }

  @Nested
  class IsWon {
    @Test
    void isWonWhenNewGameReturnsFalse() {
      assertFalse(game.isWon());
    }

    @Test
    void isWonWhenTwo1024TilesGetMergedReturnsTrue() {
      int[][] board = new int[][]{
        {0, 0, 0, 0},
        {1024, 1024, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
      };
      game = new GameImpl(board);
      game.move(Direction.left);

      assertAll(
        () -> assertTrue(game.isWon()),
        () -> assertTrue(game.isOver()));
    }
  }

  @Nested
  class GetMoves {
    @Test
    void getMovesWhenNewGameReturnsZero() {
      assertEquals(0, game.getMoves());
    }

    @Test
    void getMovesWhenMultipleMovesReturnsCount() {
      int[][] board = new int[][]{
        {0, 0, 0, 0},
        {2, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
      };
      game = new GameImpl(board);

      game.move(Direction.up);
      game.move(Direction.left);
      game.move(Direction.down);

      assertEquals(3, game.getMoves());
    }
  }

  @Nested
  class ToString {
    @Test
    void toStringWhenNewGameReturnsInitialState() {
      String result = game.toString();

      assertAll(
        () -> assertTrue(result.contains("Moves: 0")),
        () -> assertTrue(result.contains("Score: 0")),
        () -> assertFalse(result.contains("2")));
    }

    @Test
    void toStringWhenGameHasScoreReturnsGameStateWithScore() {
      int[][] board = new int[][]{
        {0, 0, 0, 0},
        {1024, 1024, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
      };
      game = new GameImpl(board);
      game.move(Direction.left);
      String result = game.toString();

      assertAll(
        () -> assertTrue(result.contains("Moves: 1")),
        () -> assertTrue(result.contains("Score: 2048")),
        () -> assertTrue(result.contains("2")));
    }
  }

  private int getGameBoardSum() {
    int sum = 0;
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        sum += game.getValueAt(i, j);
      }
    }
    return sum;
  }
}

/*
1. test initialize board -> assert 2 tiles
2. make move -> new tile is "randomly" placed - size is increased by 1
3. not sure yet: 2 or 4 with new tile (probability of 90%)
4. movement:
- all directions, no merge
- all directions, 1 merge
- all directions, multiple merge (maybe score)
5. score test
6. win or lose condition
 */