package daa;

import java.util.ArrayList;
import java.util.List;

public class NaiveSearch {

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

        for (int i = 0; i <= n - m; i++) {

            int j = 0;

            while (j < m &&
                    text.charAt(i + j) == pattern.charAt(j)) {
                j++;
            }

            if (j == m) {
                positions.add(i);
            }
        }

        return positions;
    }

    public static void main(String[] args) {

        System.out.println(search("ABABABA", "ABA"));
        System.out.println(search("AAAAA", "AA"));
        System.out.println(search("ABABDABACDABABCABAB", "ABABCABAB"));
        System.out.println(search("HELLO", "XYZ"));
    }
}
