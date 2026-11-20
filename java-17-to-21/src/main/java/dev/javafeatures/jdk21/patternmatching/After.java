package dev.javafeatures.jdk21.patternmatching;

import java.util.List;

// Nachher (Java 21, JEP 441): Pattern Matching for switch kombiniert Type Patterns,
// Guarded Patterns (when), explizite null-Behandlung und Compiler-gepruefte
// Exhaustiveness bei einem sealed interface.
public class After {

    sealed interface Payment permits CardPayment, BankTransfer, CashPayment {}

    record CardPayment(String brand, double amount) implements Payment {}
    record BankTransfer(String iban, double amount) implements Payment {}
    record CashPayment(double amount) implements Payment {}

    static String describePayment(Payment payment) {
        // Kein default-Zweig noetig: Der Compiler garantiert Exhaustiveness
        return switch (payment) {
            case null -> "No payment provided";
            case CardPayment card when card.amount() > 1000.0 ->
                    "High-value card (" + card.brand() + "): " + card.amount();
            case CardPayment card ->
                    "Card (" + card.brand() + "): " + card.amount();
            case BankTransfer transfer ->
                    "Bank transfer (" + transfer.iban() + "): " + transfer.amount();
            case CashPayment cash ->
                    "Cash: " + cash.amount();
        };
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
