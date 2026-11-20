package dev.javafeatures.jdk25.scopedvalues;

// Vorher (Java 21): Kontextdaten pro Request (z. B. Tenant-ID oder User) wurden
// ueber ThreadLocal weitergegeben. Nachteile: mutable, unbegrenzte Lebensdauer und
// hohes Memory-Leak-Risiko bei vergessener remove()-Bereinigung in Thread Pools.
public class Before {

    private static final ThreadLocal<String> CURRENT_USER = new ThreadLocal<>();

    static String handleRequest(String user) {
        // Manuelles Setzen und zwingendes Aufraeumen im finally-Block
        CURRENT_USER.set(user);
        try {
            return auditLog("create-order");
        } finally {
            CURRENT_USER.remove();
        }
    }

    static String auditLog(String action) {
        return "[" + CURRENT_USER.get() + "] performed " + action;
    }

    public static void main(String[] args) {
        System.out.println(handleRequest("alice"));
        System.out.println("Outside scope bound: " + (CURRENT_USER.get() != null));
    }
}
