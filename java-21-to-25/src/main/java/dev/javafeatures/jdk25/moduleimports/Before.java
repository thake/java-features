package dev.javafeatures.jdk25.moduleimports;

// Vorher (Java 21): Selbst fuer Standardtypen aus java.base waren viele einzelne
// Package- oder Klassen-Imports noetig.
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Before {

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
