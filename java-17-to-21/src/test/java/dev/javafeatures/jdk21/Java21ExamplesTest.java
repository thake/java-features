package dev.javafeatures.jdk21;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Verifiziert, dass alle Java-17-zu-21-Beispiele ohne Fehler laufen und
// Before sowie After fachlich identische Ergebnisse liefern.
class Java21ExamplesTest {

    @FunctionalInterface
    interface ThrowingRunnable {
        void run() throws Exception;
    }

    private static String captureStdout(ThrowingRunnable action) throws Exception {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (PrintStream capture = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            System.setOut(capture);
            action.run();
        } finally {
            System.setOut(originalOut);
        }
        return buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
    }

    @Test
    void patternMatchingBeforeAndAfterMatch() throws Exception {
        String before = captureStdout(() -> dev.javafeatures.jdk21.patternmatching.Before.main(new String[0]));
        String after = captureStdout(() -> dev.javafeatures.jdk21.patternmatching.After.main(new String[0]));
        assertEquals(before, after);
        assertTrue(after.contains("High-value card (AMEX): 2500.0"));
        assertTrue(after.contains("No payment provided"));
    }

    @Test
    void recordPatternsBeforeAndAfterMatch() throws Exception {
        String before = captureStdout(() -> dev.javafeatures.jdk21.recordpatterns.Before.main(new String[0]));
        String after = captureStdout(() -> dev.javafeatures.jdk21.recordpatterns.After.main(new String[0]));
        assertEquals(before, after);
        assertTrue(after.contains("ORD-1001 -> Alice (Berlin, DE)"));
    }

    @Test
    void sequencedCollectionsBeforeAndAfterMatch() throws Exception {
        String before = captureStdout(() -> dev.javafeatures.jdk21.sequencedcollections.Before.main(new String[0]));
        String after = captureStdout(() -> dev.javafeatures.jdk21.sequencedcollections.After.main(new String[0]));
        assertEquals(before, after);
        assertTrue(after.contains("reversed=[deploy, test, build]"));
    }

    @Test
    void virtualThreadsBeforeAndAfterComputeSameSum() throws Exception {
        String before = captureStdout(() -> dev.javafeatures.jdk21.virtualthreads.Before.main(new String[0]));
        String after = captureStdout(() -> dev.javafeatures.jdk21.virtualthreads.After.main(new String[0]));
        assertTrue(before.contains("tasks=200, sum=19900"));
        assertTrue(after.contains("tasks=200, sum=19900"));
    }

    @Test
    void libraryImprovementsBeforeAndAfterMatch() throws Exception {
        String before = captureStdout(() -> dev.javafeatures.jdk21.libraryimprovements.Before.main(new String[0]));
        String after = captureStdout(() -> dev.javafeatures.jdk21.libraryimprovements.After.main(new String[0]));
        assertEquals(before, after);
        assertTrue(after.contains("Tokens:  [10, +, 20, -, 5]"));
    }
}
