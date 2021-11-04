import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

// program to solve slider puzzles using the A* algorithm and SearchNodes
public class Solver {

    MinPQ<SearchNode> puzzle; // Priority Queue
    private int n;
    private SearchNode current;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board input) {
        n = input.size();

        // defensive copy
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = input.tileAt(i, j);
            }
        }

        puzzle = new MinPQ<SearchNode>();

        Board cpyBoard = new Board(tiles);
        if (!cpyBoard.isSolvable()) {
            throw new IllegalArgumentException("Unsovlable Board!");
        }
        if (cpyBoard == null) {
            throw new IllegalArgumentException("Unsovlable Board!");
        }

        current = new SearchNode(cpyBoard, 0, null);
        puzzle.insert(current);

        SearchNode temp;

        while (!current.board.isGoal()) {
            current = puzzle.delMin(); // dequeuen least priority or most moves
            SearchNode prev = current.previous;
            for (Board item : current.board.neighbors()) { // cycle through all neighbours
                if (prev == null || !prev.board.equals(item)) { // critical optimization
                    temp = new SearchNode(item, current.moves + 1, current);
                    puzzle.insert(temp);
                }
            }
        }
    }

    // min number of moves to solve initial board
    public int moves() {
        return current.moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        Stack<Board> stack = new Stack<Board>();
        SearchNode node = current;
        for (int i = 0; i < moves(); i++) {
            stack.push(node.board);
            node = node.previous;
        }
        return stack;
    }


    // nested class searchnode
    private class SearchNode implements Comparable<SearchNode> {
        private int moves;
        private int priority;
        private int manhattan;
        private SearchNode previous;
        private Board board;

        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            if (previous != null) this.previous = previous;
            manhattan = board.manhattan();
            priority = manhattan + this.moves;
        }

        // method to compare for PriorityQueue to delMin() on the basis of
        public int compareTo(SearchNode that) {

            // lower priority preferred
            if (this.priority > that.priority) return 1;
            if (this.priority < that.priority) return -1;

            // higher moves preferred
            if (this.moves < that.moves) return 1;
            if (this.moves > that.moves) return -1;

            // all equal
            return 0;
        }

    }

    // test client (see below)
    public static void main(String[] args) {

        In in = new In(args[0]); // command line args

        //In in = new In("puzzle07.txt"); // for manual testing

        int n = in.readInt();
        int[][] testArr = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                testArr[i][j] = in.readInt();
            }
        }
        Board testBoard = new Board(testArr);

        Solver test = new Solver(testBoard);

        for (Board item : test.solution()) {
            StdOut.println(item);
        }
        StdOut.println("Moves taken = " + test.moves());

        // testing on unsolvable board
        In in2 = new In("puzzle3x3-unsolvable.txt");
        int n2 = in2.readInt();
        int[][] testArr2 = new int[n][n];
        for (int i = 0; i < n2; i++) {
            for (int j = 0; j < n2; j++) {
                testArr2[i][j] = in2.readInt();
            }
        }
        Board testBoard2 = new Board(testArr2);

        Solver test2 = new Solver(testBoard2);

        for (Board item : test2.solution()) {
            StdOut.println(item);
        }
        StdOut.println("Moves taken = " + test2.moves());
    }
}
