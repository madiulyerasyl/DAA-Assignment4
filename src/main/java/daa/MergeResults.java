
package daa;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;

public class MergeResults {

    public static void main(String[] args) throws IOException {

        Path resultsDir = Path.of("results");

        Path editFile = resultsDir.resolve("edit_distance.csv");
        Path searchFile = resultsDir.resolve("string_search.csv");
        Path outputFile = resultsDir.resolve("results.csv");

        List<String> editLines = Files.readAllLines(editFile);
        List<String> searchLines = Files.readAllLines(searchFile);

        if (editLines.isEmpty() || searchLines.isEmpty()) {
            throw new IllegalStateException("Input CSV file is empty");
        }

        if (!editLines.get(0).equals(searchLines.get(0))) {
            throw new IllegalStateException(
                    "CSV headers do not match");
        }

        List<String> combined = new ArrayList<>();

        combined.add(editLines.get(0));

        combined.addAll(editLines.subList(1, editLines.size()));
        combined.addAll(searchLines.subList(1, searchLines.size()));

        Files.write(outputFile, combined);

        System.out.println("Results merged successfully!");
        System.out.println("Total measurements: "
                + (combined.size() - 1));
        System.out.println("Saved to: "
                + outputFile.toAbsolutePath());
    }
}
