// 2D Transformation of a polygon

public class Transform2D {

    // MAIN method
    public static void main(String[] args) {

        StdDraw.setXscale(-5.5, 5.5);
        StdDraw.setYscale(-5.5, 5.5);

        // Set up Coordinate Axes
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.0075);
        StdDraw.line(0, -5.5, 0, 5.5);
        StdDraw.line(-5.5, 0, 5.5, 0);
        StdDraw.setPenColor(StdDraw.GRAY);
        StdDraw.setPenRadius(0.001);
        // X intercepts
        for (int i = -5; i <= 5; i++) {
            StdDraw.line(i, -0.3, i, 0.3);
            if (i == 0) continue;
            StdDraw.text(i, -0.5, i + ",0");
        }
        // Y intercepts
        for (int i = -5; i <= 5; i++) {
            StdDraw.line(-0.3, i, 0.3, i);
            if (i == 0) continue;
            StdDraw.text(-0.75, i, "0," + i);
        }
        // Origin
        StdDraw.text(-0.3, -0.3, "0,0");

        StdDraw.setPenRadius(0.005);

        double x[] = { 0, 1, 1, 0 };
        double y[] = { 0, 0, 2, 1 };

        // Test copy
        double testCopy[] = copy(x);
        printArray(testCopy);

        // Test scale
        double alpha = 2;
        scale(x, y, alpha);

        // Test translate
        double dx = 2, dy = 1;
        translate(x, y, dx, dy);

        // Test rotate
        double theta = 90;
        rotate(x, y, theta);
    }

    // Print array
    public static void printArray(double[] arr) {
        StdOut.print("Array = { ");
        for (int i = 0; i < arr.length; i++) {
            StdOut.print(arr[i] + ", ");
        }
        StdOut.print("}");
        StdOut.println("");
    }

    // Copy array
    public static double[] copy(double[] array) {
        int n = array.length;
        double arrCopy[] = new double[n];

        for (int i = 0; i < n; i++) {
            arrCopy[i] = array[i];
        }
        return arrCopy;
    }

    // Scales polygon by alpha
    public static void scale(double[] x, double[] y, double alpha) {
        double x0[] = new double[x.length];
        double y0[] = new double[y.length];

        for (int i = 0; i < x.length; i++) {
            x0[i] = x[i] * alpha;
        }
        for (int i = 0; i < x.length; i++) {
            y0[i] = y[i] * alpha;
        }

        // Old
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.polygon(x, y);
        // New
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.polygon(x0, y0);
    }

    // Translates the polygon by (dx, dy).
    public static void translate(double[] x, double[] y, double dx, double dy) {

        double x0[] = new double[x.length];
        double y0[] = new double[y.length];

        for (int i = 0; i < x.length; i++) {
            x0[i] = x[i] + dx;
        }
        for (int i = 0; i < x.length; i++) {
            y0[i] = y[i] + dy;
        }
        // Old
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.polygon(x, y);
        // New
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.polygon(x0, y0);
    }

    // Rotates the polygon theta degrees counterclockwise, about the origin.
    public static void rotate(double[] x, double[] y, double theta) {
        double x0[] = new double[x.length];
        double y0[] = new double[y.length];

        double thetaRad = Math.toRadians(theta);
        double cos = Math.cos(thetaRad);
        double sin = Math.sin(thetaRad);

        for (int i = 0; i < x.length; i++) {
            x0[i] = (x[i] * cos) - (y[i] * sin);
        }
        for (int i = 0; i < x.length; i++) {
            y0[i] = (x[i] * sin) + (y[i] * cos);
        }
        // Old
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.polygon(x, y);
        // New
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.polygon(x0, y0);
    }
}
