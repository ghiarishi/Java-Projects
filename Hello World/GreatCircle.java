public class GreatCircle {
    public static void main(String[] args) {

        // Declare variables

        double x1, x2, y1, y2, dist;

        // Print to ask

        System.out.println("Enter latitudes and longitudes of 2 places respectively (in degrees): ");

        // Ask for degrees, convert to double, convert to radians

        x1 = Math.toRadians(Double.parseDouble(args[0]));
        y1 = Math.toRadians(Double.parseDouble(args[1]));
        x2 = Math.toRadians(Double.parseDouble(args[2]));
        y2 = Math.toRadians(Double.parseDouble(args[3]));

        // Use formula : distance=60arccos(sinx1sinx2+cosx1cosx2cos(y1−y2))

        // Convert radian output of Math.acos to Degrees and calculate

        dist = 60 * Math.toDegrees(Math.acos((Math.sin(x1) * (Math.sin(x2))) + ((Math.cos(x1)) * (Math.cos(x2)) * (Math.cos(y1 - y2)))));

        // Print answer

        System.out.println("The distance from Princeton to Paris is: " + dist + " Nautical Miles");
    }
}
