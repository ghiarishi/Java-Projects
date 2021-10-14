public class RBGtoCMYK {
    public static void main(String[] args) {

        // Declare variables

        double red, grn, blu, cya, mag, yel, blk, whi;

        // Ask for RGB values

        System.out.println("Enter values for Red, Green and Blue: ");

        // Take input ints and convert to double from strings

        red = Double.parseDouble(args[0]);
        grn = Double.parseDouble(args[1]);
        blu = Double.parseDouble(args[2]);

        // Calculate white by finding max between the 3

        whi = Math.max((red / 255), Math.max((grn / 255), (blu / 255)));

        // Calculate other colours

        blk = 1 - whi;
        cya = (whi - (red / 255)) / whi;
        mag = (whi - (grn / 255)) / whi;
        yel = (whi - (blu / 255)) / whi;

        // Print output

        System.out.println("Red = " + red);
        System.out.println("Green = " + grn);
        System.out.println("Blue = " + blu);
        System.out.println("Cyan = " + cya);
        System.out.println("Magenta = " + mag);
        System.out.println("Yellow = " + yel);
        System.out.println("Black = " + blk);
    }
}
