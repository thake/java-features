package dev.javafeatures.jdk25.preview;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Verifiziert, dass alle JDK-25-Preview-Beispiele mit --enable-preview kompilieren,
// fehlerfrei laufen und zwischen Before und After identische Ergebnisse liefern.
class Java25PreviewExamplesTest {

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
    void structuredConcurrencyBeforeAndAfterMatch() throws Exception {
        String before = captureStdout(() -> dev.javafeatures.jdk25.preview.structuredconcurrency.Before.main(new String[0]));
        String after = captureStdout(() -> dev.javafeatures.jdk25.preview.structuredconcurrency.After.main(new String[0]));
        assertEquals(before, after);
        assertTrue(after.contains("Dashboard: Dashboard[userProfile=User(alice), openOrders=3]"));
    }

    @Test
    void primitivePatternsBeforeAndAfterMatch() throws Exception {
        String before = captureStdout(() -> dev.javafeatures.jdk25.preview.primitivepatterns.Before.main(new String[0]));
        String after = captureStdout(() -> dev.javafeatures.jdk25.preview.primitivepatterns.After.main(new String[0]));
        assertEquals(before, after);
        assertTrue(after.contains("42 fits into byte: 42"));
        assertTrue(after.contains("300 does not fit into byte"));
    }

    @Test
    void stableValuesBeforeAndAfterMatch() throws Exception {
        String before = captureStdout(() -> dev.javafeatures.jdk25.preview.stablevalues.Before.main(new String[0]));
        String after = captureStdout(() -> dev.javafeatures.jdk25.preview.stablevalues.After.main(new String[0]));
        assertEquals(before, after);
        assertTrue(after.contains("Init count:  1"));
    }
}
