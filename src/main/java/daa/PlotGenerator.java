package daa;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class PlotGenerator {

    record Result(String algorithm, String input,
                  int n, double time, long operations) {}

    public static void main(String[] args) throws IOException {

        Path csv = Path.of("results", "results.csv");
        Path output = Path.of("results", "plots");
        Files.createDirectories(output);

        List<Result> results = new ArrayList<>();
        List<String> lines = Files.readAllLines(csv);

        for (int i = 1; i < lines.size(); i++) {
            String[] c = lines.get(i).split(",");

            results.add(new Result(
                    c[0], c[1],
                    Integer.parseInt(c[2]),
                    Double.parseDouble(c[4]),
                    Long.parseLong(c[5])
            ));
        }

        createPlot(results, output, "edit_time.png",
                "Edit Distance - Median Execution Time",
                "Input size (characters)", "Median time (ms)",
                List.of("Recursive", "DP Table", "Two Rows"),
                true, true);

        createPlot(results, output, "edit_operations.png",
                "Edit Distance - Operation Count",
                "Input size (characters)", "Operations (count)",
                List.of("Recursive", "DP Table", "Two Rows"),
                false, true);

        createPlot(results, output, "search_operations.png",
                "String Search - Character Comparisons",
                "Text size (characters)", "Comparisons (count)",
                List.of("Naive", "KMP"),
                false, false);

        createPlot(results, output, "search_time.png",
                "String Search - Median Execution Time",
                "Text size (characters)", "Median time (ms)",
                List.of("Naive", "KMP"),
                true, false);

        System.out.println("All four plots created!");
        System.out.println(output.toAbsolutePath());
    }

    private static void createPlot(
            List<Result> results, Path output,
            String filename, String title,
            String xLabel, String yLabel,
            List<String> algorithms,
            boolean useTime, boolean logScale
    ) throws IOException {

        XYSeriesCollection dataset = new XYSeriesCollection();

        for (String algorithm : algorithms) {
            for (String input : List.of("random", "adversarial")) {

                XYSeries series = new XYSeries(
                        algorithm + " - " + input
                );

                Map<Integer, List<Double>> groups = new TreeMap<>();

                for (Result r : results) {
                    if (r.algorithm().equals(algorithm)
                            && r.input().equals(input)) {

                        double value = useTime
                                ? r.time()
                                : r.operations();

                        groups.computeIfAbsent(r.n(),
                                k -> new ArrayList<>()).add(value);
                    }
                }

                for (var entry : groups.entrySet()) {
                    List<Double> values = entry.getValue();
                    Collections.sort(values);

                    double median = values.get(values.size() / 2);
                    series.add(entry.getKey().doubleValue(), median);
                }

                dataset.addSeries(series);
            }
        }

        JFreeChart chart = ChartFactory.createXYLineChart(
                title, xLabel, yLabel, dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        if (logScale) {
            LogAxis axis = new LogAxis(yLabel);
            chart.getXYPlot().setRangeAxis(axis);
        }

        ChartUtils.saveChartAsPNG(
                output.resolve(filename).toFile(),
                chart, 1100, 700
        );

        System.out.println("Created: " + filename);
    }
}
