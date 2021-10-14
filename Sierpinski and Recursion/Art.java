// Program to draw a fractal tree of order n over a background

public class Art {
    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);

        StdDraw.picture(0.4, 0.4, "xp.jpg", 1.92, 1.2); // Background

        StdDraw.setXscale(0, 1.5);
        StdDraw.setYscale(0, 1.5);

        fractalTree(0.75, 0.5, n, 0.3, 0);
    }

    private static void fractalTree(double x, double y, int n, double length, double theta) {

        if (n == 0) { // Base Case
            StdDraw.picture(x, y, "leaf.png", 0.01, 0.01, theta); // Add leaves when branch ends
            return;
        }

        double radius = (Math.pow((double) n, 1.25) / 1000); // Thickness down with height up
        StdDraw.setPenRadius(radius);

        int r = 194, g = 133, b = 68; // Lower sections are darker brown
        colour(r, g, b, n);

        drawLine(x, y, length, theta);

        double rad = Math.toRadians(theta);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        y += length * cos;
        x += length * sin;

        int branches = 4;
        double[] thetaArr = { -45, -15, 15, 45 };

        for (int i = 0; i < branches; i++) { // Stores theta for each branch in array
            thetaArr[i] += theta;
            fractalTree(x, y, n - 1, length / 1.6, thetaArr[i]);
        }
    }

    private static void drawLine(double x0, double y0, double length, double theta) {

        double rad = Math.toRadians(theta);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);
        double x1, y1;

        x1 = x0 + length * sin;
        y1 = y0 + length * cos;

        StdDraw.line(x0, y0, x1, y1);
    }

    // Make the brown darker
    private static void colour(int r, int g, int b, int n) {
        r -= (n * 0.09 * r);
        g -= (n * 0.09 * g);
        b -= (n * 0.09 * b);
        StdDraw.setPenColor(r, g, b);
    }
}


