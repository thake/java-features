package dev.javafeatures.jdk21.sequencedcollections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

// Vorher (Java 17): Jeder geordnete Collection-Typ hatte unterschiedliche und teils
// umstaendliche Wege, um auf das erste/letzte Element oder die umgekehrte Reihenfolge zuzugreifen.
public class Before {

    public static void main(String[] args) {
        // 1. List: Zugriff ueber Index-Arithmetik; Reverse erfordert Kopie oder ListIterator
        List<String> steps = new ArrayList<>(List.of("build", "test", "deploy"));
        String firstStep = steps.get(0);
        String lastStep = steps.get(steps.size() - 1);
        List<String> reversedSteps = new ArrayList<>(steps);
        Collections.reverse(reversedSteps);

        // 2. LinkedHashSet: Kein direkter Zugriff auf das letzte Element — volle Iteration noetig
        LinkedHashSet<String> tags = new LinkedHashSet<>(List.of("v1", "v2", "v3"));
        String firstTag = tags.iterator().next();
        String lastTag = null;
        for (String tag : tags) {
            lastTag = tag;
        }

        // 3. LinkedHashMap: Kein direkter Zugriff auf firstEntry/lastEntry
        LinkedHashMap<String, Integer> scores = new LinkedHashMap<>();
        scores.put("alpha", 10);
        scores.put("beta", 20);
        scores.put("gamma", 30);
        Map.Entry<String, Integer> firstScore = scores.entrySet().iterator().next();
        Map.Entry<String, Integer> lastScore = null;
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            lastScore = entry;
        }

        System.out.println("List: first=" + firstStep + ", last=" + lastStep + ", reversed=" + reversedSteps);
        System.out.println("Set:  first=" + firstTag + ", last=" + lastTag);
        System.out.println("Map:  first=" + firstScore + ", last=" + lastScore);
    }
}
