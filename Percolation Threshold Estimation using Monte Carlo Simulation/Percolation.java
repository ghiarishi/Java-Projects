import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// Implements Percolation Datatype
public class Percolation {

    private boolean[][] siteStatus; // True-Open, False-Closed
    private WeightedQuickUnionUF uf;
    private int top, bottom, n, count; // Source, Sink, SideLength, Total Open Sites

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        this.n = n;
        if (n <= 0) {
            throw new IllegalArgumentException("Illegal Argument (n)");
        }
        siteStatus = new boolean[n][n];
        count = 0;
        top = 0;
        bottom = (n * n) + 1;
        uf = new WeightedQuickUnionUF(((n * n) + 2));
        for (int i = 0; i < n; i++) { // connect to top and bottom
            uf.union(convertTo1D(0, i), top);
            uf.union(convertTo1D(n - 1, i), bottom);
        }
    }

    // [HELPER] converts 2d co-ords to 1d co-ords
    private int convertTo1D(int row, int col) {
        return ((row * n) + col + 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 0 || row > n || col < 0 || col > n) {
            throw new IllegalArgumentException("Illegal Argument (row or col)");
        }
        siteStatus[row][col] = true;
        count++;
        if (row != n - 1) {
            if (isOpen(row + 1, col)) {
                uf.union(convertTo1D(row, col), convertTo1D(row + 1, col)); // down
            }
        }
        if (row != 0) {
            if (isOpen(row - 1, col)) {
                uf.union(convertTo1D(row, col), convertTo1D(row - 1, col)); // up
            }
        }
        if (col != n - 1) {
            if (isOpen(row, col + 1)) {
                uf.union(convertTo1D(row, col), convertTo1D(row, col + 1)); // right
            }
        }
        if (col != 0) {
            if (isOpen(row, col - 1)) {
                uf.union(convertTo1D(row, col), convertTo1D(row, col - 1)); // left
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || row > n || col < 0 || col > n) {
            throw new IllegalArgumentException("Illegal Argument");
        }
        return siteStatus[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 0 || row > n || col < 0 || col > n) {
            throw new IllegalArgumentException("Illegal Argument");
        }
        if (!isOpen(row, col)) {
            return false;
        }
        int p = uf.find(top);
        int q = uf.find(convertTo1D(row, col));
        return (p == q);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        int p = uf.find(top);
        int q = uf.find(bottom);
        return p == q;
    }

    // unit testing (required)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);

        Stopwatch time = new Stopwatch();
        Percolation test = new Percolation(n);
        StdOut.println("Time Taken = " + time.elapsedTime() + " seconds");

        // Open or Close Tests
        StdOut.println("How many open? " + test.numberOfOpenSites());
        StdOut.println("Is it open? " + test.isOpen(n - 1, n - 1));
        test.open(n - 1, n - 1);
        StdOut.println("How many open? " + test.numberOfOpenSites());
        StdOut.println("Is it open? " + test.isOpen(n - 2, n - 1));
        StdOut.println("Is it open? " + test.isOpen(n - 3, n - 1));

        StdOut.println("Is it full? " + test.isFull(n - 1, n - 1));

        // Percolation Test
        StdOut.println("Does it percolate? " + test.percolates());
        test.uf.union(test.convertTo1D(n - 1, n - 1), 0);
        test.uf.union(test.convertTo1D(n - 2, n - 1), 0);
        test.uf.union(test.convertTo1D(n - 3, n - 1), 0);
        StdOut.println("Does it percolate? " + test.percolates());

        StdOut.println("Is it full? " + test.isFull(n - 1, n - 1));
    }
}