package dev.javafeatures.jdk21.recordpatterns;

// Nachher (Java 21, JEP 440): Verschachtelte Record Patterns dekonstruieren
// Records direkt in instanceof oder switch — ohne manuelle Accessor-Ketten.
public class After {

    record Address(String city, String country) {}
    record Customer(String name, Address address) {}
    record Order(String orderId, Customer customer, double total) {}

    static String formatShippingLabel(Object payload) {
        // Direkte Dekonstruktion aller verschachtelten Record-Komponenten
        // Hinweis: Ein verschachteltes Record Pattern matcht automatisch nur bei non-null
        return switch (payload) {
            case Order(var orderId, Customer(var name, Address(var city, var country)), var total) ->
                    orderId + " -> " + name + " (" + city + ", " + country + ") [" + total + " EUR]";
            default -> "Unknown payload";
        };
    }

    public static void main(String[] args) {
        Order order = new Order(
                "ORD-1001",
                new Customer("Alice", new Address("Berlin", "DE")),
                129.50
        );
        System.out.println(formatShippingLabel(order));
        System.out.println(formatShippingLabel("invalid"));
    }
}
