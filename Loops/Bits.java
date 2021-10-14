public class Bits {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        double divByTwo = (double) n;
        if (divByTwo < 0) {
            System.out.println("Illegal input");
            System.exit(1);
        }
        else {
            int count = 0;
            // allows for 1 to be divided, as minimum number required
            while ((divByTwo / 2) >= 0.5) {
                divByTwo = divByTwo / 2.0;
                count += 1;
            }
            System.out.println(count);
        }
    }
}
