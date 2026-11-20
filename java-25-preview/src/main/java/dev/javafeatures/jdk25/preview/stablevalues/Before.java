package dev.javafeatures.jdk25.preview.stablevalues;

import java.util.concurrent.atomic.AtomicInteger;

// Vorher (Java 21): Thread-safe Lazy Initialization erforderte Double-Checked Locking
// mit volatile und synchronized. Zusaetzlich verhindert ein non-final Feld, dass der
// JIT-Compiler den initialisierten Wert per Constant Folding optimiert.
public class Before {

    static class ConfigService {
        private final AtomicInteger initCount = new AtomicInteger();
        private volatile String cachedEndpoint;

        String endpoint() {
            // Double-Checked Locking Boilerplate fuer genau einmalige Initialisierung
            String value = cachedEndpoint;
            if (value == null) {
                synchronized (this) {
                    value = cachedEndpoint;
                    if (value == null) {
                        initCount.incrementAndGet();
                        value = "https://api.example.com/v2";
                        cachedEndpoint = value;
                    }
                }
            }
            return value;
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
