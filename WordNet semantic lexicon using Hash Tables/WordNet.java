import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

// Implement an immutable data type WordNet with the following API:
public class WordNet {

    // each synset ID associated with one or more nouns
    private SeparateChainingHashST<Integer, ArrayList<String>> IntStrST;

    // each noun associated with one or more synset IDs
    private SeparateChainingHashST<String, ArrayList<Integer>> StrIntST;

    // Shortest Common Ancestor object
    private ShortestCommonAncestor sca;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {

        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException("Word is null");
        }

        // hash table of nouns
        IntStrST = new SeparateChainingHashST<Integer, ArrayList<String>>();
        StrIntST = new SeparateChainingHashST<String, ArrayList<Integer>>();

        int vertices = 0; // total # vertices

        In in1 = new In(synsets);
        while (in1.hasNextLine()) {
            String[] tokens = in1.readLine().split(",");
            int synID = Integer.parseInt(tokens[0]);
            String[] nouns = tokens[1].split(" ");
            ArrayList<String> nounList = new ArrayList<String>();

            for (int i = 0; i < nouns.length; i++) {
                nounList.add(nouns[i]);

                // if noun not already in list, create new entry with arraylist
                if (!StrIntST.contains(nouns[i])) {
                    ArrayList<Integer> IDList = new ArrayList<Integer>();
                    IDList.add(synID);
                    // each noun added as a separate entry
                    StrIntST.put(nouns[i], IDList);
                }
                else { // else add to existing arraylist
                    StrIntST.get(nouns[i]).add(synID);
                }
            }
            IntStrST.put(synID, nounList);

            vertices++;
        }

        // rooted DAG creation
        Digraph graph = new Digraph(vertices);
        In in2 = new In(hypernyms);
        while (in2.hasNextLine()) {
            String[] token = in2.readLine().split(",");
            int v = Integer.parseInt(token[0]);
            for (int i = 1; i < token.length; i++) {
                int w = Integer.parseInt(token[i]);
                graph.addEdge(v, w);
            }
        }
        sca = new ShortestCommonAncestor(graph);
    }

    // the set of all WordNet nouns
    public Iterable<String> nouns() {
        return StrIntST.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException("Word is null");
        }
        return StrIntST.contains(word);
    }

    // a synset (second field of synsets.txt) that is a shortest common ancestor
    // of noun1 and noun2 (defined below)
    public String sca(String noun1, String noun2) {

        // find sca of nouns using the set of synset IDs associated with each
        int SCA = sca.ancestorSubset(StrIntST.get(noun1), StrIntST.get(noun2));
        ArrayList<String> nouns = IntStrST.get(SCA);

        // convert arraylist of nouns to CSV format as specified
        StringBuilder str = new StringBuilder();
        int i = 0;
        for (String noun : nouns) {
            i++;
            str.append(noun);
            if (i < nouns.size() - 1) {
                str.append(", ");
            }
        }
        return str.toString();
    }

    // distance between noun1 and noun2 (defined below)
    public int distance(String noun1, String noun2) {
        if (!StrIntST.contains(noun1) || !StrIntST.contains(noun2)) {
            throw new IllegalArgumentException("Wordnet doesn't contain nouns");
        }

        // returns shortest distance between the subset of synset IDs of each noun
        return sca.lengthSubset(StrIntST.get(noun1), StrIntST.get(noun2));
    }

    // unit testing (required)
    public static void main(String[] args) {

        WordNet test = new WordNet("synsets.txt", "hypernyms.txt");
        StdOut.println(test.isNoun("hello"));
        StdOut.println(test.isNoun("common_cold1"));
        StdOut.println(test.isNoun("common_cold"));
        StdOut.println(test.sca("George_W._Bush", "President_John_F._Kennedy"));
        StdOut.println(test.distance("George_W._Bush", "President_John_F._Kennedy"));

        // answers to 4 lines given in checklist
        StdOut.println(test.distance("white_marlin", "mileage")); // dist = 23
        StdOut.println(test.distance("Black_Plague", "black_marlin")); // dist = 33
        StdOut.println(test.distance("American_water_spaniel", "histology")); // dist = 27
        StdOut.println(test.distance("Brown_Swiss", "barrel_roll")); // dist = 29

        int count = 0;
        StdOut.println("These are all the nouns:");
        for (String noun : test.nouns()) {
            // StdOut.println(noun); // commented out as input too big
            count++;
        }
        StdOut.println("Total # nouns = " + count);
    }
}