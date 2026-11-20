package dev.javafeatures.jdk25;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Verifiziert, dass alle Java-21-zu-25-Beispiele sauber laufen und
// Before sowie After fachlich identische Ergebnisse erzeugen.
class Java25ExamplesTest {

    @FunctionalInterface
    interface ThrowingRunnable {
        void run() throws Throwable;
    }

    private static String captureStdout(ThrowingRunnable action) throws Throwable {
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
    void unnamedVariablesBeforeAndAfterMatch() throws Throwable {
        String before = captureStdout(() -> dev.javafeatures.jdk25.unnamedvariables.Before.main(new String[0]));
        String after = captureStdout(() -> dev.javafeatures.jdk25.unnamedvariables.After.main(new String[0]));
        assertEquals(before, after);
        assertTrue(after.contains("parsed=-1, admins=[alice], count=3, name=Alice"));
    }

    @Test
    void ffmApiBeforeAndAfterMatch() throws Throwable {
        String before = captureStdout(() -> dev.javafeatures.jdk25.ffmapi.Before.main(new String[0]));
        String after = captureStdout(() -> dev.javafeatures.jdk25.ffmapi.After.main(new String[0]));
        assertEquals(before, after);
        assertTrue(after.contains("Off-heap C-string length: 14"));
    }

    @Test
    void markdownDocBeforeAndAfterMatch() throws Throwable {
        String before = captureStdout(() -> dev.javafeatures.jdk25.markdowndoc.Before.main(new String[0]));
        String after = captureStdout(() -> dev.javafeatures.jdk25.markdowndoc.After.main(new String[0]));
        assertEquals(before, after);
        assertTrue(after.contains("Price for 150.0: 135.0"));
    }

    @Test
    void streamGatherersBeforeAndAfterMatch() throws Throwable {
        String before = captureStdout(() -> dev.javafeatures.jdk25.streamgatherers.Before.main(new String[0]));
        String after = captureStdout(() -> dev.javafeatures.jdk25.streamgatherers.After.main(new String[0]));
        assertEquals(before, after);
        assertTrue(after.contains("Fixed windows:   [[1, 2], [3, 4], [5]]"));
        assertTrue(after.contains("Running sums:    [1, 3, 6, 10, 15]"));
    }

    @Test
    void virtualThreadsPinFreeBeforeAndAfterMatch() throws Throwable {
        String before = captureStdout(() -> dev.javafeatures.jdk25.virtualthreadspinfree.Before.main(new String[0]));
        String after = captureStdout(() -> dev.javafeatures.jdk25.virtualthreadspinfree.After.main(new String[0]));
        assertEquals(before, after);
        assertTrue(after.contains("Final count: 20"));
    }

    @Test
    void scopedValuesBeforeAndAfterMatch() throws Throwable {
        String before = captureStdout(() -> dev.javafeatures.jdk25.scopedvalues.Before.main(new String[0]));
        String after = captureStdout(() -> dev.javafeatures.jdk25.scopedvalues.After.main(new String[0]));
        assertEquals(before, after);
        assertTrue(after.contains("[alice] performed create-order"));
        assertTrue(after.contains("Outside scope bound: false"));
    }

    @Test
    void kdfApiBeforeAndAfterDeriveIdenticalKey() throws Throwable {
        String before = captureStdout(() -> dev.javafeatures.jdk25.kdfapi.Before.main(new String[0]));
        String after = captureStdout(() -> dev.javafeatures.jdk25.kdfapi.After.main(new String[0]));
        assertEquals(before, after);
        assertTrue(after.contains("Derived AES-256 key: "));
    }

    @Test
    void moduleImportsBeforeAndAfterMatch() throws Throwable {
        String before = captureStdout(() -> dev.javafeatures.jdk25.moduleimports.Before.main(new String[0]));
        String after = captureStdout(() -> dev.javafeatures.jdk25.moduleimports.After.main(new String[0]));
        assertEquals(before, after);
        assertTrue(after.contains("total=249.40"));
    }

    @Test
    void flexibleConstructorsBeforeAndAfterMatch() throws Throwable {
        String before = captureStdout(() -> dev.javafeatures.jdk25.flexibleconstructors.Before.main(new String[0]));
        String after = captureStdout(() -> dev.javafeatures.jdk25.flexibleconstructors.After.main(new String[0]));
        assertEquals(before, after);
        assertTrue(after.contains("SavingsAccount[id=DE-42, interestRateBps=350]"));
        assertThrows(IllegalArgumentException.class,
                () -> new dev.javafeatures.jdk25.flexibleconstructors.After.SavingsAccount(" ", 100));
    }
}
