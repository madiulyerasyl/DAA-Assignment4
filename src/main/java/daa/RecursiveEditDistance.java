
package daa;

public class RecursiveEditDistance {

    // State: distance(i, j) is the minimum edits between prefixes.
    // Transition: match -> diagonal; otherwise insert/delete/replace.
    // Base case: an empty prefix requires the other prefix's length.

    public static int distance(String a, String b) {
        return solve(a, b, a.length(), b.length());
    }

    private static int solve(String a, String b, int i, int j) {

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

    public static void main(String[] args) {
        System.out.println(distance("cat", "cut"));
        System.out.println(distance("kitten", "sitting"));
        System.out.println(distance("flaw", "lawn"));
        System.out.println(distance("", "abc"));
    }
}
