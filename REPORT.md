# DAA Assignment 4 — Experimental Report

## 1. Experiment Design Card

### Experiment 1: Edit Distance

**Research Question:** How do the recursive, full DP table, and two-row DP algorithms compare in execution time and operation counts as string length increases?

**Input Sizes:**

- Recursive: n = 3, 5, 7, 9, 11.
- DP Table and Two Rows: n = 100, 200, 400, 800, 1600.

Both strings will have length n.

**Input Types:**

1. Random strings generated from a fixed alphabet using seed 42.
2. Different repeated characters, such as "AAAA..." versus "BBBB...", to produce many mismatches and make the recursive algorithm perform more work.

**Metrics:**

- Execution time in milliseconds, measured using `System.nanoTime()`.
- Operation count collected inside each algorithm.

The recursive version will count recursive calls, while the DP versions will count computed table cells. These counters measure different kinds of work and will be interpreted accordingly.

**Prediction (Before Running):**

The recursive algorithm will become significantly slower as n increases because it repeatedly solves the same subproblems. Both DP versions should scale approximately quadratically. Two Rows should use less memory than the full DP Table, but it may not always be faster.

### Experiment 2: String Search

**Research Question:** How do Naive Search and KMP compare in execution time and character comparisons as text length increases?

**Input Sizes:**

n = 1000, 4000, 16000, 64000.

The pattern length will be 16 characters.

**Input Types:**

1. Random text and pattern generated with fixed seed 42.
2. Adversarial input: text consisting of repeated "A" characters and pattern consisting of 15 "A" characters followed by "B".

The adversarial input is designed to cause many repeated comparisons in Naive Search.

**Metrics:**

- Execution time in milliseconds.
- Character comparison count collected inside the algorithms.

**Prediction (Before Running):**

KMP should perform fewer character comparisons than Naive Search on adversarial inputs because it uses the LPS array to avoid repeating unnecessary comparisons. On short or random inputs, Naive Search may have similar or better execution time because it has less preprocessing overhead.

### Experimental Procedure

All experiments will use deterministic input generation with seed 42. Each algorithm and input configuration will be executed five times. Every run will be saved to `results/results.csv`, and the median execution time will be used for comparisons.

### Planned Plots

1. **Edit Distance: Input Size vs Execution Time** — a logarithmic time axis will help compare recursive and DP implementations.
2. **Edit Distance: Input Size vs Operation Count** — compare growth in recursive calls and DP cell computations.
3. **String Search: Input Size vs Character Comparisons** — compare Naive and KMP on random and adversarial inputs.
4. **String Search: Input Size vs Median Execution Time** — compare measured performance.

Each plot will include a title, labeled axes with units, and a one-sentence takeaway.

## 2. Experimental Results

*To be completed after running the benchmarks.*

## 3. Prediction vs Results

*To be completed after analyzing the measurements.*

## 4. Discussion

*To be completed after the experiments.*

## 5. Conclusion

*To be completed after the experiments.*