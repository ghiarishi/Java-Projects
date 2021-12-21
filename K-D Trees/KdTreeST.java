import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StopwatchCPU;

// Using a KD Tree to represent points and perform range and nearest neighbor searches
public class KdTreeST<Value> {

    private Node root; // root node
    private int size; // number of nodes

    // construct an empty symbol table of points
    public KdTreeST() {
        size = 0;
    }

    private class Node {
        private Point2D p;     // the point
        private Value val;     // the symbol table maps the point to this value
        private RectHV rect;   // the axis-aligned rectangle corresponding to this node
        private Node lb;       // the left/bottom subtree
        private Node rt;       // the right/top subtree
        private boolean type;  // true- vertical, false - horizontal

        // Data type-Node constructor
        public Node(Point2D p, Value val, boolean type, RectHV rect) {
            this.p = p;
            this.val = val;
            this.type = type;
            this.rect = rect;
        }

        // compare x or y coordinates of points based on vertical or horizontal
        public int compareTo(Point2D p) {
            if (this.type) {
                return Double.compare(p.x(), this.p.x());
            }
            return Double.compare(p.y(), this.p.y());
        }
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points
    public int size() {
        return size;
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        root = putHelp(root, p, val, true, null);
    }

    // helper method for put
    private Node putHelp(Node current, Point2D p, Value val, boolean type, Node prev) {
        if (current == null) {
            RectHV rect;
            if (isEmpty()) { // rect of root node
                rect = new RectHV(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY,
                                  Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
            }
            else {
                // set rect to default values of parent node
                double xmin, ymin, xmax, ymax;
                xmin = prev.rect.xmin();
                ymin = prev.rect.ymin();
                xmax = prev.rect.xmax();
                ymax = prev.rect.ymax();

                // change the required coordinate
                int cmp = prev.compareTo(p);
                if (cmp < 0) {
                    if (prev.type) {
                        xmax = prev.p.x();
                    }
                    else {
                        ymax = prev.p.y();
                    }
                }
                else {
                    if (prev.type) {
                        xmin = prev.p.x();
                    }
                    else {
                        ymin = prev.p.y();
                    }
                }
                rect = new RectHV(xmin, ymin, xmax, ymax);
            }
            size++; // size of tree
            return new Node(p, val, type, rect);
        }
        int cmp = current.compareTo(p);
        if (current.p.equals(p)) { // check current node
            current.val = val;
        }
        else if (cmp < 0) { // check left/bottom
            current.lb = putHelp(current.lb, p, val, !type, current);
        }
        else { // check right/top
            current.rt = putHelp(current.rt, p, val, !type, current);
        }
        return current;
    }

    // value associated with point p 
    public Value get(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (isEmpty()) {
            StdOut.println("Empty ST");
        }
        return getHelp(root, p, true);
    }

    // helper get method
    private Value getHelp(Node current, Point2D p, boolean type) {
        if (current == null) {
            return null;
        }
        int cmp = current.compareTo(p);
        if (current.p.equals(p)) { // check current node
            return current.val;
        }
        else if (cmp < 0) { // check left/bottom
            return getHelp(current.lb, p, !type);
        }
        else { // check right/top
            return getHelp(current.rt, p, !type);
        }
    }

    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return get(p) != null;
    }

    // all points in the symbol table 
    public Iterable<Point2D> points() {
        Queue<Point2D> allPoints = new Queue<Point2D>();
        iterate(root, allPoints);
        return allPoints;
    }

    // helper method to iterate
    private void iterate(Node current, Queue<Point2D> allPoints) {
        if (current == null) return;
        allPoints.enqueue(current.p);
        iterate(current.lb, allPoints);
        iterate(current.rt, allPoints);
    }

    // all points that are inside the rectangle (or on the boundary) 
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        Queue<Point2D> rangePoints = new Queue<Point2D>();
        iterateRange(root, rangePoints, rect);
        return rangePoints;
    }

    // helper method for range
    private void iterateRange(Node current, Queue<Point2D> queue, RectHV rect) {
        if (current == null) return;
        if (!rect.intersects(current.rect)) return; // pruning
        if (rect.contains(current.p)) { // is current node contained
            queue.enqueue(current.p);
        }
        // check left/bottom then right/top
        iterateRange(current.lb, queue, rect);
        iterateRange(current.rt, queue, rect);
    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (isEmpty()) {
            throw new IllegalArgumentException();
        }
        return nearestHelp(root, root.p, p);
    }

    // helper method for nearest
    private Point2D nearestHelp(Node current, Point2D nearest, Point2D p) {
        if (current == null) return nearest;

        // if closer than nearest, current is new nearest
        if (current.p.distanceSquaredTo(p) < nearest.distanceSquaredTo(p)) {
            nearest = current.p;
        }

        // if closest closer than entire rect of query point, dont search
        if (nearest.distanceSquaredTo(p) >= current.rect.distanceSquaredTo(p)) {
            int cmp = current.compareTo(p);

            if (cmp < 0) { // if to Left/Bottom, check LB first
                nearest = nearestHelp(current.lb, nearest, p);
                nearest = nearestHelp(current.rt, nearest, p);
            }

            else { // if to Right/Top, check RT first
                nearest = nearestHelp(current.rt, nearest, p);
                nearest = nearestHelp(current.lb, nearest, p);
            }
        }
        return nearest;
    }

    // unit testing (required)
    public static void main(String[] args) {
        KdTreeST<Integer> test = new KdTreeST<Integer>();
        StdOut.println("Empty? " + test.isEmpty());
        In in = new In("input1M.txt");
        double x, y;
        while (in.hasNextLine()) {
            try {
                //  Block of code to try
                x = in.readDouble();
                y = in.readDouble();
                Point2D point = new Point2D(x, y);
                test.put(point, StdRandom.uniform(1000000));
            }
            catch (Exception e) {
                //  Block of code to handle errors
                StdOut.println("Empty last line in txt file");
            }
        }

        int n = 100000; // number of nearest() calls
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

        RectHV rectTest = new RectHV(0.3, 0.3, 0.7, 0.7);
        // StdOut.println("Points in the rect: " + test.range(rectTest));

        Point2D testPoint = new Point2D(0.999, 0.999);
        StdOut.println("Nearest to 0.999, 0.999? " + test.nearest(testPoint));
        StdOut.println("Contains 0.999, 0.999? " + test.contains(testPoint));
        test.put(testPoint, -100);
        StdOut.println("How many points? " + test.size());
        StdOut.println("Contains 0.999, 0.999? " + test.contains(testPoint));
        StdOut.println("Value of Point2D (0.999, 0.999)? " + test.get(testPoint));
    }
}