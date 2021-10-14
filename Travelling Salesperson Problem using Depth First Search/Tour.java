public class Tour {

    // sets up the node
    private class Node {
        private Point p;
        private Node next;
    }

    Node start = new Node();

    // creates an empty tour
    public Tour() {
        start.next = start;
        start.p = null;
    }

    // creates the 4-point tour a->b->c->d->a (for debugging)
    // (changed to just 2 points for testing purposes)
    public Tour(Point a, Point d) {
        Node n4 = new Node();
        start.p = a;
        start.next = n4;
        n4.p = d;
        n4.next = start;
    }

    // returns the number of points in this tour
    public int size() {

        if (start.p == null) return 0;

        Node current = start;
        int count = 0;
        do {
            count += 1;
            current = current.next;
        } while (current != start);
        return count;
    }

    // returns the length of this tour
    public double length() {

        if (start.p == null) return 0;

        Node current = start;
        double len = 0;
        do {
            len += current.p.distanceTo(current.next.p);
            current = current.next;
        } while (current != start);
        return len;
    }

    // returns a string representation of this tour
    public String toString() {

        if (start.p == null) return "";

        Node current = start;
        StringBuilder output = new StringBuilder();
        do {
            output.append(current.p.toString() + "\n");
            current = current.next;
        } while (current != start);

        return output.toString();
    }

    // draws this tour to standard drawing
    public void draw() {

        if (start.p == null) return;

        Node current = start;
        do {
            current.p.drawTo(current.next.p);
            current = current.next;
        } while (current != start);
    }

    // inserts p using the nearest neighbor heuristic
    public void insertNearest(Point p) {

        if (start.p == null) {
            start.p = p;
            start.next = start;
            return;
        }

        Node nodeNew = new Node();
        nodeNew.p = p;
        Node current = start;
        Node nearest = null;
        double minLen = Double.POSITIVE_INFINITY;

        do {
            if ((current.p.distanceTo(p)) < minLen) {
                minLen = current.p.distanceTo(nodeNew.p);
                nearest = current;
            }
            current = current.next;
        } while (current != start);

        Node oldNext = nearest.next;
        nearest.next = nodeNew;
        nodeNew.next = oldNext;
    }

    // inserts p using the smallest increase heuristic
    public void insertSmallest(Point p) {

        if (start.p == null) {
            start.p = p;
            start.next = start;
            return;
        }

        Node nodeNew = new Node();
        nodeNew.p = p;
        Node current = start;
        Node smallest = null;
        double minIncrease = Double.POSITIVE_INFINITY;

        do {
            double netIncrease =
                    current.p.distanceTo(p) + current.next.p.distanceTo(p)
                            - current.p.distanceTo(current.next.p);
            if (netIncrease < minIncrease) {
                minIncrease = netIncrease;
                smallest = current;
            }
            current = current.next;
        } while (current != start);

        Node oldNext = smallest.next;
        smallest.next = nodeNew;
        nodeNew.next = oldNext;
    }

    // tests this class by calling all constructors and instance methods
    public static void main(String[] args) {

        // define 4 points, corners of a square
        Point a = new Point(1.0, 1.0);
        //Point b = new Point(1.0, 4.0);
        //Point c = new Point(4.0, 4.0);
        Point d = new Point(4.0, 1.0);

        // create the tour a -> b -> c -> d -> a
        Tour squareTour = new Tour(a, d);

        int size = squareTour.size();
        StdOut.println("# of points = " + size);

        double length = squareTour.length();
        StdOut.println("Tour length = " + length);

        StdOut.println(squareTour);

        StdDraw.setXscale(0, 6);
        StdDraw.setYscale(0, 6);

        Point e = new Point(5.0, 6.0);
        //squareTour.insertNearest(e);
        squareTour.insertSmallest(e);
        squareTour.draw();
    }
}

