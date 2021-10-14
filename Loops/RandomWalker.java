public class RandomWalker {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int x = 0;
        int y = 0;
        int i;
        System.out.println("(" + x + ", " + y + ")");
        for (i = 1; i <= n; i++) {
            double dir = Math.random() * 4;
            if (dir <= 1) { // N
                y += 1;
            }
            else if (dir > 1 && dir <= 2) { // E
                x += 1;
            }
            else if (dir > 2 && dir <= 3) { // S
                y -= 1;
            }
            else { // W
                x -= 1;
            }
            System.out.println("(" + x + ", " + y + ")");
        }
        int squaredDist = (int) (Math.pow(x, 2) + Math.pow(y, 2));
        System.out.println("squared distance = " + squaredDist);
    }
}
