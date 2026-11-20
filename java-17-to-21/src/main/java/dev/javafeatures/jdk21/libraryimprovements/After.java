package dev.javafeatures.jdk21.libraryimprovements;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// Nachher (Java 19-21): Kleine, aber extrem praktische Core-Library-Erweiterungen
// reduzieren Boilerplate bei Math, Strings und Resource Management.
public class After {

    public static void main(String[] args) throws Exception {
        // 1. Math.clamp (Java 21): klare Semantik inkl. Validierung von min <= max
        int rawPercent = 135;
        int clamped = Math.clamp(rawPercent, 0, 100);

        // 2. StringBuilder.repeat (Java 21): direkte Wiederholung ohne eigene Schleife
        StringBuilder divider = new StringBuilder("Header ").repeat("-", 5);

        // 3. String.splitWithDelimiters (Java 21): behaelt Delimiter lesbar ohne Lookaround-Regex
        String expression = "10+20-5";
        String[] tokens = expression.splitWithDelimiters("[+-]", -1);

        // 4. ExecutorService ist seit Java 19 AutoCloseable: sauberes try-with-resources
        String taskResult;
        try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
            Future<String> future = executor.submit(() -> "completed");
            taskResult = future.get();
        }

        System.out.println("Clamped: " + clamped);
        System.out.println("Divider: " + divider);
        System.out.println("Tokens:  " + Arrays.toString(tokens));
        System.out.println("Task:    " + taskResult);
    }
}
