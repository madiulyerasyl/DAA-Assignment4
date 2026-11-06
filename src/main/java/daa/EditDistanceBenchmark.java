
package daa;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class EditDistanceBenchmark {

    private static final int RUNS = 5;
    private static final long SEED = 42;

    public static void main(String[] args) throws IOException {

        Path resultsDir = Path.of("results");
        Files.createDirectories(resultsDir);

        Path csv = resultsDir.resolve("edit_distance.csv");

        StringBuilder data = new StringBuilder();
        data.append("algorithm,input,n,run,time_ms,operations\n");

        int[] recursiveSizes = {3, 5, 7, 9, 11};
        int[] dpSizes = {100, 200, 400, 800, 1600};

        // JVM warm-up using small inputs.
        for (int w = 0; w < 100; w++) {
            RecursiveEditDistance.distance("ABCD", "BCDA");
            TableEditDistance.distance("ABCD", "BCDA");
            TwoRowsEditDistance.distance("ABCD", "BCDA");
        }

        for (String type : new String[]{"random", "adversarial"}) {

            for (int n : recursiveSizes) {

                String a = generate(n, type, 'A', SEED + n);
                String b = generate(n, type, 'B', SEED + n + 1000);

                for (int run = 1; run <= RUNS; run++) {

                    long start = System.nanoTime();

                    int answer = RecursiveEditDistance.distance(a, b);

                    long elapsed = System.nanoTime() - start;

                    // Verify correctness outside the timed section.
                    int expected = TableEditDistance.distance(a, b);

                    if (answer != expected) {
                        throw new IllegalStateException(
                                "Recursive and DP results differ!");
                    }

                    save(data, "Recursive", type, n, run,
                            elapsed,
                            RecursiveEditDistance.getOperations());
                }
            }

            for (int n : dpSizes) {

                String a = generate(n, type, 'A', SEED + n);
                String b = generate(n, type, 'B', SEED + n + 1000);

                for (int run = 1; run <= RUNS; run++) {

                    long start = System.nanoTime();

                    int tableAnswer = TableEditDistance.distance(a, b);

                    long elapsed = System.nanoTime() - start;

                    save(data, "DP Table", type, n, run,
                            elapsed,
                            TableEditDistance.getOperations());

                    start = System.nanoTime();

                    int twoRowsAnswer = TwoRowsEditDistance.distance(a, b);

                    elapsed = System.nanoTime() - start;

                    if (tableAnswer != twoRowsAnswer) {
                        throw new IllegalStateException(
                                "DP algorithms returned different results!");
                    }

                    save(data, "Two Rows", type, n, run,
                            elapsed,
                            TwoRowsEditDistance.getOperations());
                }
            }
        }

        Files.writeString(csv, data.toString());

        System.out.println("Edit Distance Benchmark completed!");
        System.out.println("Results saved to: " +
                csv.toAbsolutePath());
    }

    private static String generate(
            int n, String type, char c, long seed) {

        if (type.equals("adversarial")) {
            return String.valueOf(c).repeat(n);
        }

        Random random = new Random(seed);
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            sb.append((char) ('A' + random.nextInt(4)));
        }

        return sb.toString();
    }

    private static void save(
            StringBuilder data,
            String algorithm,
            String type,
            int n,
            int run,
            long elapsed,
            long operations) {

        double milliseconds = elapsed / 1_000_000.0;

        data.append(algorithm).append(",")
                .append(type).append(",")
                .append(n).append(",")
                .append(run).append(",")
                .append(milliseconds).append(",")
                .append(operations).append("\n");
    }
}
