package dev.javafeatures.jdk25.flexibleconstructors;

// Vorher (Java 21): super(...) bzw. this(...) musste zwingend das allererste Statement
// im Konstruktor sein. Validierung oder Vorberechnung vor super(...) erzwang
// umstaendliche private static-Hilfsmethoden.
public class Before {

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
            // Validierung und Normalisierung vor super(...) waren nur ueber statische Helper moeglich
            super(validateAndNormalizeId(rawId));
            // Dieser Check konnte erst NACH dem super()-Aufruf stattfinden
            if (interestRateBps < 0) {
                throw new IllegalArgumentException("Interest rate must not be negative");
            }
            this.interestRateBps = interestRateBps;
        }

        private static String validateAndNormalizeId(String rawId) {
            if (rawId == null || rawId.isBlank()) {
                throw new IllegalArgumentException("Account ID must not be blank");
            }
            return rawId.trim().toUpperCase();
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
