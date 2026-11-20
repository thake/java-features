package dev.javafeatures.jdk21.sequencedcollections;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.SequencedCollection;
import java.util.SequencedMap;
import java.util.SequencedSet;

// Nachher (Java 21, JEP 431): SequencedCollection, SequencedSet und SequencedMap
// bieten eine einheitliche API fuer erstes/letztes Element und Reverse-Views.
public class After {

    public static void main(String[] args) {
        // 1. List implementiert SequencedCollection: direktes getFirst(), getLast() und reversed() View
        SequencedCollection<String> steps = new ArrayList<>(List.of("build", "test", "deploy"));
        String firstStep = steps.getFirst();
        String lastStep = steps.getLast();
        SequencedCollection<String> reversedSteps = steps.reversed();

        // 2. LinkedHashSet implementiert SequencedSet: O(1)-Zugriff auf erstes und letztes Element
        SequencedSet<String> tags = new LinkedHashSet<>(List.of("v1", "v2", "v3"));
        String firstTag = tags.getFirst();
        String lastTag = tags.getLast();

        // 3. LinkedHashMap implementiert SequencedMap: direkter Zugriff auf firstEntry() und lastEntry()
        SequencedMap<String, Integer> scores = new LinkedHashMap<>();
        scores.put("alpha", 10);
        scores.put("beta", 20);
        scores.put("gamma", 30);
        var firstScore = scores.firstEntry();
        var lastScore = scores.lastEntry();

        System.out.println("List: first=" + firstStep + ", last=" + lastStep + ", reversed=" + reversedSteps);
        System.out.println("Set:  first=" + firstTag + ", last=" + lastTag);
        System.out.println("Map:  first=" + firstScore + ", last=" + lastScore);
    }
}
