public class NoonSnooze {
    public static void main(String[] args) {
        int snooze = Integer.parseInt(args[0]);

        final int MINS_IN_HOUR = 60;
        final int TWELVE_HR_CLOCK = 12;
        final int MINS_IN_TWELVEHRS = 720;
        int minutes = snooze % MINS_IN_HOUR;

        int hours = (snooze / MINS_IN_HOUR) % TWELVE_HR_CLOCK;

        // Seperate condition for 12am and 12pm
        if ((hours % TWELVE_HR_CLOCK) == 0) {
            hours = 12;
        }
        System.out.print(hours + ":");
        System.out.printf("%02d", minutes); // Makes minutes 2 digit

        // Check how many 12 hour cycles have passed
        if ((snooze / MINS_IN_TWELVEHRS) % 2 == 0) {
            System.out.print("pm");
        }
        else {
            System.out.print("am");
        }
    }
}

