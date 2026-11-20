package dev.javafeatures.jdk25.preview.structuredconcurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// Vorher (Java 21): Mit ExecutorService und Future laufen parallele Subtasks
// unstrukturiert. Schlaegt ein Subtask fehl, muss man Geschwister-Tasks manuell
// abbrechen — sonst laufen verwaiste Tasks (Thread Leaks) im Hintergrund weiter.
public class Before {

    record Dashboard(String userProfile, int openOrders) {}

    static String fetchUserProfile(String userId) throws InterruptedException {
        Thread.sleep(30);
        return "User(" + userId + ")";
    }

    static int fetchOpenOrders(String userId) throws InterruptedException {
        Thread.sleep(40);
        return 3;
    }

    static Dashboard loadDashboard(String userId) throws Exception {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<String> profileFuture = executor.submit(() -> fetchUserProfile(userId));
            Future<Integer> ordersFuture = executor.submit(() -> fetchOpenOrders(userId));

            try {
                // Wenn profileFuture fehlschlaegt, laeuft ordersFuture ohne manuelles cancel() weiter
                String profile = profileFuture.get();
                int orders = ordersFuture.get();
                return new Dashboard(profile, orders);
            } catch (ExecutionException | InterruptedException e) {
                profileFuture.cancel(true);
                ordersFuture.cancel(true);
                throw e;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Dashboard: " + loadDashboard("alice"));
    }
}
