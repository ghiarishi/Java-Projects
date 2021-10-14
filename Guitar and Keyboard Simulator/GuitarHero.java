public class GuitarHero {
    public static void main(String[] args) {

        final String keyboardString = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        final int CONCERT_A = 440;

        GuitarString[] myGuitar = new GuitarString[37];
        for (int i = 0; i < keyboardString.length(); i++) {
            myGuitar[i] = new GuitarString(CONCERT_A * Math.pow(2, (double) (i - 24) / 12));
        }

        // the main input loop
        Keyboard keyboard = new Keyboard();
        while (true) {

            // check if the user has played a key; if so, process it
            if (keyboard.hasNextKeyPlayed()) {

                // the key the user played
                char key = keyboard.nextKeyPlayed();
                int toPlay = keyboardString.indexOf(key);
                myGuitar[toPlay].pluck();
            }

            // compute the superposition of the samples
            double sample = 0;
            for (int i = 0; i < keyboardString.length(); i++) {
                sample += myGuitar[i].sample();
            }

            // play the sample on standard audio
            StdAudio.play(sample);

            // advance the simulation of each guitar string by one step
            for (int i = 0; i < keyboardString.length(); i++) {
                myGuitar[i].tic();
            }
        }
    }
}

