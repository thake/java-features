package dev.javafeatures.jdk21.virtualthreads;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// Vorher (Java 17): Klassische Platform Threads sind teuer (1:1 auf OS-Threads abgebildet).
// Daher begrenzt man sie ueber einen festen Thread Pool — blockierende I/O-Tasks muessen warten.
public class Before {

    private static final int TASK_COUNT = 200;

    static int fetchSimulatedRemoteValue(int id) throws InterruptedException {
        // Simuliert einen blockierenden I/O-Aufruf (z. B. HTTP-Request oder DB-Query)
        Thread.sleep(20);
        return id;
    }

    public static void main(String[] args) throws Exception {
        Instant start = Instant.now();

        // Begrenzter Platform-Thread-Pool: maximal 16 Tasks gleichzeitig aktiv
        ExecutorService executor = Executors.newFixedThreadPool(16);
        try {
            List<Callable<Integer>> tasks = new ArrayList<>();
            for (int i = 0; i < TASK_COUNT; i++) {
                int taskId = i;
                tasks.add(() -> fetchSimulatedRemoteValue(taskId));
            }

            List<Future<Integer>> futures = executor.invokeAll(tasks);
            int sum = 0;
            for (Future<Integer> future : futures) {
                sum += future.get();
            }

            long elapsedMs = Duration.between(start, Instant.now()).toMillis();
            System.out.println("Platform Thread Pool -> tasks=" + TASK_COUNT + ", sum=" + sum + ", elapsedMs=" + elapsedMs);
        } finally {
            executor.shutdown();
        }
    }
}
