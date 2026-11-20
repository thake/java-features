package dev.javafeatures.jdk25.moduleimports;

// Nachher (Java 25, JEP 511): Eine einzige Module Import Declaration importiert
// on-demand alle exportierten Packages eines Moduls (hier java.base: util, time, math, stream, ...).
// Bei Namenskonflikten zwischen mehreren Modulen (z. B. java.util.Date vs. java.sql.Date)
// kann weiterhin ein expliziter Single-Type-Import zur Aufloesung ergaenzt werden.
import module java.base;

public class After {

    record Invoice(String id, LocalDate date, BigDecimal amount) {}

    public static void main(String[] args) {
        List<Invoice> invoices = List.of(
                new Invoice("INV-1", LocalDate.of(2025, 9, 16), new BigDecimal("199.90")),
                new Invoice("INV-2", LocalDate.of(2025, 9, 17), new BigDecimal("49.50"))
        );

        Map<String, Invoice> byId = invoices.stream()
                .collect(Collectors.toMap(Invoice::id, Function.identity()));

        BigDecimal total = invoices.stream()
                .map(Invoice::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("Invoices: " + byId.keySet() + ", total=" + total);
    }
}
