# DAA Assignment 4 — Dynamic Programming and String Algorithms

## Project Description

This project implements and compares five algorithms for Edit Distance and String Search as part of the Design and Analysis of Algorithms course.

The project is developed using Java 25, Maven, and JUnit 5.

## Implemented Algorithms

### Edit Distance

1. **Recursive Edit Distance** — a direct recursive implementation without memoization.
2. **DP Table Edit Distance** — a bottom-up dynamic programming solution using a full table.
3. **Two Rows Edit Distance** — a memory-optimized dynamic programming solution.

### String Search

4. **Naive String Search** — checks every possible starting position in the text.
5. **KMP String Search** — uses the LPS array to avoid unnecessary repeated comparisons.

All algorithms include operation counters for experimental analysis.

## Technologies

- Java 25
- Maven
- JUnit 5
- JFreeChart

## How to Build and Test

Open the project in IntelliJ IDEA or use the terminal from the project root.

Compile the project:

```bash
mvn clean compile
```

Run all JUnit tests:

```bash
mvn test
```

## How to Run Benchmarks

Run the following Java classes in IntelliJ IDEA, in this order:

1. `daa.EditDistanceBenchmark`
2. `daa.StringSearchBenchmark`
3. `daa.MergeResults`
4. `daa.PlotGenerator`

The benchmark programs generate CSV files containing execution times and operation counts. Each experiment is repeated five times.

The combined results are saved to `results/results.csv`.

## Generated Plots

The plots are saved in `results/plots/`:

- `edit_time.png`
- `edit_operations.png`
- `search_operations.png`
- `search_time.png`

The plots compare execution time and operation counts for different input sizes and input types.

## Project Structure

```text
Assignment4/
├── src/
│   ├── main/java/daa/
│   │   ├── RecursiveEditDistance.java
│   │   ├── TableEditDistance.java
│   │   ├── TwoRowsEditDistance.java
│   │   ├── NaiveSearch.java
│   │   ├── KMPSearch.java
│   │   ├── EditDistanceBenchmark.java
│   │   ├── StringSearchBenchmark.java
│   │   ├── MergeResults.java
│   │   └── PlotGenerator.java
│   └── test/java/daa/
│       ├── EditDistanceTest.java
│       └── StringSearchTest.java
├── results/
│   ├── edit_distance.csv
│   ├── string_search.csv
│   ├── results.csv
│   └── plots/
├── REPORT.md
├── README.md
└── pom.xml
```

## Experimental Method

The experiments use random and adversarial inputs. A fixed random seed of 42 is used to ensure reproducibility.

Execution time is measured using `System.nanoTime()`. Operation counters are implemented inside the algorithms.

Five runs are recorded for each configuration, and median execution times are used for visualization.

## Report

The full experimental report is available in `REPORT.md`. It includes the Experiment Design Card, four plots, predictions, results, discussion, and conclusions.

## AI Use Acknowledgement

