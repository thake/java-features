package dev.javafeatures.jdk21.libraryimprovements;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

// Vorher (Java 17): Alltaegliche Aufgaben in der Standard-Library erforderten
// haeufig manuelles Boilerplate (Executor-Shutdown, verschachtelte Math-Aufrufe,
// Schleifen fuer StringBuilder oder schwer lesbare Lookaround-Regex).
public class Before {

    public static void main(String[] args) throws Exception {
        // 1. Wertebegrenzung (Clamping) ueber verschachteltes Math.max / Math.min
        int rawPercent = 135;
        int clamped = Math.max(0, Math.min(100, rawPercent));

        // 2. Zeichenwiederholung im StringBuilder per Schleife
        StringBuilder divider = new StringBuilder("Header ");
        for (int i = 0; i < 5; i++) {
            divider.append("-");
        }

        // 3. String splitten UND Delimiter behalten ueber fehleranfaellige Lookaround-Regex
        String expression = "10+20-5";
        String[] tokens = expression.split("(?=[+-])|(?<=[+-])");

        // 4. ExecutorService war nicht AutoCloseable: manuelles shutdown() + awaitTermination() im finally-Block
        ExecutorService executor = Executors.newSingleThreadExecutor();
        String taskResult;
        try {
            Future<String> future = executor.submit(() -> "completed");
            taskResult = future.get();
        } finally {
            executor.shutdown();
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        }

        System.out.println("Clamped: " + clamped);
        System.out.println("Divider: " + divider);
        System.out.println("Tokens:  " + Arrays.toString(tokens));
        System.out.println("Task:    " + taskResult);
    }
}
