package daa;

public class TwoRowsEditDistance {

    // State: prev[j] and curr[j] represent edit distances
    // between prefixes of the two strings.
    // Transition: use insertion, deletion and replacement,
    // taking the minimum cost.
    // Base case: an empty prefix has distance equal
    // to the length of the other prefix.

    public static int distance(String a, String b) {

        // Keep the shorter string in b to save memory.
        if (b.length() > a.length()) {
            String temp = a;
            a = b;
            b = temp;
        }

        int n = a.length();
        int m = b.length();

        int[] prev = new int[m + 1];
        int[] curr = new int[m + 1];

        for (int j = 0; j <= m; j++) {
            prev[j] = j;
        }

        for (int i = 1; i <= n; i++) {

            curr[0] = i;

            for (int j = 1; j <= m; j++) {

                int cost = (a.charAt(i - 1) == b.charAt(j - 1))
                        ? 0 : 1;

                int insert = curr[j - 1] + 1;
                int delete = prev[j] + 1;
                int replace = prev[j - 1] + cost;

                curr[j] = Math.min(insert,
                        Math.min(delete, replace));
            }

            int[] temp = prev;
            prev = curr;
            curr = temp;
        }

        return prev[m];
    }

    public static void main(String[] args) {

        System.out.println(distance("cat", "cut"));
        System.out.println(distance("kitten", "sitting"));
        System.out.println(distance("flaw", "lawn"));
        System.out.println(distance("intention", "execution"));
        System.out.println(distance("", "abc"));
        System.out.println(distance("abc", "abc"));
    }
}
