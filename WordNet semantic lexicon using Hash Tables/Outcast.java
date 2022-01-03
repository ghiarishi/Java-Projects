import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

// program to identify the "odd one out" from a set of nouns
public class Outcast {

    private WordNet net;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        net = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        String outcast = null;
        int max = Integer.MIN_VALUE;
        int[] dist = new int[nouns.length];
        for (int i = 0; i < nouns.length; i++) {
            dist[i] = 0;
            for (int j = 0; j < nouns.length; j++) {
                dist[i] += net.distance(nouns[i], nouns[j]);
            }
            if (dist[i] > max) {
                max = dist[i];
                outcast = nouns[i];
            }
        }
        return outcast;
    }

    // test client (see below)
    // works with input format as specified on site
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}