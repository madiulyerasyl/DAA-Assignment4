package daa;

public class RecursiveEditDistance {

    private static long operations = 0;

    // State: distance(i, j) is the minimum edits between prefixes.
    // Transition: match -> diagonal; otherwise insert/delete/replace.
    // Base case: an empty prefix requires the other prefix's length.

    public static int distance(String a, String b) {
        operations = 0;
        return solve(a, b, a.length(), b.length());
    }

    private static int solve(String a, String b, int i, int j) {

        // Count every recursive call.
        operations++;

        if (i == 0) return j;
        if (j == 0) return i;

        if (a.charAt(i - 1) == b.charAt(j - 1)) {
            return solve(a, b, i - 1, j - 1);
        }

        int insert = solve(a, b, i, j - 1);
        int delete = solve(a, b, i - 1, j);
        int replace = solve(a, b, i - 1, j - 1);

        return 1 + Math.min(insert, Math.min(delete, replace));
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
