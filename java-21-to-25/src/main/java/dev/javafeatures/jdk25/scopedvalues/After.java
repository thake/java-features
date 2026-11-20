package dev.javafeatures.jdk25.scopedvalues;

// Nachher (Java 25, JEP 506): Scoped Values sind unveraenderlich (immutable),
// automatisch an einen lexikalischen Scope gebunden und fuer Virtual Threads optimiert.
public class After {

    private static final ScopedValue<String> CURRENT_USER = ScopedValue.newInstance();

    static String handleRequest(String user) {
        // Bindung gilt exakt fuer die Dauer von call(...) — kein manuelles remove() noetig
        return ScopedValue.where(CURRENT_USER, user)
                .call(() -> auditLog("create-order"));
    }

    static String auditLog(String action) {
        return "[" + CURRENT_USER.get() + "] performed " + action;
    }

    public static void main(String[] args) {
        System.out.println(handleRequest("alice"));
        System.out.println("Outside scope bound: " + CURRENT_USER.isBound());
    }
}
