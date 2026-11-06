package daa;

import java.util.ArrayList;
import java.util.List;

public class NaiveSearch {

    private static long operations = 0;

    public static List<Integer> search(String text, String pattern) {

        operations = 0;

        if (pattern.isEmpty()) {
            throw new IllegalArgumentException(
                    "Pattern must not be empty");
        }

        List<Integer> positions = new ArrayList<>();

        int n = text.length();
        int m = pattern.length();

        if (m > n) {
            return positions;
        }

        for (int i = 0; i <= n - m; i++) {

            int j = 0;

            while (j < m) {

                // Count every character comparison.
                operations++;

                if (text.charAt(i + j) != pattern.charAt(j)) {
                    break;
                }

                j++;
            }

            if (j == m) {
                positions.add(i);
            }
        }

        return positions;
    }

    public static long getOperations() {
        return operations;
    }

    public static void main(String[] args) {

        System.out.println("Matches: " +
                search("ABABABA", "ABA"));
        System.out.println("Operations: " + getOperations());

        System.out.println("Matches: " +
                search("AAAAA", "AA"));
        System.out.println("Operations: " + getOperations());
    }
}
