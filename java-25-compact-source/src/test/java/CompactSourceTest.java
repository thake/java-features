import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Verifiziert, dass sowohl die klassische Before-Klasse als auch das
// Compact Source File (After.java, JEP 512) sauber kompilieren und dieselbe Ausgabe liefern.
class CompactSourceTest {

    private static String captureStdout(Runnable action) {
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
    void compactSourceBeforeAndAfterMatch() throws Exception {
        String before = captureStdout(() -> Before.main(new String[0]));

        // Compact Source Files duerfen im Quellcode nicht direkt per Klassennamen referenziert werden,
        // lassen sich aber ueber Reflection (oder direkt via `java After.java`) ausfuehren.
        Class<?> afterClass = Class.forName("After");
        Constructor<?> constructor = afterClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        Object instance = constructor.newInstance();
        Method mainMethod = afterClass.getDeclaredMethod("main");
        mainMethod.setAccessible(true);

        String after = captureStdout(() -> {
            try {
                mainMethod.invoke(instance);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        assertEquals(before, after);
        assertTrue(after.contains("Hello from Java 25!"));
    }
}
