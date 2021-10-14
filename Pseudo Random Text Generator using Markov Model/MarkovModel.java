// Generates the next char of a sequence based on the possibilites following a
// certain k-gram

public class MarkovModel {

    private final int k;
    private ST<String, Integer> kgramST; // kgram itself
    private ST<String, int[]> afterkgramST; // chars following kgram
    public final int ASCII_VAL = 128;

    // creates a Markov model of order k based on the specified text
    public MarkovModel(String text, int k) {

        kgramST = new ST<String, Integer>();
        afterkgramST = new ST<String, int[]>();

        this.k = k;

        String newText = text + text; // dead-end workaround

        for (int i = 0; i < text.length(); i++) {
            String input = newText.substring(i, i + k);

            // begin count
            if (!kgramST.contains(input)) {
                kgramST.put(input, 1);
                afterkgramST.put(input, new int[ASCII_VAL]);
                int[] ascii = afterkgramST.get(input);
                ascii[newText.charAt(i + k)] = 1;
                afterkgramST.put(input, ascii);
            }

            // increment
            else {
                kgramST.put(input, kgramST.get(input) + 1);
                int[] ascii = afterkgramST.get(input);
                ascii[newText.charAt(i + k)]++;
                afterkgramST.put(input, ascii);
            }
        }
    }

    // returns the order of the model (also known as k)
    public int order() {
        return k;
    }

    // returns a String representation of the model (more info below)
    public String toString() {
        String result = "";
        for (String s : afterkgramST.keys()) {
            result += s + ": ";
            for (int i = 0; i < ASCII_VAL; i++) {
                int[] arr = afterkgramST.get(s);
                if (arr[i] > 0) {
                    char c = (char) i;
                    result += c + " " + arr[i] + " ";
                }
            }
            result += '\n';
        }
        return result;
    }

    // returns the # of times 'kgram' appeared in the input text
    public int freq(String kgram) {
        if (kgram.length() != k) {
            throw new IllegalArgumentException("illegal k-gram");
        }
        return kgramST.get(kgram);
    }


    // returns the # of times 'c' followed 'kgram' in the input text
    public int freq(String kgram, char c) {
        if (kgram.length() != k) {
            throw new IllegalArgumentException("illegal k-gram");
        }
        int[] arr = afterkgramST.get(kgram);
        return arr[c];
    }

    // returns a random character, chosen with weight proportional to the
    // number of times each character followed 'kgram' in the input text
    public char random(String kgram) {
        if (kgram.length() != k) {
            throw new IllegalArgumentException("incorrect length of k-gram");
        }
        int data = StdRandom.discrete(afterkgramST.get(kgram)); // pick next letter
        return (char) data;
    }

    // tests all instance methods to make sure they're working as expected
    public static void main(String[] args) {
        String text1 = "banana";
        MarkovModel model1 = new MarkovModel(text1, 2);
        StdOut.println(model1);
        StdOut.println(model1.random("na"));
        StdOut.println(model1.random("na"));
        StdOut.println(model1.random("na"));
        StdOut.println(model1.random("na"));
        StdOut.println(model1.random("na"));
        StdOut.println(model1.random("na"));
        StdOut.println(model1.random("na"));
        StdOut.println(model1.random("na"));
        StdOut.println(model1.random("na"));
        StdOut.println(model1.random("na"));
    }
}


