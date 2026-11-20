package dev.javafeatures.jdk25.streamgatherers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

// Vorher (Java 21): Die Stream-API hatte keinen Erweiterungspunkt fuer eigene
// Intermediate Operations. Windowing, Running Totals oder Distinct-by-Key erforderten
// Index-Workarounds oder mutable Hilfsstrukturen ausserhalb der Stream-Pipeline.
public class Before {

    record Event(String category, String message) {}

    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);

        // 1. Fixed Windows der Groesse 2 ueber Index-Arithmetik
        List<List<Integer>> fixedWindows = IntStream.iterate(0, i -> i < numbers.size(), i -> i + 2)
                .mapToObj(i -> numbers.subList(i, Math.min(i + 2, numbers.size())))
                .toList();

        // 2. Sliding Windows der Groesse 3 ueber Index-Range
        List<List<Integer>> slidingWindows = IntStream.rangeClosed(0, numbers.size() - 3)
                .mapToObj(i -> numbers.subList(i, i + 3))
                .toList();

        // 3. Running Sum (Scan) ueber imperative Schleife
        List<Integer> runningSums = new ArrayList<>();
        int acc = 0;
        for (int n : numbers) {
            acc += n;
            runningSums.add(acc);
        }

        // 4. Distinct-by-Key ueber externes stateful Set im filter()-Lambda (Anti-Pattern)
        List<Event> events = List.of(
                new Event("AUTH", "login"),
                new Event("AUTH", "refresh"),
                new Event("BILLING", "invoice"),
                new Event("BILLING", "paid")
        );
        Set<String> seenCategories = new HashSet<>();
        List<Event> firstPerCategory = events.stream()
                .filter(e -> seenCategories.add(e.category()))
                .toList();

        System.out.println("Fixed windows:   " + fixedWindows);
        System.out.println("Sliding windows: " + slidingWindows);
        System.out.println("Running sums:    " + runningSums);
        System.out.println("Distinct by key: " + firstPerCategory);
    }
}
