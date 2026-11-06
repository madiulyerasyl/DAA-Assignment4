package daa;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class StringSearchTest {

    @Test
    void testLps() {
        assertArrayEquals(
                new int[]{0, 1, 2, 0, 1, 2, 3, 3},
                KMPSearch.lps("AAACAAAA")
        );

        assertArrayEquals(
                new int[]{0, 0, 1, 2, 0, 1, 2, 3, 4},
                KMPSearch.lps("ABABCABAB")
        );
    }

    @Test
    void testReferenceCases() {
        checkBoth("ABABDABACDABABCABAB",
                "ABABCABAB", List.of(10));

        checkBoth("AAAAA", "AA",
                List.of(0, 1, 2, 3));

        checkBoth("ABABABA", "ABA",
                List.of(0, 2, 4));
    }

    @Test
    void testNoMatch() {
        checkBoth("HELLO", "XYZ", List.of());
        checkBoth("ABC", "ABCD", List.of());
        checkBoth("", "ABC", List.of());
    }

    @Test
    void testEmptyPattern() {
        assertThrows(IllegalArgumentException.class,
                () -> NaiveSearch.search("ABC", ""));

        assertThrows(IllegalArgumentException.class,
                () -> KMPSearch.search("ABC", ""));
    }

    @Test
    void testRandomStrings() {
        Random random = new Random(42);

        for (int t = 0; t < 100; t++) {
            String text = randomString(random, 20);
            String pattern = randomString(random, 5);

            if (pattern.isEmpty()) {
                pattern = "A";
            }

            List<Integer> naive =
                    NaiveSearch.search(text, pattern);

            List<Integer> kmp =
                    KMPSearch.search(text, pattern);

            assertEquals(naive, kmp,
                    "Mismatch for text=" + text
                            + ", pattern=" + pattern);
        }
    }

    private void checkBoth(String text,
                           String pattern,
                           List<Integer> expected) {

        assertEquals(expected,
                NaiveSearch.search(text, pattern));

        assertEquals(expected,
                KMPSearch.search(text, pattern));
    }

    private String randomString(Random random, int maxLength) {
        int length = random.nextInt(maxLength + 1);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append((char) ('A' + random.nextInt(3)));
        }

        return sb.toString();
    }
}
