package dev.javafeatures.jdk25.virtualthreadspinfree;

import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

// Vorher (Java 21): Wenn ein Virtual Thread innerhalb eines synchronized-Blocks
// blockierte (z. B. I/O oder Thread.sleep), wurde der Carrier Thread gepinnt (Pinning).
// Als Workaround musste man selbst einfache kritische Abschnitte auf ReentrantLock umbauen.
public class Before {

    static class RateLimitedCounter {
        private final ReentrantLock lock = new ReentrantLock();
        private int count = 0;

        int incrementSlowly() throws InterruptedException {
            // ReentrantLock + try/finally Boilerplate, nur um Carrier-Thread-Pinning zu vermeiden
            lock.lock();
            try {
                Thread.sleep(5);
                return ++count;
            } finally {
                lock.unlock();
            }
        }

        int current() {
            lock.lock();
            try {
                return count;
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        RateLimitedCounter counter = new RateLimitedCounter();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 20; i++) {
                executor.submit(() -> {
                    counter.incrementSlowly();
                    return null;
                });
            }
        }
        System.out.println("Final count: " + counter.current());
    }
}
