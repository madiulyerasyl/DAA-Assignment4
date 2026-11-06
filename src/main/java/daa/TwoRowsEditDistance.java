package daa;

public class TwoRowsEditDistance {

    private static long operations = 0;

    // State: prev[j] and curr[j] store edit distances
    // between prefixes of the two strings.
    // Transition: minimum of insert, delete and replace.
    // Base case: an empty prefix requires the other length.

    public static int distance(String a, String b) {

        operations = 0;

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

                // Count each computed DP cell.
                operations++;
            }

            int[] temp = prev;
            prev = curr;
            curr = temp;
        }

        return prev[m];
    }

    public static long getOperations() {
        return operations;
    }

    public static void main(String[] args) {

        System.out.println("Distance: " +
                distance("cat", "cut"));
        System.out.println("Operations: " + getOperations());

        System.out.println("Distance: " +
                distance("kitten", "sitting"));
        System.out.println("Operations: " + getOperations());
    }
}
