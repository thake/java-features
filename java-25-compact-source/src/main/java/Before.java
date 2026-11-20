import java.util.List;

// Vorher (Java 21): Selbst fuer das einfachste Skript oder Prototyp-Programm
// waren explizite Imports, eine public class, `public static void main(String[] args)`
// und `System.out.println(...)` Pflicht.
public class Before {

    public static void main(String[] args) {
        List<String> versions = List.of("Java 17", "Java 21", "Java 25");
        for (String version : versions) {
            System.out.println("Hello from " + version + "!");
        }
    }
}
