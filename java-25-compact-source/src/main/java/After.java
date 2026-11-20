// Nachher (Java 25, JEP 512): Compact Source Files benoetigen keine class-Deklaration,
// importieren java.base automatisch on-demand, erlauben eine kompakte Instance Main Method
// (`void main()`) und bieten `java.lang.IO` fuer einfache Konsolen-Ausgaben.
// Direkt startbar via: java java-25-compact-source/src/main/java/After.java

void main() {
    List<String> versions = List.of("Java 17", "Java 21", "Java 25");
    for (String version : versions) {
        IO.println("Hello from " + version + "!");
    }
}
