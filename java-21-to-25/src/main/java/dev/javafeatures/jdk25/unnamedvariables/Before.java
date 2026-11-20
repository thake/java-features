package dev.javafeatures.jdk25.unnamedvariables;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Vorher (Java 21): Ungenutzte Variablen und Record-Komponenten mussten trotzdem
// explizit benannt werden (z. B. ignored, unused, city) — das erzeugt Rauschen und IDE-Warnungen.
public class Before {

    record Address(String city, String zipCode) {}
    record Customer(String name, Address address) {}

    static String extractCustomerName(Object obj) {
        // Obwohl address nicht benoetigt wird, muss die Komponente einen Namen erhalten
        if (obj instanceof Customer(String name, Address unusedAddress)) {
            return name;
        }
        return "unknown";
    }

    public static void main(String[] args) {
        // 1. Ungenutzte Exception-Variable im catch-Block
        int parsed;
        try {
            parsed = Integer.parseInt("not-a-number");
        } catch (NumberFormatException ignored) {
            parsed = -1;
        }

        // 2. Ungenutzter Lambda-Parameter (key wird fuer den Default-Wert nicht gebraucht)
        Map<String, List<String>> groups = new HashMap<>();
        groups.computeIfAbsent("admins", ignoredKey -> List.of("alice"));

        // 3. Ungenutzte Schleifenvariable (wir zaehlen nur Elemente)
        int count = 0;
        for (String ignoredItem : List.of("a", "b", "c")) {
            count++;
        }

        String name = extractCustomerName(new Customer("Alice", new Address("Berlin", "10115")));
        System.out.println("parsed=" + parsed + ", admins=" + groups.get("admins")
                + ", count=" + count + ", name=" + name);
    }
}
