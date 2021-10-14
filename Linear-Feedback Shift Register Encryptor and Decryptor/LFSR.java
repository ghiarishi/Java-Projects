// Program to create an LFSR taking the seed value and tap bit as inputs
// Performs operations such as bitAt, toString, step, generate and length on LFSR
// Allows for random number generation of K bits

public class LFSR {

    private final int tapLoc; // Signifies location of the Tap Bit
    private int[] lfsr; // Array for the LFSR bits
    private final int arrLength; // Length of the LFSR bits Array

    // Creates an LFSR with the specified seed and tap
    public LFSR(String seed, int tap) {
        tapLoc = tap;
        arrLength = seed.length() + 1;
        lfsr = new int[arrLength];
        for (int i = 1; i <= arrLength - 1; i++) {
            lfsr[i] = seed.charAt(seed.length() - i) - 48;
        }
    }

    // Returns the number of bits in this LFSR
    public int length() {
        return arrLength - 1;
    }

    // Returns the ith bit of this LFSR (as 0 or 1)
    public int bitAt(int i) {
        return lfsr[i];
    }

    // Returns a string representation of this LFSR
    public String toString() {
        long sum = 0;

        for (int i = 1; i <= lfsr.length - 1; i++) {
            sum += (lfsr[arrLength - i] * (long) Math.pow(10, arrLength - i - 1));
        }
        String ret = leadingZeros(sum);
        return ret;
    }

    // Simulates one step of this LFSR and returns the new bit (as 0 or 1)
    public int step() {
        int tap = lfsr[tapLoc];
        int newBit = tap ^ lfsr[arrLength - 1];

        for (int i = arrLength - 1; i >= 1; i--) {
            lfsr[i] = lfsr[i - 1];
        }
        lfsr[1] = newBit;
        return lfsr[1];
    }

    // Simulates k steps of this LFSR and returns the k bits as a k-bit integer
    public int generate(int k) {
        int sum = 0;
        for (int i = 1; i <= k; i++) {
            sum += step() * Math.pow(2, k - i);
        }
        return (sum);
    }

    // Add leading zeroes to returned value for 1-20 bit numbers
    // No leading zeroes above 20 bits
    private String leadingZeros(long sum) {
        int length = arrLength - 1;
        String result = "";
        switch (length) {
            case 1:
                result = String.format("%01d", sum);
                break;
            case 2:
                result = String.format("%02d", sum);
                break;
            case 3:
                result = String.format("%03d", sum);
                break;
            case 4:
                result = String.format("%04d", sum);
                break;
            case 5:
                result = String.format("%05d", sum);
                break;
            case 6:
                result = String.format("%06d", sum);
                break;
            case 7:
                result = String.format("%07d", sum);
                break;
            case 8:
                result = String.format("%08d", sum);
                break;
            case 9:
                result = String.format("%09d", sum);
                break;
            case 10:
                result = String.format("%010d", sum);
                break;
            case 11:
                result = String.format("%011d", sum);
                break;
            case 12:
                result = String.format("%012d", sum);
                break;
            case 13:
                result = String.format("%013d", sum);
                break;
            case 14:
                result = String.format("%014d", sum);
                break;
            case 15:
                result = String.format("%015d", sum);
                break;
            case 16:
                result = String.format("%016d", sum);
                break;
            case 17:
                result = String.format("%17d", sum);
                break;
            case 18:
                result = String.format("%18d", sum);
                break;
            case 19:
                result = String.format("%19d", sum);
                break;
            case 20:
                result = String.format("%20d", sum);
                break;
            default:
                result = String.format("%d", sum);
                break;
        }
        return result;
    }

    // tests this class by directly calling all instance methods
    public static void main(String[] args) {

        LFSR lfsr1 = new LFSR("01101000010", 9);

        StdOut.println("toString() test");
        StdOut.println(lfsr1);
        StdOut.println("");

        StdOut.println("length() test");
        StdOut.println(lfsr1.length());
        StdOut.println("");

        StdOut.println("bitAt() test");
        StdOut.println(lfsr1.bitAt(1));
        StdOut.println("");

        StdOut.println("step() test");
        int bit = lfsr1.step();
        StdOut.println(lfsr1 + " " + bit);
        StdOut.println("");

        StdOut.println("generate() test");
        LFSR lfsr2 = new LFSR("01101000010", 9);
        for (int i = 0; i < 10; i++) {
            int r = lfsr2.generate(5);
            StdOut.println(lfsr2 + " " + r);
        }
        StdOut.println("");
    }
}
