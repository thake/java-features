package dev.javafeatures.jdk25.flexibleconstructors;

// Nachher (Java 25, JEP 513): Flexible Constructor Bodies erlauben Statements
// (wie Argument-Validierung, Vorberechnung und Initialisierung eigener Felder)
// direkt im Konstruktor-Prolog VOR dem Aufruf von super(...) bzw. this(...).
public class After {

    static class Account {
        private final String normalizedId;

        Account(String normalizedId) {
            this.normalizedId = normalizedId;
        }

        String id() {
            return normalizedId;
        }
    }

    public static class SavingsAccount extends Account {
        private final int interestRateBps;

        public SavingsAccount(String rawId, int interestRateBps) {
            // Fail-Fast-Validierung und Normalisierung direkt vor super(...), ohne static Helper
            if (rawId == null || rawId.isBlank()) {
                throw new IllegalArgumentException("Account ID must not be blank");
            }
            if (interestRateBps < 0) {
                throw new IllegalArgumentException("Interest rate must not be negative");
            }
            String normalized = rawId.trim().toUpperCase();

            // Sogar eigene Felder koennen bereits vor super(...) initialisiert werden
            this.interestRateBps = interestRateBps;
            super(normalized);
        }

        @Override
        public String toString() {
            return "SavingsAccount[id=" + id() + ", interestRateBps=" + interestRateBps + "]";
        }
    }

    public static void main(String[] args) {
        SavingsAccount account = new SavingsAccount("  de-42  ", 350);
        System.out.println(account);
    }
}
