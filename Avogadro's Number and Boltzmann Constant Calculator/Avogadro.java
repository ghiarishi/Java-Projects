// Program to estimate the value of Avogadros Number and the Boltzmann Constant
// with a maximum error of 10%

public class Avogadro {
    public static void main(String[] args) {

        final double PIXELS_TO_METERS = 0.175 * Math.pow(10, -6);
        final double DELTA_T = 0.5; // Seconds
        final double T_ABSOLUTE = 297.0; // Kelvin
        final double MU = 9.135 * Math.pow(10, -4); // Viscosity
        final double BEAD_RAD = 0.5 * Math.pow(10, -6); // Meters
        final double R = 8.31446; // Universal Gas Constant

        double D, k, nAvogadro, vari = 0; // Self-Diffusion Constant, Boltzmann, Avogadro, Variance
        
        int n = 0;

        while (!StdIn.isEmpty()) {
            n++;
            vari += Math.pow(StdIn.readDouble() * PIXELS_TO_METERS, 2);
        }

        vari /= (2 * n);

        D = vari / (2 * DELTA_T);

        k = (6 * D * Math.PI * MU * BEAD_RAD) / T_ABSOLUTE;

        nAvogadro = R / k;

        System.out.println(String.format("Boltzmann = %.4e", k));
        System.out.println(String.format("Avogadro  = %.4e", nAvogadro));
    }
}
