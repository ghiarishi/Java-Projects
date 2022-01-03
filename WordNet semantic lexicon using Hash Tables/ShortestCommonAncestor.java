import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

// program to find ShortestCommonAncestor and dist betweeen any two nouns or subsets
public class ShortestCommonAncestor {

    private final Digraph graph; // rooted DAG
    private BreadthFirstDirectedPaths search1; // from vertex v
    private BreadthFirstDirectedPaths search2; // from vertex w

    // constructor takes a rooted DAG as argument
    public ShortestCommonAncestor(Digraph G) {

        if (G == null) {
            throw new IllegalArgumentException("G is null");
        }

        graph = new Digraph(G); // defensive copy

        DirectedCycle check = new DirectedCycle(graph);
        if (check.hasCycle()) {
            throw new IllegalArgumentException("Graph is not acyclic");
        }

        int count = 0;
        for (int i = 0; i < graph.V(); i++) {
            if (graph.outdegree(i) == 0) {
                count++;
            }
        }
        if (count == 0) { // must be a vertex with outdegree 0
            throw new IllegalArgumentException("Graph is not rooted");
        }
    }

    // length of shortest ancestral path between v and w
    public int length(int v, int w) {
        if (!checkRange(v) || !checkRange(w)) {
            throw new IllegalArgumentException("Vertex outside prescribed range");
        }
        search1 = new BreadthFirstDirectedPaths(graph, v);
        search2 = new BreadthFirstDirectedPaths(graph, w);
        return runBFS()[0];
    }

    // a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w) {
        if (!checkRange(v) || !checkRange(w)) {
            throw new IllegalArgumentException("Vertex outside presceibed range");
        }
        search1 = new BreadthFirstDirectedPaths(graph, v);
        search2 = new BreadthFirstDirectedPaths(graph, w);
        return runBFS()[1];
    }


    // length of shortest ancestral path of vertex subsets A and B
    public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        if (subsetA == null || subsetB == null) {
            throw new IllegalArgumentException("One, or both of the subsets are null");
        }
        for (int v : subsetA) {
            if (!checkRange(v)) {
                throw new IllegalArgumentException("Vertex outside prescribed range");
            }
        }
        for (int w : subsetB) {
            if (!checkRange(w)) {
                throw new IllegalArgumentException("Vertex outside prescribed range");
            }
        }
        search1 = new BreadthFirstDirectedPaths(graph, subsetA);
        search2 = new BreadthFirstDirectedPaths(graph, subsetB);
        return runBFS()[0];
    }

    // a shortest common ancestor of vertex subsets A and B
    public int ancestorSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        if (subsetA == null || subsetB == null) {
            throw new IllegalArgumentException("One, or both of the subsets are null");
        }
        for (int v : subsetA) {
            if (!checkRange(v)) {
                throw new IllegalArgumentException("Vertex outside prescribed range");
            }
        }
        for (int w : subsetB) {
            if (!checkRange(w)) {
                throw new IllegalArgumentException("Vertex outside prescribed range");
            }
        }
        search1 = new BreadthFirstDirectedPaths(graph, subsetA);
        search2 = new BreadthFirstDirectedPaths(graph, subsetB);
        return runBFS()[1];
    }

    // helper method to check length and find ancestor
    private int[] runBFS() {
        int dist = Integer.MAX_VALUE;
        int ancestor = -1;
        for (int i = 0; i < graph.V(); i++) {
            if (search1.hasPathTo(i) && search2.hasPathTo(i)) {
                if (dist > search1.distTo(i) + search2.distTo(i)) {
                    dist = search1.distTo(i) + search2.distTo(i);
                    ancestor = i;
                }
            }
        }
        int[] ret = new int[2];
        ret[0] = dist;
        ret[1] = ancestor;
        return ret;
    }

    // helper method to check if vertex is within range
    private boolean checkRange(int v) {
        if (v < 0 || v >= graph.V()) {
            return false;
        }
        return true;
    }

    // unit testing (required)
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);

        // subset code testing
        Bag<Integer> test = new Bag<Integer>();
        Bag<Integer> test2 = new Bag<Integer>();

        // below subsets based on digraph25.txt
        // subset 1
        test.add(13);
        test.add(23);
        test.add(24);

        // subset 2
        test2.add(6);
        test2.add(16);
        test2.add(17);

        int lengthSubset = sca.lengthSubset(test, test2);
        int ancestorSubset = sca.ancestorSubset(test, test2);
        StdOut.printf("SUBSETS | length = %d, ancestor = %d\n", lengthSubset, ancestorSubset);

        // individual points testing (given with assignment website)
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sca.length(v, w);
            int ancestor = sca.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}