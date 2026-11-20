package dev.javafeatures.jdk21.virtualthreads;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// Nachher (Java 21, JEP 444): Virtual Threads sind leichtgewichtige, von der JVM verwaltete Threads.
// Jeder blockierende Task bekommt einen eigenen Virtual Thread — ohne Thread-Pool-Flaschenhals.
public class After {

    private static final int TASK_COUNT = 200;

    static int fetchSimulatedRemoteValue(int id) throws InterruptedException {
        // Beim Blockieren gibt der Virtual Thread seinen Carrier Thread sofort frei
        Thread.sleep(20);
        return id;
    }

    public static void main(String[] args) throws Exception {
        Instant start = Instant.now();

        // Kein Pool-Limit noetig: ein neuer Virtual Thread pro Task + AutoCloseable via try-with-resources
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
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
            System.out.println("Virtual Threads      -> tasks=" + TASK_COUNT + ", sum=" + sum + ", elapsedMs=" + elapsedMs);
        }
    }
}
