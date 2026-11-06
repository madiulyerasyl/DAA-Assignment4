
package daa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KMPSearch {

    private static long operations = 0;

    public static int[] lps(String pattern) {

        if (pattern.isEmpty()) {
            throw new IllegalArgumentException(
                    "Pattern must not be empty");
        }

        int m = pattern.length();
        int[] lps = new int[m];

        int len = 0;
        int i = 1;

        while (i < m) {

            // Count comparison during LPS preprocessing.
            operations++;

            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }

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

        int[] prefix = lps(pattern);

        int i = 0;
        int j = 0;

        while (i < n) {

            // Count each text-pattern character comparison.
            operations++;

            if (text.charAt(i) == pattern.charAt(j)) {

                i++;
                j++;

                if (j == m) {
                    positions.add(i - j);
                    j = prefix[j - 1];
                }

            } else {

                if (j != 0) {
                    j = prefix[j - 1];
                } else {
                    i++;
                }
            }
        }

        return positions;
    }

    public static long getOperations() {
        return operations;
    }

    public static void main(String[] args) {

        System.out.println(Arrays.toString(
                lps("AAACAAAA")));

        System.out.println(Arrays.toString(
                lps("ABABCABAB")));

        System.out.println("Matches: " +
                search("ABABABA", "ABA"));

        System.out.println("Operations: " + getOperations());

        System.out.println("Matches: " +
                search("AAAAA", "AA"));

        System.out.println("Operations: " + getOperations());
    }
}
