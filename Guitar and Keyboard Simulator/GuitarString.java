// Guitar String: Simulates one singular guitar string for one input frequency

public class GuitarString {
    // YOUR INSTANCE VARIABLES HERE
    private final int n;
    private RingBuffer strng;

    // creates a guitar string of the specified frequency,
    public GuitarString(double frequency) {
        final int SAMPLING_RATE = 44100;
        n = (int) Math.ceil(SAMPLING_RATE / frequency);
        strng = new RingBuffer(n);
        for (int i = 0; i < n; i++)
            strng.enqueue(0); // string at rest
    }

    // creates a guitar string whose size and initial values from array
    public GuitarString(double[] init) {
        n = init.length;
        strng = new RingBuffer(n);
        for (int i = 0; i < n; i++)
            strng.enqueue(init[i]);
    }

    // returns the number of samples in the ring buffer
    public int length() {
        return strng.capacity();
    }

    // plucks the guitar string
    public void pluck() {
        for (int i = 0; i < n; i++) {
            strng.dequeue();
            strng.enqueue(StdRandom.uniform(-0.5, 0.5)); // white noise
        }
    }

    // advances the Karplus-Strong simulation one time step
    public void tic() {
        final double DECAY_RATE = 0.996;
        strng.enqueue((strng.dequeue() + strng.peek()) * 0.5 * DECAY_RATE);
    }

    // returns the current sample
    public double sample() {
        return strng.peek();
    }

    // tests and calls every constructor and instance method in this class
    public static void main(String[] args) {
        GuitarString gs1 = new GuitarString(1000);
        StdOut.println(gs1.length());
        StdOut.println(gs1.sample());

        double[] samples = { 0.2, 0.4, 0.5, 0.3, -0.2, 0.4, 0.3, 0.0, -0.1, -0.3 };
        GuitarString testString = new GuitarString(samples);

        int m = 25; // number of tics
        for (int i = 0; i < m; i++) {
            double sample = testString.sample();
            StdOut.printf("%6d %8.4f\n", i, sample);
            testString.tic();
        }
    }
}
