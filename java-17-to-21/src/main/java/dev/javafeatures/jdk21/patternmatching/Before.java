package dev.javafeatures.jdk21.patternmatching;

import java.util.List;

// Vorher (Java 17): Ohne Pattern Matching for switch (JEP 441) sind
// if/else-if-Ketten mit instanceof, manuellem null-Check und verschachtelten
// Bedingungen notwendig. Der Compiler prueft dabei keine Exhaustiveness.
public class Before {

    sealed interface Payment permits CardPayment, BankTransfer, CashPayment {}

    record CardPayment(String brand, double amount) implements Payment {}
    record BankTransfer(String iban, double amount) implements Payment {}
    record CashPayment(double amount) implements Payment {}

    static String describePayment(Payment payment) {
        // null muss explizit vor den Typpruefungen abgefangen werden
        if (payment == null) {
            return "No payment provided";
        }
        if (payment instanceof CardPayment card) {
            // Zusaetzliche Bedingungen (Guards) erfordern verschachtelte if-Bloecke
            if (card.amount() > 1000.0) {
                return "High-value card (" + card.brand() + "): " + card.amount();
            }
            return "Card (" + card.brand() + "): " + card.amount();
        } else if (payment instanceof BankTransfer transfer) {
            return "Bank transfer (" + transfer.iban() + "): " + transfer.amount();
        } else if (payment instanceof CashPayment cash) {
            return "Cash: " + cash.amount();
        }
        // Obwohl Payment ein sealed interface ist, erzwingt if/else einen Fallback
        throw new IllegalStateException("Unexpected payment: " + payment);
    }

    public static void main(String[] args) {
        List<Payment> payments = List.of(
                new CardPayment("VISA", 49.90),
                new CardPayment("AMEX", 2500.00),
                new BankTransfer("DE89370400440532013000", 420.00),
                new CashPayment(15.00)
        );

        payments.forEach(p -> System.out.println(describePayment(p)));
        System.out.println(describePayment(null));
    }
}
