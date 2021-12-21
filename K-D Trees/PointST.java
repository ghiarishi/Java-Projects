import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StopwatchCPU;

// Brute force method to implement range search & nearest-neighbor search
// using RedBlackBSTs

public class PointST<Value> {

    RedBlackBST<Point2D, Value> points;

    // construct an empty symbol table of points
    public PointST() {
        points = new RedBlackBST<Point2D, Value>();
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points
    public int size() {
        return points.size();
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        if (p == null || val == null) {
            throw new IllegalArgumentException("Argument can't be null");
        }
        points.put(p, val);
    }

    // value associated with point p
    public Value get(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Argument can't be null");
        }
        return points.get(p);
    }

    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Argument can't be null");
        }
        return points.contains(p);
    }

    // all points in the symbol table
    public Iterable<Point2D> points() {
        return points.keys();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("Argument can't be null");
        }
        Queue<Point2D> pointsQ = new Queue<Point2D>();
        for (Point2D p : points()) {
            if (rect.contains(p)) {
                pointsQ.enqueue(p);
            }
        }
        return pointsQ;
    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Argument can't be null");
        }
        if (points.isEmpty()) {
            return null;
        }
        double minDist = Double.POSITIVE_INFINITY;
        Point2D nearest = null;
        for (Point2D that : points()) {
            if (p.distanceSquaredTo(that) < minDist && !p.equals(that)) {
                minDist = p.distanceSquaredTo(that);
                nearest = that;
            }
        }
        return nearest;
    }

    // unit testing (required)
    public static void main(String[] args) {
        PointST<Integer> test = new PointST<Integer>();

        In in = new In("input1M.txt");
        double x, y;
        while (in.hasNextLine()) {
            x = in.readDouble();
            y = in.readDouble();
            Point2D point = new Point2D(x, y);
            test.put(point, StdRandom.uniform(100));
        }

        int n = 1000; // number of nearest() calls
        Point2D p;
        StopwatchCPU time = new StopwatchCPU();
        for (int i = 0; i < n; i++) {
            x = StdRandom.uniform(0.0, 1.0);
            y = StdRandom.uniform(0.0, 1.0);
            p = new Point2D(x, y);
            test.nearest(p);
        }
        StdOut.println("Calls per second = " + n / time.elapsedTime());

        StdOut.println("Empty? " + test.isEmpty());
        StdOut.println("How many points? " + test.size());
        // StdOut.println("List of all points: " + test.points());

        RectHV rect = new RectHV(0.2, 0.2, 0.4, 0.4);
        // StdOut.println("List of all points within rect: " + test.range(rect));

        Point2D testPoint = new Point2D(0.7, 0.2);
        StdOut.println("Nearest to 0.7, 0.2? " + test.nearest(testPoint));
        StdOut.println("Contains 0.7, 0.2? " + test.contains(testPoint));
        StdOut.println("Value of Point2D (0.7, 0.2)? " + test.get(testPoint));
    }
}
