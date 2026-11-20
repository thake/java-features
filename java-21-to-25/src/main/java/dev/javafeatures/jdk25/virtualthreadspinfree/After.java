package dev.javafeatures.jdk25.virtualthreadspinfree;

import java.util.concurrent.Executors;

// Nachher (Java 24, JEP 491): Virtual Threads koennen jetzt direkt innerhalb von
// synchronized-Methoden oder -Bloecken unmounted werden — ganz ohne Pinning des Carrier Threads.
// Der ReentrantLock-Workaround fuer Virtual Threads ist damit nicht mehr noetig.
public class After {

    static class RateLimitedCounter {
        private int count = 0;

        // Idiomatisches synchronized: kein Pinning mehr bei blockierenden Aufrufen in Virtual Threads
        synchronized int incrementSlowly() throws InterruptedException {
            Thread.sleep(5);
            return ++count;
        }

        synchronized int current() {
            return count;
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
