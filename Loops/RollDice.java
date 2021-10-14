public class RollDice {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        final int NO_OF_DICE = 10;
        final int SIDES_DICE = 6;
        int i, j, k, m;
        int[] gauss = new int[51]; // 10 to 60
        for (i = 1; i <= n; i++) {
            int sum = 0;
            for (j = 1; j <= NO_OF_DICE; j++) {
                double roll = Math.random() * SIDES_DICE;
                if (roll < 1) {
                    sum += 1;
                }
                else if (roll >= 1 && roll < 2) {
                    sum += 2;
                }
                else if (roll >= 2 && roll < 3) {
                    sum += 3;
                }
                else if (roll >= 3 && roll < 4) {
                    sum += 4;
                }
                else if (roll >= 4 && roll < 5) {
                    sum += 5;
                }
                else {
                    sum += 6;
                }
            }
            sum -= NO_OF_DICE;
            gauss[sum] += 1; // Map 10-60 to 0-50
        }
        for (m = 10; m <= 60; m++) {
            System.out.print(m + ": ");
            for (k = 0; k < gauss[m - 10]; k++) {
                System.out.print("*");
            }
            System.out.print('\n');
        }
    }
}
