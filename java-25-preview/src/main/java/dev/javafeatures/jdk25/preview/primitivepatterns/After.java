package dev.javafeatures.jdk25.preview.primitivepatterns;

// Nachher (Java 25 Preview, JEP 507): Primitive Types funktionieren direkt in
// switch-Expressions (inkl. long, double, boolean) und in instanceof fuer
// garantiert verlustfreie (exact) Typkonvertierungen.
public class After {

    static String classifyStatusCode(long code) {
        // Direktes switch auf long mit Konstanten und Guarded Primitive Patterns
        return switch (code) {
            case 200L -> "OK";
            case long c when c >= 400L && c < 500L -> "Client error (" + c + ")";
            case long c when c >= 500L && c < 600L -> "Server error (" + c + ")";
            case long c -> "Other (" + c + ")";
        };
    }

    static String checkByteFit(int value) {
        // instanceof prueft automatisch, ob value verlustfrei in ein byte passt
        if (value instanceof byte exact) {
            return value + " fits into byte: " + exact;
        }
        return value + " does not fit into byte";
    }

    public static void main(String[] args) {
        System.out.println(classifyStatusCode(200L));
        System.out.println(classifyStatusCode(404L));
        System.out.println(classifyStatusCode(503L));
        System.out.println(checkByteFit(42));
        System.out.println(checkByteFit(300));
    }
}
