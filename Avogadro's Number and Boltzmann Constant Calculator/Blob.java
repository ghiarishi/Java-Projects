// helper data type in order to denote a connected component of pixels

public class Blob {

    private double sx;
    private double sy;
    private int mass;

    //  creates an empty blob
    public Blob() {
        sx = 0;
        sy = 0;
        mass = 0;
    }

    //  adds pixel (x, y) to this blob
    public void add(int x, int y) {
        sx = ((sx * mass) + x) / (mass + 1);
        sy = ((sy * mass) + y) / (mass + 1);
        mass++;
    }

    //  number of pixels added to this blob
    public int mass() {
        return mass;
    }

    //  Euclidean distance between the center of masses of the two blobs
    public double distanceTo(Blob that) {
        return Math.pow(Math.pow(that.sx - sx, 2.0) + Math.pow(that.sy - sy, 2.0), 0.5);

    }

    //  string representation of this blob (see below)
    public String toString() {
        return String.format("%2d (%8.4f, %8.4f)", mass, sx, sy);
    }

    //  tests this class by directly calling all instance methods
    public static void main(String[] args) {

        Blob blob1 = new Blob();
        Blob blob2 = new Blob();

        blob1.add(0, 0);
        blob1.add(4, 4);
        blob2.add(1, 1);
        StdOut.println(blob1.distanceTo(blob2));
        StdOut.println(blob1);
    }
}
