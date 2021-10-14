public class BeadTracker {
    public static void main(String[] args) {

        Stopwatch timer = new Stopwatch();

        int min = Integer.parseInt(args[0]);
        double tau = Double.parseDouble(args[1]);
        double delta = Double.parseDouble(args[2]);

        int noOfFrames = args.length - 3;

        // Loop for 2 images at a time
        for (int i = 3; i < noOfFrames + 2; i++) {

            Picture img1 = new Picture(args[i]);
            Picture img2 = new Picture(args[i + 1]);

            BeadFinder imgOne = new BeadFinder(img1, tau);
            BeadFinder imgTwo = new BeadFinder(img2, tau);

            Blob[] arr1 = imgOne.getBeads(min);
            Blob[] arr2 = imgTwo.getBeads(min);

            // Loop for each bead in img1
            for (int j = 0; j < arr2.length; j++) {

                double minDist = Double.POSITIVE_INFINITY;

                // Loop for each bead in img2
                for (int k = 0; k < arr1.length; k++) {

                    if (arr2[j].distanceTo(arr1[k]) < minDist) {
                        minDist = arr2[j].distanceTo(arr1[k]);
                    }
                }
                if (minDist > delta) continue;

                StdOut.printf("%8.4f", minDist);
                StdOut.println("");
            }
            StdOut.println("");
        }
        double timeTaken = timer.elapsedTime();
        // StdOut.println(timeTaken + " seconds");
    }
}
