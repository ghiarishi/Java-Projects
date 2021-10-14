// Draw Serpinski triangle of order n (command line arg)

public class Sierpinski {

    //  Height of an equilateral triangle whose sides are of the specified length.
    public static double height(double length) {
        double h = length * (Math.sqrt(3) / 2);
        return h;
    }

    // Draw filled Eqi. Triangle (Bottom Vertex specified)
    public static void filledTriangle(double x, double y, double length) {

        // Bottom Vertex
        double x0 = x;
        double y0 = y;

        // Top Right Vertex
        double x1 = x + (length / 2);
        double y1 = y + height(length);

        // Top Left Vertex
        double x2 = x - (length / 2);
        double y2 = y + height(length);

        double[] a = { x0, x1, x2 };
        double[] b = { y0, y1, y2 };
        StdDraw.filledPolygon(a, b);
    }

    //  Draws a Sierpinski triangle of order n, bottom vertex and length specified
    public static void sierpinski(int n, double x, double y, double length) {

        if (n == 0) return; // Base Case

        filledTriangle(x, y, length); // Draw Centre Triangle

        // Top Triangle
        double x0 = x;
        double y0 = y + height(length);
        sierpinski(n - 1, x0, y0, length / 2);

        // Bottom Left Triangle
        double x1 = x - (length / 2);
        double y1 = y;
        sierpinski(n - 1, x1, y1, length / 2);

        // Bottom Right Triangle
        double x2 = x + (length / 2);
        double y2 = y;
        sierpinski(n - 1, x2, y2, length / 2);


    }

    // MAIN Method
    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);

        // Dimensions to ensure uniformity
        StdDraw.setXscale(-0.067, 1.067);
        StdDraw.setYscale(-0.067, .933);
        StdDraw.setPenColor(StdDraw.BLACK);

        double[] a = { 0, 1, 0.5 };
        double[] b = { 0, 0, height(1) };
        StdDraw.polygon(a, b);

        sierpinski(n, 0.5, 0, 0.5);
    }
}
