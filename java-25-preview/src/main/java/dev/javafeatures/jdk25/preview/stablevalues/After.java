package dev.javafeatures.jdk25.preview.stablevalues;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

// Nachher (Java 25 Preview, JEP 502): StableValue bietet Thread-safe Lazy Initialization
// mit garantierter Einmal-Initialisierung und erlaubt der JVM anschliessend Constant Folding
// — aehnlich wie bei einem final-Feld, aber verzoegert initialisiert.
public class After {

    static class ConfigService {
        private final AtomicInteger initCount = new AtomicInteger();
        // Deklarativer, Thread-safe Stable Supplier ohne volatile/synchronized-Boilerplate
        private final Supplier<String> cachedEndpoint = StableValue.supplier(() -> {
            initCount.incrementAndGet();
            return "https://api.example.com/v2";
        });

        String endpoint() {
            return cachedEndpoint.get();
        }

        int initCount() {
            return initCount.get();
        }
    }

    public static void main(String[] args) {
        ConfigService service = new ConfigService();
        System.out.println("First call:  " + service.endpoint());
        System.out.println("Second call: " + service.endpoint());
        System.out.println("Init count:  " + service.initCount());
    }
}
