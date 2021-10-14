public class Ordered {
    public static void main(String[] args) {

        // Create Ints

        int x, y, z;

        // Accept 3 inputs, convert to Int

        x = Integer.parseInt(args[0]);
        y = Integer.parseInt(args[1]);
        z = Integer.parseInt(args[2]);

        // Create Boolean

        boolean isOrdered;

        // Asc and Desc condition

        isOrdered = ((x > y) && (y > z)) || ((z > y) && (y > x));

        // Print true/false

        System.out.println(isOrdered);
    }
}

