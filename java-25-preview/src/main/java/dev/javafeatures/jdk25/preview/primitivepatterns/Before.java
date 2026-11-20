package dev.javafeatures.jdk25.preview.primitivepatterns;

// Vorher (Java 21): instanceof und switch unterstuetzten keine primitiven Typen wie
// long, double oder boolean fuer Pattern Matching. Verlustfreie Narrowing-Pruefungen
// erforderten fehleranfaellige manuelle Bereichs-Checks.
public class Before {

    static String classifyStatusCode(long code) {
        // switch auf long war nicht erlaubt -> if/else-Kette noetig
        if (code == 200L) {
            return "OK";
        } else if (code >= 400L && code < 500L) {
            return "Client error (" + code + ")";
        } else if (code >= 500L && code < 600L) {
            return "Server error (" + code + ")";
        }
        return "Other (" + code + ")";
    }

    static String checkByteFit(int value) {
        // Manuelle Bereichspruefung vor dem Narrowing Cast auf byte
        if (value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE) {
            byte exact = (byte) value;
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
