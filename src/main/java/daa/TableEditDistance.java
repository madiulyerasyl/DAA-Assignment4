package daa;

public class TableEditDistance {

    private static long operations = 0;

    // State: dp[i][j] stores the edit distance between prefixes.
    // Transition: minimum of insertion, deletion and replacement.
    // Base case: dp[i][0] = i and dp[0][j] = j.

    public static int distance(String a, String b) {

        operations = 0;

        int n = a.length();
        int m = b.length();

        int[][] dp = new int[n + 1][m + 1];

        for (int i = 0; i <= n; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= m; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {

                int cost = (a.charAt(i - 1) == b.charAt(j - 1))
                        ? 0 : 1;

                int insert = dp[i][j - 1] + 1;
                int delete = dp[i - 1][j] + 1;
                int replace = dp[i - 1][j - 1] + cost;

                dp[i][j] = Math.min(insert,
                        Math.min(delete, replace));

                // Count each computed DP cell.
                operations++;
            }
        }

        return dp[n][m];
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
