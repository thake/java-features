package dev.javafeatures.jdk25.preview.structuredconcurrency;

import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;

// Nachher (Java 25 Preview, JEP 505): Structured Concurrency bindet die Lebensdauer
// aller parallelen Subtasks strikt an einen try-with-resources-Block.
// Schlaegt ein Subtask fehl, bricht der Scope alle anderen automatisch ab (kein Thread Leak).
public class After {

    record Dashboard(String userProfile, int openOrders) {}

    static String fetchUserProfile(String userId) throws InterruptedException {
        Thread.sleep(30);
        return "User(" + userId + ")";
    }

    static int fetchOpenOrders(String userId) throws InterruptedException {
        Thread.sleep(40);
        return 3;
    }

    static Dashboard loadDashboard(String userId) throws InterruptedException {
        // Default-Policy von open(): alle Subtasks muessen erfolgreich sein, sonst sofortige Cancellation
        try (var scope = StructuredTaskScope.open()) {
            Subtask<String> profileTask = scope.fork(() -> fetchUserProfile(userId));
            Subtask<Integer> ordersTask = scope.fork(() -> fetchOpenOrders(userId));

            // Wartet auf alle Subtasks und wirft FailedException, falls mindestens einer fehlschlaegt
            scope.join();

            return new Dashboard(profileTask.get(), ordersTask.get());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Dashboard: " + loadDashboard("alice"));
    }
}
