public class RandomWalkers {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        int i, j;
        double sumSqDist = 0;
        for (i = 1; i <= trials; i++) {
            int x = 0;
            int y = 0;
            for (j = 1; j <= n; j++) {
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
            }
            int squaredDist = (int) (Math.pow(x, 2) + Math.pow(y, 2));
            sumSqDist += squaredDist;
        }
        System.out.println("mean squared distance = " + sumSqDist / (double) trials);
    }
}
