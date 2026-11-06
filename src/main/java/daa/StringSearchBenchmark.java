package daa;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class StringSearchBenchmark {

    private static final int RUNS = 5;
    private static final int[] SIZES = {
            1000, 4000, 16000, 64000
    };

    private static final int PATTERN_LENGTH = 16;
    private static final long SEED = 42;

    public static void main(String[] args) throws IOException {

        Path resultsDir = Path.of("results");
        Files.createDirectories(resultsDir);

        Path csv = resultsDir.resolve("string_search.csv");

        StringBuilder data = new StringBuilder();
        data.append("algorithm,input,n,run,time_ms,operations\n");

        for (String type : new String[]{"random", "adversarial"}) {

            for (int n : SIZES) {

                String text;
                String pattern;

                if (type.equals("adversarial")) {
                    text = "A".repeat(n);
                    pattern = "A".repeat(PATTERN_LENGTH - 1) + "B";
                } else {
                    Random random = new Random(SEED + n);
                    text = randomString(random, n);
                    pattern = randomString(random, PATTERN_LENGTH);
                }

                // Warm up the JVM before measuring.
                for (int warmup = 0; warmup < 3; warmup++) {
                    NaiveSearch.search(text, pattern);
                    KMPSearch.search(text, pattern);
                }

                for (int run = 1; run <= RUNS; run++) {

                    long start = System.nanoTime();

                    var naiveMatches = NaiveSearch.search(text, pattern);

                    long elapsed = System.nanoTime() - start;
                    long naiveOperations = NaiveSearch.getOperations();

                    save(data, "Naive", type, n, run,
                            elapsed, naiveOperations);

                    start = System.nanoTime();

                    var kmpMatches = KMPSearch.search(text, pattern);

                    elapsed = System.nanoTime() - start;
                    long kmpOperations = KMPSearch.getOperations();

                    if (!naiveMatches.equals(kmpMatches)) {
                        throw new IllegalStateException(
                                "Algorithms returned different results!");
                    }

                    save(data, "KMP", type, n, run,
                            elapsed, kmpOperations);
                }
            }
        }

        Files.writeString(csv, data.toString());

        System.out.println("String Search Benchmark completed!");
        System.out.println("Results saved to: " +
                csv.toAbsolutePath());
    }

    private static String randomString(Random random, int length) {

        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
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
