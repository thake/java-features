# Die wichtigsten Features: Java 17 → Java 21 LTS

Diese Übersicht fasst die praxisrelevantesten Neuerungen auf dem Weg von **Java 17 LTS** zu **Java 21 LTS** zusammen und verlinkt direkt auf die ausführbaren **Before/After**-Beispiele im Modul [`java-17-to-21`](../java-17-to-21/) (`--release 21`).

---

## Schnellübersicht

| Feature | JEP | JDK | Before | After |
| :--- | :---: | :---: | :--- | :--- |
| **Pattern Matching for `switch`** | JEP 441 | 21 | [Before.java](../java-17-to-21/src/main/java/dev/javafeatures/jdk21/patternmatching/Before.java) | [After.java](../java-17-to-21/src/main/java/dev/javafeatures/jdk21/patternmatching/After.java) |
| **Record Patterns** | JEP 440 | 21 | [Before.java](../java-17-to-21/src/main/java/dev/javafeatures/jdk21/recordpatterns/Before.java) | [After.java](../java-17-to-21/src/main/java/dev/javafeatures/jdk21/recordpatterns/After.java) |
| **Sequenced Collections** | JEP 431 | 21 | [Before.java](../java-17-to-21/src/main/java/dev/javafeatures/jdk21/sequencedcollections/Before.java) | [After.java](../java-17-to-21/src/main/java/dev/javafeatures/jdk21/sequencedcollections/After.java) |
| **Virtual Threads** | JEP 444 | 21 | [Before.java](../java-17-to-21/src/main/java/dev/javafeatures/jdk21/virtualthreads/Before.java) | [After.java](../java-17-to-21/src/main/java/dev/javafeatures/jdk21/virtualthreads/After.java) |
| **Core-Library-Verbesserungen** (`AutoCloseable` `ExecutorService`, `Math.clamp`, `StringBuilder.repeat`, `String.splitWithDelimiters`) | — | 19–21 | [Before.java](../java-17-to-21/src/main/java/dev/javafeatures/jdk21/libraryimprovements/Before.java) | [After.java](../java-17-to-21/src/main/java/dev/javafeatures/jdk21/libraryimprovements/After.java) |

---

## Highlights im Detail

### 1. Pattern Matching for `switch` (JEP 441, JDK 21)
Ersetzt lange `if`/`else if`-Ketten mit `instanceof` durch ausdrucksstarke `switch`-Expressions inklusive Type Patterns, `case null`, Guarded Patterns (`when`) und Compiler-geprüfter Exhaustiveness bei `sealed` Hierarchien.
- **Beispiele:** [Before.java](../java-17-to-21/src/main/java/dev/javafeatures/jdk21/patternmatching/Before.java) → [After.java](../java-17-to-21/src/main/java/dev/javafeatures/jdk21/patternmatching/After.java)

### 2. Record Patterns (JEP 440, JDK 21)
Ermöglicht das direkte Dekonstruieren — auch beliebig verschachtelter — Records in `instanceof` und `switch`, ohne manuelle Accessor-Aufrufe und `null`-Ketten.
- **Beispiele:** [Before.java](../java-17-to-21/src/main/java/dev/javafeatures/jdk21/recordpatterns/Before.java) → [After.java](../java-17-to-21/src/main/java/dev/javafeatures/jdk21/recordpatterns/After.java)

### 3. Sequenced Collections (JEP 431, JDK 21)
Führt `SequencedCollection`, `SequencedSet` und `SequencedMap` ein und vereinheitlicht endlich den Zugriff auf erstes/letztes Element (`getFirst()`, `getLast()`, `firstEntry()`) sowie Reverse-Views (`reversed()`) über `List`, `LinkedHashSet` und `LinkedHashMap` hinweg.
- **Beispiele:** [Before.java](../java-17-to-21/src/main/java/dev/javafeatures/jdk21/sequencedcollections/Before.java) → [After.java](../java-17-to-21/src/main/java/dev/javafeatures/jdk21/sequencedcollections/After.java)

### 4. Virtual Threads (JEP 444, JDK 21)
Leichtgewichtige, von der JVM gemanagte Threads für hochskalierbaren, blockierenden I/O-Code im einfachen Thread-per-Request-Modell — ohne feste Platform-Thread-Pool-Engpässe und ohne komplexe Reactive-Chains.
- **Beispiele:** [Before.java](../java-17-to-21/src/main/java/dev/javafeatures/jdk21/virtualthreads/Before.java) → [After.java](../java-17-to-21/src/main/java/dev/javafeatures/jdk21/virtualthreads/After.java)

### 5. Core-Library-Verbesserungen (JDK 19–21)
Kleine, aber im Alltag ständig nützliche API-Upgrades: `ExecutorService` unterstützt `try-with-resources` (`AutoCloseable`), `Math.clamp(...)` ersetzt verschachteltes `min`/`max`, `StringBuilder.repeat(...)` spart Schleifen und `String.splitWithDelimiters(...)` erspart Lookaround-Regex.
- **Beispiele:** [Before.java](../java-17-to-21/src/main/java/dev/javafeatures/jdk21/libraryimprovements/Before.java) → [After.java](../java-17-to-21/src/main/java/dev/javafeatures/jdk21/libraryimprovements/After.java)

---

Weiter zu den **[Java 21 → Java 25 LTS Highlights](java-25-lts.md)** oder zurück zur **[README](../README.md)**.
