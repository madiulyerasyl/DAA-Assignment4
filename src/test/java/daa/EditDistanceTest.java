package daa;

import org.junit.jupiter.api.Test;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class EditDistanceTest {

    private void checkAll(String a, String b, int expected) {
        assertEquals(expected, RecursiveEditDistance.distance(a, b));
        assertEquals(expected, TableEditDistance.distance(a, b));
        assertEquals(expected, TwoRowsEditDistance.distance(a, b));
    }

    @Test
    void referenceValues() {
        checkAll("kitten", "sitting", 3);
        checkAll("flaw", "lawn", 2);
        checkAll("intention", "execution", 5);
        checkAll("", "abc", 3);
        checkAll("abc", "abc", 0);
    }

    @Test
    void emptyStrings() {
        checkAll("", "", 0);
        checkAll("abc", "", 3);
        checkAll("", "abc", 3);
    }

    @Test
    void randomShortStrings() {
        Random random = new Random(42);

        for (int t = 0; t < 100; t++) {
            String a = randomString(random, 4);
            String b = randomString(random, 4);

            int recursive = RecursiveEditDistance.distance(a, b);
            int table = TableEditDistance.distance(a, b);
            int twoRows = TwoRowsEditDistance.distance(a, b);

            assertEquals(recursive, table,
                    "Recursive and Table differ for: " + a + ", " + b);

            assertEquals(table, twoRows,
                    "Table and Two Rows differ for: " + a + ", " + b);
        }
    }

    private String randomString(Random random, int maxLength) {
        int length = random.nextInt(maxLength + 1);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append((char) ('a' + random.nextInt(3)));
        }

        return sb.toString();
    }
}
