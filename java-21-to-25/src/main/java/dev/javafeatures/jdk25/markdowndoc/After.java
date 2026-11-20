package dev.javafeatures.jdk25.markdowndoc;

// Nachher (Java 23, JEP 467): Markdown Documentation Comments mit `///` ersetzen
// HTML-Tags und {@code}/{@link}-Boilerplate durch direkt lesbares Markdown.

/// Berechnet Rabatte fuer Bestellungen anhand fester Schwellenwerte.
///
/// Folgende Regeln gelten fuer den Rabatt:
/// - Ab `100.0` EUR: `10%` Rabatt
/// - Darunter: `0%` Rabatt
///
/// Beispielaufruf:
/// ```java
/// double finalPrice = After.applyDiscount(150.0);
/// ```
///
/// Siehe auch [Math#round(double)].
public class After {

    /// Wendet den geltenden Rabatt auf den uebergebenen Betrag an.
    ///
    /// @param amount Ursprungsbetrag in EUR (darf nicht negativ sein)
    /// @return rabattierter Endbetrag
    /// @throws IllegalArgumentException falls `amount < 0`
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
