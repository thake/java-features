package dev.javafeatures.jdk25.streamgatherers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Gatherer;
import java.util.stream.Gatherers;

// Nachher (Java 24, JEP 485): Stream::gather fuehrt Built-in Gatherers (windowFixed,
// windowSliding, scan, fold) sowie eine saubere API fuer eigene Intermediate Operations ein.
public class After {

    record Event(String category, String message) {}

    // Eigener Gatherer fuer Distinct-by-Key: kapselt den Zustand sauber innerhalb der Operation
    static <T, K> Gatherer<T, Set<K>, T> distinctBy(Function<T, K> keyExtractor) {
        return Gatherer.ofSequential(
                HashSet::new,
                (seen, element, downstream) -> {
                    if (seen.add(keyExtractor.apply(element))) {
                        return downstream.push(element);
                    }
                    return true;
                }
        );
    }

    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);

        // 1. Built-in Fixed Windows
        List<List<Integer>> fixedWindows = numbers.stream()
                .gather(Gatherers.windowFixed(2))
                .toList();

        // 2. Built-in Sliding Windows
        List<List<Integer>> slidingWindows = numbers.stream()
                .gather(Gatherers.windowSliding(3))
                .toList();

        // 3. Built-in Scan fuer Running Sums
        List<Integer> runningSums = numbers.stream()
                .gather(Gatherers.scan(() -> 0, Integer::sum))
                .toList();

        // 4. Custom Gatherer fuer Distinct-by-Key — vollstaendig wiederverwendbar in jeder Pipeline
        List<Event> events = List.of(
                new Event("AUTH", "login"),
                new Event("AUTH", "refresh"),
                new Event("BILLING", "invoice"),
                new Event("BILLING", "paid")
        );
        List<Event> firstPerCategory = events.stream()
                .gather(distinctBy(Event::category))
                .toList();

        System.out.println("Fixed windows:   " + fixedWindows);
        System.out.println("Sliding windows: " + slidingWindows);
        System.out.println("Running sums:    " + runningSums);
        System.out.println("Distinct by key: " + firstPerCategory);
    }
}
