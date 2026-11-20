package dev.javafeatures.jdk21.recordpatterns;

// Vorher (Java 17): Nach einem instanceof-Check auf einen Record muessen alle
// verschachtelten Komponenten ueber explizite Accessor-Aufrufe extrahiert werden.
public class Before {

    record Address(String city, String country) {}
    record Customer(String name, Address address) {}
    record Order(String orderId, Customer customer, double total) {}

    static String formatShippingLabel(Object payload) {
        if (payload instanceof Order order) {
            // Manuelles Auspacken ueber mehrere Accessor-Ebenen hinweg
            Customer customer = order.customer();
            if (customer != null && customer.address() != null) {
                Address address = customer.address();
                return order.orderId() + " -> " + customer.name()
                        + " (" + address.city() + ", " + address.country() + ") [" + order.total() + " EUR]";
            }
        }
        return "Unknown payload";
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
