// Simulate N Particles, mutually affected by gravitational forces

public class NBody {
    public static void main(String[] args) {

        double t = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);

        int noOfBodies = StdIn.readInt();
        double univRad = StdIn.readDouble();

        StdDraw.setXscale(-univRad, univRad);
        StdDraw.setYscale(-univRad, univRad);

        double[] sx = new double[noOfBodies];
        double[] sy = new double[noOfBodies];
        double[] vx = new double[noOfBodies];
        double[] vy = new double[noOfBodies];
        double[] mass = new double[noOfBodies];
        String[] gifName = new String[noOfBodies];

        for (int i = 0; i < noOfBodies; i++) {
            sx[i] = StdIn.readDouble();
            sy[i] = StdIn.readDouble();
            vx[i] = StdIn.readDouble();
            vy[i] = StdIn.readDouble();
            mass[i] = StdIn.readDouble();
            gifName[i] = StdIn.readString();
        }

        double[] fx = new double[noOfBodies];
        double[] fy = new double[noOfBodies];
        double[] ax = new double[noOfBodies];
        double[] ay = new double[noOfBodies];

        final double G = 6.67E-11; // G

        StdAudio.play("2001.wav");

        StdDraw.enableDoubleBuffering();

        // Big loop to run till end of Total T

        for (double time = 0; time < t; time += dt) {

            StdDraw.picture(0, 0, "starfield.jpg");

            // Draw all bodies

            for (int i = 0; i < noOfBodies; i++) {
                StdDraw.picture(sx[i], sy[i], gifName[i]);
            }

            // Loop for selecting first body

            for (int i = 0; i < noOfBodies; i++) {

                fx[i] = 0;
                fy[i] = 0;
                double feq;

                // Loop for selecting second body
                for (int j = 0; j < noOfBodies; j++) {
                    if (i == j) {
                        continue;
                    }

                    // Distances between 2 bodies

                    double x = sx[i] - sx[j];
                    double y = sy[i] - sy[j];

                    // R from the formula for Gravity

                    double dist = Math.pow((Math.pow(x, 2) + Math.pow(y, 2)), 0.5);

                    // Using sin as tan goes to infinity when x=0

                    double theta = Math.asin(y / dist);

                    feq = (G * mass[i] * mass[j]) / Math.pow(dist, 2);

                    // As we're using Sin instead of Tan, it does not take into
                    // account negative valus of X, hence the IF statement

                    if (sx[i] < sx[j]) {
                        fx[i] += (-feq * Math.cos(theta));
                    }
                    else {
                        fx[i] += (feq * Math.cos(theta));
                    }
                    fy[i] += (feq * Math.sin(theta));
                }

                // Update Accelarations

                ax[i] = fx[i] / mass[i];
                ay[i] = fy[i] / mass[i];
            }

            // Update velocities then distances

            for (int i = 0; i < noOfBodies; i++) {
                vx[i] = vx[i] - (ax[i] * dt);
                vy[i] = vy[i] - (ay[i] * dt);

                sx[i] = sx[i] + (vx[i] * dt);
                sy[i] = sy[i] + (vy[i] * dt);
            }
            StdDraw.show();
            StdDraw.pause(1);
        }

        // StdOut directed to NBodyOutput.txt in the nbody directory

        StdOut.println(noOfBodies);
        StdOut.println(univRad);
        for (int i = 0; i < noOfBodies; i++) {
            StdOut.printf("%12.4e", sx[i]);
            StdOut.printf("%12.4e", sy[i]);
            StdOut.printf("%12.4e", vx[i]);
            StdOut.printf("%12.4e", vy[i]);
            StdOut.printf("%12.4e", mass[i]);
            StdOut.printf("%13s", gifName[i]);
            StdOut.println();
        }
    }
}
