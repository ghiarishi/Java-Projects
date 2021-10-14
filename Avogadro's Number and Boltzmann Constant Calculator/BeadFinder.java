// Code to find each connected component of light pixels (blob)
// in the image, and identify which are longer than 'min'

import java.awt.Color;

public class BeadFinder {

    private boolean[][] visited; // boolean array of all visited pixels
    private boolean[][] lumLevel; // boolean array of all bright pixels

    private int imgWidth;
    private int imgHeight;

    private Queue<Blob> blobQueue;

    //  finds all blobs in the specified picture using luminance threshold tau
    public BeadFinder(Picture picture, double tau) {

        imgWidth = picture.width();
        imgHeight = picture.height();

        visited = new boolean[imgWidth][imgHeight];
        lumLevel = new boolean[imgWidth][imgHeight];

        blobQueue = new Queue<Blob>();

        // Check each pixels luminance, if above tau
        for (int currentWidth = 0; currentWidth < imgWidth; currentWidth++) {
            for (int currentHeight = 0; currentHeight < imgHeight; currentHeight++) {

                Color pixel = picture.get(currentWidth, currentHeight);

                double lum = Luminance.intensity(pixel);

                if (lum >= tau) {
                    lumLevel[currentWidth][currentHeight] = true; // mark as bright pixel
                }
                else {
                    lumLevel[currentWidth][currentHeight] = false; // mark as dark pixel
                }
            }
        }

        // run DFS on each pixel recursively
        for (int currentWidth = 0; currentWidth < imgWidth; currentWidth++) {
            for (int currentHeight = 0; currentHeight < imgHeight; currentHeight++) {

                Blob blob = new Blob();

                dfs(currentWidth, currentHeight, blob);

                if (blob.mass() > 0) {
                    blobQueue.enqueue(blob);
                }
            }
        }
    }

    //  returns all beads (blobs with >= min pixels)
    public Blob[] getBeads(int min) {

        int arrLen = blobQueue.size();
        Blob[] beads = new Blob[arrLen];

        for (int i = 0; i < arrLen; i++) {

            if (blobQueue.isEmpty()) break;

            if (blobQueue.peek().mass() >= min) {
                beads[i] = blobQueue.dequeue(); // is a bead
            }
            else {
                blobQueue.dequeue(); // not a bead
            }
        }
        return removeNulls(beads);
    }

    // DFS method to recursively check all pixels and find connected components
    private void dfs(int x, int y, Blob blob) {

        // Return if already visited or pixel is dark
        if (visited[x][y]) return;
        visited[x][y] = true;
        if (!lumLevel[x][y]) return;
        blob.add(x, y);

        // Check up, right, down and left
        if (!(y == 0)) dfs(x, y - 1, blob);
        if (!(x == imgWidth - 1)) dfs(x + 1, y, blob);
        if (!(y == imgHeight - 1)) dfs(x, y + 1, blob);
        if (!(x == 0)) dfs(x - 1, y, blob);
    }

    // method to remove nulls left behind in array
    private Blob[] removeNulls(Blob[] input) {

        int nullCount = 0;
        for (int i = 0; i < input.length; i++) {
            if (input[i] == null) nullCount++;
        }

        Blob[] noNullArr = new Blob[input.length - nullCount];

        int j = 0;
        for (int i = 0; i < input.length; i++) {
            if (input[i] == null) {
                j++;
            }
            else {
                noNullArr[i - j] = input[i];
            }
        }
        return noNullArr;
    }

    //  test client, as described below
    public static void main(String[] args) {

        int min = Integer.parseInt(args[0]);
        double tau = Double.parseDouble(args[1]);
        Picture img = new Picture(args[2]);

        BeadFinder test = new BeadFinder(img, tau);
        Blob[] allBeads = test.getBeads(min);

        for (int i = 0; i < allBeads.length; i++) {
            StdOut.println(allBeads[i]);
        }
    }
}
