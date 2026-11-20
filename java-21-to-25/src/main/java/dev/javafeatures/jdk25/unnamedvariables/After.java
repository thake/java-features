package dev.javafeatures.jdk25.unnamedvariables;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Nachher (Java 22, JEP 456): Der Underscore (_) markiert absichtlich ungenutzte
// Variablen und Unnamed Patterns eindeutig fuer Mensch, Compiler und Linter.
public class After {

    record Address(String city, String zipCode) {}
    record Customer(String name, Address address) {}

    static String extractCustomerName(Object obj) {
        // Unnamed Pattern (_) fuer die nicht benoetigte Record-Komponente
        if (obj instanceof Customer(String name, _)) {
            return name;
        }
        return "unknown";
    }

    public static void main(String[] args) {
        // 1. Unnamed Variable im catch-Block
        int parsed;
        try {
            parsed = Integer.parseInt("not-a-number");
        } catch (NumberFormatException _) {
            parsed = -1;
        }

        // 2. Unnamed Parameter im Lambda
        Map<String, List<String>> groups = new HashMap<>();
        groups.computeIfAbsent("admins", _ -> List.of("alice"));

        // 3. Unnamed Variable in der Enhanced-for-Schleife
        int count = 0;
        for (String _ : List.of("a", "b", "c")) {
            count++;
        }

        String name = extractCustomerName(new Customer("Alice", new Address("Berlin", "10115")));
        System.out.println("parsed=" + parsed + ", admins=" + groups.get("admins")
                + ", count=" + count + ", name=" + name);
    }
}
