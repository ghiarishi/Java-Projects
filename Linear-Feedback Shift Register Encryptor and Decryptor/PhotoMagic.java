// Program used for encryption and decryption of images through LFSRs.

import java.awt.Color;

public class PhotoMagic {

    // Transforms image acc. to given LFSR, returns new image
    public static Picture transform(Picture picture, LFSR lfsr1) {
        Picture modified = new Picture(picture);
        for (int col = 0; col < modified.width(); col++) {
            for (int row = 0; row < modified.height(); row++) {

                // Store current pixels colour in object
                Color color = modified.get(col, row);

                int rnd1 = lfsr1.generate(8);
                int rnd2 = lfsr1.generate(8);
                int rnd3 = lfsr1.generate(8);

                // EXOR the colors for encryption/decryption
                int r = color.getRed() ^ rnd1;  // r
                int g = color.getGreen() ^ rnd2;  // g
                int b = color.getBlue() ^ rnd3;  // b

                modified.set(col, row, new Color(r, g, b));
            }
        }
        return modified;
    }

    // Input name of imagefile and desc. of LFSR. Outputs enc/dec image.
    public static void main(String[] args) {
        Picture img = new Picture(args[0]); // Original picture
        int tap = Integer.parseInt(args[2]);
        LFSR lfsr1 = new LFSR(args[1], tap);

        Picture transformed; // New picture object for output
        transformed = transform(img, lfsr1);
        transformed.show();
    }
}
