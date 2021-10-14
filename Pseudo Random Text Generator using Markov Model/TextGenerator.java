// Generates text by using the MarkovModel on an input txt file.

public class TextGenerator {
    public static void main(String[] args) {

        int k = Integer.parseInt(args[0]); // order
        int T = Integer.parseInt(args[1]); // iterations

        String text = StdIn.readAll();
        text += text; // prevent dead end

        MarkovModel generate = new MarkovModel(text, k);
        String result = new String();

        for (int i = 0; i < T - k; i++) {
            String input = text.substring(i, i + k);
            result += generate.random(input);
        }
        StdOut.println(result);
    }
}
