package dev.javafeatures.jdk25.markdowndoc;

// Vorher (Java 21): Klassische JavaDoc-Kommentare nutzten HTML-Tags (<p>, <ul>, <li>)
// sowie Inline-Tags wie {@code ...} und {@link ...}, was im Quellcode schwer lesbar war.

/**
 * Berechnet Rabatte fuer Bestellungen anhand fester Schwellenwerte.
 *
 * <p>Folgende Regeln gelten fuer den Rabatt:</p>
 * <ul>
 *   <li>Ab {@code 100.0} EUR: {@code 10%} Rabatt</li>
 *   <li>Darunter: {@code 0%} Rabatt</li>
 * </ul>
 *
 * <p>Beispielaufruf:</p>
 * <pre>{@code
 * double finalPrice = Before.applyDiscount(150.0);
 * }</pre>
 *
 * @see Math#round(double)
 */
public class Before {

    /**
     * Wendet den geltenden Rabatt auf den uebergebenen Betrag an.
     *
     * @param amount Ursprungsbetrag in EUR (darf nicht negativ sein)
     * @return rabattierter Endbetrag
     * @throws IllegalArgumentException falls {@code amount < 0}
     */
    public static double applyDiscount(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must not be negative");
        }
        return amount >= 100.0 ? amount * 0.9 : amount;
    }

    public static void main(String[] args) {
        System.out.println("Price for 80.0:  " + applyDiscount(80.0));
        System.out.println("Price for 150.0: " + applyDiscount(150.0));
    }
}
