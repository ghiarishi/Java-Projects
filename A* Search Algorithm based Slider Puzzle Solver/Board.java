// Program to create a board, check if its solvable, and find its neighbors

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Board {

    // create a board from an n-by-n array of tiles,
    private final int[] blocks;
    private final int n;

    // 4 lines below are for caching purposes
    private int hammingNum;
    private int manhattanNum;
    private int rowZero;
    private int colZero;

    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {

        n = tiles.length;
        hammingNum = -1;
        manhattanNum = -1;
        rowZero = -1;
        colZero = -1;

        blocks = new int[n * n]; // to save memory

        // defensive copy
        // convert nxn matrix into n*n length array
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[(i * n) + j] = tiles[i][j];
                if (tiles[i][j] == 0) {
                    // cached for later
                    rowZero = i;
                    colZero = j;
                }
            }
        }
    }


    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(n);
        sb.append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(" " + String.format("%2d ", tileAt(i, j)));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // tile at (row, col) or 0 if blank
    public int tileAt(int row, int col) {
        if (row >= n || col >= n) {
            throw new IllegalArgumentException("Row or Col >= n");
        }
        return this.blocks[(row * n) + col];
    }

    // board size n
    public int size() {
        return n;
    }

    // what should the value of the tile at i,j be?
    private int tileVal(int i, int j) {
        if (i == n - 1 && j == n - 1) {
            return 0;
        }
        return ((i * n) + j + 1);
    }

    // number of tiles out of place
    public int hamming() {
        if (hammingNum == -1) {
            hammingNum = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if ((tileAt(i, j) != tileVal(i, j)) && (tileAt(i, j) != 0)) {
                        hammingNum++;
                    }
                }
            }
        }
        return hammingNum;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        if (manhattanNum == -1) {
            manhattanNum = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int row = (tileAt(i, j) - 1) / n;
                    int col = (tileAt(i, j) % n) - 1;
                    if (col == -1) { // protect from overflow over n-1
                        col = n - 1;
                    }
                    if (tileAt(i, j) != 0) {
                        manhattanNum += Math.abs(i - row) + Math.abs(j - col);
                    }
                }
            }
        }
        return manhattanNum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        Board that = (Board) y;
        return Arrays.equals(this.blocks, that.blocks);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighbours = new Stack<Board>();
        if (rowZero != 0) {
            // up
            int[][] tilesUp = arrClone(blocks);
            tilesUp = exch(tilesUp, 0);
            Board upBoard = new Board(tilesUp);
            neighbours.push(upBoard);
        }
        if (rowZero != n - 1) {
            // down
            int[][] tilesDown = arrClone(blocks);
            tilesDown = exch(tilesDown, 1);
            Board downBoard = new Board(tilesDown);
            neighbours.push(downBoard);
        }
        if (colZero != 0) {
            // left
            int[][] tilesLeft = arrClone(blocks);
            tilesLeft = exch(tilesLeft, 2);
            Board leftBoard = new Board(tilesLeft);
            neighbours.push(leftBoard);
        }
        if (colZero != n - 1) {
            // right
            int[][] tilesRight = arrClone(blocks);
            tilesRight = exch(tilesRight, 3);
            Board rightBoard = new Board(tilesRight);
            neighbours.push(rightBoard);
        }
        return neighbours;
    }

    // exchange 0 and another tile
    private int[][] exch(int[][] arr, int dir) {
        if (dir == 0) {
            int temp = arr[rowZero - 1][colZero];
            arr[rowZero - 1][colZero] = 0;
            arr[rowZero][colZero] = temp;
        }
        if (dir == 1) {
            int temp = arr[rowZero + 1][colZero];
            arr[rowZero + 1][colZero] = 0;
            arr[rowZero][colZero] = temp;
        }
        if (dir == 2) {
            int temp = arr[rowZero][colZero - 1];
            arr[rowZero][colZero - 1] = 0;
            arr[rowZero][colZero] = temp;
        }
        if (dir == 3) {
            int temp = arr[rowZero][colZero + 1];
            arr[rowZero][colZero + 1] = 0;
            arr[rowZero][colZero] = temp;
        }
        return arr;
    }

    // is this board solvable?
    public boolean isSolvable() {
        if (n % 2 == 1) {
            return (inversions() % 2 == 0);
        }
        else {
            return ((inversions() + rowZero) % 2 == 1);
        }
    }
    // calculate # inversions required
    private int inversions() {
        int inverts = 0;
        int correctionFactor = 0; // to skip 0 while making the rowOrder array
        int[] rowOrder = new int[(n * n) - 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tileAt(i, j) != 0) {
                    rowOrder[n * i + j - correctionFactor] = tileAt(i, j);
                }
                else {
                    correctionFactor = 1;
                }
            }
        }
        for (int i = 0; i < rowOrder.length - 1; i++) {
            for (int j = i + 1; j < rowOrder.length; j++) {
                if (rowOrder[i] > rowOrder[j]) {
                    inverts++;
                }
            }
        }
        return inverts;
    }

    // clone an array
    private int[][] arrClone(int[] array) {
        int[][] cloned = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // nxn matrix to let the APi work when creating new boards
                cloned[i][j] = tileAt(i, j);
            }
        }
        return cloned;
    }

    // unit testing (required)
    public static void main(String[] args) {
        int[][] test = new int[3][3];
        test[0][0] = 1;
        test[0][1] = 3;
        test[0][2] = 4;
        test[1][0] = 2;
        test[1][1] = 5;
        test[1][2] = 7;
        test[2][0] = 0;
        test[2][1] = 6;
        test[2][2] = 8;
        Board testBoard = new Board(test);
        testBoard.hamming();
        StdOut.println("Hamming Number = " + testBoard.hamming());
        testBoard.manhattan();
        StdOut.println("Manhattan Number = " + testBoard.manhattan());
        StdOut.println("Board is the Goal board. " + testBoard.isGoal());
        StdOut.println("Inversions = " + testBoard.inversions());
        StdOut.println("Board is solvable. " + testBoard.isSolvable());
        StdOut.println(testBoard);
        StdOut.println(testBoard.neighbors());

        int[][] test2 = new int[3][3];
        test2[0][0] = 1;
        test2[0][1] = 2;
        test2[0][2] = 3;
        test2[1][0] = 4;
        test2[1][1] = 5;
        test2[1][2] = 6;
        test2[2][0] = 7;
        test2[2][1] = 8;
        test2[2][2] = 0;
        Board testBoard2 = new Board(test2);
        StdOut.println("Board is the Goal board. " + testBoard2.isGoal());
        StdOut.println("These boards are equal. " + testBoard2.equals(testBoard));
        StdOut.println("These boards are equal. " + testBoard2.equals(testBoard2));
        StdOut.println("Size = " + testBoard.size());
        StdOut.println("Tile value at 1,1 = " + testBoard2.tileAt(1, 1));
    }
}
