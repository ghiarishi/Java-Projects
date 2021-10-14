import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

// Collects statistics on the percolation problem, for T trials
public class PercolationStats {

    private Percolation per;
    private int n, T; // side length, # trials
    private double[] pThresh; // store p* for each trial

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.n = n;
        T = trials;
        if (n <= 0 || T <= 0) {
            throw new IllegalArgumentException("Illegal Argument (n)");
        }
        pThresh = new double[T];
        for (int i = 0; i < T; i++) {
            per = new Percolation(n);
            while (!per.percolates()) {
                randomOpen();
            }
            pThresh[i] = per.numberOfOpenSites() / (double) (n * n);
        }
    }

    // [HELPER] Open site at random
    private void randomOpen() {
        int row;
        int col;
        do {
            row = StdRandom.uniform(n);
            col = StdRandom.uniform(n);
        } while (per.isOpen(row, col));
        per.open(row, col);
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(pThresh);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(pThresh);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return (mean() - (1.96 * stddev() / Math.pow(T, 0.5)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return (mean() + (1.96 * stddev() / Math.pow(T, 0.5)));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        Stopwatch time = new Stopwatch();
        PercolationStats test = new PercolationStats(n, t);
        StdOut.printf("mean()           = %7.6f%n", test.mean());
        StdOut.printf("stddev()         = %7.6f%n", test.stddev());
        StdOut.printf("confidenceLow()  = %7.6f%n", test.confidenceLow());
        StdOut.printf("confidenceHigh() = %7.6f%n", test.confidenceHigh());
        StdOut.println("elapsed time     = " + time.elapsedTime());
    }
}