import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

// Takes command line argument k, reads strings from StdIn,
// prints k of them in random order.
public class Permutation {
    public static void main(String[] args) {

        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> perm = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            perm.enqueue(StdIn.readString());
        }

        int i = 0;
        while (i < k) {
            StdOut.println(perm.dequeue());
            i++;
        }
    }
}
