package daa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KMPSearch {

    // Build the Longest Proper Prefix which is also Suffix array.
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

        int[] lps = lps(pattern);

        int i = 0;
        int j = 0;

        while (i < n) {

            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            }

            if (j == m) {
                positions.add(i - j);
                j = lps[j - 1];
            } else if (i < n &&
                    text.charAt(i) != pattern.charAt(j)) {

                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }

        return positions;
    }

    public static void main(String[] args) {

        System.out.println(Arrays.toString(lps("AAACAAAA")));
        System.out.println(Arrays.toString(lps("ABABCABAB")));

        System.out.println(search("ABABDABACDABABCABAB", "ABABCABAB"));
        System.out.println(search("AAAAA", "AA"));
        System.out.println(search("ABABABA", "ABA"));
    }
}
