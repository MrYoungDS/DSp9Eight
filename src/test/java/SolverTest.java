import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.Test;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.In;

import java.util.Arrays;
import java.util.Scanner;

public class SolverTest {


    private Solver generateSolver(String filename) {
        // create initial board from file
        In in = new In("./8puzzle-test-files/" + filename);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        return new Solver(initial);
    }

    @Test
    public void testShortPuzzleSolvable() {
        for (int i = 1; i < 10; i++) {
            Solver testSolver = generateSolver(String.format("puzzle%02d.txt", i));
            assertTrue(testSolver.isSolvable(), "The puzzle from file puzzle" + i + ".txt is solvable.");
        }
    }

    @Test
    public void testZeroPuzzleMoves() {
        Solver testSolver = generateSolver("puzzle00.txt");
        assertEquals(testSolver.moves(), 0, "The puzzle from file puzzle0.txt is already solved.");
    }

    @Test
    @Timeout(5)
    public void testShortPuzzleMoves() {
        for (int i = 1; i < 10; i++) {
            Solver testSolver = generateSolver(String.format("puzzle%02d.txt", i));
            assertEquals(testSolver.moves(), i, "The puzzle from file puzzle" + i + ".txt can be solved in " + i + "moves.");
        }
    }

    @Test
    public void testUnsolvableSolvable() {
        for (int d = 2; d < 7; d++) {
            int[] testArray = new int[d * d];
            for (int i = 0; i < testArray.length; i++) {
                testArray[i] = i;
            }
            StdRandom.shuffle(testArray);
            int[][] testBlocks = new int[d][d];
            for (int row = 0; row < d; row++)
                for (int col = 0; col < d; col++)
                    testBlocks[row][col] = testArray[row * d + col];
            Board testBoard = new Board(testBlocks);
            assertEquals(testBoard.dimension(), d, "Board has wrong dimension.");
        }
    }

    @Test
    @Timeout(10)
    public void testChallengingPuzzle30() {
        Solver solver = generateSolver("puzzle30.txt");
        assertTrue(solver.isSolvable());
        assertEquals(30, solver.moves());
    }
}
